package com.example.demo.util;

public class ContactData {
    
    NodeForEmailSearch rootEmail = null;
    NodeForNameSearch rootName = null;
    
    public ContactData(){
        initializeTrie();
    }
    
    public boolean isTrieInitialzed(){
        return rootEmail!=null && rootName!=null;
    }
    
    public void initializeTrie(){
        if(isTrieInitialzed())
            return;
        rootEmail = new NodeForEmailSearch();
        rootName = new NodeForNameSearch();
    }
    
    public boolean insertIntoTrieEmail(NodeForEmailSearch node, String email, String name, String contact){
        //end node
        int firstCharIndex = email.charAt(0);//-'0';
        if(node.next[firstCharIndex]==null){
            node.next[firstCharIndex]= new NodeForEmailSearch();
        }
        if(email.length()==1){
            if(node.next[firstCharIndex].validContact())
                return false;
            return node.next[firstCharIndex].setContact(contact) && node.next[firstCharIndex].setName(name);
        }else
            return insertIntoTrieEmail(node.next[firstCharIndex], email.substring(1), name, contact);
    }
    
    public boolean insertIntoTrieName(NodeForNameSearch node, String name, String email, String contact){
        int firstCharIndex = name.charAt(0);//-'0';
        if(node.next[firstCharIndex]==null){
            node.next[firstCharIndex] = new NodeForNameSearch();
        }
        if(name.length()==1){
            return node.next[firstCharIndex].addEntry(email, contact);
        }else{
            return insertIntoTrieName(node.next[firstCharIndex], name.substring(1), email, contact);
        }
    }
    
    public NodeForEmailSearch searchOnEmail(NodeForEmailSearch node, String email){
        NodeForEmailSearch searchResult=null;
        int firstCharIndex = email.charAt(0);//-'0';
        if(node.next[firstCharIndex]==null)
            return searchResult;
        
        if(email.length()==1 && node.next[firstCharIndex]!=null){
            if(node.next[firstCharIndex].validContact())
                searchResult = node.next[firstCharIndex];
        }else{
            searchResult = searchOnEmail(node.next[firstCharIndex], email.substring(1));
        }
        return searchResult;
    }
    
    public NodeForNameSearch searchOnName(NodeForNameSearch node, String name){
        NodeForNameSearch searchResult=null;
        int firstCharIndex = name.charAt(0);//-'0';
        if(node.next[firstCharIndex]==null)
            return searchResult;
        
        if(name.length()==1 && node.next[firstCharIndex]!=null){
            if(node.next[firstCharIndex].validContact())
                searchResult = node.next[firstCharIndex];
        }else{
            searchResult = searchOnName(node.next[firstCharIndex], name.substring(1));
        }
        return searchResult;
    }
    
    public boolean modifyOnEmail(NodeForEmailSearch node, String email, String name,String contact){
        boolean result = true;
        int firstCharIndex = email.charAt(0);//-'0';
        if(node.next[firstCharIndex]==null)
            return result;
        
        if(email.length()==1 && node.next[firstCharIndex]!=null){
            if(node.next[firstCharIndex].validContact()){
                result = result & node.next[firstCharIndex].setContact(contact);
                result = result & node.next[firstCharIndex].setName(name);
            }else{
                System.out.println("Not a valid contact or parameter passed are not correct.");
            }
        }else{
            result = result | modifyOnEmail(node.next[firstCharIndex], email.substring(1), name, contact);
        }
        return result;
    }
    
    public boolean modifyOnName(NodeForNameSearch node, String name, String email,String contact){
        boolean result = false;
        int firstCharIndex = name.charAt(0);//-'0';
        if(node.next[firstCharIndex]==null)
            return result;
        
        if(name.length()==1 && node.next[firstCharIndex]!=null){
            if(node.next[firstCharIndex].validContact()){
                result = result | node.next[firstCharIndex].modifyEntry(email, contact);
            }
        }else{
            result = result | modifyOnName(node.next[firstCharIndex], name.substring(1), email, contact);
        }
        return result;
    }
    
    public boolean deleteByEmail(NodeForEmailSearch node, String email){
        boolean result=false;
        int firstCharIndex = email.charAt(0);//-'0';
        if(node.next[firstCharIndex]==null)
            return result;
        
        if(email.length()==1 && node.next[firstCharIndex]!=null){
            if(node.next[firstCharIndex].validContact()){
                node.next[firstCharIndex].clearContact();
                result=true;
            }
        }else{
            result = result | deleteByEmail(node.next[firstCharIndex], email.substring(1));
        }
        return result;
    }
    
    public boolean deleteByName(NodeForNameSearch node, String name, String email){
        boolean result=false;
        int firstCharIndex = name.charAt(0);
        if(node.next[firstCharIndex]==null)
            return result;
        
        if(name.length()==1 && node.next[firstCharIndex]!=null){
            if(node.next[firstCharIndex].validContact()){
                node.next[firstCharIndex].deleteEntry(email);
                result=true;
            }
        }else{
            result = result | deleteByName(node.next[firstCharIndex], name.substring(1), email);
        }
        return result;
    }
    
}

