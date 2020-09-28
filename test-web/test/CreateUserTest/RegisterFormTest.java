package CreateUserTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import CreateUser.RegisterForm;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RegisterFormTest {

	private RegisterForm registerForm;
	@Mock FacesContext facesContextMock;
	@Mock ExternalContext externalContextMock;	
	
	//Create a new user
	@Before
	public void setUp() throws Exception {		
		registerForm = new RegisterForm();
		registerForm.setUname("john");
		registerForm.setFirstname("john");
		registerForm.setLastname("doe");
		registerForm.setEmail("john@gmail.com");
		registerForm.setPwd("johndoe");
		registerForm.setCheckpwd("johndoe");
		
	}
	
	//mocked the arguments to test if the test goes through
	@Test
	public void checkPassTest() throws IOException {
		RegisterForm mock = mock(RegisterForm.class);
		//when(mock.checkUser(registerForm)).thenReturn(false);
		//when(mock.checkEmail(registerForm)).thenReturn(false);
		Mockito.doReturn(true).when(mock).checkUser(registerForm); 
		Mockito.doReturn(true).when(mock).checkEmail(registerForm); 
			
		assertEquals("user-info?faces-redirect=true" , registerForm.checkPass(registerForm));
	 }
	
	@Test 
	public void checkEmailTest() {
		assertTrue(!registerForm.checkEmail(registerForm));
	}
		
	@Test 
	public void checkUserTest() {
		assertTrue(!registerForm.checkUser(registerForm));
	}
	
}
