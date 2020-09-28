package CreateUserTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import CreateUser.Connect2DB;
import CreateUser.RegisterForm;

@RunWith(MockitoJUnitRunner.Silent.class)
public class Connect2DBTest{

	@Mock private DataSource ds;
	@Mock private Connection c;
    @Mock private PreparedStatement stmt;
    @Mock private ResultSet rs;
    
    private RegisterForm rform;
    
    //setting up all the parameters
    @Before
    public void setUp() throws Exception {
    	assertNotNull(ds);
        when(c.prepareStatement(any(String.class))).thenReturn(stmt);
        when(ds.getConnection()).thenReturn(c);
        rform = new RegisterForm();
        rform.setUname("johndoe");
        rform.setFirstname("john");
        rform.setLastname("doe");
        rform.setEmail("johndoe@gmail.com");
        rform.setPassword("johndoe");
        when(rs.first()).thenReturn(true);
        when(rs.getString(2)).thenReturn(rform.getUname());
        when(rs.getString(3)).thenReturn(rform.getFirstname());
        when(rs.getString(4)).thenReturn(rform.getLastname());
        when(rs.getString(5)).thenReturn(rform.getEmail());
        when(stmt.executeQuery()).thenReturn(rs);
    }

    //testing addUser(create), but since it is difficult to catch queries passed to the database, using retrieve class to check the added class
    @Test
    public void createAndRetrievePerson() throws Exception {
        Connect2DB connect2DB = new Connect2DB();
        connect2DB.addUser(rform);
        RegisterForm r = connect2DB.retrieve("johndoe");
        String rformUser = rform.getUname();
        String rUser = r.getUname();
        assertEquals(rformUser, rUser);
    }
}

