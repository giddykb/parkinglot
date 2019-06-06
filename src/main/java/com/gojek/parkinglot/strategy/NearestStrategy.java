package com.gojek.parkinglot.strategy;

import java.util.TreeSet;

/**
 * Created using IntelliJ IDEA
 * Author:  girishkumar
 * Date:    06/06/19
 * Time:    1:37 AM
 */
public class NearestStrategy implements ParkingStrategy {
  private TreeSet<Integer> freeSlots;

  public NearestStrategy () {
    freeSlots = new TreeSet<>();
  }

  public void add( int slot) {
    freeSlots.add(slot);
  }

  public int getSlot(){
    return freeSlots.pollFirst();
  }

  public void  remove(int slot) {
    freeSlots.remove(slot);
  }
}
