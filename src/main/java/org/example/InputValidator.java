package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * <h1>InputValidator.</h1>
 * <p>
 * This class contains all the methods that validates user input, throwing exceptions
 * if the input is not valid.
 * </p>
 *
 * @author Mikael Stray Froeyshov
 * @version 1.0
 * @since 2023-12-04
 */

public class InputValidator {

  private static final String INPUT_CANNOT_BE_EMPTY = "Input cannot be empty. ";

  /**
   * Method to ensure right time format when creating a new departure.
   * Ensures that the time user input is not empty or before the current time.
   *
   * @param newTime The user input as a String.
   * @param timeNow The current time as a LocalTime object.
   * @return The user input as a LocalTime object.
   * @throws IllegalArgumentException if the input is empty or before the current time.
   */

  public static LocalTime validateTimeInput(String newTime, LocalTime timeNow) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    if (newTime.isEmpty()) {
      throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
    }
    if (LocalTime.parse(newTime).isBefore(timeNow)) {
      throw new IllegalArgumentException("Time cannot be before the current time: "
              + formatter.format(timeNow) + ". ");
    }
    return LocalTime.parse(newTime);
  }

  /**
   * Method to ensure right line format when creating a new departure.
   * Ensures that the line user input is not empty, is not longer than 5 characters
   * and only contains letters and numbers.
   *
   * @param line The user input as a String.
   * @return The user input as a String.
   * @throws IllegalArgumentException if the input is not valid
   */

  public static String validateLineInput(String line) {
    if (line.isEmpty() || line.equals("0")) {
      throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
    } else if (line.length() > 5) {
      throw new IllegalArgumentException("Line cannot be longer than 5 characters.");
    } else if (!line.matches("^[a-zA-Z0-9]+$")) {
      throw new IllegalArgumentException("Line can only contain letters and numbers.");
    }
    return line;
  }

  /**
   * Method to ensure right train number format when creating a new departure.
   * Ensures that the train number user input is not empty, consists
   * and is a positive number between 1 and 9999.
   *
   * @param trainNumber The user input as a String.
   * @return The user input as an integer.
   * @throws IllegalArgumentException if the input is empty, not a number or not between 1 and 9999.
   */

  public int validateTrainNumber(String trainNumber, TrainDispatch trainDispatch) {
    if (trainNumber.isEmpty()) {
      throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
    }
    int trainNumberInt = Integer.parseInt(trainNumber);
    if (trainNumberInt <= 0 || trainNumberInt > 9999) {
      throw new IllegalArgumentException("Train number has to be positive and max 4 digits.");
    }
    if (trainDispatch.findDuplicateTrainNumber(trainNumberInt)) {
      throw new IllegalArgumentException("Train number already exists.");
    }
    return trainNumberInt;
  }

  /**
   * Method to ensure right destination format when creating a new departure.
   * Ensures that the destination user input is not empty and only contains letters.
   *
   * @param input The user input as a String.
   * @return The user input as a String.
   * @throws IllegalArgumentException if the input is empty or is not only letters.
   */

  public static String validateDestination(String input) {
    if (input.isEmpty()) {
      throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
    } else if (!input.matches("^[ A-Za-z]+$")) {
      throw new IllegalArgumentException("Destination can only contain letters. ");
    }
    return input;
  }

  /**
   * Method to ensure right train number format when finding a departure.
   * Ensures that the train number user input is not empty, consists
   * and is a positive number between 1 and 9999.
   *
   * @param trainNumber The user input as a String.
   * @return The user input as an integer.
   * @throws IllegalArgumentException if the input is empty, not a number or not between 1 and 9999.
   */

  public int validateTrainNumberToFind(String trainNumber, TrainDispatch trainDispatch) {
    if (trainNumber.isEmpty()) {
      throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
    }
    int trainNumberInt = Integer.parseInt(trainNumber);
    if (trainNumberInt <= 0 || trainNumberInt > 9999) {
      throw new IllegalArgumentException("Train number has to be positive and max 4 digits. ");
    }
    if (!trainDispatch.findDuplicateTrainNumber(trainNumberInt)) {
      throw new IllegalArgumentException("Train number does not exist. ");
    }
    return trainNumberInt;
  }

  /**
   * Method to ensure right destination format when finding a departure.
   * Ensures that the destination user input is not empty, consists in the list
   * and consists of only letters.
   *
   * @param destination The user input as a String.
   * @return The user input as a String.
   * @throws IllegalArgumentException if the input is not valid
   */

  public String validateDestinationToFind(String destination, TrainDispatch trainDispatch) {
    if (destination.isEmpty()) {
      throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
    } else if (!destination.matches("^[ A-Za-z0]+$")) {
      throw new IllegalArgumentException("Destination can only contain letters and numbers. ");
    } else if (trainDispatch.findDeparturesByDestination(destination).isEmpty()) {
      throw new IllegalArgumentException("Destination does not exist. ");
    }
    return destination;
  }

  /**
   * Method to ensure right track or delay format when setting track or delay.
   * Ensures that the user input is not empty, consists and is a positive number between 1 and 999.
   *
   * @param value The user input as a String.
   * @return The user input as an integer.
   * @throws IllegalArgumentException if the input is empty, not a number or not between 1 and 999.
   */

  public static int validateNumericInput(String value) {
    if (value.isEmpty()) {
      throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
    }
    int valueInt = Integer.parseInt(value);
    if (valueInt < 0 || valueInt > 999) {
      throw new IllegalArgumentException("Number has to be positive and max 3 digits.");
    }
    return valueInt;
  }
}
