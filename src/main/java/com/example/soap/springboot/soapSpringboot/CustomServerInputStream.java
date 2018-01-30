package com.example.soap.springboot.soapSpringboot;

import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class CustomServerInputStream extends ServletInputStream {

	final private ServletInputStream in;
	private StringBuffer strbuf = new StringBuffer();

	public CustomServerInputStream(ServletInputStream inputStream) {
		super();
		in = inputStream;
	}

	public int read(byte[] b, int off, int len) throws IOException {
		final int chr = in.read(b, 0, b.length);
		System.out.println(" total : " + chr);
		return chr;
	}

	public int read(byte[] b) throws IOException {
		int chr = in.read(b);
		strbuf.append(new String(b));
		System.out.println(" content " + new String(b));
		System.out.println(" noOfBytes " + chr);

		return chr;
	}

	public int read() throws IOException {
		final int chr = in.read();
		System.out.println(" char " + chr);
		return chr;
	}

	public StringBuffer getBody() {
		return strbuf;
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setReadListener(ReadListener listener) {
		// TODO Auto-generated method stub
		
	}
}