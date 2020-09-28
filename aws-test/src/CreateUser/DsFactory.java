package CreateUser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
public class DsFactory {

	//Class that would retrieve database information from db.propertis, prevent people from finding database credentials easily
	public static DataSource getDataSource() {
		Properties props = new Properties();
		InputStream fis = null;
		MysqlDataSource mysqlDS = null;
		
		try {
			//fis = new InputStream("db.properties");
			fis = DsFactory.class.getClassLoader().getResourceAsStream("db.properties");
			props.load(fis);
			mysqlDS = new MysqlDataSource();
			mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
			mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
			mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mysqlDS;
	}
}
