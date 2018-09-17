
package com.br.glossario.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;

public class UtilsDateTime { 
	
	private Context context;
	
	public UtilsDateTime(Context context) {
		this.context = context;
	}

	public long setTimeAlarm(String miliDate, int tempoAlarme){
		
		long dateTimeAlarm = 0;
		
		Long DataHora = Long.parseLong(miliDate);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
		
		String dateString = dateFormat.format(new Date(DataHora));
		Date convertedDate = new Date();
		
		int alarme = tempoAlarme * (-1);
		
		try {
			convertedDate = dateFormat.parse(dateString);
						
			Calendar dataHora = Calendar.getInstance();			
						
			dataHora.setTimeInMillis(convertedDate.getTime());
			dataHora.add(Calendar.MINUTE, alarme);
			
			dateTimeAlarm = dataHora.getTimeInMillis();						
			
		} catch (ParseException e) {
			e.printStackTrace();
		} 	
		
		return dateTimeAlarm;
	}
	
	public static String sendFormatDate(String miliDate){
		
		String dataHora = null;
		
		String date ;
		String hour;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat hourFormat = new SimpleDateFormat("HHmmss");
		
		Long DataHora = Long.parseLong(miliDate);		
		
			date = dateFormat.format(new Date(DataHora));
			hour = hourFormat.format(new Date(DataHora));
			
			dataHora = date + "_" + hour; 				
		
		return dataHora;
	}
	
	
	public static Long formatDateStringLong(String date, String time){
		
		Date convertedDate = new Date();
		
		String dataHora =  date + " " + time;		
				
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		
		try {
			convertedDate = dateFormat.parse(dataHora);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar calendar = Calendar.getInstance();	
		
		calendar.setTimeInMillis(convertedDate.getTime());
		
		Long DataHora = calendar.getTimeInMillis();		
			
		System.out.println("DataHora Milisegundo: " + DataHora); 				
		
		return DataHora;
	}
	
	public static String formatDateLongString(String miliDate){
		
		String dataHora = null;
		
		String date ;
		String hour;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
		
		Long DataHora = Long.parseLong(miliDate);		
		
			date = dateFormat.format(new Date(DataHora));
			hour = hourFormat.format(new Date(DataHora));
			
			dataHora = date + "   " + hour; 				
		
		return dataHora;
	}
}
