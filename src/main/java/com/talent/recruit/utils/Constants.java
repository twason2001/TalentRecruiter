package com.talent.recruit.utils;

public class Constants {
	
	private Constants() {
	    throw new IllegalStateException("Utility class");
	  }
	
	public static final String TITLE_EXIST="Job Title %s already exist";
	public static final String APPLICATION_EXIST="Application already exist with status %s";
	public static final String OFFER_NOT_FOUND="Offer Id %s is not valid";
	public static final String EMAIL_NOT_FOUND="Email Id %s is not valid";
	public static final String SERVER_ERROR="Something went wrong. Please try again later!";
}
