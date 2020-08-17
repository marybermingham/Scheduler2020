package scheduler.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DateUtils {
	
	public static LocalDate getDateFromString(String dateString){

        try {
    		LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yy"));
            return date;

        } catch (DateTimeParseException e) {
            System.out.println("Wrong format!");
        	return null;
        }
	}

	public static boolean doesPeriodContainAnyDates(LocalDate startPeriod, LocalDate endPeriod, List<LocalDate> dates)	{
		for (LocalDate date =  startPeriod; !date.isAfter(endPeriod); date = date.plusDays(1)) {
			if(dates.contains(date)) {
				return true;
			}			
		}
		return false;
	}
	
	public static List<LocalDate> toDateList(LocalDate startPeriod, LocalDate endPeriod){
		List<LocalDate> dateList = new ArrayList<>();
		for (LocalDate date =  startPeriod; !date.isAfter(endPeriod); date = date.plusDays(1)) {
			dateList.add(date);
		}
		return dateList;
	}
	

	public static LocalDate getEarliestDate(List<LocalDate> dates) {
		
		LocalDate earliestDate = dates.get(0);
		for(LocalDate date : dates) {
			if(date.isBefore(earliestDate)) {
				earliestDate = date;
			}
		}
		return earliestDate;
	}
	
	public static LocalDate getLatestDate(List<LocalDate> dates) {
		LocalDate latestDate = dates.get(0);
		for(LocalDate date : dates) {
			if(date.isAfter(latestDate)) {
				latestDate = date;
			}
		}
		return latestDate;
	}
}
