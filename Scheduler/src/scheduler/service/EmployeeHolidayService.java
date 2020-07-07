package scheduler.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import scheduler.domain.Employee;
import scheduler.domain.EmployeeHoliday;
import scheduler.domain.Product;

public class EmployeeHolidayService {

	private Connection connection;
	private EmployeeService employeeService;
	

	
	
	public EmployeeHolidayService(Connection connection, EmployeeService employeeService ) {
		super();
		this.connection = connection;
		this.employeeService = employeeService;
	}


	
	public List<EmployeeHoliday> getAllEmployeeHolidays() {
		
		List<EmployeeHoliday> result = new ArrayList<>();
		//return connection.;
			    Statement stmt = null;
	    String query = "select * from EmployeeHoliday";
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	EmployeeHoliday employeeHoliday = new EmployeeHoliday();
	        	employeeHoliday.setId(rs.getString("ID"));
	        	employeeHoliday.setDate(rs.getDate("HOL_DATE"));
	            String employeeId = rs.getString("EMPLOYEE_ID");
	        	Employee employee = employeeService.getEmployeeById(employeeId);
 	 	
		           result.add(employeeHoliday);
		        }
	        
		    } catch (SQLException e ) {
		      
		    } finally {
		       // if (stmt != null) { stmt.close(); }
		    }
		 
			return result;
		}


	public Connection getConnection() {
		return connection;
	}



	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}