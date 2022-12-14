package com.info.moesapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class MoesClient {
	public HashMap<String, ArrayList<Results>> getConsolidateArticles()
			throws SAXException, IOException, ParserConfigurationException {
		
		//Making last 90 days date, for which result to be dragged from scopus API - Elesvier
		/*Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -90);
		String strDate90DaysBack = new SimpleDateFormat("yyyyMMdd").format(cal.getTime()); 
		System.out.println(strDate90DaysBack);*/
		
		//Picking the scopus Query & key for API call
		/*Properties prop = new Properties();
		InputStream input = null;
		try {
			input = getClass().getClassLoader().getResourceAsStream(
					"config.properties");
			prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != input) {
				input.close();
			}
		}
		//Queries and key for API call, singleAffCallQuery should replace 'AFFIDREPLACEMENT' by affid dynamically
		String singleAffCallQuery = prop.getProperty("singleAffScopusQuery");
		String queryKey = prop.getProperty("scopusKey");*/
		
		//List of affiliation for which result to be captured as API response
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
		
		JgateClient jc = new JgateClient();		
		ArrayList<String> apiReponse = jc.getAPIresponseFromDatabase();
		HashMap<String, ArrayList<Results>> affMap = new HashMap<>();
		//Client client = Client.create();
		
		//Making API call
		//for(String str : affiliationListMap.keySet()) {
		for(String str : apiReponse) {
			/*String urlString = singleAffCallQuery.replace("AFFIDREPLACEMENT", str) + strDate90DaysBack + queryKey;
			System.out.println("URL to be Hit >> " + urlString);
			WebResource webResource = client.resource(urlString);
			ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
			String output = response.getEntity(String.class);
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(new StringReader(output)));*/
			
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(new StringReader(str)));
			
			NodeList entryNodes = doc.getElementsByTagName("entry");
			int length = entryNodes.getLength();
			String affiliationId = "";
			String affiliationName = "";
			
			for (int i = 0; i < length; i++) {
				Element e = (Element) entryNodes.item(i);
				if (e.getNodeName().contains("entry")) {
	
					Results results = new Results();
					ArrayList<Results> resultList = new ArrayList<>();
	
					NodeList titleNodes = e.getElementsByTagName("dc:title");
					results.setTitle(Jsoup.parse(titleNodes.item(0).getTextContent().replaceAll("\"", "'")).text());
					
					NodeList UrlNodes = e.getElementsByTagName("link");
					for (int j = 0; j < UrlNodes.getLength(); j++) {
						Element element=(Element) UrlNodes.item(j);
						if(element.getAttribute("ref").equals("scopus"))
						//System.out.println("link="+element.getAttribute("href"));
						results.setArticleUrl(element.getAttribute("href"));
					}
	
					NodeList publicationNameNodes = e.getElementsByTagName("prism:publicationName");
	
					results.setPublication(publicationNameNodes.item(0).getTextContent());
					
					NodeList volumeNodes = e.getElementsByTagName("prism:volume");
					if(null != volumeNodes.item(0))
						results.setVolume(volumeNodes.item(0).getTextContent());
					
					NodeList pageRangeNodes = e.getElementsByTagName("prism:pageRange");
					if(null != pageRangeNodes.item(0))
						results.setPageNumber(pageRangeNodes.item(0).getTextContent());
					
					NodeList issueIdentifierNodes = e.getElementsByTagName("prism:issueIdentifier");
					if(null != issueIdentifierNodes.item(0))
						results.setIssueIdentifier(issueIdentifierNodes.item(0).getTextContent());
					
					NodeList YearNode = e.getElementsByTagName("prism:coverDate");
					
					results.setYear(YearNode.item(0).getTextContent().substring(0, 4));
	
					NodeList authorNodes = e.getElementsByTagName("author");
					StringBuffer authorList = new StringBuffer();
					ArrayList<String> authorArrayList=new ArrayList<>();
					for (int j = 0; j < authorNodes.getLength(); j++) {
						Element e1 = (Element) authorNodes.item(j);
	
						if(null != e1.getElementsByTagName("afid").item(0))
						{
						//if (affidList.contains(e1.getElementsByTagName("afid").item(0).getTextContent())) {
							if(authorArrayList.contains(e1.getElementsByTagName("surname").item(0).getTextContent()))
								continue;
							if (authorList.length() > 0)
								authorList.append(", ");
							authorArrayList.add(e1.getElementsByTagName("surname").item(0).getTextContent());
							authorList.append(e1.getElementsByTagName("surname").item(0).getTextContent());
							authorList.append(",");
							authorList.append(e1.getElementsByTagName("initials").item(0).getTextContent());
						//}
						}
					}
	
					results.setAuthor(authorList.toString());
	
	
					NodeList affiliationNodes = e.getElementsByTagName("affiliation");
					for (int j = 0; j < affiliationNodes.getLength(); j++) {
						Element e1 = (Element) affiliationNodes.item(j);
						affiliationId = e1.getElementsByTagName("afid").item(0).getTextContent();
						//affiliationName = e1.getElementsByTagName("affilname").item(0).getTextContent();
						affiliationName = affiliationListMap.get(affiliationId);
						if (affidList.contains(affiliationId)) {
	
							if (!resultList.contains(results))
								resultList.add(results);
	
							if (null != affMap.get(affiliationName)) {
								ArrayList<Results> existingList = new ArrayList<>(affMap.get(affiliationName));
								if(!existingList.contains(results))
								{
									existingList.add(results);
									affMap.put(affiliationName, existingList);
								}	
							} else {
								affMap.put(affiliationName, resultList);
							}
						}
	
					}
				}
			}
		}
		return affMap;
	}
	
	public HashMap<String, ArrayList<Results>> getArticles()
			throws SAXException, IOException, ParserConfigurationException {
		
		HashMap<String, String> affiliationListMap=new HashMap<>();
			
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
		
		Date d=new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(d);
		cal.add(Calendar.DAY_OF_MONTH, -90);
		Date today90 = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String strDate90DaysBack=formatter.format(today90);  
		
		
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = getClass().getClassLoader().getResourceAsStream(
					"config.properties");
			prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != input) {
				input.close();
			}
		}
		
		
		String queryString=prop.getProperty("scopusQuery");
		String queryKey=prop.getProperty("scopusKey");
		
		
		Client client = Client.create();
		//String resourceUrlString = "https://api.elsevier.com/content/search/scopus?query=af-id(60024474)+OR+af-id(60029040)+OR+af-id(60103916)+OR+af-id(60029763)+OR+af-id(60109973)+OR+af-id(60110135)+OR+af-id(60022480)+OR+af-id(60097177)+OR+af-id(60021495)+OR+af-id(60029709)+AND+orig-load-date+aft+"+strDate90DaysBack+"&apiKey=6530011ac00ab88c1911c7741c66d412&insttoken=1cb3904ea6d86261533e5f40b8093259&view=COMPLETE";
		//String resourceUrlString = queryString+strDate90DaysBack+queryKey;
		String resourceUrlString = "https://api.elsevier.com/content/search/scopus?query=af-id(60029040)+AND+orig-load-date+aft+20201121&apiKey=6530011ac00ab88c1911c7741c66d412&insttoken=1cb3904ea6d86261533e5f40b8093259&view=COMPLETE";
		String resourceUrl = resourceUrlString;

		ArrayList<String> affidList = new ArrayList<>();

		while (resourceUrl.indexOf("af-id(") > 0) {
			int firstIndexTemp = resourceUrl.indexOf("af-id(");
			int firstIndexBracket = resourceUrl.indexOf(")");

			String temp = resourceUrl.substring(resourceUrl.indexOf("af-id(", firstIndexTemp) + 6,
					resourceUrl.indexOf(")", firstIndexBracket));
			affidList.add(temp);

			resourceUrl = resourceUrl.substring(firstIndexBracket + 1, resourceUrl.length());

		}
		
		HashMap<String, ArrayList<Results>> affMap = new HashMap<>();

		WebResource webResource = client.resource(resourceUrlString);
		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
		String output = response.getEntity(String.class);
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new InputSource(new StringReader(output)));

		NodeList entryNodes = doc.getElementsByTagName("entry");
		int length = entryNodes.getLength();
		String affiliationId = "";
		String affiliationName = "";
		for (int i = 0; i < length; i++) {
			Element e = (Element) entryNodes.item(i);
			if (e.getNodeName().contains("entry")) {

				Results results = new Results();
				ArrayList<Results> resultList = new ArrayList<>();

				NodeList titleNodes = e.getElementsByTagName("dc:title");
				results.setTitle(Jsoup.parse(titleNodes.item(0).getTextContent().replaceAll("\"", "'")).text());
				
				NodeList UrlNodes = e.getElementsByTagName("link");
				for (int j = 0; j < UrlNodes.getLength(); j++) {
					Element element=(Element) UrlNodes.item(j);
					if(element.getAttribute("ref").equals("scopus"))
					//System.out.println("link="+element.getAttribute("href"));
						results.setArticleUrl(element.getAttribute("href"));
					
				}

				NodeList publicationNameNodes = e.getElementsByTagName("prism:publicationName");

				results.setPublication(publicationNameNodes.item(0).getTextContent());
				
				NodeList volumeNodes = e.getElementsByTagName("prism:volume");
				if(null != volumeNodes.item(0))
					results.setVolume(volumeNodes.item(0).getTextContent());
				
				
				NodeList pageRangeNodes = e.getElementsByTagName("prism:pageRange");
				if(null != pageRangeNodes.item(0))
					results.setPageNumber(pageRangeNodes.item(0).getTextContent());
				
				NodeList issueIdentifierNodes = e.getElementsByTagName("prism:issueIdentifier");
				if(null != issueIdentifierNodes.item(0))
					results.setIssueIdentifier(issueIdentifierNodes.item(0).getTextContent());
				
				NodeList YearNode = e.getElementsByTagName("prism:coverDate");
				
				results.setYear(YearNode.item(0).getTextContent().substring(0, 4));

				NodeList authorNodes = e.getElementsByTagName("author");
				StringBuffer authorList = new StringBuffer();
				ArrayList<String> authorArrayList=new ArrayList<>();
				for (int j = 0; j < authorNodes.getLength(); j++) {
					Element e1 = (Element) authorNodes.item(j);

					if(null != e1.getElementsByTagName("afid").item(0))
					{
					//if (affidList.contains(e1.getElementsByTagName("afid").item(0).getTextContent())) {
						if(authorArrayList.contains(e1.getElementsByTagName("surname").item(0).getTextContent()))
							continue;
						if (authorList.length() > 0)
							authorList.append(", ");
						authorArrayList.add(e1.getElementsByTagName("surname").item(0).getTextContent());
						authorList.append(e1.getElementsByTagName("surname").item(0).getTextContent());
						authorList.append(",");
						authorList.append(e1.getElementsByTagName("initials").item(0).getTextContent());
					//}
					}

				}

				results.setAuthor(authorList.toString());


				NodeList affiliationNodes = e.getElementsByTagName("affiliation");
				for (int j = 0; j < affiliationNodes.getLength(); j++) {
					Element e1 = (Element) affiliationNodes.item(j);
					affiliationId = e1.getElementsByTagName("afid").item(0).getTextContent();
					//affiliationName = e1.getElementsByTagName("affilname").item(0).getTextContent();
					affiliationName = affiliationListMap.get(affiliationId);
					if (affidList.contains(affiliationId)) {

						if (!resultList.contains(results))
							resultList.add(results);

						if (null != affMap.get(affiliationName)) {
							ArrayList<Results> existingList = new ArrayList<>(affMap.get(affiliationName));
							if(!existingList.contains(results))
							{
								existingList.add(results);
								affMap.put(affiliationName, existingList);
							}	
						} else {
							affMap.put(affiliationName, resultList);
						}
					}

				}
			}
		}
		return affMap;
	}

}

