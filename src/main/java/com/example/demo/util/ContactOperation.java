package com.example.demo.util;

public class ContactOperation {
    ContactData data;
    public ContactOperation(ContactData data){
        this.data=data;
        data.initializeTrie();
    }
    
    public boolean addContact(String name, String email, String phone){
        String[] nameToken = name.trim().split("\\s+");
        String processedName="";
        String processedEmail = email.toLowerCase();
        for(String token : nameToken){
            processedName+=token.toLowerCase();
        }
        boolean insertStatus = data.insertIntoTrieEmail(data.rootEmail, processedEmail, name, phone) && 
                data.insertIntoTrieName(data.rootName, processedName, email, phone);
        if(!insertStatus){
            System.out.println("Warning !!! Unable to insert data into Tries.");
        }
        return true;
    }
    
    public boolean deleteContact(String name, String email){
        boolean result=true;
        String[] nameToken = name.trim().split("\\s+");
        String processedName="";
        String processedEmail = email.toLowerCase();
        for(String token : nameToken){
            processedName+=token.toLowerCase();
        }
        result = result & data.deleteByName(data.rootName, processedName, processedEmail) & 
                data.deleteByEmail(data.rootEmail, processedEmail);
        if(!result)
            System.out.println("Warning!!! Unable to delete the contact.");
        return result;
    }
    
    public boolean modify(String name, String email, String contact){
        boolean result=false;
        String[] nameToken = name.trim().split("\\s+");
        String processedName="";
        String processedEmail = email.toLowerCase();
        NodeForEmailSearch targetContact = data.searchOnEmail(data.rootEmail, processedEmail);
        if(targetContact!=null && !targetContact.getName().equalsIgnoreCase(name)){
            //System.out.println("in here");
            for(String token : targetContact.getName().split("\\s+")){
                processedName+=token.toLowerCase();
            }
            boolean deleteResult = data.deleteByName(data.rootName, processedName, email);
            if(deleteResult && data.modifyOnEmail(data.rootEmail, processedEmail, name, contact)){
                processedName="";
                for(String token : nameToken){
                    processedName+=token.toLowerCase();
                }
                result=data.insertIntoTrieName(data.rootName, processedName, email, contact);
            }
        }else if(targetContact!=null){
            processedName="";
            for(String token : nameToken){
                processedName+=token.toLowerCase();
            }
            result=data.modifyOnEmail(data.rootEmail, processedEmail, name, contact) &
                    data.modifyOnName(data.rootName, processedName, email, contact);
        }
        return result;
    }
    
    public ContactByEmail searchByEmail(String email){
        String processedEmail = email.toLowerCase();
        NodeForEmailSearch contact = data.searchOnEmail(data.rootEmail, processedEmail);
        if(contact==null){
            System.out.println("No entries found!!!");
            return new ContactByEmail();
        }else{
            return new ContactByEmail(contact.getName(), contact.getContact());
        }
    }
    
    public ContactByName searchByName(String name){
        String[] nameToken = name.trim().split("\\s+");
        String processedName="";
        for(String token : nameToken){
            processedName+=token.toLowerCase();
        }
        NodeForNameSearch contact = data.searchOnName(data.rootName, processedName);
        if(contact==null){
            System.out.println("No entries found!!!");
            return new ContactByName();
        }else{
            return new ContactByName(contact.getEmailList(), contact.getContactList());
        }
    }
}
