//package epicentr.filters;
//
//import epicentr.helpers.JwtTokenUtil;
//import epicentr.services.UserDetailsServiceImpl;
//import io.jsonwebtoken.ExpiredJwtException;
//import org.apache.coyote.http11.InputFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//import static epicentr.constraint.SecurityConstants.HEADER_STRING;
//import static epicentr.constraint.SecurityConstants.TOKEN_PREFIX;
//
//@WebFilter(urlPatterns = "/api/v1/products")
//public class MyWebFilter implements Filter {
//    @Autowired
//    private UserDetailsServiceImpl jwtUserDetailsService;
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
////        System.out.println("filter for greeting project");
//////        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//////        httpResponse.sendRedirect("/login");
////        System.out.println("-----------filter for greeting project");
////        HttpServletRequest req = (HttpServletRequest ) servletRequest;
////        HttpServletResponse res = (HttpServletResponse) servletResponse;
////        String header = req.getHeader(HEADER_STRING);
////
////
////
////
////        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
////            filterChain.doFilter(req, res);
////            return;
////        }
////
//////        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
//////
//////        SecurityContextHolder.getContext().setAuthentication(authentication);
////        filterChain.doFilter(req, res);
////        //httpResponse.sendRedirect("/login");
////        filterChain.doFilter(servletRequest,servletResponse);
//        HttpServletRequest request = (HttpServletRequest ) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        final String requestTokenHeader = request.getHeader("Authorization");
//        String username = null;
//        String jwtToken = null;
//        // JWT Token is in the form "Bearer token". Remove Bearer word and get
//        // only the Token
//        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//            jwtToken = requestTokenHeader.substring(7);
//            try {
//                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
//            } catch (IllegalArgumentException e) {
//                System.out.println("Unable to get JWT Token");
//            } catch (ExpiredJwtException e) {
//                System.out.println("JWT Token has expired");
//            }
//        } else {
//            System.out.println("JWT Token does not begin with Bearer String");
//        }
//        // Once we get the token validate it.
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
//            // if token is valid configure Spring Security to manually set
//            // authentication
//            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken
//                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                // After setting the Authentication in the context, we specify
//                // that the current user is authenticated. So it passes the
//                // Spring Security Configurations successfully.
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }
//        else {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
