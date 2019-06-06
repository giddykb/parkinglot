package com.gojek.parkinglot.exception;

/**
 * Created using IntelliJ IDEA
 * Author:  girishkumar
 * Date:    06/06/19
 * Time:    4:57 PM
 */
public class ErrorCode {
   public static final String PARKING_LOT_EXISTS = "Parking Lot is already created";
   public static final String PARKING_LOT_FULL= "Parking Lot is full";
   public static final String PARKING_LOT_NOT_EXISTS= "Parking Lot does not exist";
   public static final String VEHICLE_ALREADY_PARKED= "Vehicle is already parked";
   public static final String INVALID_COMMAND= "Not a valid command";
   public static final String SLOT_IS_EMPTY= "Parking slot is already empty";
}
