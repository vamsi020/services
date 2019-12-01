package com.vamsi.newsletter.subscriptionapi.util;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.format.datetime.DateFormatter;

public final class GenericUtil {

	public static Date castStringToDate(String dateString) {
		try {
			DateFormatter dateFormatter = new DateFormatter("yyyy-MM-dd HH:mm:ss");
			return dateFormatter.parse(dateString, Locale.ENGLISH);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern regexPattern = Pattern.compile(emailRegex);
		return regexPattern.matcher(email).matches();
	}

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