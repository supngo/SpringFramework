package com.naturecode.test.models;

import java.lang.reflect.Field;

public class CloneTest {
  public static void main (String[] args){
    Student a = new Student();
    a.setFirstName("Thom");
    Student b = a;

    System.out.println("b name: " + b.getFirstName());
    a.setFirstName("John");  
    System.out.println("b name: " + b.getFirstName());

    Student c = (Student)cloneObject(a);
    System.out.println("\nc name: " + c.getFirstName());
    a.setFirstName("Peter");  
    System.out.println("c name: " + c.getFirstName());

    try {
      Student d = (Student) a.clone();
      System.out.println("\nd name: " + d.getFirstName());
      a.setFirstName("Nancy");  
      System.out.println("d name: " + d.getFirstName());
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  } 

  // For deep copy, either use this method as it uses Reflection API or declare class as Clonable and implement clone()
  private static Object cloneObject(Object obj){
    try{
      Object clone = obj.getClass().newInstance();
      for (Field field : obj.getClass().getDeclaredFields()) {
          field.setAccessible(true);
          field.set(clone, field.get(obj));
      }
      return clone;
    }catch(Exception e){
      return null;
    }
  }
}