package com.gojek.parkinglot.strategy;

/**
 * Created using IntelliJ IDEA
 * Author:  girishkumar
 * Date:    06/06/19
 * Time:    1:38 AM
 */
public interface ParkingStrategy {
  void add(int slot);
  int getSlot();
  void remove(int slot);
}
