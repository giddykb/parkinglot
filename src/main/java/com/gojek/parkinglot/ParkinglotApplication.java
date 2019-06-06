package com.gojek.parkinglot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.Scanner;

@SpringBootApplication
public class ParkinglotApplication {
	static ParkingLotRequestProcessor requestProcessor = new ParkingLotRequestProcessor();

	public static void main(String[] args) {
		//SpringApplication.run(ParkinglotApplication.class, args);

	/*	File file = new File("data/input.txt");
		Scanner scr = null;
		try {
			scr = new Scanner(file);
			while (scr.hasNextLine()) {
				String line = scr.nextLine();
				requestProcessor.execute(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
*/
	switch (args.length) {
		case 0:
			Scanner scanner  = null;
			while (true) {
				scanner = new Scanner(System.in);
				String input = scanner.nextLine();
				if (input.equals("exit")) break;
				else {
					requestProcessor.execute(input);
				}
			}
			break;
		case 1:
			File file = new File(args[0]);
			Scanner scr = null;
			try {
				scr = new Scanner(file);
				while (scr.hasNextLine()) {
					String line = scr.nextLine();
					requestProcessor.execute(line);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	}

}
