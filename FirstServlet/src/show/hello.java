package show;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//实现servlet接口成为一个servlet类需重写Servlet接口的所有方法
public class hello implements Servlet {

	@Override
	public void destroy() {
		System.out.println("销毁方法");
		
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// 初始化方法，初始化servlet数据，一个servlet中的init方法只会执行一次
		System.out.println("这是一个初始化方法");
		
	}

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		//使用http协议需将非http协议对象转换为http协议对象
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse) response;
		//获取一个输出对象，用于打印信息
		PrintWriter out=response.getWriter();
		//设置打印格式
		response.setContentType("text/html;charset=utf-8");
		//ͨ打印内容
		out.print("<html><head><title></title></head><body><h1>nbclass</h1></body></html>");
		out.flush();
		out.close();
	}

}
