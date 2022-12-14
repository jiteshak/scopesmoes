package com.info.moesapi;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DatabaseResult {
	public String title;
	public String articleUrl;
	public String publication;
	public String year;
	public String author;
	public String volume;
	public String pageNumber;
	public String issueIdentifier;
	public String instName;
	
	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArticleUrl() {
		return articleUrl;
	}

	public void setArticleUrl(String articleUrl) {
		this.articleUrl = articleUrl;
	}

	public String getPublication() {
		return publication;
	}

	public void setPublication(String publication) {
		this.publication = publication;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public String getIssueIdentifier() {
		return issueIdentifier;
	}

	public void setIssueIdentifier(String issueIdentifier) {
		this.issueIdentifier = issueIdentifier;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\n" + title + ",\n" + publication;
	}

}
