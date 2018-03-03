package com.spgroup.friendsmanagement.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FriendsMgmtUtils {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public static boolean isValidEmail(String emailAddress) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(emailAddress);
		return matcher.matches();
	}

	public static List<String> findEmailsinString(String text) {
		List<String> emails = new ArrayList<String>();
		Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(text);
		while (m.find()) {
			emails.add(m.group());
		}
		return emails;
	}

}
