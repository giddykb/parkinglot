package com.gojek.parkinglot.model;

import com.gojek.parkinglot.strategy.NearestStrategy;
import com.gojek.parkinglot.strategy.ParkingStrategy;
import java.io.Serializable;

/**
 * Created using IntelliJ IDEA
 * Author:  girishkumar
 * Date:    06/06/19
 * Time:    1:43 PM
 */

public class ParkingLot implements Serializable {

  private Integer floor;
  private Integer capacity;
  private ParkingStrategy strategy;
  private Integer availableSlots;
  private static ParkingLot INSTANCE = null;

  private ParkingLot() {}

  private ParkingLot(Integer floor, Integer capacity) {
    this.floor = floor;
    this.capacity = capacity;
    availableSlots = capacity;
    this.strategy = new NearestStrategy();
    for (int slot = 1; slot <=capacity ; slot++) {
      strategy.add(slot);
    }
  }

  private ParkingLot(Integer floor, Integer capacity, ParkingStrategy strategy) {
    this(floor,capacity);
    this.strategy = strategy;
  }

  public static ParkingLot getInstance(Integer capacity) {

    if (INSTANCE == null) {
      synchronized (ParkingLot.class) {
        if (INSTANCE == null) {
          INSTANCE = new ParkingLot(1, capacity);
        }
      }
    }
    return INSTANCE;
  }

  public static ParkingLot getInstance(Integer floor, Integer capacity) {

    if (INSTANCE == null) {
      synchronized (ParkingLot.class) {
        if (INSTANCE == null) {
          INSTANCE = new ParkingLot(floor, capacity);
        }
      }
    }
    return INSTANCE;
  }

  public void increment() {
    availableSlots++;
  }

  public void decrement() {
    availableSlots--;
  }

  public boolean isFull() {
    return availableSlots == 0;
  }
  public Integer getFloor() {
    return floor;
  }

  public void setFloor(Integer floor) {
    this.floor = floor;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  public ParkingStrategy getStrategy() {
    return strategy;
  }

  public void setStrategy(ParkingStrategy strategy) {
    this.strategy = strategy;
  }

  public Integer getAvailableSlots() {
    return availableSlots;
  }

  public void setAvailableSlots(Integer availableSlots) {
    this.availableSlots = availableSlots;
  }
}
