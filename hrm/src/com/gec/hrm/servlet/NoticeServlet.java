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

import com.gec.hrm.bean.Notice;
import com.gec.hrm.bean.PageModel;
import com.gec.hrm.bean.Type;
import com.gec.hrm.bean.User;
import com.gec.hrm.service.NoticeService;
import com.gec.hrm.service.TypeService;
import com.gec.hrm.service.serviceImpl.NoticeServiceImpl;
import com.gec.hrm.service.serviceImpl.TypeServiceImpl;

@WebServlet({"/selectNotice.action","/addNotice.action","/noticelist.action","/noticesaveOrUpdate.action",
	"/queryNotice.action","/noticedel.action","/viewNotice.action"})
public class NoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	NoticeService ns=new NoticeServiceImpl();
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
		
		PageModel<Notice> pageModel=null;
		List<Notice> noticelist=null;
		int pageIndex=1;
		if (action.equals("selectNotice.action")||action.equals("noticelist.action")) {
			noticelist = ns.find();
			pageModel = ns.findAll(pageIndex);
			String now = request.getParameter("pageIndex");
			if (now != null) {
				pageIndex = Integer.parseInt(now);
			}
			pageModel.setPageIndex(pageIndex);
			pageModel = ns.findAll(pageIndex);
			pageModel.setPageIndex(pageIndex);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("deptlist", noticelist);
			
			request.getRequestDispatcher("/WEB-INF/jsp/notice/noticelist.jsp").forward(request, response);
		}else if (action.equals("addNotice.action")) {
			List<Type> types=ts.find();
			request.setAttribute("types", types);
			request.getRequestDispatcher("/WEB-INF/jsp/notice/notice_save_update.jsp").forward(request, response);
		}else if (action.equals("noticesaveOrUpdate.action")) {
			String id=request.getParameter("id");
			
			if (id==null||"".equals(id)) {//添加公告
				System.out.println("添加公告");
				String name=request.getParameter("name");
				String typeid=request.getParameter("type_id");
				String text=request.getParameter("text");
				Notice notice=new Notice();
				notice.setName(name);
				notice.setType(ts.findById(Integer.parseInt(typeid)));
				notice.setContent(text);
				User user=(User) request.getSession().getAttribute("user_session");// 从session中获取用户
				System.out.println("用户："+user);
				ns.addNotice(notice,user);
				request.getRequestDispatcher("noticelist.action").forward(request, response);
			}else if(id!=null&&!("".equals(id))){//修改
				String name=request.getParameter("name");
				String typeid=request.getParameter("type_id");
				String text=request.getParameter("text");
				Notice notice=ns.findById(Integer.parseInt(id));
				notice.setName(name);
				notice.setType(ts.findById(Integer.parseInt(typeid)));
				notice.setContent(text);
				ns.editNotice(notice);
				request.getRequestDispatcher("noticelist.action").forward(request, response);
			}
		}else if (action.equals("queryNotice.action")) {
			noticelist = ns.find();
			pageModel = ns.findAll(pageIndex);
			pageModel.setPageIndex(pageIndex);
			String name = request.getParameter("name");
			Integer now = Integer.parseInt(request.getParameter("pageIndex"));
			if (now != null) {
				pageIndex = now;
			}
			pageModel.setPageIndex(pageIndex);
			Notice notice=new Notice();
			if (name != null && !("").equals(name)) {
				notice.setName(name);
			}
			pageModel = ns.findByNameLike(pageIndex, notice);
			pageModel.setPageIndex(pageIndex);
			request.setAttribute("pageModel", pageModel);
			request.getRequestDispatcher("/WEB-INF/jsp/notice/noticelist.jsp").forward(request, response);
		}else if (action.equals("noticedel.action")) {
			PrintWriter out=response.getWriter();
			User user=(User) request.getSession().getAttribute("user_session");
			if (user.getStatus()==2) {
				String[] noticeIds = request.getParameterValues("noticeIds");// 要删除的文件id数组
				if (noticeIds.length > 0) {
					for (String id : noticeIds) {// 循环删除
						List<Object> list = new ArrayList<>();
						list.add(Integer.parseInt(id));
						ns.delNotice(list);
					}
				}
				out.print(1);
			}else {
				out.print(2);
			}
			
			request.getRequestDispatcher("noticelist.action").forward(request, response);
		}else if (action.equals("viewNotice.action")) {
			String id=request.getParameter("id");
			Notice notice=ns.findById(Integer.parseInt(id));
			request.setAttribute("notice", notice);
			List<Type> types=ts.find();
			request.setAttribute("types", types);
			request.getRequestDispatcher("/WEB-INF/jsp/notice/notice_save_update.jsp").forward(request, response);
		}
	}

}
