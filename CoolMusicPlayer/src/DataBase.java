import java.sql.*;
import java.util.Properties;
import java.util.Date;


public class DataBase {
	
	/** The name of the MySQL account to use (or empty for anonymous) */
	private final String userName = "mharris5";

	/** The password for the MySQL account (or empty for anonymous) */
	private final String password = "mharris579471665";

	/** The name of the computer running MySQL */
	private final String serverName = "ada.gonzaga.edu";

	/** The port of the MySQL server (default is 3306) */
	private final int portNumber = 3306;

	/** The name of the database we are testing with (this default is installed with MySQL) */
	private final String dbName = "applicationTracking_DB";
	
	/** The name of the table we are testing with */
	private final String tableName = "Test";
	
	private Connection conn;
	
	public DataBase() {
		try {
			conn = this.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * Get a new database connection
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		Connection conn = DriverManager.getConnection("jdbc:mysql://"
				+ this.serverName + ":" + this.portNumber + "/" + this.dbName,
				connectionProps);

		return conn;
	}
	
	public ResultSet getInfoByID (int type, int id) throws SQLException {
		//0 is student
		//1 is alumni
		if (type == 0) {
			Statement s2 = conn.createStatement();
			s2.execute("SELECT applicant_id, first_name, last_name, class, expected_grad_date, MSC FROM Student where (applicant_id='"+id+"')");    
			ResultSet rs2 = s2.getResultSet(); 
			return rs2;
		}
		else {
			Statement s2 = conn.createStatement();
			s2.execute("SELECT applicant_id, first_name, last_name, graduation_date, age, current_job FROM Alumni where (applicant_id='"+id+"')");    
			ResultSet rs2 = s2.getResultSet(); 
			return rs2;
		}
	}
	
	public ResultSet getInfoByName(int type, String first, String last) throws SQLException {
		//0 is student
		//1 is alumni
		if (type == 0) {
			Statement s2 = conn.createStatement();
			s2.execute("SELECT applicant_id, first_name, last_name, class, expected_grad_date, MSC FROM Student where (first_name='"+first+"' and last_name='"+last+"')");    
			ResultSet rs2 = s2.getResultSet(); 
			return rs2;
		}
		else {
			Statement s2 = conn.createStatement();
			s2.execute("SELECT applicant_id, first_name, last_name, graduation_date, age, current_job FROM Alumni where (first_name='"+first+"' and last_name='"+last+"')");    
			ResultSet rs2 = s2.getResultSet(); 
			return rs2;
		}
	}
	
	public ResultSet getInfoByCompany(String company) throws SQLException {
		Statement s2 = conn.createStatement();
		s2.execute("SELECT position_id, title, salary, description, dept_name, name, accepted_major, accepted_gpa, apply_by_date  FROM Company join Department on Company.company_id=Department.company_id join Position on Department.department_id=Position.department_id where (Company.name='"+company+"')");    
		ResultSet rs2 = s2.getResultSet(); 
		return rs2;
	}
	
	public ResultSet getInfoByTitle(String title) throws SQLException {
		Statement s2 = conn.createStatement();
		s2.execute("SELECT position_id, title, salary, description, dept_name, name, accepted_major, accepted_gpa, apply_by_date  FROM Company join Department on Company.company_id=Department.company_id join Position on Department.department_id=Position.department_id where (Position.title='"+title+"')");    
		ResultSet rs2 = s2.getResultSet(); 
		return rs2;
	}
	
	public ResultSet getUserInfo(int ID, int type) throws SQLException {
		//0 is student, 1 is alumni
		Statement s2 = conn.createStatement();
		if (type == 0) {
			s2.execute("SELECT first_name, last_name, class, expected_grad_date, MSC, student_number, username_student, password_student, GPA, resume_on_file, cover_letter_on_file, major_1, major_2, home_address, phone_number  FROM Student natural join Applicant where (applicant_id='"+ID+"')");    
		}
		else {
			s2.execute("SELECT first_name, last_name, graduation_date, age, current_job, username_alumni, password_alumni, GPA, resume_on_file, cover_letter_on_file, major_1, major_2, home_address, phone_number  FROM Alumni natural join Applicant where (applicant_id='"+ID+"')");    
		}
		ResultSet rs2 = s2.getResultSet();
		return rs2;
	}
	
	public ResultSet getApplicationInfo(int userID, int positionID) throws SQLException {
		Statement s2 = conn.createStatement();
		s2.execute("SELECT application_id, application_date, under_consideration, phone_interview, site_interview, offer, rejection FROM Application where (applicant_id='"+userID+"' and position_id='"+positionID+"')");    
		ResultSet rs2 = s2.getResultSet(); 
		return rs2;
	}
	
	public int login(String username, String password, String type) throws SQLException {
		if (username.equals("Admin") & password.equals("password1")) {
			return 1;
		}
		else if (type == "Student") {
			int ID = 0;
			Statement s2 = conn.createStatement();
			s2.execute("SELECT applicant_id FROM Student where (username_student='"+username+"' and password_student='"+password+"')");    
			ResultSet rs2 = s2.getResultSet(); // 
			if ( rs2.next() ){
			  ID = rs2.getInt(1);
			}
			return ID;
		}
		else {
			int ID = 0;
			Statement s2 = conn.createStatement();
			s2.execute("SELECT applicant_id FROM Alumni where (username_alumni='"+username+"' and password_alumni='"+password+"')");    
			ResultSet rs2 = s2.getResultSet(); // 
			if ( rs2.next() ){
			  ID = rs2.getInt(1);
			}
			return ID;
		}
	}
	
	public boolean addStudent(String first, String last, String classP, String date, int msc, int studentNum, String user, String pass, double gpa, int resume, int letter, String major1, String major2, String address, String phone) throws SQLException {
		try {
			int maxID = 0;
			Statement s2 = conn.createStatement();
			s2.execute("SELECT MAX(applicant_id) FROM Applicant");    
			ResultSet rs2 = s2.getResultSet(); // 
			if ( rs2.next() ){
			  maxID = rs2.getInt(1);
			}
			maxID++;
			String query = " insert into Student (applicant_id, first_name, last_name, class, expected_grad_date, MSC, student_number, username_student, password_student)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
        	stmt.setInt(1, maxID);
        	stmt.setString(2, first);
        	stmt.setString(3, last);
        	stmt.setString(4, classP);
            java.sql.Date javaSqlDate = java.sql.Date.valueOf(date);
        	stmt.setDate(5, javaSqlDate);
        	stmt.setInt(6, msc);
        	stmt.setInt(7, studentNum);
        	stmt.setString(8, user);
        	stmt.setString(9, pass);
        	stmt.execute(); // This will throw a SQLException if it fails
        	try {
				this.addApplicant(maxID, gpa, resume, letter, major1, major2, address, phone);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return true;
		} finally {	
		}
	}
	
	public boolean addAlumni(String first, String last, String date, String user, String pass, double gpa, int resume, int letter, String major1, String major2, String address, String phone, int age, String job) throws SQLException {
		try {
			int maxID = 0;
			Statement s2 = conn.createStatement();
			s2.execute("SELECT MAX(applicant_id) FROM Applicant");    
			ResultSet rs2 = s2.getResultSet(); // 
			if ( rs2.next() ){
			  maxID = rs2.getInt(1);
			}
			maxID++;
			String query = " insert into Alumni (applicant_id, first_name, last_name, graduation_date, age, current_job, username_alumni, password_alumni)" + " values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
        	stmt.setInt(1, maxID);
        	stmt.setString(2, first);
        	stmt.setString(3, last);
            java.sql.Date javaSqlDate = java.sql.Date.valueOf(date);
        	stmt.setDate(4, javaSqlDate);
        	stmt.setInt(5, age);
        	stmt.setString(6, job);
        	stmt.setString(7, user);
        	stmt.setString(8, pass);
        	stmt.execute(); // This will throw a SQLException if it fails
        	try {
				this.addApplicant(maxID, gpa, resume, letter, major1, major2, address, phone);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return true;
		} finally {	
		}
	}
	
	public boolean addApplicant(int ID, double gpa, int resume, int letter, String major1, String major2, String address, String phone) throws SQLException {
		try {
			String query = " insert into Applicant (applicant_id, GPA, resume_on_file, cover_letter_on_file, major_1, major_2, home_address, phone_number)" + " values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
        	stmt.setInt(1, ID);
        	stmt.setDouble(2, gpa);
        	stmt.setInt(3, resume);
        	stmt.setInt(4, letter);
        	stmt.setString(5, major1);
        	stmt.setString(6, major2);
        	stmt.setString(7, address);
        	stmt.setString(8, phone);
        	stmt.execute(); // This will throw a SQLException if it fails
        	return true;
		} finally {	
		}
	}
	
	public boolean addApplication(int appID, int userID, int posID, String date, String consider, String phone, String site, String offer, String reject) throws SQLException {
		try {
			int maxID = 0;
			if (appID == 0) {
				Statement s2 = conn.createStatement();
				s2.execute("SELECT MAX(application_id) FROM Application");    
				ResultSet rs2 = s2.getResultSet(); // 
				if ( rs2.next() ){
					maxID = rs2.getInt(1);
				}
				maxID++;
				String query = " insert into Application (application_id, applicant_id, position_id, application_date, under_consideration, phone_interview, site_interview, offer, rejection)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement stmt = conn.prepareStatement(query);
	        	stmt.setInt(1, maxID);
	        	stmt.setInt(2, userID);
	        	stmt.setInt(3, posID);
	            java.sql.Date javaSqlDate = java.sql.Date.valueOf(date);
	        	stmt.setDate(4, javaSqlDate);
	        	stmt.setString(5, consider);
	        	stmt.setString(6, phone);
	        	stmt.setString(7, site);
	        	stmt.setString(8, offer);
	        	stmt.setString(9, reject);
	        	stmt.execute(); // This will throw a SQLException if it fails
			}
			else {
				maxID = appID;
				String query = " UPDATE Application SET applicant_id=?, position_id=?, application_date=?, under_consideration=?, phone_interview=?, site_interview=?, offer=?, rejection=? " + "WHERE application_id =?";
				PreparedStatement stmt = conn.prepareStatement(query);
	        	stmt.setInt(9, maxID);
	        	stmt.setInt(1, userID);
	        	stmt.setInt(2, posID);
	            java.sql.Date javaSqlDate = java.sql.Date.valueOf(date);
	        	stmt.setDate(3, javaSqlDate);
	        	stmt.setString(4, consider);
	        	stmt.setString(5, phone);
	        	stmt.setString(6, site);
	        	stmt.setString(7, offer);
	        	stmt.setString(8, reject);
	        	stmt.execute(); // This will throw a SQLException if it fails
			}
        	return true;
		} finally {	
		}
	}
	
	public boolean addPosition(String title, String salary, String description, String major, double gpa, String date, String dept_name, String dept_hiring, String comp_name, String comp_city, String comp_state) throws SQLException {
		try {
			//company, then dept, then position
			Statement s1 = conn.createStatement();
			s1.execute("SELECT company_id FROM Company WHERE name='"+comp_name+"'");    
			ResultSet rs1 = s1.getResultSet(); // 
			int newCompId = 0;
			if ( !rs1.next() ){
				Statement s3 = conn.createStatement();
				s3.execute("SELECT MAX(company_id) FROM Company");    
				ResultSet rs3 = s3.getResultSet(); 
				if (rs3.next()) {
					newCompId = rs3.getInt(1);
				}
				newCompId++;
				String query2 = " insert into Company (company_id, name, city, state)" + " values (?, ?, ?, ?)";
				PreparedStatement stmt2 = conn.prepareStatement(query2);
	        	stmt2.setInt(1, newCompId);
	        	stmt2.setString(2, comp_name);
	        	stmt2.setString(3, comp_city);
	        	stmt2.setString(4, comp_state);
	        	//opens new window requesting necessary information for company and stuff
	        	stmt2.execute(); // This will throw a SQLException if it fails
			}
			else {
				newCompId = rs1.getInt(1);
			}
			//dept

			Statement s4 = conn.createStatement();
			s4.execute("SELECT department_id FROM Department WHERE dept_name='"+dept_name+"' and company_id='"+newCompId+"'");    
			ResultSet rs4 = s4.getResultSet(); // 
			int newDeptId = 0;
			if ( !rs4.next() ){
				Statement s5 = conn.createStatement();
				s5.execute("SELECT MAX(department_id) FROM Department");    
				ResultSet rs5 = s5.getResultSet(); 
				if (rs5.next()) {
					newDeptId = rs5.getInt(1);
				}
				newDeptId++;
				String query3 = " insert into Department (department_id, company_id, dept_name, hiring)" + " values (?, ?, ?, ?)";
				PreparedStatement stmt3 = conn.prepareStatement(query3);
	        	stmt3.setInt(1, newDeptId);
	        	stmt3.setInt(2, newCompId);
	        	stmt3.setString(3, dept_name);
	        	stmt3.setString(4, dept_hiring);
	        	//opens new window requesting necessary information for company and stuff
	        	stmt3.execute(); // This will throw a SQLException if it fails
			}
			else {
				newDeptId = rs4.getInt(1);
			}
			
			
			//pos
			int maxID = 0;
			Statement s2 = conn.createStatement();
			s2.execute("SELECT MAX(position_id) FROM Position");    
			ResultSet rs2 = s2.getResultSet(); // 
			if ( rs2.next() ){
			  maxID = rs2.getInt(1);
			}
			maxID++;
			String query = " insert into Position (position_id, department_id, title, salary, description, accepted_major, accepted_gpa, apply_by_date)" + " values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query);
        	stmt.setInt(1, maxID);
        	stmt.setInt(2, newDeptId);
        	stmt.setString(3, title);
        	stmt.setString(4, salary);
        	stmt.setString(5, description);
        	stmt.setString(6, major);
        	stmt.setDouble(7, gpa);
            java.sql.Date javaSqlDate = java.sql.Date.valueOf(date);
        	stmt.setDate(8, javaSqlDate);
        	//opens new window requesting necessary information for company and stuff
        	stmt.execute(); // This will throw a SQLException if it fails
        	return true;
		} finally {	
		}
	}
	
	public boolean removeStudent(int ID) throws SQLException {
		try {
			String query = " delete from Student where applicant_id=" + "?";
			PreparedStatement stmt = conn.prepareStatement(query);
        	stmt.setInt(1, ID);
        	stmt.execute(); // This will throw a SQLException if it fails
        	return true;
		} finally {	
		}
	}
	
	public boolean removeAlumni(int ID) throws SQLException{
		try {
			String query = " delete from Alumni where applicant_id=" + "?";
			PreparedStatement stmt = conn.prepareStatement(query);
        	stmt.setInt(1, ID);
        	stmt.execute(); // This will throw a SQLException if it fails
        	return true;
		} finally {	
		}
	}
	
	public boolean removeApplicant(int ID) throws SQLException {
		try {
			String query = " delete from Applicant where applicant_id=" + "?";
			PreparedStatement stmt = conn.prepareStatement(query);
        	stmt.setInt(1, ID);
        	stmt.execute(); // This will throw a SQLException if it fails
        	return true;
		} finally {	
		}
	}
	
	public boolean removeApplication(int ID) throws SQLException {
		try {
			String query = " delete from Application where position_id=" + "?";
			PreparedStatement stmt = conn.prepareStatement(query);
        	stmt.setInt(1, ID);
        	stmt.execute(); // This will throw a SQLException if it fails
        	return true;
		} finally {	
		}
	}
	
	public boolean removePosition(int ID) throws SQLException {
		try {
			String query = " delete from Position where position_id=" + "?";
			PreparedStatement stmt = conn.prepareStatement(query);
        	stmt.setInt(1, ID);
        	stmt.execute(); // This will throw a SQLException if it fails
        	return true;
		} finally {	
		}
	}
	
	public boolean editStudent(int ID, String first, String last, String classP, String date, int msc, int studentNum, String user, String pass, double gpa, int resume, int letter, String major1, String major2, String address, String phone) throws SQLException {
		try {
			String query = " UPDATE Student SET first_name=?, last_name=?, class=?, expected_grad_date=?, MSC=?, student_number=?, username_student=?, password_student=?" + "where applicant_id="+ID;
			PreparedStatement stmt = conn.prepareStatement(query);
        	stmt.setString(1, first);
        	stmt.setString(2, last);
        	stmt.setString(3, classP);
            java.sql.Date javaSqlDate = java.sql.Date.valueOf(date);
        	stmt.setDate(4, javaSqlDate);
        	stmt.setInt(5, msc);
        	stmt.setInt(6, studentNum);
        	stmt.setString(7, user);
        	stmt.setString(8, pass);
        	stmt.execute(); // This will throw a SQLException if it fails
        	try {
				this.editApplicant(ID, gpa, resume, letter, major1, major2, address, phone);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return true;
		} finally {	
		}
	}
	
	public boolean editAlumni(int ID, String first, String last, String date, String user, String pass, double gpa, int resume, int letter, String major1, String major2, String address, String phone, int age, String job) throws SQLException {
		try {
			String query = " UPDATE Alumni SET first_name=?, last_name=?, graduation_date=?, age=?, current_job=?, username_alumni=?, password_alumni=?" + " WHERE applicant_id="+ID;
			PreparedStatement stmt = conn.prepareStatement(query);
        	stmt.setString(1, first);
        	stmt.setString(2, last);
            java.sql.Date javaSqlDate = java.sql.Date.valueOf(date);
        	stmt.setDate(3, javaSqlDate);
        	stmt.setInt(4, age);
        	stmt.setString(5, job);
        	stmt.setString(6, user);
        	stmt.setString(7, pass);
        	stmt.execute(); // This will throw a SQLException if it fails
        	try {
				this.editApplicant(ID, gpa, resume, letter, major1, major2, address, phone);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return true;
		} finally {	
		}
	}
	
	public boolean editApplicant(int ID, double gpa, int resume, int letter, String major1, String major2, String address, String phone) throws SQLException {
		try {
			String query = " UPDATE Applicant SET GPA=?, resume_on_file=?, cover_letter_on_file=?, major_1=?, major_2=?, home_address=?, phone_number=?" + " where applicant_id=" + ID;
			PreparedStatement stmt = conn.prepareStatement(query);
        	stmt.setDouble(1, gpa);
        	stmt.setInt(2, resume);
        	stmt.setInt(3, letter);
        	stmt.setString(4, major1);
        	stmt.setString(5, major2);
        	stmt.setString(6, address);
        	stmt.setString(7, phone);
        	stmt.execute(); // This will throw a SQLException if it fails
        	return true;
		} finally {	
		}
	}
	
	public boolean searchDatabase(int type, String keyWord) {
		if (type == 1) {
			//admin can access jobs and people?
		}
		else {
			//user can only access jobs
		}
		return true;
	}

	/**
	 * Run a SQL command which does not return a recordset:
	 * CREATE/INSERT/UPDATE/DELETE/DROP/etc.
	 * 
	 * @throws SQLException If something goes wrong
	 */
	public boolean executeUpdate(String command) throws SQLException {
		Statement stmt1 = null;
	    try {
	    	stmt1 = conn.createStatement();
	        stmt1.executeUpdate(command); // This will throw a SQLException if it fails
	        
			String query = " insert into Test (ID, NAME, STREET, CITY, STATE)" + " values (?, ?, ?, ?, ?)";
		    PreparedStatement stmt = conn.prepareStatement(query);
	        stmt.setInt(1, 002);
	        stmt.setString(2, "Testing Test");
	        stmt.setString(3, "4455 Main St");
	        stmt.setString(4, "Seattle");
	        stmt.setString(5, "WA");
	        stmt.execute(); // This will throw a SQLException if it fails
	        return true;
	    } finally {

	    	// This will run whether we throw an exception or not
	       // if (stmt != null) { stmt.close(); }
	    }
	}
	
	/**
	 * Connect to MySQL and do some stuff.
	 */
	public void run() {
		// Create a table
		try {
		    String createString =
			        "CREATE TABLE " + this.tableName + " ( " +
			        "ID INTEGER NOT NULL, " +
			        "NAME varchar(40) NOT NULL, " +
			        "STREET varchar(40) NOT NULL, " +
			        "CITY varchar(20) NOT NULL, " +
			        "STATE char(2) NOT NULL, " +
			        "ZIP char(5), " +
			        "PRIMARY KEY (ID))";
			this.executeUpdate(createString);
			System.out.println("Created a table");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not create the table");
			e.printStackTrace();
			return;
		}
		
		// Drop the table
		/*try {
		    String dropString = "DROP TABLE " + this.tableName;
			this.executeUpdate(conn, dropString);
			System.out.println("Dropped the table");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not drop the table");
			e.printStackTrace();
			return;
		}*/
	}
}
