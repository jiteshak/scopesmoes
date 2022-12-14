package com.info.moesapi;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;

import com.info.moesapi.connection.DBConnection;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class JgateClient {

	DBConnection dbc = new DBConnection();
	
	public HashMap<String, ArrayList<Results>> getArticles() {
		
		String query = "SELECT * FROM moes_publication_data";
		ArrayList<DatabaseResult> dbResult = dbc.getResultFromDB(query);
		HashMap<String, ArrayList<Results>> affMap = new HashMap<>();
		for(DatabaseResult dr : dbResult) {
			ArrayList<Results> res = 
				null != affMap.get(dr.getInstName()) ? 
					new ArrayList<Results>(affMap.get(dr.getInstName())) : new ArrayList<Results>();
			Results resultElement = new Results(dr.getTitle(), dr.getArticleUrl(), dr.getPublication(), dr.getYear(),
					dr.getAuthor(), dr.getVolume(), dr.getPageNumber(), dr.getIssueIdentifier());
			res.add(resultElement);
			affMap.put(dr.getInstName(), res);
			/*
			 * if(res.size() > 0) {
			 * 
			 * } else { res.add(resultElement); affMap.put(dr.getInstName(), res); }
			 */
		}					
		return affMap;
	}
	
	public ArrayList<String> getAPIresponseFromDatabase() {
		String query = "CALL get_response_fromdatabase();";
		ArrayList<String> apiresponseResult = dbc.getApiResponseFromDB(query);
		return apiresponseResult;
	}
	
	public void updateAPIResponse() {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -90);
		String strDate90DaysBack = new SimpleDateFormat("yyyyMMdd").format(cal.getTime()); 
		
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
		affiliationListMap.put("60199757", "National Centre for Seismology (NCS)");

		ArrayList<String> affidList = new ArrayList<>(affiliationListMap.keySet());
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = getClass().getClassLoader().getResourceAsStream(
					"config.properties");
			prop.load(input);
			if (null != input) {
				input.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//Queries and key for API call, singleAffCallQuery should replace 'AFFIDREPLACEMENT' by affid dynamically
		String singleAffCallQuery = prop.getProperty("singleAffScopusQuery");
		String queryKey = prop.getProperty("scopusKey");
		String insertQuery = "call update_apiresult(?, ?);";
		
		Client client = Client.create();
		
		for(String str : affidList) {
			String urlString = singleAffCallQuery.replace("AFFIDREPLACEMENT", str) + strDate90DaysBack + queryKey;
			System.out.println("URL to be Hit >> " + urlString);
			WebResource webResource = client.resource(urlString);
			ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
			String responseOutput = response.getEntity(String.class);
			System.out.print("Updating for affid[" + str + "]");
			int insertStatus = dbc.updateApiResponse(insertQuery, str, responseOutput);
			System.out.println(insertStatus > 0 ? " Status : updated " : " status : failed to update ");
		}
	}
}

