package com.pasahero.android;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Utils {

	private static SimpleDateFormat shortTime;
	private static DecimalFormat shortDistance;

	public static Object ifExists(Object object) {
		try {
			return object;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public static String insertToTemplate(String template, String pattern,
			String insert) {
		return template.replace(pattern, insert);
	}

	public static String insertToTemplate(String template,
			Map<String, String> patternPairs) {
		for (Map.Entry<String, String> pair : patternPairs.entrySet()) {
			String key = pair.getKey();
			String value = pair.getValue();
			template = template.replace(key, value);
			System.out.println("pattern: " + key + "\n" + "data: " + value);
			System.out.println("inserted: " + template);
		}
		return template;
	}

	public static Object assignIfNotNull(Object... objects) {
		int count = 0;
		while (objects[count] == null && count < objects.length) {
			count++;
		}
		return objects[count];
	}

	public static String getShortTime(Date date) {
		try {
			return shortTime.format(date);
		} catch (NullPointerException e) {
			shortTime = new SimpleDateFormat(Config.SHORT_DATE_FORMAT);
		}
		return shortTime.format(date);
	}

	public static String toDurationReadable(long millis) {
		return String.format(Config.DURATION_READABLE_FORMAT,
				TimeUnit.MILLISECONDS.toMinutes(millis));
	}

	public static String trimDistance(double meters) {
		try {
			return shortDistance.format(meters);
		} catch (NullPointerException e) {
			shortDistance = new DecimalFormat(Config.DISTANCE_FORMAT);
		}
		return shortDistance.format(meters);
	}
}
