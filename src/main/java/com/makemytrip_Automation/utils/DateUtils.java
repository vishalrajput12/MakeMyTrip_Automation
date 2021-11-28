package com.makemytrip_Automation.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class DateUtils 
{
	public static String getTimeStamp() 
	{
		Date date =new Date();
		return date.toString().replaceAll(":","_").replaceAll(" ", "_");
	}
	/*
	public static void main(String []rags) 
	{
		//
		Date date=new Date();
	
		
		LocalDate d =LocalDate.now();
		
			  System.out.println( d.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)));
			
		

	}
	*/
	
	

}
