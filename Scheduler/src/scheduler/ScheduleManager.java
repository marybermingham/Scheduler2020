package scheduler;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import scheduler.domain.*;
import scheduler.service.*;
import scheduler.utils.DateUtils;

import static java.time.temporal.ChronoUnit.DAYS;

public class ScheduleManager {
	
	Connection connection;
	Scanner scanner;
	ProductService productService;
	OrderService orderService;
	EmployeeService employeeService;
	EmployeeHolidayService employeeHolidayService;
	OrderListService orderListService;
	BestScheduleService bestScheduleService;
	
	public ScheduleManager() {
		super();
	}
		

	public ScheduleManager(Connection connection) {
		super();
		this.connection = connection;
		bestScheduleService = new BestScheduleService(connection);
		productService = new ProductService(connection);
		orderService = new OrderService(connection);
		employeeService = new EmployeeService(connection);
		employeeHolidayService = new EmployeeHolidayService(connection);
		orderListService = new OrderListService(connection);
	}


	public void run() {


		List<Product> products = productService.getAll();

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
		System.out.println("How many randomly selected schedules do you want to try?");
		int numTries = getNumberOfTries();

		System.out.println("Starting search for best schedule");
		OrderList orderList = new OrderList(orders);
		orderList = orderListService.save(orderList);
		Schedule bestSchedule = null;
		Integer bestScore = null;
		//get first best score
		int numberOfFailedAttemptsForFirstValidSchedule = 0;
		while(bestSchedule == null && numberOfFailedAttemptsForFirstValidSchedule <= numTries){
			bestSchedule = getRandomSchedule(orderList);
			if(bestSchedule == null) {
				System.out.print(",");
				numberOfFailedAttemptsForFirstValidSchedule++;
				continue;
			}
			bestScore = getScheduleScore(bestSchedule);
			bestSchedule = bestScheduleService.saveBestSchedule(bestSchedule, orderList.getId());
			System.out.println(bestSchedule);
		}
		if(bestSchedule == null) {
			System.out.println("\n No valid schedules found.");
			return;
		}

		for(int i = numberOfFailedAttemptsForFirstValidSchedule; i < numTries; i++){
			Schedule randomSchedule = getRandomSchedule(orderList);
        	if(randomSchedule == null){
				System.out.print(",");
				continue;
			}
        	Integer score = getScheduleScore(randomSchedule) ;
        	if(score > bestScore) {
        		bestScore = score;
				bestScheduleService.clearBestSchedule(bestSchedule, orderList.getId());
				bestSchedule = randomSchedule;
				bestScheduleService.saveBestSchedule(bestSchedule, orderList.getId());
				System.out.print("," + score);
				System.out.println("\n" + bestSchedule);
        	} else{
				System.out.print("," + score);
			}
        }
		orderListService.save(orderList);

	}
	
	private Integer getScheduleScore(Schedule schedule) {
		List<ScheduledOrder> scheduledOrders =  schedule.getScheduledOrders();
		int score = 0;
		for (ScheduledOrder scheduledOrder : scheduledOrders){
			LocalDate endDate = scheduledOrder.getEndDate();
			LocalDate requiredDate = scheduledOrder.getOrder().getRequiredDate();
			score = score + (int)DAYS.between(endDate, requiredDate);
		}
		schedule.setScore(score);
		return score;
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

	private int getNumberOfTries(){
		String numTriesString = scanner.nextLine();
		Integer numTries;
		try {
			numTries = Integer.parseInt(numTriesString);
		} catch (NumberFormatException e){
			System.out.println("Not a number! Please enter a number:");
			return getNumberOfTries();
		}
		return numTries;
	}


	private Order getOrderFromUser() {
        Product product = null;
        while (product == null) {
            System.out.println("Please enter product code:");
            String productCode = scanner.nextLine();
            product = productService.getByCode(productCode);
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
	
	
	private Schedule getRandomSchedule(OrderList orderList){
		    
		Map<Employee, List<LocalDate>>  employeeUnavailableDaysMap = getEmployeeUnavailableDaysMap();
		Map<String, List<LocalDate>>  machineUnavailableDaysMap = getMachineUnavailableDaysMap();
		List<Order> orders = orderList.getOrders();
		List<ScheduledOrder> scheduledOrders = new ArrayList<>();
//		List<LocalDate> orderRequiredDates = new ArrayList<>();
//		for(Order order : orders) {
//			orderRequiredDates.add(order.getRequiredDate());
//		}
		LocalDate startDate = LocalDate.now();		
		for(Order order : orders) {
			ScheduledOrder scheduledOrder = randomlyScheduleOrder(startDate, order, employeeUnavailableDaysMap, machineUnavailableDaysMap);
			if(scheduledOrder == null){
				//this schedule could not be completed. Start from scratch
				return null;
			}
			scheduledOrders.add(randomlyScheduleOrder(startDate, order, employeeUnavailableDaysMap, machineUnavailableDaysMap));
		}
		Schedule schedule = new Schedule(scheduledOrders);
		return  schedule;

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
		
		List<String> machineCodes =  productService.getAllMachineCodes();
		Map<String, List<LocalDate>>  machineUnavailableDaysMap = new HashMap<>();
		
	    for (String machineCode : machineCodes) {
	    	machineUnavailableDaysMap.put(machineCode, new ArrayList<LocalDate>());
	    }
	    return machineUnavailableDaysMap;
	}
	

	
	private ScheduledOrder randomlyScheduleOrder(LocalDate startDate, Order order, Map<Employee, List<LocalDate>>  employeeUnavailableDaysMap, Map<String, List<LocalDate>>  machineUnavailableDaysMap) {

		Product product = order.getProduct();
		Integer numProcessDays = product.getNoProcessDays();
		Integer noEmployeesRequired = product.getNoEmployeesRequired();
		String machineId = product.getMachineCode();

		//loop through all dates up till required completion date and build list of applicable dates based on available resources
		List<LocalDate> applicableDates = new ArrayList<>();
		LocalDate date = startDate;
		while (!date.isAfter(order.getRequiredDate())) {
			
			LocalDate endDate = date.plusDays(numProcessDays);
			if(endDate.isAfter(order.getRequiredDate())) {
				break;
			}
			boolean isMachineAvailable = !DateUtils.doesPeriodContainAnyDates(date, endDate, machineUnavailableDaysMap.get(machineId));
			List<Employee> availableEmployees = getAvailableEmployeesForPeriod(date, endDate, employeeUnavailableDaysMap);
			boolean isMinNoOfEmployeesAvailable = noEmployeesRequired <= availableEmployees.size();
			if(isMachineAvailable && isMinNoOfEmployeesAvailable) {
				applicableDates.add(date);
			}
			date = date.plusDays(1);			
		}
		// if no applicable dates then discontinue
		if(applicableDates.size() == 0){
			return null;
		}
		
		int randomIndex = (int)(Math.random() * (applicableDates.size() - 1));
		
		LocalDate orderStartDate = applicableDates.get(randomIndex);
		LocalDate orderEndDate = orderStartDate.plusDays(numProcessDays);
		List<LocalDate> orderDateList = DateUtils.toDateList(orderStartDate, orderEndDate);
		List<LocalDate> machineUnavailableDays = machineUnavailableDaysMap.get(machineId);
		machineUnavailableDays.addAll(orderDateList);
		List<Employee> availableEmployeesForOrder = getAvailableEmployeesForPeriod(orderStartDate, orderEndDate, employeeUnavailableDaysMap);
		List<Employee> employeesForOrder = new ArrayList<>();
		for(int i = 0; i < noEmployeesRequired; i++ ) {
			Employee orderEmployee = availableEmployeesForOrder.get(i);
			employeesForOrder.add(orderEmployee);
			List<LocalDate> employeeUnavailableDays = employeeUnavailableDaysMap.get(orderEmployee);
			employeeUnavailableDays.addAll(orderDateList);		
		}
		ScheduledOrder scheduledOrder = new ScheduledOrder(order, orderStartDate, orderEndDate, employeesForOrder);

		
		return scheduledOrder;
	}
	
	private List<Employee> getAvailableEmployeesForPeriod(LocalDate startPeriod, LocalDate endPeriod,  Map<Employee, List<LocalDate>>  employeeUnavailableDaysMap){
		List<Employee> allEmployees = employeeService.getAll();
		List<Employee> availableEmployees = new ArrayList<>();
		for(Employee employee : allEmployees) {
			List<LocalDate> unavailableDays = employeeUnavailableDaysMap.get(employee);
			if(unavailableDays != null && !DateUtils.doesPeriodContainAnyDates(startPeriod, endPeriod, unavailableDays)){
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

	

