package com.example.demo.util;

import java.util.*;

class NodeForNameSearch{
    private ArrayList<String> emailList;
    private ArrayList<String> contactList;
    NodeForNameSearch[] next;

    public NodeForNameSearch(){
        this.emailList=null;
        this.contactList=null;
        next = new NodeForNameSearch[256];
    }

    public NodeForNameSearch(String email,String contact){
        if(this.emailList==null)
            this.emailList = new ArrayList<>();
        if(this.contactList==null)
            this.contactList = new ArrayList<>();
        emailList.add(email);
        contactList.add(contact);
        next = new NodeForNameSearch[256];
    }

    public ArrayList<String> getEmailList(){
        return this.emailList;
    } 

    public boolean addEntry(String email, String contact){
        if(!validContact()){
            this.emailList = new ArrayList<>();
            this.contactList = new ArrayList<>();
        }
        if(email==null || contact==null || email.length()==0 || contact.length()<10 || this.emailList.contains(email)){
            return false;
        }
        this.contactList.add(contact);
        this.emailList.add(email);
        return true;
    }

    public ArrayList<String> getContactList(){
        return this.contactList;
    }

    public boolean modifyEntry(String email, String contact){
        if(contact==null || contact.length()<10 || !this.validContact())
            return false;
        if(contactList.size()==emailList.size()){
            for(int i=0;i<this.emailList.size();i++){
                 if(this.emailList.get(i).equalsIgnoreCase(email)){
                     this.contactList.set(i, contact);
                     return true;
                 }
            }
        }
        return false;
    }

    public boolean deleteEntry(String email){
        if(!this.validContact())
            return false;
        if(this.contactList.size()==this.emailList.size()){
            for(int i=0;i<this.emailList.size();i++){
                 if(this.emailList.get(i).equalsIgnoreCase(email)){
                     this.emailList.remove(i);
                     this.contactList.remove(i);
                     return true;
                 }
            }
        }
        if(this.contactList.isEmpty()){
            this.contactList=null;
            this.emailList=null;
        }
        return false;
    }

    public boolean validContact(){
        return this.contactList!=null && this.emailList!=null;
    }
}

