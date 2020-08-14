package com.gec.hrm.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gec.hrm.bean.Document;
import com.gec.hrm.bean.PageModel;
import com.gec.hrm.bean.User;
import com.gec.hrm.service.DocumentService;
import com.gec.hrm.service.serviceImpl.DocumentServiceImpl;

@WebServlet({ "/documentlist.action", "/documentadd.action", "/documentaddsave.action", "/queryDoc.action",
		"/removeDocument.action", "/updateDocument.action","/documentdownload.action"})
public class DocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DocumentService ds = new DocumentServiceImpl();

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
		Document document = new Document();
		PageModel<Document> pageModel = null;
		List<Document> documentlist = null;
		int pageIndex = 1;

		if (action.equals("documentlist.action")) {
			documentlist = ds.find();
			pageModel = ds.findAll(pageIndex);
			String now = request.getParameter("pageIndex");
			if (now != null) {
				pageIndex = Integer.parseInt(now);
			}
			pageModel.setPageIndex(pageIndex);
			pageModel = ds.findAll(pageIndex);
			pageModel.setPageIndex(pageIndex);
			request.setAttribute("pageModel", pageModel);
			request.setAttribute("documentlist", documentlist);

			request.getRequestDispatcher("/WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
		} else if (action.equals("documentadd.action")) {
			request.getRequestDispatcher("/WEB-INF/jsp/document/documentadd.jsp").forward(request, response);
		} else if (action.equals("documentaddsave.action")) {
			boolean flag = false;// 判断上传是否成功
			boolean isMulitipart = ServletFileUpload.isMultipartContent(request);
			if (isMulitipart) {
				FileItemFactory factory = new DiskFileItemFactory();// 创建工厂对象
				ServletFileUpload upload = new ServletFileUpload(factory);// 构建工厂保存数据

				try {
					// 通过工厂解析request中所有的数据,存储到FileItem集合中
					List<FileItem> list = upload.parseRequest(request);
					if (list != null) {
						// 循环集合,将每一项内容获取出来
						for (FileItem fileItem : list) {
							// 判断每一项数据中是否为普通数据 isformField判断 true表示普通数据
							if (fileItem.isFormField()) {// 读取普通字符属性数据
								if ("title".equals(fileItem.getFieldName())) {
									document.setTitle(fileItem.getString("utf-8"));
								}
								if ("remark".equals(fileItem.getFieldName())) {
									document.setRemark(fileItem.getString("utf-8"));
								}
							} else {// 不是普通数据
									// 获取上传的文件
									// 获取到上传文件需要存放的真实路径
								String path = request.getServletContext().getRealPath("/upload");
								System.out.println(path);

								File file = new File(path);
								if (!file.exists()) {
									// 若文件不存在，mkdirs()会根据路径创建文件和文件夹，mkdir()若路径中的文件夹不存在则不会新建
									file.mkdirs();
								}
								// 获取到文件名
								String fileName = fileItem.getName();

								int index = fileName.lastIndexOf(".");// 获得文件名中“.”的下标
								String fileType = fileName.substring(index + 1);// 获得文件后缀

								// 在upload文件夹下创建一个新的文件
								// fileName=fileName.substring(0,index)+System.currentTimeMillis()
								// +fileName.substring(index);

								document.setFileName(fileName);// 设置文件名
								document.setFileType(fileType);// 设置文件类型

								File newFile = new File(file, fileName);// file为需要上传文件，上传后的文件的文件名为fileName
								// write方法用于将FileItem对象中保存的主体内容保存到指定的newFile文件中
								fileItem.write(newFile);
								document.setFileBytes(file.length());// 设置文件大小
								System.out.println("上传中..........");

							}
						}
						flag = true;// 上传成功
						document.setCreateDate(new Date());// 设置文件创建时间
						User user = (User) request.getSession().getAttribute("user_session");// 从session中获取用户
						System.out.println("上传者：" + user.getLoginname() + "..........");

						document.setUser(user);// 设置上传者

					} else {
						System.err.println("数据异常！");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {// 非二进制获取，普通表单获取
				String title = request.getParameter("title");
				String remark = request.getParameter("remark");
				document.setTitle(title);
				document.setRemark(remark);
			}
			ds.uploadFile(document);

			if (flag) {
				System.out.println("上传成功！");
				request.getRequestDispatcher("documentlist.action").forward(request, response);
			} else {
				request.getRequestDispatcher("/WEB-INF/jsp/document/documentadd.jsp").forward(request, response);
			}
		} else if (action.equals("queryDoc.action")) {
			documentlist = ds.find();
			pageModel = ds.findAll(pageIndex);
			pageModel.setPageIndex(pageIndex);
			String title = request.getParameter("title");
			Integer now = Integer.parseInt(request.getParameter("pageIndex"));
			if (now != null) {
				pageIndex = now;
			}

			document = new Document();
			if (title != null && !("").equals(title)) {
				document.setTitle("%"+title+"%");
			}
			pageModel = ds.findByTitleLike(pageIndex, document);
			pageModel.setPageIndex(pageIndex);
			request.setAttribute("pageModel", pageModel);
			request.getRequestDispatcher("/WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
		} else if (action.equals("removeDocument.action")) {
			PrintWriter out=response.getWriter();
			User user=(User) request.getSession().getAttribute("user_session");
			if (user.getStatus()==2) {
				String str = request.getParameter("ids");// 要删除的文件id数组
				String[] ids =str.split(",");
				System.out.println(ids.length+".......");
				if (ids.length > 0) {
					for (String id : ids) {// 循环删除
						List<Object> list = new ArrayList<>();
						list.add(Integer.parseInt(id));
						ds.delDoc(list);
					}
				}
				out.print(1);
			}else {
				out.print(2);
			}
			
			request.getRequestDispatcher("documentlist.action").forward(request, response);
		} else if (action.equals("updateDocument.action")) {
			String flag = request.getParameter("flag");
			String id = request.getParameter("id");
			int id1 = Integer.parseInt(id);

			System.out.println(flag + "......" + id + ">>>>>>>");
			if ("1".equals(flag)) {// 从文件列表点击修改
				Document doc = ds.findById(id1);
				request.setAttribute("document", doc);
				request.getRequestDispatcher("/WEB-INF/jsp/document/showUpdateDocument.jsp").forward(request, response);
			} else {// 从修改页面返回
				Document doc=ds.findById(id1);
				System.out.println("修改上传文件......");
				boolean isSuccess=false;
				
				boolean isMulitipart = ServletFileUpload.isMultipartContent(request);
				if (isMulitipart) {
					FileItemFactory factory = new DiskFileItemFactory();// 创建工厂对象
					ServletFileUpload upload = new ServletFileUpload(factory);// 构建工厂保存数据
					
					try {
						// 通过工厂解析request中所有的数据,存储到FileItem集合中
						List<FileItem> list = upload.parseRequest(request);
						if (list != null) {
							// 循环集合,将每一项内容获取出来
							for (FileItem fileItem : list) {
								// 判断每一项数据中是否为普通数据 isformField判断 true表示普通数据
								if (fileItem.isFormField()) {// 读取普通字符属性数据
									
									if ("title".equals(fileItem.getFieldName())) {
										doc.setTitle(fileItem.getString("utf-8"));
									}
									if ("remark".equals(fileItem.getFieldName())) {
										doc.setRemark(fileItem.getString("utf-8"));
									}
								} else {// 不是普通数据
										// 获取上传的文件
										// 获取到上传文件需要存放的真实路径
									String path = request.getServletContext().getRealPath("upload");
									System.out.println(path);

									File file = new File(path);
									if (!file.exists()) {
										// 若文件不存在，mkdirs()会根据路径创建文件和文件夹，mkdir()若路径中的文件夹不存在则不会新建
										file.mkdirs();
									}
									// 获取到文件名
									String fileName = fileItem.getName();
									System.out.println(fileName);

									int index = fileName.lastIndexOf(".");// 获得文件名中“.”的下标
									String fileType = fileName.substring(index + 1);// 获得文件后缀

									// 在upload文件夹下创建一个新的文件
									// fileName=fileName.substring(0,index)+System.currentTimeMillis()
									// +fileName.substring(index);

									doc.setFileName(fileName);// 设置文件名
									doc.setFileType(fileType);// 设置文件类型

									File newFile = new File(file, fileName);// file为需要上传文件，上传后的文件的文件名为fileName
									// write方法用于将FileItem对象中保存的主体内容保存到指定的newFile文件中
									fileItem.write(newFile);
									doc.setFileBytes(file.length());// 设置文件大小
									System.out.println("上传中..........");

								}
							}
							isSuccess=true;//上传成功

						} else {
							System.err.println("数据异常！");
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {// 非二进制获取，普通表单获取
					String title = request.getParameter("title");
					String remark = request.getParameter("remark");
					doc.setTitle(title);
					doc.setRemark(remark);
				}
				if (isSuccess) {
					System.out.println("文件上传成功！");
				}
				boolean f=ds.editDoc(doc);
				if (f) {
					System.out.println("文件修改成功！");
					request.getRequestDispatcher("documentlist.action").forward(request, response);
				}else {
					System.err.println("修改失败！");
					request.getRequestDispatcher("updateDocument.action").forward(request, response);
				}
				
			}
		}else if (action.equals("documentdownload.action")) {
			int id=Integer.parseInt(request.getParameter("id"));
			Document d=ds.findById(id);
			
			String path = request.getServletContext().getRealPath("/upload");
			
			String fileName = d.getFileName();
			System.out.println(fileName);
			
			//文件输入流读取文件内容
			InputStream in = new FileInputStream(path+File.separator+fileName);//注意separator！在不同浏览器通用
			//设置浏览器下载模式
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"),"ISO8859-1"));
			//response.setHeader("Content-Disposition", "attachment;filename="+fileName);
			//创建一个输出流
			ServletOutputStream out = response.getOutputStream();
			//从输出流中将内容输出到客户端
			int len = 0;
			byte[] by = new byte[1024];
			while((len=in.read(by))>0){
				out.write(by, 0, len);
			}
			//关闭流
			out.flush();
			out.close();
		}
	}

}
