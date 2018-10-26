package com.naturecode.test.models;

public class Student implements Cloneable {
  private String firstName;
  private String lastName;

  public String getFirstName(){
    return this.firstName;
  }

  public String getLastName(){
    return this.lastName;
  }

  public void setFirstName(String fname) {
    this.firstName = fname;
  }

  public void setLastName(String lname) {
    this.lastName = lname;
  }

  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}