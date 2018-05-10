package com.example.demo.util;

class NodeForEmailSearch{
    private String name;
    private String contactNumber;
    NodeForEmailSearch[] next;

    public NodeForEmailSearch(){
        this.name=null;
        this.contactNumber=null;
        next = new NodeForEmailSearch[256];
    }

    public NodeForEmailSearch(String name,String contact){
        this.name=name;
        this.contactNumber=contact;
        next = new NodeForEmailSearch[256];
    }

    public String getName(){
        return this.name;
    } 

    public boolean setName(String name){
        if(name==null || name.length()==0){
            this.clearContact();
            return false;
        }
        this.name=name;
        return true;
    }

    public String getContact(){
        return this.contactNumber;
    }

    public boolean setContact(String contact){
        if(contact==null || contact.length()<10){
            this.clearContact();
            return false;
        }
        this.contactNumber=contact;
        return true;
    }

    public void clearContact(){
        this.contactNumber=null;
        this.name=null;
    }

    public boolean validContact(){
        return this.name!=null && this.contactNumber!=null;
    }
}
