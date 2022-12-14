package com.info.moesapi;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

@Path("/webservices")
public class Webcontroller {
	
	
	/*
	 * @GET
	 * 
	 * @Path("/getresponse")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Document showMessages() throws
	 * SAXException, IOException, ParserConfigurationException { return
	 * GenerateResponse.getLatestResults(); }
	 */
	
	
	@GET
	@Path("/getresponse")
	@Produces(MediaType.APPLICATION_JSON)
	public Document showJgateResponse() throws SAXException, IOException, ParserConfigurationException 
	{
		System.out.println("showResponse");
		//return GenerateResponse.getJgateLatestResults();
		return GenerateResponse.getLatestResults();
		//return GenerateResponse.getConsolidateArticles();
	}
	
	@GET
	@Path("/updateresponse")
	@Produces(MediaType.TEXT_HTML)
	public String updateAPIResponseToDB() {
		System.out.println("Inside update api call");
		JgateClient jc = new JgateClient();	
		jc.updateAPIResponse();
		return "updated successfully!";
	}
}