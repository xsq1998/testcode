package com.gec.hrm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.hrm.bean.Dept;
import com.gec.hrm.bean.PageModel;
import com.gec.hrm.bean.User;
import com.gec.hrm.service.DeptService;
import com.gec.hrm.service.serviceImpl.DeptServiceImpl;

@WebServlet({ "/selectDept.action", "/addDept.action", "/deptlist.action", "/querydept.action", "/deptdel.action",
		"/viewDept.action", "/saveOrUpdate.action" })
public class DeptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DeptService ds = new DeptServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取到请求路径
		String uri = request.getRequestURI();
		// 截取请求名
		String action = uri.substring(uri.lastIndexOf("/") + 1);
		Dept dept = new Dept();
		PageModel<Dept> pageModel = null;
		List<Dept> deptlist = null;
		int pageIndex = 1;

		if (action.equals("selectDept.action") || action.equals("deptlist.action")) {
			deptlist = ds.find();
			pageModel = ds.findAll(pageIndex);
			String now = request.getParameter("pageIndex");
			if (now != null) {
				pageIndex = Integer.parseInt(now);
			}
			pageModel.setPageIndex(pageIndex);
			pageModel = ds.findAll(pageIndex);
			pageModel.setPageIndex(pageIndex);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("deptlist", deptlist);
			request.getRequestDispatcher("/WEB-INF/jsp/dept/deptlist.jsp").forward(request, response);
		} else if (action.equals("addDept.action")) {
			request.getRequestDispatcher("/WEB-INF/jsp/dept/deptedit.jsp").forward(request, response);
		} else if (action.equals("querydept.action")) {
			deptlist = ds.find();
			pageModel = ds.findAll(pageIndex);
			pageModel.setPageIndex(pageIndex);
			String name = request.getParameter("name");
			Integer now = Integer.parseInt(request.getParameter("pageIndex"));
			if (now != null) {
				pageIndex = now;
			}
			dept = new Dept();
			if (name != null && !("").equals(name)) {
				dept.setName("%"+name+"%");
			}
			pageModel = ds.findByNameLike(pageIndex, dept);
			pageModel.setPageIndex(pageIndex);
			request.setAttribute("pageModel", pageModel);
			request.getRequestDispatcher("/WEB-INF/jsp/dept/deptlist.jsp").forward(request, response);
		} else if (action.equals("deptdel.action")) {
			PrintWriter out=response.getWriter();
			User user=(User) request.getSession().getAttribute("user_session");
			if (user.getStatus()==2) {
				String[] ids = request.getParameterValues("deptIds");// 要删除的文件id数组
				if (ids.length > 0) {
					for (String id : ids) {// 循环删除
						List<Object> list = new ArrayList<>();
						list.add(Integer.parseInt(id));
						ds.delDept(list);
					}
				}
				out.print(1);
			}else {
				out.print(2);
			}
			
			request.getRequestDispatcher("deptlist.action").forward(request, response);
		} else if (action.equals("viewDept.action")) {
			String id = request.getParameter("id");
			Dept d = ds.findById(Integer.parseInt(id));
			request.setAttribute("dept", d);
			request.getRequestDispatcher("/WEB-INF/jsp/dept/deptedit.jsp").forward(request, response);

		} else if (action.equals("saveOrUpdate.action")) {
			boolean flag = false;
			String name = request.getParameter("name");
			String remark = request.getParameter("remark");
			List<Dept> list = ds.find();
			for (Dept dp : list) {
				if (dp.getName().equals(name)) {//修改
					int id = Integer.parseInt(request.getParameter("id"));
					Dept d = ds.findById(id);
					d.setRemark(remark);
					ds.edit(d);
					flag = true;
					break;
				}
			}
			if (!flag) {//新建
				Dept d=new Dept();
				d.setName(name);
				d.setRemark(remark);
				ds.add(d);
			}

			request.getRequestDispatcher("deptlist.action").forward(request, response);
		}
	}

}
