package com.gojek.parkinglot.repositories;

import com.gojek.parkinglot.model.ParkingTicket;
import com.gojek.parkinglot.model.Vehicle;

/**
 * Created using IntelliJ IDEA
 * Author:  girishkumar
 * Date:    06/06/19
 * Time:    1:25 AM
 */
public interface ParkingRepository {
  ParkingTicket park(Vehicle vehicle);
}
