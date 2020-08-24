package Validation;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class SessionFilter
 */
//@WebFilter("login-page")
public class SessionFilter implements Filter {

    /**
     * Default constructor. 
     */
    public SessionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hRequest = (HttpServletRequest) request;
	    HttpServletResponse hResponse = (HttpServletResponse) response;
	    HttpSession session = hRequest.getSession(false); // returns existing or
	                                                        // null

	    if (session == null || session.getAttribute("username") == null) {
	        hResponse.sendRedirect(hRequest.getContextPath() + "/login-page.xhtml");
	        // return;
	    } else {
	        hResponse.setHeader("Cache-Control",
	                "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	        hResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	        hResponse.setDateHeader("Expires", 0);
	        chain.doFilter(request, response);
	    }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
