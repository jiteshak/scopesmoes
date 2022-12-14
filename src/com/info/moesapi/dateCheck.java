package com.info.moesapi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

public class dateCheck {

	public static void main(String[] args) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -90);
		String strDate90DaysBack = new SimpleDateFormat("yyyyMMdd").format(cal.getTime()); 
		System.out.println(strDate90DaysBack);
		
		/*
		ArrayList<String> affidList = new ArrayList<>();
		String resourceUrl = "https://api.elsevier.com/content/search/scopus?query=af-id(60024474)+OR+af-id(60029040)+OR+af-id(60103916)+OR+af-id(60029763)+OR+af-id(60109973)+OR+af-id(60110135)+OR+af-id(60022480)+OR+af-id(60097177)+OR+af-id(60021495)+OR+af-id(60029709)+AND+orig-load-date+aft+20201120&apiKey=6530011ac00ab88c1911c7741c66d412&insttoken=1cb3904ea6d86261533e5f40b8093259&view=COMPLETE";
		System.out.println(resourceUrl);
		System.out.println("-----------------------------");
		while (resourceUrl.indexOf("af-id(") > 0) {
			int firstIndexTemp = resourceUrl.indexOf("af-id(");
			int firstIndexBracket = resourceUrl.indexOf(")");

			String temp = resourceUrl.substring(resourceUrl.indexOf("af-id(", firstIndexTemp) + 6,
					resourceUrl.indexOf(")", firstIndexBracket));
			affidList.add(temp);

			resourceUrl = resourceUrl.substring(firstIndexBracket + 1, resourceUrl.length());

		}
		System.out.println(resourceUrl);
		
		LinkedHashMap<String, String> affiliationListMap = new LinkedHashMap<>();		
		affiliationListMap.put("60110135", "National Center of Coastal Research (NCCR)");
		affiliationListMap.put("60109973", "Centre for Marine Living Resources & Ecology (CMLRE)");
		affiliationListMap.put("60103916", "Indian National Centre for Ocean Information Services (INCOIS)");
		affiliationListMap.put("60029040", "Indian Institute of Tropical Meteorology (IITM)");
		affiliationListMap.put("60029709", "National Centre for Earth Science Studies (NCESS)");
		affiliationListMap.put("60024474", "National Centre for Polar and Ocean Research (NCPOR)");
		affiliationListMap.put("60029763", "National Institute of Ocean Technology India (NIOT)");
		affiliationListMap.put("60097177", "Ministry of Earth Sciences (MoES)");
		affiliationListMap.put("60022480", "National Centre for Medium Range Weather Forecasting (NCMRWF)");
		affiliationListMap.put("60021495", "India Meteorological Department (IMD)");
		System.out.println("AAAAAAAAA " + affiliationListMap.keySet());
		
		ArrayList<String> affidListFromMap = new ArrayList<>(affiliationListMap.keySet());
		System.out.println(affidListFromMap);
		*/
	}

}
