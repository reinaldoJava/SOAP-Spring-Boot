package com.example.soap.springboot.soapSpringboot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CustomRequestWrapper extends HttpServletRequestWrapper {
	private CustomServerInputStream in;

	private HttpServletRequest request;

	public CustomRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		this.request = request;
		in = new CustomServerInputStream(request.getInputStream());
	}

	public ServletInputStream getInputStream() {
		return in;
	}

	public BufferedReader getReader() {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		return br;
	}
}
