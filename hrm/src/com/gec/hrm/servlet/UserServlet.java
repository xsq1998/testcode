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
import com.gec.hrm.bean.User;
import com.gec.hrm.service.UserService;
import com.gec.hrm.service.serviceImpl.UserServiceImpl;

@WebServlet({ "/userlist.action", "/useradd.action", "/useraddsave.action", "/queryUser.action", "/userdel.action",
		"/viewUser.action", "/userupdate.action","/useredit.action" })
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService us = new UserServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> userlist = null;// 展示在用户查询页的用户集合
		PageModel<User> pageModel=null;
		int pageIndex=1;

		// 获取到请求路径
		String uri = request.getRequestURI();
		// 截取请求名
		String action = uri.substring(uri.lastIndexOf("/") + 1);
		if (action.equals("userlist.action")) {
			userlist = us.find();
			pageModel=us.findAll(pageIndex);
			String now=request.getParameter("pageIndex");
			if (now!=null) {
				pageIndex=Integer.parseInt(now);
			}
			pageModel.setPageIndex(pageIndex);
			pageModel=us.findAll(pageIndex);
			pageModel.setPageIndex(pageIndex);
			request.setAttribute("pageModel", pageModel);
			
			request.setAttribute("userlist", userlist);// 获取到所有用户数据，传值
			request.getRequestDispatcher("/WEB-INF/jsp/user/userlist.jsp").forward(request, response);
		} else if (action.equals("useradd.action")) {
			request.getRequestDispatcher("/WEB-INF/jsp/user/useradd.jsp").forward(request, response);
		} else if (action.equals("useraddsave.action")) {
			String username = request.getParameter("username");
			int status = Integer.valueOf(request.getParameter("status"));
			String loginname = request.getParameter("loginname");
			String password = request.getParameter("password");
			System.out.println(username + "," + status + "," + loginname + "," + password);

			if (us.findByLoginName(loginname) == null) {
				System.out.println("注册中............");
				User u = new User();
				u.setUsername(username);
				u.setStatus(status);
				u.setPassword(password);
				u.setLoginname(loginname);
				u.setCreateDate(new Date());
				us.addUser(u);
				// request.setAttribute("addMsg", "添加成功！");
				request.getRequestDispatcher("userlist.action").forward(request, response);

			} else {
				// request.setAttribute("addMsg", "添加失败！登录名已存在！");
			}
		} else if (action.equals("queryUser.action")) {
			String loginname = request.getParameter("loginname");
			String username = request.getParameter("username");
			String status = request.getParameter("status");
			Integer now=Integer.parseInt(request.getParameter("pageIndex"));
			if (now!=null) {
				pageIndex=now;
			}
			User user=new User();
			if (loginname!=null&&!("").equals(loginname)) {
				user.setLoginname("%"+loginname+"%");
			}else if (username!=null&&!("".equals(username))) {
				user.setUsername("%"+username+"%");
			}else if (status!=null&&!("".equals(status))) {
				int s=Integer.parseInt(status);
				user.setStatus(s);
			}else {
				pageModel=us.findAll(pageIndex);
			}
			pageModel=us.findByNameLike(pageIndex, user);
			request.setAttribute("pageModel", pageModel);
			//request.setAttribute("userlist", userlist);// 获取到所有用户数据，传值
			request.getRequestDispatcher("/WEB-INF/jsp/user/userlist.jsp").forward(request, response);

		} else if (action.equals("userdel.action")) {// 点击删除
			PrintWriter out=response.getWriter();
			User user=(User) request.getSession().getAttribute("user_session");
			int status = user.getStatus();
			System.out.println(status);
			if (status==2) {// 管理员权限
				String[] str = request.getParameterValues("userIds");
				if (str.length > 0) {
					for (String uid : str) {
						List<Object> list = new ArrayList<>();
						list.add(Integer.parseInt(uid));
						us.delUser(list);
					}
				}
				out.print(1);
			}else {
				out.print(2);
			}
			request.getRequestDispatcher("userlist.action").forward(request, response);

		} else if (action.equals("viewUser.action")) {// 点击修改
			String id = request.getParameter("id");
			User user=us.findById(Integer.parseInt(id));
			request.setAttribute("user", user);
			request.getRequestDispatcher("/WEB-INF/jsp/user/useredit.jsp").forward(request, response);
		}else if (action.equals("useredit.action")) {
			PrintWriter out=response.getWriter();
			User user=(User) request.getSession().getAttribute("user_session");//获取当前用户
			String id=request.getParameter("id");
			int nowSt = user.getStatus();
				if (nowSt==2) {
					String loginname = request.getParameter("loginname");
					String pwd = request.getParameter("password");
					String status = request.getParameter("status");
					String username = request.getParameter("username");
					us.updateUser(loginname, status, username, pwd, Integer.parseInt(id));
					out.print(1);
				}else {
					out.print(2);
				}
				request.getRequestDispatcher("userlist.action").forward(request, response);

		} 

	}

}
