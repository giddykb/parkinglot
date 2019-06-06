package com.gojek.parkinglot.service.impl;

import com.gojek.parkinglot.exception.ErrorCode;
import com.gojek.parkinglot.exception.ParkingLotException;
import com.gojek.parkinglot.model.ParkingLot;
import com.gojek.parkinglot.model.ParkingTicket;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.repositories.ParkingLotRepository;
import com.gojek.parkinglot.service.ParkingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created using IntelliJ IDEA
 * Author:  girishkumar
 * Date:    06/06/19
 * Time:    11:19 AM
 */
public class ParkingServiceImpl implements ParkingService {

  ParkingLot parkingLot;
  ParkingLotRepository repository = new ParkingLotRepository();

  @Override
  public void createParkingLot(int capacity) {
    if (parkingLot != null) {
      throw new ParkingLotException(ErrorCode.PARKING_LOT_EXISTS);
    }
    parkingLot = ParkingLot.getInstance(capacity);
    System.out.println("Created parking lot with " + capacity + " slots");

  }

  @Override
  public ParkingTicket park(Vehicle vehicle) {
    validate();
    ParkingTicket ticket = null;
    if (parkingLot.isFull()) {
      System.out.println("Sorry, parking lot is full");
      throw new ParkingLotException(ErrorCode.PARKING_LOT_FULL);
    } else if(isVehicleAlreadyParked(vehicle)) {
      System.out.println(ErrorCode.VEHICLE_ALREADY_PARKED);
      throw new ParkingLotException(ErrorCode.VEHICLE_ALREADY_PARKED);
    } else {
      ticket = issueTicket(vehicle);
      System.out.println("Allocated slot number: " + ticket.getSlot());
    }
    return ticket;
  }

  @Override
  public void leave(int slot) {
    validate();
    ParkingTicket parkingTicket = repository.getTicket(slot);
    if (parkingTicket != null) {
      repository.leave(parkingTicket);
      parkingLot.getStrategy().add(parkingTicket.getSlot());
      parkingLot.increment();
      System.out.println("Slot number " + parkingTicket.getSlot() + " is free");
    }
  }

  @Override
  public void getStatus() {
    validate();
    Map<Integer, Vehicle> slotVehicleMap = repository.getSlotVehicleMap();
    System.out.println("Slot No." + "\t" + "Registration No" + "\t\t" + "Color");
    for (Map.Entry entry : slotVehicleMap.entrySet()) {
      Integer slot = (Integer) entry.getKey();
      Vehicle vehicle = (Vehicle) entry.getValue();
      System.out.println( slot + "\t\t\t" +  vehicle.getRegistrationNo() + "\t\t" + vehicle.getColor());
    }
  }

  @Override
  public void fetchRegistrationNoForColor(String color) {
    validate();
    List<String> result = new ArrayList<>();
    Map<Integer, Vehicle> vehiclesWithSameColor = fetchVehiclesWithSameColor(color);
    if (!vehiclesWithSameColor.isEmpty()) {
      vehiclesWithSameColor.forEach( (k,v) -> result.add(v.getRegistrationNo()));
      String output = result.toString();
      System.out.println(output.substring(1,output.length()-1));
    }
  }


  @Override
  public void fetchSlotNoForColor(String color) {
    validate();
    Map<Integer, Vehicle> vehiclesWithSameColor = fetchVehiclesWithSameColor(color);
    List<Integer> result = new ArrayList<>();
    if (!vehiclesWithSameColor.isEmpty()) {
      vehiclesWithSameColor.forEach((k,v) -> result.add(k));
      String output = result.toString();
      System.out.println(output.substring(1,output.length()-1));
    }
  }

  @Override
  public int getSlotNoForRegistrationNo(String regNo) {
    validate();
    Map<Integer, Vehicle> slotVehicleMap = repository.getSlotVehicleMap();
    for (Map.Entry entry : slotVehicleMap.entrySet()) {
      Integer slot = (Integer) entry.getKey();
      Vehicle vehicle = (Vehicle) entry.getValue();
      if (vehicle.getRegistrationNo().equalsIgnoreCase(regNo)) {
        System.out.println(slot);
        return slot;
      }
    }
    System.out.println("Not Found");
    return -1;
  }

  private boolean isVehicleAlreadyParked(Vehicle vehicle) {
    Map<Integer, Vehicle> slotVehicleMap = repository.getSlotVehicleMap();
    for (Map.Entry entry : slotVehicleMap.entrySet()) {
      Integer slot = (Integer) entry.getKey();
      Vehicle veh = (Vehicle) entry.getValue();
      if (veh.getRegistrationNo().equalsIgnoreCase(vehicle.getRegistrationNo())) {
        return true;
      }
    }
    return false;
  }

  public Map<Integer, Vehicle> fetchVehiclesWithSameColor(String color) {
    Map<Integer, Vehicle> slotVehicleMap = repository.getSlotVehicleMap();
    Map<Integer, Vehicle> vehiclesWithSameColor = new HashMap<>();
    for (Map.Entry entry : slotVehicleMap.entrySet()) {
      Integer slot = (Integer) entry.getKey();
      Vehicle vehicle = (Vehicle) entry.getValue();
      if (vehicle.getColor().equalsIgnoreCase(color)) {
        vehiclesWithSameColor.put(slot,vehicle);
      }
    }
    return vehiclesWithSameColor;
  }

  private void validate() {
    if (parkingLot == null) {
      throw  new ParkingLotException(ErrorCode.PARKING_LOT_NOT_EXISTS);
    }
  }


  private ParkingTicket issueTicket(Vehicle vehicle) {
    int currentNearestSlot = parkingLot.getStrategy().getSlot();
    ParkingTicket ticket = new ParkingTicket(vehicle.getRegistrationNo(),currentNearestSlot);
    repository.park(currentNearestSlot, vehicle);
    repository.addTicket(ticket);
    parkingLot.decrement();
    return ticket;
  }

}
