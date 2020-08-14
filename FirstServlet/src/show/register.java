package show;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class register extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@Override
	public void init() throws ServletException {
		// TODO 自动生成的方法存根
		System.out.println("初始化方法");
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO 自动生成的方法存根
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out=resp.getWriter();
		out.print("<html><head><title></title></head><body>"
				+ "<form action=registerServlet method=post >" + 
				"<p>用户名:<input type=text name=account /></p>" + 
				"<p>密码：<input type=password name=pwd /></p>" + 
				"<p><input type=submit value='注册' ></p>" + 
				"</form></body></html>");
		out.flush();
		out.close();
	}
}
