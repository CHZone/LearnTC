package com.cai.ch2;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer1 {
	public static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	private boolean shutdown = false;
	
	public void await(){
		ServerSocket serverSocket = null;
		int port = 8080;
		try{
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		while(!shutdown){
			Socket socket = null;
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try{
				socket = serverSocket.accept();
				inputStream = socket.getInputStream();
				outputStream = socket.getOutputStream();
				Request request = new Request(inputStream);
				request.parse();
				
				Response response = new Response(outputStream);
				response.setRequest(request);
				
				if(request.getUri().startsWith("/servlet")){
					ServletProcessor1 servletProcessor = new ServletProcessor1();
					servletProcessor.process(request, response);
				}else{
					StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
					staticResourceProcessor.process(request, response);
				}
				socket.close();
				shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.toString());
			}
		}
	}
	public static void main(String[] args){
		HttpServer1 httpServer = new HttpServer1();
		httpServer.await();
	}
}
