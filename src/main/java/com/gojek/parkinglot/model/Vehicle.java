package com.gojek.parkinglot.model;

import java.io.Serializable;

/**
 * Created using IntelliJ IDEA
 * Author:  girishkumar
 * Date:    06/06/19
 * Time:    1:06 AM
 */
public abstract class Vehicle implements Serializable {
  private String registrationNo;
  private String color;

  public Vehicle(String registrationNo, String color) {
    this.registrationNo = registrationNo;
    this.color = color;
  }

  public String getRegistrationNo() {
    return registrationNo;
  }

  public void setRegistrationNo(String registrationNo) {
    this.registrationNo = registrationNo;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  @Override
  public String toString() {
    return "Vehicle {" +
        "registrationNo='" + registrationNo + '\'' +
        ", color='" + color + '\'' +
        '}';
  }
}
