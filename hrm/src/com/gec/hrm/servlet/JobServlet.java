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

import com.gec.hrm.bean.Job;
import com.gec.hrm.bean.PageModel;
import com.gec.hrm.bean.User;
import com.gec.hrm.service.JobService;
import com.gec.hrm.service.serviceImpl.JobServiceImpl;

@WebServlet({"/selectJob.action","/addJob.action","/joblist.action","/queryjob.action",
	"/deljob.action","/viewJob.action","/jobaddsave.action","/jobedit.action"})
public class JobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JobService js=new JobServiceImpl();

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
		PageModel<Job> pageModel = null;
		List<Job> joblist = null;
		int pageIndex = 1;
		
		
		if (action.equals("selectJob.action")||action.equals("joblist.action")) {
			joblist = js.find();
			pageModel = js.findAll(pageIndex);
			String now = request.getParameter("pageIndex");
			if (now != null) {
				pageIndex = Integer.parseInt(now);
			}
			pageModel.setPageIndex(pageIndex);
			pageModel = js.findAll(pageIndex);
			pageModel.setPageIndex(pageIndex);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("deptlist", joblist);
			request.getRequestDispatcher("/WEB-INF/jsp/job/joblist.jsp").forward(request, response);
		}else if (action.equals("addJob.action")) {
			request.getRequestDispatcher("/WEB-INF/jsp/job/jobadd.jsp").forward(request, response);
		}else if (action.equals("queryjob.action")) {
			joblist = js.find();
			pageModel = js.findAll(pageIndex);
			pageModel.setPageIndex(pageIndex);
			String name = request.getParameter("name");
			Integer now = Integer.parseInt(request.getParameter("pageIndex"));
			if (now != null) {
				pageIndex = now;
			}
			pageModel.setPageIndex(pageIndex);
			Job job=new Job();
			if (name != null && !("").equals(name)) {
				job.setName("%"+name+"%");
			}
			pageModel = js.findByNameLike(pageIndex, job);
			pageModel.setPageIndex(pageIndex);
			request.setAttribute("pageModel", pageModel);
			request.getRequestDispatcher("/WEB-INF/jsp/job/joblist.jsp").forward(request, response);
		}else if (action.equals("deljob.action")) {
			PrintWriter out =response.getWriter();
			User user=(User) request.getSession().getAttribute("user_session");
			if (user.getStatus()==2) {
				String[] ids = request.getParameterValues("jobIds");// 要删除的文件id数组
				if (ids.length > 0) {
					for (String id : ids) {// 循环删除
						List<Object> list = new ArrayList<>();
						list.add(Integer.parseInt(id));
						js.delJob(list);
					}
				}
				out.print(1);
			}else {
				out.print(2);
			}
			request.getRequestDispatcher("joblist.action").forward(request, response);
		}else if (action.equals("viewJob.action")) {
			String id=request.getParameter("id");
			Job job=js.findById(Integer.parseInt(id));
			request.setAttribute("job", job);
			request.getRequestDispatcher("/WEB-INF/jsp/job/jobedit.jsp").forward(request, response);
		}else if (action.equals("jobedit.action")) {
			String id=request.getParameter("id");
			String name=request.getParameter("name");
			String remark=request.getParameter("remark");
			Job job=js.findById(Integer.parseInt(id));
			job.setName(name);
			job.setRemark(remark);
			js.editJob(job);
			request.getRequestDispatcher("joblist.action").forward(request, response);
		}else if (action.equals("jobaddsave.action")) {
			String name=request.getParameter("name");
			String remark=request.getParameter("remark");
			Job job=new Job();
			job.setName(name);
			job.setRemark(remark);
			js.addJob(job);
			request.getRequestDispatcher("joblist.action").forward(request, response);
		}
	}

}
