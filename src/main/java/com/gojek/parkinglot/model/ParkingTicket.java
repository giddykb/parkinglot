package com.gojek.parkinglot.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created using IntelliJ IDEA
 * Author:  girishkumar
 * Date:    06/06/19
 * Time:    2:22 AM
 */
public class ParkingTicket implements Serializable {
  private String id;
  private String registrationNo;
  private Integer slot;

  public  ParkingTicket(String registrationNo, int slot) {
    this.id = UUID.randomUUID().toString();
    this.registrationNo = registrationNo;
    this.slot = slot;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRegistrationNo() {
    return registrationNo;
  }

  public void setRegistrationNo(String registrationNo) {
    this.registrationNo = registrationNo;
  }


  public Integer getSlot() {
    return slot;
  }

  public void setSlot(Integer slot) {
    this.slot = slot;
  }

}
