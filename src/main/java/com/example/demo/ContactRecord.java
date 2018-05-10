package com.example.demo;
import javax.persistence.*;

@Entity
public class ContactRecord {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String contact;
	
	private String email;
	
	public ContactRecord() {
		System.out.println("In here");
	}
	
	public ContactRecord(final String name, final String contact, final String email) {
		this.name = name;
		this.contact = contact;
		this.email = email;
	}
	
	public Long getId(){
		return this.id;
	}
	
	public void setId(Long id){
		this.id=id;
	}
	
	public String getContactName(){
		return this.name;
	}
	
	public void setContactName(String name){
		this.name=name;
	}
	
	public String getContactNumber() {
		return this.contact;
	}
	
	public void setContactNumber(String contact) {
		this.contact=contact;
	}
	
	public String getContactEmail() {
		return this.email;
	}
	
	public void setContactEmail(String email) {
		this.email=email;
	}
}
