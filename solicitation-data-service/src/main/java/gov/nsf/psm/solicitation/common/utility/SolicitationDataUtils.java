package gov.nsf.psm.solicitation.common.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolicitationDataUtils {

	private final static Logger LOGGER = LoggerFactory.getLogger(SolicitationDataUtils.class);

	// A ThreadLocal copy of the date format that will allow it to be safely
	// used in multi-threaded environments
	// Each thread will have a local copy of this variable
	private static final ThreadLocal<DateFormat> twoDigitYearFormat = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yy");
		}
	};

	/**
	 * no-args private constructor
	 */
	private SolicitationDataUtils() {

	}

	/**
	 * Method to check if the String is null. If not null, then trim it.
	 * Otherwise return null.
	 * 
	 * @param value
	 */
	public static String getStringValue(String value) {
		return value != null ? value.trim() : null;
	}

	/**
	 * Method to extract Integer from String. Otherwise return null.
	 * 
	 * @param value
	 */
	public static Integer extractIntegerFromString(String value) {
		// sanitize numeric string
		return value != null ? Integer.valueOf(value.replaceAll("[^\\d]", "")) : null;
	}

	/**
	 * Method to convert a 2-digit String (YY) to Date.
	 * 
	 * @param value
	 */
	public static Date convertStringToYear(String twoDigitYearString) {
		try {
			return twoDigitYearFormat.get().parse(twoDigitYearString);
		} catch (ParseException e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Method to generate an SQL IN clause input string from an array of strings
	 * 
	 * @param value
	 */
	public static String getSQLStringListFromArray(String[] inputs) {
		StringJoiner joiner = new StringJoiner("','", "'", "'");
		for (int i = 0; i < inputs.length; i++) {
			joiner.add(inputs[i]);
		}
		return joiner.toString();
	}
	
	/**
	 * 
	 * @param inputs
	 * @return
	 */
	public static List<String> convertStringToList(String inputs) {
		List<String> ids = new ArrayList<String>();
		StringTokenizer st2 = new StringTokenizer(inputs, ",");
		while (st2.hasMoreElements()) {
			String foId = st2.nextElement().toString();
			ids.add(foId);
		}
		return ids;

	}

	/**
	 * 
	 * @param string
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringtoDate(String string) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		return formatter.parse(string);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String convertDatetoString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		return formatter.format(date);
	}

}
