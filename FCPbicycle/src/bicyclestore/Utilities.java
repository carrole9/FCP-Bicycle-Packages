package bicyclestore;

import java.util.Calendar;
import java.util.Date;

/*
 * Utilities class contains general static methods 
 * Feel free to add more methods. Useful for general methods that can be 
 * used in multiple places without needing an instance
 */

public final class Utilities {
	
	// separate words contained in camelCase. E.g class name "RoadBike" will become "Road Bike"
	// Expression retrieved from:
	// http://stackoverflow.com/questions/2559759/how-do-i-convert-camelcase-into-human-readable-names-in-java
	public static String splitCamelCase(String s) {
		return s.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
				"(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
	}

	// returns true if the date passed in is in the current week
	public static boolean isDateInCurrentWeek(Date date) {
		Calendar currentCalendar = Calendar.getInstance();
		int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
		int year = currentCalendar.get(Calendar.YEAR);
		Calendar targetCalendar = Calendar.getInstance();
		targetCalendar.setTime(date);
		int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
		int targetYear = targetCalendar.get(Calendar.YEAR);
		return week == targetWeek && year == targetYear;
	}

}
