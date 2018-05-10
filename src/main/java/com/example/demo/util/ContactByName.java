package com.example.demo.util;

import java.util.ArrayList;

public class ContactByName {
	private ArrayList<String> email;
	private ArrayList<String> contact;
	
	public ContactByName(){
		this.email=null;
		this.contact=null;
	}
	
	public ContactByName(ArrayList<String> email, ArrayList<String> contact){
		this.email=email;
		this.contact=contact;
	}
	
	public ArrayList<String> getEmailList(){
		return this.email;
	}
	
	public ArrayList<String> getContactList(){
		return this.contact;
	}
}
