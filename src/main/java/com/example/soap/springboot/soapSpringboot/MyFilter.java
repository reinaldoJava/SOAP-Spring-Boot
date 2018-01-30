package com.example.soap.springboot.soapSpringboot;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter
public class MyFilter implements Filter {

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    
    System.out.println("MyFilter doFilter() is invoked.");
    //CustomRequestWrapper cr = new CustomRequestWrapper((HttpServletRequest)req);
    Enumeration<String> params = req.getParameterNames();
    
    while (params.hasMoreElements()) {
      String param=params.nextElement();
      System.out.println("Parameter:"+param+"\tValue:"+req.getParameter(param));
    }
    chain.doFilter(req, res);
  }

  @Override
  public void init(FilterConfig config) throws ServletException {
    
  }

  @Override
  public void destroy() {

  }

}
