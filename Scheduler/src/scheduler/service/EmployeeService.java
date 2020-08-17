package scheduler.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


import scheduler.domain.Employee;
import scheduler.domain.EmployeeHoliday;

public class EmployeeService {
	
	
	private Connection connection;
		
	public EmployeeService(Connection connection) {
		super();
		this.connection = connection;
	
	}


	
	public Employee getEmployeeById(String id) {
		

		//return connection.;
	    Statement stmt = null;
	    Employee employee = null;
	    String query = "select * from Employee where id = " + id;
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);        
	        employee = getEmployeeFromResult(rs);
		    } catch (SQLException e ) {
		      
		    } finally {
		       // if (stmt != null) { stmt.close(); }
		    }	 
			return employee;
		}
	
	private Employee getEmployeeFromResult (ResultSet rs) {
		Employee employee = new Employee();
		try {
		
	    	employee.setId(rs.getString("ID"));
	    	employee.setEmployeeName(rs.getString("EMPLOYEE_NAME"));
	    	employee.setAddress(rs.getString("ADDRESS"));
	    	employee.setPhoneNo(rs.getString("PHONE_NO"));
	    	employee.setEmail(rs.getString("EMAIL"));
	    	  	
		} catch (SQLException e ) {
		      
	    }
		return employee;
	}


	public List<Employee> getAllEmployees() {
		
		List<Employee> result = new ArrayList<>();
		//return connection.;
			    Statement stmt = null;
	    String query = "select * from Employee";
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	Employee employee = new Employee();
	        	employee.setId(rs.getString("ID"));
	        	employee.setEmployeeName(rs.getString("EMPLOYEE_NAME"));
	        	employee.setAddress(rs.getString("ADDRESS"));
	        	employee.setPhoneNo(rs.getString("PHONE_NO"));
	        	employee.setEmail(rs.getString(" EMAIL"));
	        	
	        	
	        	
	        	
	           result.add(employee);
	           
	        }
	    } catch (SQLException e ) {
	      
	    } finally {
	       // if (stmt != null) { stmt.close(); }
	    }
	 
		return result;
	}
	
	  public List<EmployeeHoliday> getHolidaysByEmployee(Employee employee) {
			
			List<EmployeeHoliday> result = new ArrayList<>();
			//return connection.;
				    Statement stmt = null;
		    String query = "select * from EmployeeHoliday where startDate<Date >EndDate";
		    try {
		        stmt = connection.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) {
		        	EmployeeHoliday employeeHoliday = new EmployeeHoliday();
		        	employeeHoliday.setId(rs.getString("ID"));
		            employeeHoliday.setDate(rs.getDate("HOL_DATE").toLocalDate());
		            String employeeId = rs.getString("EMPLOYEE_ID");
		        	
	 	 	
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
