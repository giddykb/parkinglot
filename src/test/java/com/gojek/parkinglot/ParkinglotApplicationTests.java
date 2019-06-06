package com.gojek.parkinglot;

import com.gojek.parkinglot.exception.ErrorCode;
import com.gojek.parkinglot.exception.ParkingLotException;
import com.gojek.parkinglot.model.Car;
import com.gojek.parkinglot.model.ParkingLot;
import com.gojek.parkinglot.service.ParkingService;
import com.gojek.parkinglot.service.impl.ParkingServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ParkinglotApplicationTests {
	ParkingService parkingService = new ParkingServiceImpl();
  ByteArrayOutputStream output = new ByteArrayOutputStream();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

	@Before
	public void outputStream () {
	  System.setOut(new PrintStream(output));
	}

  @After
  public void clean() {
    System.setOut(null);
    parkingService.cleanUp();
  }

	@Test
	public void testCreateParkingLot()  throws  Exception {
    ParkingLot parkingLot = parkingService.createParkingLot(10);
    assertEquals(10,parkingLot.getCapacity().intValue());
    assertEquals(10,parkingLot.getAvailableSlots().intValue());
	}

	@Test
	public void testParkingLotAlreadyExists() throws Exception {
    ParkingLot parkingLot = parkingService.createParkingLot(10);
    assertTrue("Createdparkinglotwith10slots".equalsIgnoreCase(output.toString().trim().replace(" ", "")));
    thrown.expect(ParkingLotException.class);
    thrown.expectMessage(ErrorCode.PARKING_LOT_EXISTS);
    parkingLot = parkingService.createParkingLot(10);
    assertEquals("ParkingLotisalreadycreated",output.toString().trim().replace(" ", ""));
  }


  @Test
  public void testParking() throws Exception {
    thrown.expect(ParkingLotException.class);
    thrown.expectMessage(ErrorCode.PARKING_LOT_NOT_EXISTS);
    parkingService.park(new Car("KA-07-EV-7368","Black"));
    ParkingLot parkingLot = parkingService.createParkingLot(20);
	  parkingService.park(new Car("KA-07-EV-7368","Black"));
    parkingService.park(new Car("KA-07-EV-3217","Grey"));
    assertEquals(18,parkingLot.getAvailableSlots().intValue());
  }

  @Test
  public void testParkingIsFull() throws  Exception {
    ParkingLot parkingLot = parkingService.createParkingLot(2);
    parkingService.park(new Car("KA-07-EV-7368","Black"));
    parkingService.park(new Car("KA-07-EV-3217","Grey"));
    assertTrue(parkingLot.isFull());
  }

  @Test
  public void leave() throws Exception {
    ParkingLot parkingLot = parkingService.createParkingLot(4);
    parkingService.park(new Car("KA-07-EV-7368","Black"));
    parkingService.park(new Car("KA-07-EV-3217","Grey"));
    parkingService.leave(1);
    assertEquals(3,parkingLot.getAvailableSlots().intValue());
    parkingService.leave(2);
    assertEquals(4,parkingLot.getAvailableSlots().intValue());
  }

 @Test
  public void testSlotEmpty() throws  Exception {
    ParkingLot parkingLot = parkingService.createParkingLot(5);
    parkingService.park(new Car("KA-07-EV-7368","Black"));
    parkingService.park(new Car("KA-07-EV-3217","Grey"));
    thrown.expect(ParkingLotException.class);
    thrown.expectMessage(ErrorCode.SLOT_IS_EMPTY);
    parkingService.leave(3);
  }


  @Test
  public void status() throws  Exception {
    ParkingLot parkingLot = parkingService.createParkingLot(6);
    parkingService.park(new Car("KA-07-EV-7368","White"));
    parkingService.park(new Car("KA-07-EV-3217","White"));
    parkingService.park(new Car("KA-07-EV-3219","Black"));
    parkingService.getStatus();
    assertEquals("Createdparkinglotwith6slots\n" +
                     "Allocatedslotnumber:1\n" +
                     "Allocatedslotnumber:2\n" +
                     "Allocatedslotnumber:3\n" +
                     "SlotNo.RegistrationNoColor\n" +
                     "1KA-07-EV-7368White\n" +
                     "2KA-07-EV-3217White\n" +
                     "3KA-07-EV-3219Black" ,output.toString().trim().replace(" ","").replace("\t",""));
  }

  @Test
  public void testGetSlotByRegistrationNo() throws Exception {
    ParkingLot parkingLot = parkingService.createParkingLot(6);
    parkingService.park(new Car("KA-07-EV-7368","White"));
    parkingService.park(new Car("KA-07-EV-3217","White"));
    parkingService.park(new Car("KA-07-EV-3219","Black"));
    int slot = parkingService.getSlotNoForRegistrationNo("KA-07-EV-3217");
    assertEquals(2,slot);
  }

  @Test
  public  void  testFetchRegistrationNoForColor() throws Exception {
    ParkingLot parkingLot = parkingService.createParkingLot(8);
    parkingService.park(new Car("KA-07-EV-7368","White"));
    parkingService.park(new Car("KA-07-EV-3217","White"));
    parkingService.park(new Car("KA-07-EV-3219","Black"));
    List<String> res = parkingService.fetchRegistrationNoForColor("White");
    String actual = res.toString();
    assertEquals("KA-07-EV-7368, KA-07-EV-3217", actual.substring(1, actual.length()-1));

  }

  @Test
  public  void  testFetchSlotNoForColor() throws Exception {
    ParkingLot parkingLot = parkingService.createParkingLot(8);
    parkingService.park(new Car("KA-07-EV-7368","White"));
    parkingService.park(new Car("KA-07-EV-3217","White"));
    parkingService.park(new Car("KA-07-EV-3819","Black"));
    parkingService.park(new Car("KA-07-EV-3517","White"));
    parkingService.park(new Car("KA-07-EV-5219","Red"));
    parkingService.park(new Car("KA-07-EV-3229","Black"));
    parkingService.park(new Car("KA-07-EV-3249","Black"));
    parkingService.park(new Car("KA-07-EV-5299","White"));

    List<Integer> res = parkingService.fetchSlotNoForColor("White");
    String actual = res.toString();
    assertEquals("1, 2, 4, 8", actual.substring(1, actual.length()-1));

  }

  @Test
  public void testVehicleAlreadyParked() throws Exception {
    ParkingLot parkingLot = parkingService.createParkingLot(8);
    thrown.expect(ParkingLotException.class);
    thrown.expectMessage(ErrorCode.VEHICLE_ALREADY_PARKED);
    parkingService.park(new Car("KA-07-EV-7368","White"));
    parkingService.park(new Car("KA-07-EV-7368","White"));
/*
    assertEquals("Createdparkinglotwith8slots\n" +
                     "Allocatedslotnumber:1\nVehicleisalreadyparked",output.toString().trim().replace(" ",""));

*/
}
}
