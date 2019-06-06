package com.gojek.parkinglot;

import com.gojek.parkinglot.constants.Commands;
import com.gojek.parkinglot.exception.ErrorCode;
import com.gojek.parkinglot.exception.ParkingLotException;
import com.gojek.parkinglot.model.Car;
import com.gojek.parkinglot.model.Vehicle;
import com.gojek.parkinglot.service.ParkingService;
import com.gojek.parkinglot.service.impl.ParkingServiceImpl;

/**
 * Created using IntelliJ IDEA
 * Author:  girishkumar
 * Date:    06/06/19
 * Time:    3:46 PM
 */
public class ParkingLotRequestProcessor {
  static ParkingService parkingService = new ParkingServiceImpl();

  public void execute(String input)  {
    String args []  = input.split(" ");
    try {
      processingCommand(args);
    } catch (ParkingLotException e) {
      if (e.getMessage().equals(ErrorCode.PARKING_LOT_NOT_EXISTS)) {
        System.out.println(e);
      }
    }

  }

  private void processingCommand(String args[]) throws  ParkingLotException {
    String command = args[0];
    if (command.equals(Commands.CREATE_PARKING_LOT)) {
      int capacity = Integer.parseInt(args[1]);
      parkingService.createParkingLot(capacity);
    }
    else if (command.equals(Commands.PARK)) {
      String registrationNo = args[1];
      String color = args[2];
      parkVehicle(registrationNo, color);
    }
    else if (command.equals(Commands.LEAVE)) {
      int slot = Integer.parseInt(args[1]);
      leave(slot);
    }
    else if (command.equals(Commands.STATUS)) {
      getStatus();
    } else if ( command.equals(Commands.REG_NUMBER_FOR_CARS_WITH_COLOR)) {
      String color = args[1];
      parkingService.fetchRegistrationNoForColor(color);
    }
    else if (command.equals(Commands.SLOTS_NUMBER_FOR_CARS_WITH_COLOR)) {
      String color = args[1];
      parkingService.fetchSlotNoForColor(color);
    }
    else if (command.equals(Commands.SLOTS_NUMBER_FOR_REG_NUMBER)){
      String regNo = args[1];
      parkingService.getSlotNoForRegistrationNo(regNo);
    } else {
      throw  new ParkingLotException(ErrorCode.INVALID_COMMAND);
    }
  }

  private void parkVehicle(String registrationNo, String color ) {
    Vehicle vehicle = new Car(registrationNo, color) ;
    parkingService.park(vehicle);
  }

  private   void leave(Integer slot) {
    parkingService.leave(slot);
  }

  private void  getStatus() {
    parkingService.getStatus();
  }
}
