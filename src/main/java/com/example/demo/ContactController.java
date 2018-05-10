package com.example.demo;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.util.ContactByEmail;
import com.example.demo.util.ContactByName;
import com.example.demo.util.ContactData;
import com.example.demo.util.ContactOperation;

@RestController
public class ContactController {
	private ContactOperation contactOperation;
    
	ContactController() {
		// TODO Auto-generated constructor stub
    	this.contactOperation = new ContactOperation(new ContactData());
	}
    
    @PostMapping(value="/contact")
    public ResponseEntity<?> createContact(@RequestBody ContactRecord newContact) {
        boolean insertFlag = this.contactOperation.addContact(newContact.getContactName(), newContact.getContactEmail(), newContact.getContactNumber());
        if (insertFlag) {
        	System.out.println("Created successfully");
            return new ResponseEntity<String>(new String("Contact created successfully"), HttpStatus.CREATED);
        }else{
        	System.out.println("Unable to create record");
        	return new ResponseEntity<String>(new String("Contact already exists"), HttpStatus.CONFLICT);
        }
    }
    
    //search contact by email
    @GetMapping("/contact/search")
    public ContactByEmail searchEmail(@RequestParam(value="email") String email){
    	return this.contactOperation.searchByEmail(email);
    }

    //search contact by name
    @GetMapping("/contacts/search")
    public ArrayList<ContactRecord> searchName(@RequestParam(value="name") String name){
    	ContactByName contacts = this.contactOperation.searchByName(name);
    	ArrayList<ContactRecord> response = new ArrayList<>();
    	for(int i=0;i<contacts.getEmailList().size();i++){
    		ContactRecord responseTuple = new ContactRecord(name, contacts.getEmailList().get(i), contacts.getContactList().get(i));
    		response.add(responseTuple);
    	}
    	return response;
    }
    
    @DeleteMapping("/contact/{email}")
	public void deleteStudent(@PathVariable String email) {
    	ContactByEmail targetContact = this.contactOperation.searchByEmail(email);
		this.contactOperation.deleteContact(targetContact.getName(), email);
    }
    
    @PutMapping("/contact/{email}")
	public ResponseEntity<String> updateContact(@RequestBody ContactRecord newContact, @PathVariable String email) {
    	boolean deleteFlag = this.contactOperation.modify(newContact.getContactName(), email, newContact.getContactNumber());
    	if(deleteFlag){
    		return new ResponseEntity<String>("Successfully modified !!!", null);
    	}else{
    		return new ResponseEntity<String>("Unable to modify entry !!!", null);
    	}
    }
    
}
