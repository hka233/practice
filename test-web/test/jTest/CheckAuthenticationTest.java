package jTest;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.omnifaces.util.Faces;
//import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Mockito.mock;

import Validation.CheckAuthentication;

@RunWith(MockitoJUnitRunner.class)
public class CheckAuthenticationTest {

	private CheckAuthentication checkAuthentication;
	
	@Mock FacesContext facesContextMock;
	@Mock ExternalContext externalContextMock;		
	
	@Before
	public void setUp() throws Exception {
		Faces.setContext(facesContextMock);
		
		Mockito.when(facesContextMock.getExternalContext()).thenReturn(externalContextMock);
	  }
	
	@Test
	public void testCheckAuth() throws Exception {
		CheckAuthentication mock = mock(CheckAuthentication.class);
		Mockito.doReturn(true).when(mock).checkDB(); //returns true for password checking
		
		ArgumentCaptor<String> redirectUrl = ArgumentCaptor.forClass(String.class);
		Mockito.doNothing().when(externalContextMock).redirect(redirectUrl.capture()); //captures the String that passes the method
		
		checkAuthentication = new CheckAuthentication();
		checkAuthentication.checkAuth();
		
		assertEquals("welcome-page.xhtml" , redirectUrl.getValue()); //test value 
	}
	
	@Test
	public void testLogout() throws IOException {
		ArgumentCaptor<String> redirectUrl = ArgumentCaptor.forClass(String.class);
		Mockito.doNothing().when(externalContextMock).redirect(redirectUrl.capture());
		
		checkAuthentication = new CheckAuthentication();
		checkAuthentication.logout();
		
		assertEquals("login-page.xhtml" , redirectUrl.getValue());
	  }
}
