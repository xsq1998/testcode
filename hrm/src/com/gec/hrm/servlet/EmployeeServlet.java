package com.gec.hrm.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.hrm.bean.Dept;
import com.gec.hrm.bean.Employee;
import com.gec.hrm.bean.Job;
import com.gec.hrm.bean.PageModel;
import com.gec.hrm.bean.User;
import com.gec.hrm.service.DeptService;
import com.gec.hrm.service.EmployeeService;
import com.gec.hrm.service.JobService;
import com.gec.hrm.service.serviceImpl.DeptServiceImpl;
import com.gec.hrm.service.serviceImpl.EmployeeServiceImpl;
import com.gec.hrm.service.serviceImpl.JobServiceImpl;
import com.google.gson.Gson;

@WebServlet({"/employeelist.action","/employeeadd.action","/queryemp.action","/employeedel.action","/addemployee.action",
	"/updateEmployee.action","/getcardid.action"})
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JobService js=new JobServiceImpl();
	DeptService ds=new DeptServiceImpl();
	EmployeeService es=new EmployeeServiceImpl();

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
		PageModel<Employee> pageModel = null;
		List<Employee> employeelist = null;
		List<Job> jobList=null;
		List<Dept> deptList=null;
		int pageIndex = 1;
		
		if (action.equals("employeelist.action")) {
			jobList=js.find();
			deptList=ds.find();
			employeelist=es.find();
			
			pageModel=es.findAll(pageIndex);
			System.out.println(pageModel);
			String now=request.getParameter("pageIndex");
			if (now!=null) {
				pageIndex=Integer.parseInt(now);
			}
			pageModel.setPageIndex(pageIndex);
			pageModel=es.findAll(pageIndex);
			pageModel.setPageIndex(pageIndex);
			
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("jobList", jobList);
			request.setAttribute("deptList", deptList);
			request.setAttribute("employeelist", employeelist);
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeelist.jsp").forward(request, response);
		}else if (action.equals("employeeadd.action")) {
			jobList=js.find();
			deptList=ds.find();
			employeelist=es.find();
			request.setAttribute("jobList", jobList);
			request.setAttribute("deptList", deptList);
			request.setAttribute("employeelist", employeelist);
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeadd.jsp").forward(request, response);
		}else if (action.equals("addemployee.action")) {
			String name=request.getParameter("name");
			String cardId=request.getParameter("cardId");
			String sex=request.getParameter("sex");
			String job_id=request.getParameter("job_id");
			String education=request.getParameter("education");
			String email=request.getParameter("email");
			String phone=request.getParameter("phone");
			String tel=request.getParameter("tel");
			String party=request.getParameter("party");
			String qqNum=request.getParameter("qqNum");
			String address=request.getParameter("address");
			String postCode=request.getParameter("postCode");
			String birthday=request.getParameter("birthday");
			String race=request.getParameter("race");
			String speciality=request.getParameter("speciality");
			String hobby=request.getParameter("hobby");
			String remark=request.getParameter("remark");
			String dept_id=request.getParameter("dept_id");
			
			Employee e=new Employee();
			e.setName(name);
			e.setCardId(cardId);
			if (sex!=null&&!("".equals(sex))) {
				e.setSex(Integer.parseInt(sex));
			}
			e.setJob(js.findById(Integer.parseInt(job_id)));
			e.setEducation(education);
			e.setEmail(email);
			e.setPhone(phone);
			e.setTel(tel);
			e.setParty(party);
			e.setQq(qqNum);
			e.setAddress(address);
			e.setPostCode(postCode);
			if (birthday!=null&&!("".equals(birthday))) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = sdf.parse(birthday);
					e.setBirthday(date);
				} catch (ParseException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
			e.setRace(race);
			e.setSpeciality(speciality);
			e.setHobby(hobby);
			e.setRemark(remark);
			if (dept_id!=null&&!("".equals(dept_id))) {
				e.setDept(ds.findById(Integer.parseInt(dept_id)));
			}
			e.setCreateDate(new Date());
			es.addEmployee(e);
			request.getRequestDispatcher("employeelist.action").forward(request, response);
		}
		else if (action.equals("queryemp.action")) {
			String jobid=request.getParameter("job_id");
			String name=request.getParameter("name");
			String cardId=request.getParameter("cardId");
			System.out.println(cardId);
			String sex=request.getParameter("sex");
			String phone=request.getParameter("phone");
			String deptid=request.getParameter("dept_id");
			
			Employee employee=new Employee();
			pageModel=es.findAll(pageIndex);
			pageModel.setPageIndex(pageIndex);
			String now =request.getParameter("pageIndex");
			if (now != null) {
				pageIndex = Integer.parseInt(now);
			}
			pageModel.setPageIndex(pageIndex);
			
			if (jobid!=null&&!("".equals(jobid))) {
				employee.setJob(js.findById(Integer.parseInt(jobid)));
			}
			if (name!=null&&!("".equals(name))) {
				employee.setName("%"+name+"%");
			}
			if (cardId!=null&&!("".equals(cardId))) {
				employee.setCardId("%"+cardId+"%");
			}
			if (sex!=null&&!("".equals(sex))) {
				employee.setSex(Integer.parseInt(sex));
			}
			if (phone!=null&&!("".equals(phone))) {
				employee.setPhone("%"+phone+"%");
			}
			if (deptid!=null&&!("".equals(deptid))) {
				employee.setDept(ds.findById(Integer.parseInt(deptid)));
			}
			pageModel=es.findByNameLike(pageIndex, employee);
			pageModel.setPageIndex(pageIndex);
			request.setAttribute("pageModel", pageModel);
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeelist.jsp").forward(request, response);
		}else if (action.equals("employeedel.action")) {
			PrintWriter out=response.getWriter();
			User user=(User) request.getSession().getAttribute("user_session");
			if (user.getStatus()==2) {
				String[] ids = request.getParameterValues("employees");// 要删除的文件id数组
				if (ids.length > 0) {
					for (String id : ids) {// 循环删除
						List<Object> list = new ArrayList<>();
						list.add(Integer.parseInt(id));
						es.delEmployee(list);
					}
				}
				out.print(1);
			}else {
				out.print(2);
			}
			
			request.getRequestDispatcher("employeelist.action").forward(request, response);
		}
		else if (action.equals("updateEmployee.action")) {//修改职员信息
			String flag=request.getParameter("flag");
			String id=request.getParameter("id");
			if ("1".equals(flag)) {
				Employee employee=es.findById(Integer.parseInt(id));
				List<Job> jobs=js.find();
				List<Dept> depts=ds.find();
				
				request.setAttribute("employee", employee);
				request.setAttribute("jobs", jobs);
				request.setAttribute("depts", depts);
				
				request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeedit.jsp").forward(request, response);
			}else {
				Employee employee=es.findById(Integer.parseInt(id));
				System.out.println("用户"+employee);
				String name=request.getParameter("name");
				employee.setName(name);
				String cardId=request.getParameter("cardId");
				employee.setCardId(cardId);
				
				String sex=request.getParameter("sex");
				if (sex!=null&&!("".equals(name))) {
					employee.setSex(Integer.parseInt(sex));
				}
				
				String job_id=request.getParameter("job_id");
				if (job_id!=null&&!("".equals(job_id))) {
					employee.setJob(js.findById(Integer.parseInt(job_id)));
				}
				
				String education=request.getParameter("education");
				employee.setEducation(education);
				String email=request.getParameter("email");
				employee.setEmail(email);
				String phone=request.getParameter("phone");
				employee.setPhone(phone);
				String tel=request.getParameter("tel");
				employee.setTel(tel);
				String party=request.getParameter("party");
				employee.setParty(party);
				String qqNum=request.getParameter("qqNum");
				employee.setQq(qqNum);
				String address=request.getParameter("address");
				employee.setAddress(address);
				String postCode=request.getParameter("postCode");
				employee.setPostCode(postCode);
				
				String birthday=request.getParameter("birthday");
				if (birthday!=null&&!("".equals(birthday))) {
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date=sdf.parse(birthday);
						employee.setBirthday(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
				String race=request.getParameter("race");
				employee.setRace(race);
				String speciality=request.getParameter("speciality");
				employee.setSpeciality(speciality);
				String hobby=request.getParameter("hobby");
				employee.setHobby(hobby);
				String remark=request.getParameter("remark");
				employee.setRemark(remark);
				
				String dept_id=request.getParameter("dept_id");
				if (dept_id!=null&&!("".equals(dept_id))) {
					employee.setDept(ds.findById(Integer.parseInt(dept_id)));
				}
				
				es.editEmployee(employee);
				request.getRequestDispatcher("employeelist.action").forward(request, response);
			}
			
		}else if (action.equals("getcardid.action")) {
			String cardId = request.getParameter("cardId");
			System.out.println(cardId);

			// 获取json数据
			String json = getJsonData(request);
			// 解释json数据
			// {"cardId":"001"}
			// Gson工具类（开源类）
			Gson gson = new Gson();
			// 将json数据解释到员工的javabean属性中
			Employee employee = gson.fromJson(json, Employee.class);
			System.out.println("cardid=" + employee.getCardId());
			// 根据cardid检索员工表
/*			PrintWriter writer=response.getWriter();
			boolean isExits=es.findByCardId(employee.getCardId());
			if (isExits) {
				System.out.println(employee.getCardId());
				writer.print(employee.getCardId());
			}else {
				writer.print(1);
				System.out.println(1);
			}
			writer.flush();
			writer.close();*/
			boolean isEnable = es.findByCardId(employee.getCardId());

			String resultJon = gson.toJson(isEnable);
			System.out.println("resultJon=" + resultJon);
			// 以json数据格式作响应
			response.setContentType("application/json; charset=utf-8");
			
			
			OutputStream out = response.getOutputStream();
			
			out.write(resultJon.getBytes());
			
			out.flush();

		}
		
	}
	public String getJsonData(HttpServletRequest request) {
		// 获取请求体正文
		StringBuffer jsonBuf = new StringBuffer();
		try {
			BufferedReader br = request.getReader();
			String json = null;
			while ((json = br.readLine()) != null) {
				jsonBuf.append(json);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return jsonBuf.toString();
	}

}
