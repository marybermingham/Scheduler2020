package scheduler.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import scheduler.domain.Employee;
import scheduler.domain.EmployeeHoliday;

public class EmployeeHolidayService {

	private Connection connection;
	private EmployeeService employeeService;

	public EmployeeHolidayService(Connection connection) {
		super();
		this.connection = connection;
		this.employeeService = new EmployeeService(connection);
	}

	public List<EmployeeHoliday> getAllEmployeeHolidays() {
		
		List<EmployeeHoliday> employeeHolidays = new ArrayList<>();
		//return connection.;
			    Statement stmt = null;
	    String query = "select * from EMPLOYEE_HOLIDAY";
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
					EmployeeHoliday employeeHoliday = new EmployeeHoliday();
					employeeHoliday.setId(rs.getInt("ID"));
					employeeHoliday.setDate(rs.getDate("HOL_DATE").toLocalDate());
					Integer employeeId = rs.getInt("EMPLOYEE_ID");
					Employee employee = employeeService.getById(employeeId);
					employeeHoliday.setEmployee(employee);
				employeeHolidays.add(employeeHoliday);
		        }
	        
		    } catch (SQLException e ) {
				System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());

		    } finally {
		    }
		 
			return employeeHolidays;
		}

//  public List<EmployeeHoliday> getHolidaysByEmployee(Employee employee) {
//		
//		List<EmployeeHoliday> result = new ArrayList<>();
//		//return connection.;
//			    Statement stmt = null;
//	    String query = "select * from EmployeeHoliday where start<Date >End";
//	    try {
//	        stmt = connection.createStatement();
//	        ResultSet rs = stmt.executeQuery(query);
//	        while (rs.next()) {
//	        	EmployeeHoliday employeeHoliday = new EmployeeHoliday();
//	        	employeeHoliday.setId(rs.getString("ID"));
//	        	employeeHoliday.setDate(rs.getDate("HOL_DATE"));
//	            String employeeId = rs.getString("EMPLOYEE_ID");
//	       
// 	 	
//		           result.add(employee);
//		        }
//	        
//		    } catch (SQLException e ) {
//		      
//		    } finally {
//		       // if (stmt != null) { stmt.close(); }
//		    }
//		 
//			return result;
//		}
//  
  //  public List<Date> getEmployeeUnavailableDays() {
	  
	 // return EmployeeUnavailableDays;
 // }
  
  
    public Map<String, Date> EmployeeUnavailableDays = new HashMap<>();{
 
 	
 }
 		
 		
 		

	
	



	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
