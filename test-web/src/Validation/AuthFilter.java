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
import javax.faces.application.ResourceHandler;


/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter({ "*.xhtml" })
public class AuthFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthFilter() {
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
		
	    HttpServletResponse res = (HttpServletResponse) response;
	    HttpServletRequest req = (HttpServletRequest) request;
	    HttpSession session = req.getSession(false);
	    
	    String loginURL = req.getContextPath() + "/faces/login-page.xhtml";
	    /*
	     * Set response headers to no-cache
	     * Erasing the cache would solve the problem of the back button bringing the user back
	     * to the welcome screen after logging out
	     */
	    /*
		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		res.setDateHeader("Expires", 0); // Proxies.
		*/
		boolean loggedIn = session != null && session.getAttribute("username") != null;
	    boolean loginRequest = req.getRequestURI().equals(loginURL);
	    boolean resourceRequest = req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER);

	    if (loggedIn || loginRequest || resourceRequest) {
	        chain.doFilter(request, response);
	    } else {
	        res.sendRedirect(loginURL);
	    }
	    
	    }  

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
