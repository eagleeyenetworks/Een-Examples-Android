package com.eagleeye.examples.util;

import java.text.DecimalFormat;
import java.util.Arrays;

public class UtilString {

	public static String convertIntToString(int num, int digits) {
		if(num <= 0) {
			return "";}
		
		// create variable length array of zeros
		char[] zeros = new char[digits];
		Arrays.fill(zeros, '0');
		// format number as String
		DecimalFormat df = new DecimalFormat(String.valueOf(zeros));

		return df.format(num);
	}
	
}
