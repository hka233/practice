package CreateUserTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import CreateUser.RegisterForm;
import CreateUser.UserController;
import Validation.DbConnect;

/*
 * Contains all tests of the methods in usercontroller
 * Note - All the tests were performed with Facescontext removed
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class UserControllerTest {

	private RegisterForm user1;
	private RegisterForm user2;
	private RegisterForm user3;
	List<RegisterForm> testUsers = new ArrayList<>();
	
	@InjectMocks private UserController usercontroller;
	@Mock private DbConnect dbconnect;
	@Mock private DataSource dataSource;
	@Mock private Connection connection;
	@Mock private Statement statement;
	@Mock private PreparedStatement preparedStatement;
	@Mock private ResultSet resultSet;
	@Mock private RegisterForm registerform;
	
	
	
	//Create a new user
	@Before
	public void setUp() throws Exception {
		when(dataSource.getConnection()).thenReturn(connection);
		
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        
		user1 = new RegisterForm();
		user1.setFirstname("Sheldon");
		user1.setLastname("Cooper");
		user1.setUname("Sheldr");
		user1.setEmail("s.cooperphd@yahoo.co");
		user1.setPassword("BIGbangtheory");
		
		user2 = new RegisterForm();
		user2.setFirstname("Jon");
		user2.setLastname("Snow");
		user2.setUname("lordsnow");
		user2.setEmail("firstsword@nightwatch.com");
		user2.setPassword("longclaw");
		
		user3 = new RegisterForm();
		user3.setFirstname("Jon");
		user3.setLastname("Snow");
		user3.setUname("lordsnows");
		user3.setEmail("firstsword@nightwatch.com");
		user3.setPassword("longclaw");
		
		
		testUsers.add(user1);
		testUsers.add(user2);
		testUsers.add(user3);
		
		
	}
	
	
	@Test
	public void getUserTest() throws IOException, SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("first_name")).thenReturn(user1.getFirstname()).thenReturn(user2.getFirstname()).thenReturn(user3.getFirstname());
        when(resultSet.getString("last_name")).thenReturn(user1.getLastname()).thenReturn(user2.getLastname()).thenReturn(user3.getLastname());
        when(resultSet.getString("username")).thenReturn(user1.getUname()).thenReturn(user2.getUname()).thenReturn(user3.getUname());
        when(resultSet.getString("email")).thenReturn(user1.getEmail()).thenReturn(user2.getEmail()).thenReturn(user3.getEmail());
        when(resultSet.getString("password")).thenReturn(user1.getPassword()).thenReturn(user2.getPassword()).thenReturn(user3.getPassword());
        
        List<RegisterForm> users = new ArrayList<>();
        //UserController usercontroller = new UserController();
        users = usercontroller.getUsers();
        
		assertTrue(users.get(1).getUname().equals(testUsers.get(1).getUname()));
	 }
	
	@Test
	public void loadUserTest() throws SQLException {
		FacesContext context = ContextMocker.mockFacesContext();
		when(resultSet.next()).thenReturn(true).thenReturn(false);
		when(resultSet.getString("first_name")).thenReturn(user1.getFirstname());
        when(resultSet.getString("last_name")).thenReturn(user1.getLastname());
        when(resultSet.getString("username")).thenReturn(user1.getUname());
        when(resultSet.getString("email")).thenReturn(user1.getEmail());
        when(resultSet.getString("password")).thenReturn(user1.getPassword());
        Map<String, Object> session = new HashMap<String, Object>(); 
        ExternalContext ext = mock(ExternalContext.class);
        when(ext.getRequestMap()).thenReturn(session);
        when(context.getExternalContext()).thenReturn(ext);
        
		String user = "sheldor";
		String link = "random";
		link = usercontroller.loadUser(user);
		
		assertTrue(link.equals("update-userinfo.xhtml"));
	}
	
	@Test
	public void updateUserInfoTest() throws Exception {
		when(registerform.checkUser(user1)).thenReturn(false);
		when(registerform.checkEmail(user1)).thenReturn(false);
		doNothing().when(preparedStatement).setString(1, "first_name");
		doNothing().when(preparedStatement).setString(2, "last_name");
		doNothing().when(preparedStatement).setString(3, "username");
		doNothing().when(preparedStatement).setString(4, "email");
		doNothing().when(preparedStatement).setString(5, "first_name");
		when(preparedStatement.execute()).thenReturn(true); // Since this is a void method, used this as a marker of the code reaching the end with no issue
        
        usercontroller.updateUserInfo(user1 , user1.getUname());
        
        verify(preparedStatement).execute();
	}
	
	@Test
	public void updateUserPassTest() throws Exception {
		when(dbconnect.getPass(anyString(), anyString())).thenReturn(true);
		FacesContext context = ContextMocker.mockFacesContext();
		doNothing().when(preparedStatement).setString(1, "first_name");
		doNothing().when(preparedStatement).setString(2, "last_name");
		doNothing().when(preparedStatement).setString(3, "username");
		doNothing().when(preparedStatement).setString(4, "email");
		when(preparedStatement.execute()).thenReturn(true);	// Since this is a void method, used this as a marker of the code reaching the end with no issue
		doNothing().when(context).addMessage(null, null);
		
		usercontroller.updateUserPass("tester" , "testpass1234", "newpass", "newpass");
        
        verify(preparedStatement).execute();
	}
	
	
}
