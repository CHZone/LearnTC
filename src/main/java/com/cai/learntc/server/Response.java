package com.cai.learntc.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Response {
	private static final int BUFFER_SIZE = 1024;
	private OutputStream outputStream;
	private Request request;

	public Response(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public void sendStaticResources() {
		byte[] buff = new byte[BUFFER_SIZE];
		FileInputStream input = null;
		try {
			File file = new File(HttpServer.WEB_ROOT + File.separator + request.getUri());
			sendHead(outputStream);
			if (file.exists()) {
				input = new FileInputStream(file);
				int ch = input.read(buff, 0, BUFFER_SIZE);
				while (ch != -1) {
					outputStream.write(buff, 0, ch);
					ch = input.read(buff, 0, BUFFER_SIZE);
				}
			} else {
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n" 	
						+ "Content-Type:text/html"
						+ "Content-Length:23"
						+ "\r\n" 
						+ "<h1>File Not Found</h1>";
				outputStream.write(errorMessage.getBytes());
			}
//			outputStream.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			try {
				if (input != null) {
					input.close();
//					outputStream.close();// 漏了这句导致response数据没有发出去。
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private String getGMTDateTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'");
		Date curDate = new Date();
		return sdf.format(curDate);
	}
	
	private void sendHead(OutputStream outputStream) {
		String header = "HTTP/1.1 200 OK\r\n"
				+ "Server: tomedog" 
				+ "Date: "+getGMTDateTime()+"\r\n"
				+ "Content-Type: text/html;charset=UTF-8\r\n" 
				+"\r\n";
		try {
			outputStream.write(header.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
