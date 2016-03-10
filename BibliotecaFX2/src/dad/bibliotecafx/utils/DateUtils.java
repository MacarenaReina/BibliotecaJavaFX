package dad.bibliotecafx.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateUtils {
	private static final String DATE_PATTERN = "dd/MM/yyyy";
	private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);
	private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

	public static String format(LocalDate date) {
		if (date == null) {
			return null;
		}
		return FORMATTER.format(date);
	}

	public static LocalDate parse(String dateString) {
		try {
			return FORMATTER.parse(dateString, LocalDate::from);
		} catch (DateTimeParseException e) {
			return null;
		}
	}
	
	public static LocalDate toLocalDate(Date date){
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Date toDate(LocalDate localDate) throws ParseException{
		return SIMPLE_DATE_FORMAT.parse(format(localDate));    	
	}
	
	public static String toStringDate(Date date){
		return SIMPLE_DATE_FORMAT.format(date);
	}

	public static boolean validDate(String dateString) {
		return DateUtils.parse(dateString) != null;
	}
}
