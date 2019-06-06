package com.gojek.parkinglot.exception;

/**
 * Created using IntelliJ IDEA
 * Author:  girishkumar
 * Date:    06/06/19
 * Time:    4:54 PM
 */
public class ParkingLotException extends RuntimeException {
  private String message;

  public ParkingLotException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
