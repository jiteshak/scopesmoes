package com.info.moesapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class GenerateResponse {

	public static Document getLatestResults() throws SAXException, IOException, ParserConfigurationException {
		Document d = new Document();
		
		MoesClient client=new MoesClient();
		
		/*HashMap<String, ArrayList<Results>> mapResults = client.getArticles();
		System.out.println(mapResults);*/
		HashMap<String, ArrayList<Results>> mapResultsNew = client.getConsolidateArticles();
		//System.out.println(mapResults);
		//System.out.println(mapResultsNew);
		//d.setAffiliationResult(mapResults);
		d.setAffiliationResult(mapResultsNew);
		return d;

	}
	
	public static Document getJgateLatestResults() throws SAXException, IOException, ParserConfigurationException {
		Document d = new Document();
		
		JgateClient client=new JgateClient();
		
		HashMap<String, ArrayList<Results>> mapResults = client.getArticles();

		d.setAffiliationResult(mapResults);
		return d;

	}

}
