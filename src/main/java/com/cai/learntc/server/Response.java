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
			if (file.exists()) {
				input = new FileInputStream(file);
				int ch = input.read(buff, 0, BUFFER_SIZE);
				sendHead(ResponseStateEnum.OK);
				while (ch != -1) {
					outputStream.write(buff, 0, ch);
					ch = input.read(buff, 0, BUFFER_SIZE);
				}
			} else {
				sendHead(ResponseStateEnum.FILE_NOT_FOUND);
				String errorMessage = "<html>"
						+ "<head>"
						+ "</head>"
						+ "<body>"
						+ "<h1>File Not Found</h1>"
						+ "</body>"
						+ "</html>";
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
	
	private void sendHead(ResponseStateEnum statusEnum ) {
		String header = "HTTP/1.1 "
				+ statusEnum.getStatusCode()
				+ " "+ statusEnum.getStatusPhrase()
				+ "\r\n"
				+ "Server: tomedog\r\n" 
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
