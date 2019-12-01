package com.vamsi.newsletter.subscriptionapi.util;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.format.datetime.DateFormatter;

/**
 * 
 * @author Vamsi Krihna
 * 
 *         Generic utility class for Newsletter API
 *
 */
public final class GenericUtil {

	/**
	 * Reg Ex for Email validation
	 */
	public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

	/**
	 * Cast date string to Date
	 * 
	 * @param dateString
	 * @return Date or null
	 */
	public static Date castStringToDate(String dateString) {
		try {
			DateFormatter dateFormatter = new DateFormatter("yyyy-MM-dd HH:mm:ss");
			return dateFormatter.parse(dateString, Locale.ENGLISH);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email) {
		Pattern regexPattern = Pattern.compile(EMAIL_REGEX);
		return regexPattern.matcher(email).matches();
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isValidDate(String date) {
		try {
			LocalDateTime.parse(date);
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}