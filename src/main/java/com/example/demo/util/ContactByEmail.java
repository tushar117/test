package com.example.demo.util;

public class ContactByEmail {
	private String name;
	private String contact;
	
	public ContactByEmail(){
		this.name=null;
		this.contact=null;
	}
	
	public ContactByEmail(String name, String contact){
		this.name=name;
		this.contact=contact;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getContact(){
		return this.contact;
	}
}
