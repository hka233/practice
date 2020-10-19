package CreateUserTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

import CreateUser.Connect2DB;
import CreateUser.RegisterForm;
import CreateUser.UserController;
import Validation.DbConnect;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RegisterFormTest {

	RegisterForm registerForm;
	@Mock private RegisterForm rform;
	@Mock FacesContext facesContextMock;
	@Mock ExternalContext externalContextMock;	
	@InjectMocks private RegisterForm registerform;
	@Mock private Connect2DB connect2db;
	@Mock private DataSource dataSource;
	@Mock private Connection connection;
	@Mock private Statement statement;
	@Mock private PreparedStatement preparedStatement;
	@Mock private ResultSet resultSet;
	
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
	public void checkPassTest() throws Exception {
		FacesContext context = ContextMocker.mockFacesContext();
		ExternalContext ext = mock(ExternalContext.class);
		when(context.getExternalContext()).thenReturn(ext);
		when(rform.checkUser(registerForm)).thenReturn(false);
		when(rform.checkEmail(registerForm)).thenReturn(false);
		doNothing().when(connect2db).addUser(registerForm);
			
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
