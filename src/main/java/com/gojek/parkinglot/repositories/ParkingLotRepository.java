package com.gojek.parkinglot.repositories;

import com.gojek.parkinglot.model.ParkingTicket;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.strategy.NearestStrategy;
import com.gojek.parkinglot.strategy.ParkingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created using IntelliJ IDEA
 * Author:  girishkumar
 * Date:    06/06/19
 * Time:    1:26 AM
 */
public class ParkingLotRepository {

  private Map<Integer, Vehicle> slotVehicleMap;
  private Map<String, ParkingTicket> activeTickets;

  public ParkingLotRepository() {
    slotVehicleMap = new ConcurrentHashMap<>();
    activeTickets = new ConcurrentHashMap<>();
  }

  public void park( int slot, Vehicle vehicle) {
    if (!slotVehicleMap.containsKey(slot)) {
      slotVehicleMap.put(slot,vehicle);
    }
  }

  public void addTicket( ParkingTicket ticket ) {
    activeTickets.put(ticket.getId(),ticket);
  }

  public boolean leave(ParkingTicket ticket) {
    if (!slotVehicleMap.containsKey(ticket.getSlot())) {
      return false;
    }
    slotVehicleMap.remove(ticket.getSlot());
    activeTickets.remove(ticket.getId());
    return true;
  }

  public ParkingTicket getTicket(int slot) {
    for (Map.Entry entry : activeTickets.entrySet()) {
      ParkingTicket ticket = (ParkingTicket) entry.getValue();
      if (ticket.getSlot() == slot) return ticket;
    }
    return null;
  }

  public Map<Integer, Vehicle> getSlotVehicleMap() {
    return slotVehicleMap;
  }

  public void setSlotVehicleMap(Map<Integer, Vehicle> slotVehicleMap) {
    this.slotVehicleMap = slotVehicleMap;
  }

  public Map<String, ParkingTicket> getActiveTickets() {
    return activeTickets;
  }

  public void setActiveTickets(Map<String, ParkingTicket> activeTickets) {
    this.activeTickets = activeTickets;
  }

}
