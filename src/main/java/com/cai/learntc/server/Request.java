package com.cai.learntc.server;

import java.io.InputStream;

public class Request {
	private InputStream inputStream;
	private String uri;
	public Request(InputStream inputStream){
		this.inputStream = inputStream;
	}
	public void parse(){
		// 为毛要指定capacity，更有效的进行capacity扩展？
		StringBuffer request = new StringBuffer(2048);
		int i;
		byte[] buff = new byte[2048];
		try {
			i = inputStream.read(buff);
		} catch (Exception e) {
			e.printStackTrace();
			i = -1;
		}
		// 中文是否有问题
		for(int j = 0; j < buff.length; j++){
			request.append((char)buff[j]);
		}
		System.out.println(request.toString());
		uri = parseUri(request.toString());
	}
	private String parseUri(String requestString){
		int index1,index2;
		index1 = requestString.indexOf(" ");
		if(index1 != -1){
			index2 = requestString.indexOf(" ",index1 + 1);
			if(index2 > index1)
				return requestString.substring(index1+1, index2);
		}
		return null;
	}
	public String getUri(){
		return this.uri;
	}
}
