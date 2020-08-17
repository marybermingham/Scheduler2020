package scheduler;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import scheduler.domain.Employee;
import scheduler.domain.EmployeeHoliday;
import scheduler.domain.Order;
import scheduler.domain.Product;
import scheduler.domain.Schedule;
import scheduler.domain.ScheduledOrder;
import scheduler.service.EmployeeHolidayService;
import scheduler.service.EmployeeService;
import scheduler.service.OrderService;
import scheduler.service.ProductMachineService;
import scheduler.service.ProductService;
import scheduler.utils.DateUtils;

public class ScheduleManager {
	
	Connection connection;
	Scanner scanner;
	ProductService productService;
	OrderService orderService;
	EmployeeService employeeService;
	EmployeeHolidayService employeeHolidayService;
	ProductMachineService productMachineService;
	
	public ScheduleManager() {
		super();
	}
		

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
        
        Schedule bestSchedule = getRandomSchedule(orders);
        //save best schedule to DB
        //print best schedule
        
        Integer bestScore = getScheduleScore(getRandomSchedule(orders));
        
        while(true) {
        	Schedule schedule = getRandomSchedule(orders);
        	Integer score = getScheduleScore(schedule) ;
        	if(score > bestScore) {
        		bestScore = score;
        		bestSchedule = schedule;
        	      //save best schedule to DB
                //print best schedule
        	}
        }
        
           
	}
	
	private Integer getScheduleScore(Schedule schedule) {
		return 0;
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
        
        LocalDate completionDate = null;
        while (completionDate == null) {
            System.out.println("Please enter completion date (dd/mm/yy)");
            String completionDateString = scanner.nextLine();
            completionDate = DateUtils.getDateFromString(completionDateString);
        }
        
        Order order = new Order(product, customerName, completionDate);
        System.out.println(order);

        return order;
	}
	
	
	private Schedule getRandomSchedule(List<Order> orders){
		    
		Map<Employee, List<LocalDate>>  employeeUnavailableDaysMap = getEmployeeUnavailableDaysMap();
		Map<String, List<LocalDate>>  machineUnavailableDaysMap = getMachineUnavailableDaysMap();
		List<ScheduledOrder> scheduledOrders = new ArrayList<>();
		List<LocalDate> orderRequiredDates = new ArrayList<>();
		for(Order order : orders) {
			orderRequiredDates.add(order.getRequiredDate());
		}
		LocalDate startDate = LocalDate.now();		
		for(Order order : orders) {
			scheduledOrders.add(randomlyScheduleOrder(startDate, order, employeeUnavailableDaysMap, machineUnavailableDaysMap));
		}
		
		return new Schedule(scheduledOrders);

	}

	
	
	
	private Map<Employee, List<LocalDate>>  getEmployeeUnavailableDaysMap(){
		List<EmployeeHoliday> employeeHolidays = employeeHolidayService.getAllEmployeeHolidays();
		Map<Employee, List<LocalDate>>  employeeUnavailableDaysMap = new HashMap<>();
		
	    for (EmployeeHoliday employeeHoliday : employeeHolidays) {
	        if (!employeeUnavailableDaysMap.containsKey(employeeHoliday.getEmployee())) {
	        	employeeUnavailableDaysMap.put(employeeHoliday.getEmployee(), new ArrayList<LocalDate>());
	        }
	        employeeUnavailableDaysMap.get(employeeHoliday.getEmployee()).add(employeeHoliday.getDate());
	    }
	
	    return employeeUnavailableDaysMap;
	}
	
	private Map<String, List<LocalDate>>  getMachineUnavailableDaysMap(){
		
		List<String> machineIds =  productMachineService.getAllMachineIds();		
		Map<String, List<LocalDate>>  machineUnavailableDaysMap = new HashMap<>();
		
	    for (String machineId : machineIds) {

	    	machineUnavailableDaysMap.put(machineId, new ArrayList<LocalDate>());
	    }
	
	    return machineUnavailableDaysMap;
	}
	
	
  
	
	public static LocalDate getStartDate(LocalDate now, LocalDate completionDate) {
    long nowEpochDay = now.toEpochDay();
    long completionDateEpochDay = completionDate.toEpochDay();
    long randomDay = ThreadLocalRandom.current().nextLong(nowEpochDay, completionDateEpochDay);
    System.out.println(randomDay);
    return LocalDate.ofEpochDay(randomDay);
}
	
	private ScheduledOrder randomlyScheduleOrder(LocalDate startDate, Order order, Map<Employee, List<LocalDate>>  employeeUnavailableDaysMap, Map<String, List<LocalDate>>  machineUnavailableDaysMap) {
		List<LocalDate> applicableDates = new ArrayList<>();
		LocalDate date = startDate;
		Product product = order.getProduct();
		Integer numProcessDays = product.getNoProcessDays();
		Integer noEmployeesRequired = product.getNoEmployeesRequired();
		String machineId = product.getMachineId();
		
		while (!date.isAfter(order.getRequiredDate())) {
			
			LocalDate endDate = date.plusDays(numProcessDays);
			boolean isMachineAvailable = DateUtils.doesPeriodContainAnyDates(date, endDate, machineUnavailableDaysMap.get(machineId));
			List<Employee> availableEmployees = getAvailableEmployeesForPeriod(date, endDate, employeeUnavailableDaysMap);
			boolean isMinNoOfEmployeesAvailable = noEmployeesRequired <= availableEmployees.size();
			if(isMachineAvailable && isMinNoOfEmployeesAvailable) {
				applicableDates.add(date);
			}
			date = date.plusDays(1);			
		}
		
		int randomIndex = (int)(Math.random() * (applicableDates.size() - 1));
		
		LocalDate orderStartDate = applicableDates.get(randomIndex);
		LocalDate orderEndDate = orderStartDate.plusDays(numProcessDays);
		List<LocalDate> orderDateList = DateUtils.toDateList(orderStartDate, orderEndDate);
		List<LocalDate> machineUnavailableDays = machineUnavailableDaysMap.get(machineId);
		machineUnavailableDays.addAll(orderDateList);
		List<Employee> availableEmployeesForOrder = getAvailableEmployeesForPeriod(orderStartDate, orderEndDate, employeeUnavailableDaysMap);
		List<Employee> employeesForOrder = new ArrayList<>();
		for(int i = 0; i <= noEmployeesRequired; i++ ) {
			Employee orderEmployee = availableEmployeesForOrder.get(i);
			employeesForOrder.add(orderEmployee);
			List<LocalDate> employeeUnavailableDays = employeeUnavailableDaysMap.get(orderEmployee);
			employeeUnavailableDays.addAll(orderDateList);		
		}
		ScheduledOrder scheduledOrder = new ScheduledOrder(order, orderStartDate, orderEndDate, employeesForOrder);

		
		return scheduledOrder;
	}
	
	private List<Employee> getAvailableEmployeesForPeriod(LocalDate startPeriod, LocalDate endPeriod,  Map<Employee, List<LocalDate>>  employeeUnavailableDaysMap){
		List<Employee> allEmployees = employeeService.getAllEmployees();
		List<Employee> availableEmployees = new ArrayList<>();
		for(Employee employee : allEmployees) {
			if(!DateUtils.doesPeriodContainAnyDates(startPeriod, endPeriod, employeeUnavailableDaysMap.get(employee))){
				availableEmployees.add(employee);
			}
		}
		return availableEmployees;
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

	

