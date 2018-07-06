package com.cai.learntc.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

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
				while (ch != -1) {
					outputStream.write(buff, 0, ch);
					ch = input.read(buff, 0, BUFFER_SIZE);
				}
			} else {
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type:text/html" + "Content-Length:23"
						+ "\r\n" + "<h1>File Not Found</h1>";
				outputStream.write(errorMessage.getBytes());
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
