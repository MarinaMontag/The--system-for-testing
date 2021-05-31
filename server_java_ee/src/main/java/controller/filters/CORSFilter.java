package controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println("CORSFilter HTTP Request: " + request.getMethod());

        // Authorize (allow) all domains to consume the content
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Methods",
                "GET, OPTIONS, HEAD, PUT, POST");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Headers", "*");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Credentials", "true");
        ((HttpServletResponse) servletResponse).setContentType("application/json");
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        // pass the request along the filter chain
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
