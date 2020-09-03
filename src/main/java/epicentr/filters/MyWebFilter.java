package epicentr.filters;

import org.apache.coyote.http11.InputFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static epicentr.constraint.SecurityConstants.HEADER_STRING;
import static epicentr.constraint.SecurityConstants.TOKEN_PREFIX;

//@WebFilter(urlPatterns = "/api/v1/products")
//public class MyWebFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("filter for greeting project");
////        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
////        httpResponse.sendRedirect("/login");
//        System.out.println("-----------filter for greeting project");
//        HttpServletRequest req = (HttpServletRequest ) servletRequest;
//        HttpServletResponse res = (HttpServletResponse) servletResponse;
//        String header = req.getHeader(HEADER_STRING);
//
//
//
//
//        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
//            filterChain.doFilter(req, res);
//            return;
//        }
//
////        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
////
////        SecurityContextHolder.getContext().setAuthentication(authentication);
//        filterChain.doFilter(req, res);
//        //httpResponse.sendRedirect("/login");
//        filterChain.doFilter(servletRequest,servletResponse);
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
