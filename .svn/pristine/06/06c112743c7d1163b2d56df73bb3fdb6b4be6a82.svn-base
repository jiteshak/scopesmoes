package com.info.moesapi.connection;

import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.info.moesapi.DatabaseResult;
import com.mysql.jdbc.Connection;

public class DBConnection {
	static Connection con;
	public void getDBConnection() throws Exception {
		InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
		Properties props = new Properties();
		props.load(input);
		Class.forName("com.mysql.jdbc.Driver");
		String sURL = props.getProperty("DB_URL");
		String username = props.getProperty("DB_USERNAME");
		String password = props.getProperty("DB_PASSWORD");
		con = (Connection)DriverManager.getConnection(sURL, username, password);
	}
	
public ArrayList<DatabaseResult> getResultFromDB(String q) {
	ArrayList<DatabaseResult> result = new ArrayList<DatabaseResult>();
	try {
		getDBConnection();
		java.sql.Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(q);
		
		while(rs.next()) {
			DatabaseResult dbr = new DatabaseResult();
			dbr.setInstName(rs.getString("institute_name"));
			dbr.setTitle(rs.getString("article_name"));
			dbr.setPublication(rs.getString("journal_name"));
			dbr.setAuthor(rs.getString("author_names"));
			dbr.setVolume(rs.getString("volume_no"));
			dbr.setPageNumber(rs.getString("page_no"));
			dbr.setYear(rs.getString("created_date"));
			dbr.setArticleUrl(rs.getString("article_url"));
			result.add(dbr);
		}
		
	} catch(Exception e) {
		e.printStackTrace();
	} finally {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return result;
}

	public ArrayList<String> getApiResponseFromDB(String query) {
		//String output = "";
		ArrayList<String> result = new ArrayList<String>();
		try {
			getDBConnection();
			java.sql.Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				result.add(rs.getString("resultOutput"));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int updateApiResponse(String query, String affid, String apiResponse) {
		int status = 0;
		try {
			getDBConnection();
			PreparedStatement pst = con.prepareStatement(query); 
			pst.setString(1, affid);
			pst.setString(2, apiResponse);
			status = pst.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return status;
	}
}
