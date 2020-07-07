package scheduler;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import scheduler.domain.Employee;
import scheduler.domain.EmployeeHoliday;
import scheduler.domain.Order;
import scheduler.domain.Product;
import scheduler.domain.ProductMachine;
import scheduler.service.EmployeeHolidayService;
import scheduler.service.EmployeeService;
import scheduler.service.OrderService;
import scheduler.service.ProductMachineService;
import scheduler.service.ProductService;

public class ScheduleManager {
	
	Connection connection;
	Scanner scanner;
	ProductService productService;
	OrderService orderService;
	EmployeeService employeeService;
	EmployeeHolidayService employeeHolidayService;
	ProductMachineService productMachineService;
		

	public ScheduleManager(Connection connection) {
		super();
		this.connection = connection;
	}


	public void run() {
		productService = new ProductService(connection);
		orderService = new OrderService(connection, productService);
		employeeService = new EmployeeService(connection);
		employeeHolidayService = new EmployeeHolidayService(connection, employeeService);
		productMachineService = new ProductMachineService(connection);
	
		List<Product> products = productService.getAllProducts();

	    scanner = new Scanner(System.in);

				
        System.out.println("ORDER ENTRY");
        System.out.println("Available product details:");
        for (Product product : products) {
            System.out.println(product);
        }
        List<Order> orders = new ArrayList<Order>();
        System.out.println("Please enter order 1");
        orders.add(getOrderFromUser());
        int orderNum = 2;
        while(isNewOrderRequired()) {
            System.out.println("Please enter order " + orderNum);
            orders.add(getOrderFromUser());
            orderNum++;
        }
        orderService.saveAll(orders);
       
    
	}
	
	private boolean isNewOrderRequired(){
        System.out.println("Add another order (y/n)?");
        String newOrderRequired = scanner.nextLine();
        if(newOrderRequired.toLowerCase().equals("y")) {
        	return true;
        }else if (newOrderRequired.toLowerCase().equals("n")) {
        	return false;
        }else {
            System.out.println("Unrecognised!");
        	return isNewOrderRequired();
        }

	}
	
	private Order getOrderFromUser() {
        Product product = null;
        while (product == null) {
            System.out.println("Please enter product id:");       
            String productId = scanner.nextLine();
            product = productService.getProductById(productId);
            if(product == null) {
                System.out.println("Unrecognised product id!");       
            }           
        }
        
        System.out.println("Please enter customer name:");
        String customerName = scanner.nextLine();
        
        Date completionDate = null;
        while (completionDate == null) {
            System.out.println("Please enter completion date (dd/mm/yy)");
            String completionDateString = scanner.nextLine();
            completionDate = getDateFromString(completionDateString);
        }
        
        Order order = new Order(product, customerName, completionDate);
        System.out.println(order);

        return order;
	}
	
	private Date getDateFromString(String dateString){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");

        try {
            Date date = formatter.parse(dateString);
            return date;

        } catch (ParseException e) {
            System.out.println("Wrong format!");
        	return null;
        }
	}
	
	
	private void findBestSchedule(List<Order> orders){
		List<Employee> employees = employeeService.getAllEmployees();
		List<EmployeeHoliday> employeeHolidays = employeeHolidayService.getAllEmployeeHolidays();
		List<ProductMachine> productMachines = productMachineService.getAllProductMachines();

	}
	


	public static void main(String[] args)  {
		try {
			//TODO Auto-Generated method stub
			Connection connection = DBConnector.getConnection();
			ScheduleManager scheduleManager = new ScheduleManager(connection);
			scheduleManager.run();
		
	   } catch (Exception e) {
		System.out.println(e);
	   }
	}
	


	
}
