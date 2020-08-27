package Validation;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

public class CacheControl implements PhaseListener{
	public PhaseId getPhaseId()
    {
        return PhaseId.RENDER_RESPONSE;
    }
 
    public void afterPhase(PhaseEvent event)
    {
    }
 
    public void beforePhase(PhaseEvent event)
    {
        FacesContext facesContext = event.getFacesContext();
        HttpServletResponse res = (HttpServletResponse) facesContext
                .getExternalContext().getResponse();
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		res.setDateHeader("Expires", 0); // Proxies.
    }
}
