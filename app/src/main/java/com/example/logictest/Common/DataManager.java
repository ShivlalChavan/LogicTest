package com.example.logictest.Common;

import android.content.Context;

public class DataManager extends Session {

    Session session;

    private static final String  firstName = "firstName";
    private static final String  lastName = "lastName";

   public DataManager(Context con){
       session = createSession(con,"GlobalList");
   }



   public void setFirstName(String firstname){
       session.putString(firstName,firstname);
   }

 public String getFirstName(){
       return  session.getString(firstName,null);
 }

   public  void setLastName(String lastname){
       session.putString(lastName,lastname);
   }

   public  String getlastName(){
       return session.getString(lastName,null);
   }





}
