package com.cai.ch2;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cai.ch1.ResponseStateEnum;

public class HttpProtocolUtils {
	public static void sendResponseLine(OutputStream outputStream , ResponseStateEnum responseStateEnum)
			throws IOException{
		String responseLine = "HTTP1.1 " 
				+ responseStateEnum.getStatusCode()
				+ " "
				+ responseStateEnum.getStatusPhrase()
				+"\r\n";
		outputStream.write(responseLine.getBytes());
	}
	
	private static String getGMTDateTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'");
		Date curDate = new Date();
		return sdf.format(curDate);
	}
	
	public static void sendResponseHeader(OutputStream outputStream) throws IOException{
		String responseHeader = "Server: tomedog\r\n" 
						+ "Date: "+getGMTDateTime()+"\r\n"
						+ "Content-Type: text/html;charset=UTF-8\r\n" 
						+"\r\n";
		outputStream.write(responseHeader.getBytes());
	}
}
