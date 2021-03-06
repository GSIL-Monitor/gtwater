package com.gt.manager;

import com.gt.manager.Constant;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Description: api接口日志帮助类 </p>
 * <p>Author:steve/han, 16/09/29</p>
 */
public class ApiHttpServletRequestHelper extends HttpServletRequestWrapper {
	private final byte[] body;
	
	public ApiHttpServletRequestHelper(HttpServletRequest request) throws IOException {
		super(request);
		body = IOReaderHelper.ioReader(request).getBytes(Charset.forName(Constant.ENCODING));
	}
	
	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);
		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				return bais.read();
			}
			
			public boolean isFinished() {
				return false;
			}
			
			public boolean isReady() {
				return false;
			}
			
			public void setReadListener(ReadListener readListener) {
			}
		};
	}
}