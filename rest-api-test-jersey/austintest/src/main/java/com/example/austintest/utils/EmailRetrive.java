package com.example.austintest.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author austin
 *
 */
public class EmailRetrive {

	private final static Pattern emailer = Pattern.compile("[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+");

	public static String retriveValidMail(String text)  {

		Matcher matchr = emailer.matcher(text);
		StringBuffer sb = new StringBuffer();
		sb.append(" ");
		while (matchr.find()) {

			String email = matchr.group();
			sb.append(email.trim()).append(",");
			System.out.println(email);

		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

}
