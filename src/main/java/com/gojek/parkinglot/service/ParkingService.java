package com.gojek.parkinglot.service;

import com.gojek.parkinglot.exception.ParkingLotException;
import com.gojek.parkinglot.model.ParkingLot;
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

  ParkingLot createParkingLot(int capacity);
  ParkingTicket park(Vehicle vehicle) throws ParkingLotException;
  void leave(int slot);
  void getStatus();
  List<String> fetchRegistrationNoForColor(String color);
  List<Integer> fetchSlotNoForColor(String color);
  int getSlotNoForRegistrationNo(String regNo);
  void cleanUp();

}
