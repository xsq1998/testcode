package com.gec.hrm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.hrm.bean.PageModel;
import com.gec.hrm.bean.Type;
import com.gec.hrm.bean.User;
import com.gec.hrm.service.TypeService;
import com.gec.hrm.service.serviceImpl.TypeServiceImpl;

@WebServlet({"/typelist.action","/addtype.action","/typesaveOrUpdate.action","/viewType.action",
	"/querytype.action","/typedel.action"})
public class TypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TypeService ts=new TypeServiceImpl();

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
		PageModel<Type> pageModel = null;
		List<Type> typelist = null;
		int pageIndex = 1;
		if (action.equals("typelist.action")) {
			typelist = ts.find();
			pageModel = ts.findAll(pageIndex);
			String now = request.getParameter("pageIndex");
			if (now != null) {
				pageIndex = Integer.parseInt(now);
			}
			pageModel.setPageIndex(pageIndex);
			pageModel = ts.findAll(pageIndex);
			pageModel.setPageIndex(pageIndex);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("typelist", typelist);
			
			request.getRequestDispatcher("/WEB-INF/jsp/notice/typelist.jsp").forward(request, response);
			
		}else if (action.equals("addtype.action")) {
			request.getRequestDispatcher("/WEB-INF/jsp/notice/type_save_update.jsp").forward(request, response);
		}else if (action.equals("viewType.action")) {
			String id=request.getParameter("id");
			Type type=ts.findById(Integer.parseInt(id));
			request.setAttribute("type", type);
			request.getRequestDispatcher("/WEB-INF/jsp/notice/type_save_update.jsp").forward(request, response);
		}
		else if (action.equals("typesaveOrUpdate.action")) {
			String id=request.getParameter("id");
			System.out.println(id);
			if (id==null||("".equals(id))) {
				Type type=new Type();
				String name=request.getParameter("name");
				User user=(User) request.getSession().getAttribute("user_session");
				System.out.println("添加公告类型");
				type.setName(name);
				type.setCreateDate(new Date());
				type.setUser(user);
				type.setModifyDate(new Date());
				ts.addType(type);
			}else if (id!=null&&!("".equals(id))) {
				Type type=ts.findById(Integer.parseInt(id));
				String name=request.getParameter("name");
				User user=(User) request.getSession().getAttribute("user_session");
				System.out.println("修改公告类型");
				type.setName(name);
				type.setModifyDate(new Date());
				type.setUser(user);
				boolean flag=ts.editType(type);
				System.out.println(flag);
			}
			request.getRequestDispatcher("typelist.action").forward(request, response);
		}else if (action.equals("querytype.action")) {
			typelist = ts.find();
			pageModel = ts.findAll(pageIndex);
			pageModel.setPageIndex(pageIndex);
			String name = request.getParameter("name");
			Integer now = Integer.parseInt(request.getParameter("pageIndex"));
			if (now != null) {
				pageIndex = now;
			}
			pageModel.setPageIndex(pageIndex);
			Type type=new Type();
			if (name != null && !("").equals(name)) {
				type.setName(name);
			}
			pageModel = ts.findByNameLike(pageIndex, type);
			pageModel.setPageIndex(pageIndex);
			request.setAttribute("pageModel", pageModel);
			request.getRequestDispatcher("/WEB-INF/jsp/notice/typelist.jsp").forward(request, response);
		}else if (action.equals("typedel.action")) {
			PrintWriter out=response.getWriter();
			User user=(User) request.getSession().getAttribute("user_session");
			if (user.getStatus()==2) {
				String[] typeIds = request.getParameterValues("typeIds");// 要删除的文件id数组
				if (typeIds.length > 0) {
					for (String id : typeIds) {// 循环删除
						List<Object> list = new ArrayList<>();
						list.add(Integer.parseInt(id));
						ts.delType(list);
					}
				}
				out.print(1);
			}else {
				out.print(2);
			}
			
			request.getRequestDispatcher("typelist.action").forward(request, response);
		}
	}

}
