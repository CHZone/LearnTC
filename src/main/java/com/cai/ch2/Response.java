package com.cai.ch2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

import com.cai.ch1.ResponseStateEnum;

public class Response implements ServletResponse {
	private static final int BUFFER_SIZE = 1024;
	OutputStream outputStream;
	Request request;
	//如果构建多个printWriter是否会导致response数据混乱？
	PrintWriter printWriter;
	
	public Response(OutputStream outputStream){
		this.outputStream = outputStream;
	}
	
	public void setRequest(Request request){
		this.request = request;
	}
	
	/**
	 *  这个方法放到 StaticResourceProcessor 可能看起来更好
	 */
	public void sendStaticResource() throws IOException{
		byte[] buff = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		try{
			File file = new File(Constants.WEB_ROOT+File.separator+request.getUri());
			if(file.exists()){
				this.sendResponseLineAndHeader(ResponseStateEnum.OK);
				fis = new FileInputStream(file);
				int ch = fis.read(buff);
				while(ch != -1){
					outputStream.write(buff, 0, ch);
					ch = fis.read(buff, 0, BUFFER_SIZE);
				}
			}else{
				this.sendResponseLineAndHeader(ResponseStateEnum.FILE_NOT_FOUND);
				String errorMessage = 
						"<html>"
						+ "<head>"
						+ "</head>"
						+ "<body>"
						+ "<h1>File Not Found</h1>"
						+ "</body>"
						+"</html>";
				outputStream.write(errorMessage.getBytes());
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(fis !=null){
				fis.close();
			}
		}
	}

	public void sendResponseLineAndHeader(ResponseStateEnum responseStateEnum){
		try {
			HttpProtocolUtils.sendResponseLine(outputStream, responseStateEnum);
			HttpProtocolUtils.sendResponseHeader(outputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub

	}

	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public PrintWriter getWriter() throws IOException {
		printWriter = new PrintWriter(outputStream, true);
		return printWriter;
	}

	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	public void reset() {
		// TODO Auto-generated method stub

	}

	public void resetBuffer() {
		// TODO Auto-generated method stub

	}

	public void setBufferSize(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setCharacterEncoding(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setContentLength(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setContentType(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setLocale(Locale arg0) {
		// TODO Auto-generated method stub

	}

}
