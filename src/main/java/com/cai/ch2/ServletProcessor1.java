package com.cai.ch2;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;

import com.cai.ch1.ResponseStateEnum;

public class ServletProcessor1 {
	public void process(Request request, Response response){
		String uri = request.getUri();
		int index = uri.lastIndexOf('/');
		String servletName = uri.substring(index + 1);
		URLClassLoader urlClassLoader = null;
		try{
			URL[] urls = new URL[1];
			URLStreamHandler urlStreamHandler = null;
			File classPath = new File(Constants.WEB_ROOT);
			String repository = (new URL("file", null, classPath.getCanonicalPath()+File.separator)).toString();
			urls[0] = new URL(null, repository, urlStreamHandler);
			urlClassLoader = new URLClassLoader(urls);
		}catch (IOException e) {
			System.out.println(e.toString());
		}
		
		Class servletClass = null;
		try{
			servletClass = urlClassLoader.loadClass(servletName);
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
		
		Servlet servlet = null;
		try{
			servlet = (Servlet) servletClass.newInstance();
			// 为了浏览器能够显示数据，需要header
			// 这种做法不正规, 临时测试用
			response.sendResponseLineAndHeader(ResponseStateEnum.OK);
			
			servlet.service(request, response);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
	}
}
