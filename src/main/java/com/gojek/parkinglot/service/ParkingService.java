package com.gojek.parkinglot.service;

import com.gojek.parkinglot.model.ParkingTicket;
import com.gojek.parkinglot.model.Vehicle;

import java.util.List;

/**
 * Created using IntelliJ IDEA
 * Author:  girishkumar
 * Date:    06/06/19
 * Time:    11:19 AM
 */
public interface ParkingService {

  void createParkingLot(int capacity);
  ParkingTicket park(Vehicle vehicle);
  void leave(int slot);
  void getStatus();
  void fetchRegistrationNoForColor(String color);
  void fetchSlotNoForColor(String color);
  int getSlotNoForRegistrationNo(String regNo);

}
