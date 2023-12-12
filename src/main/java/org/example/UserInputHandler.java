package org.example;

import java.time.LocalTime;
import java.util.Scanner;

/**
 * <h1>UserInputHandler.</h1>
 * <p>
 * This class handles user input by cooperating with the InputValidator class to return the input
 * when it is valid. It also handles the creation of a new Departure object.
 * </p>
 *
 * @author Mikael Stray Froeyshov
 * @version 1.0
 * @since 2023-12-04
 */

public class UserInputHandler {

  private final InputValidator inputValidator = new InputValidator();
  private final Scanner scanner = new Scanner(System.in);

  // String constants
  private static final String PLEASE_TRY_AGAIN = " Please try again: ";
  private static final String WRONG_FORMAT = "Wrong format. ";

  /**
   * Method that uses the class methods to validate and get user input
   * to create a new Departure object.
   *
   * @return The user input as a Departure object.
   */

  public Departure createDepartureFromUserInput(TrainDispatch trainDispatch) {

    // The timeNow variable is used to ensure that the user can only update the time to a later time
    LocalTime timeNow = trainDispatch.getTime();
    LocalTime time = validateAndGetTime(timeNow);

    String line = validateAndGetLine();

    int trainNumber = validateAndGetTrainNumber(trainDispatch);

    String destination = validateAndGetDestination();

    System.out.print("\nTrack (type 0 if not existing yet), max 3 digits: ");
    int track = validateAndGetNumericInput();

    System.out.print("\nDelay, max 3 digits: ");
    int delay = validateAndGetNumericInput();

    return new Departure(time, line, trainNumber, destination, track, delay);
  }

  /**
   * Method to ensure right time format and getting input when creating a new departure.
   *
   * @return The user input as a LocalTime object.
   */

  public LocalTime validateAndGetTime(LocalTime timeNow) {
    System.out.println("Time? In format (hh:mm): ");
    // asks for right format until user input is valid
    while (true) {
      String newTime = scanner.nextLine();
      try {
        // if input is valid (validation method does not throw exception), return new time
        return InputValidator.validateTimeInput(newTime, timeNow);
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
      } catch (Exception e) {
        System.out.print("Wrong format. Should be in the format [00-23]:[00-59]."
                + PLEASE_TRY_AGAIN);
      }
    }
  }

  /**
   * Method to ensure right line format and getting input when creating a new departure.
   *
   * @return The user input as a String.
   */

  private String validateAndGetLine() {
    System.out.print("\nLine, max 5 digits: ");
    // asks for right format until user input is valid
    while (true) {
      String line = scanner.nextLine().trim();
      try {
        // if input is valid (validation method does not throw exception), return new time
        return InputValidator.validateLineInput(line);
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
      }
    }
  }

  /**
   * Method to ensure right train number format and getting input when creating a new departure.
   *
   * @return The user input as an Integer.
   */

  private int validateAndGetTrainNumber(TrainDispatch trainDispatch) {
    System.out.print("\nTrain number, max 4 digits: ");
    // asks for right format until user input is valid
    while (true) {
      String trainNumber = scanner.nextLine();
      try {
        // if input is valid (validation method does not throw exception), return new time
        return inputValidator.validateTrainNumber(trainNumber, trainDispatch);
      } catch (NumberFormatException e) {
        System.out.print(WRONG_FORMAT);
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
      }
    }
  }

  /**
   * Method to ensure right destination format and getting the input when creating a new departure.
   *
   * @return The user input as a String.
   */

  private String validateAndGetDestination() {
    System.out.println("\nDestination: ");
    // asks for right format until user input is valid
    while (true) {
      String input = scanner.nextLine().trim();
      try {
        // if input is valid (validation method does not throw exception), return new time
        return InputValidator.validateDestination(input);
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
      }
    }
  }

  /**
   * Method to ensure right train number format and getting input when finding a departure.
   *
   * @return The user input as an Integer.
   */

  public int validateTrainNumberToFindDeparture(TrainDispatch trainDispatch) {
    System.out.print("\nTrain number, max 4 digits. Press 0 to exit: ");
    // asks for right format until user input is valid
    while (true) {
      String trainNumber = scanner.nextLine();
      try {
        // Check if the user wants to exit
        if (trainNumber.equals("0")) {
          return 0;
        }
        // if input is valid (validation method does not throw exception), return new time
        return inputValidator.validateTrainNumberToFind(trainNumber, trainDispatch);
      } catch (NumberFormatException e) {
        System.out.print(WRONG_FORMAT);
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN + " (0 to exit): ");
      }
    }
  }

  /**
   * Method to ensure right destination format and getting input when finding a departure.
   *
   * @return The user input as an Integer.
   */

  public String validateDestinationToFindDeparture(TrainDispatch trainDispatch) {
    System.out.print("\nDestination (0 to exit): ");
    // asks for right format until user input is valid
    while (true) {
      String destination = scanner.nextLine().trim();
      try {
        // Check if the user wants to exit
        if (destination.equals("0")) {
          return null;
        }
        // if input is valid (validation method does not throw exception), return new time
        return inputValidator.validateDestinationToFind(destination, trainDispatch);
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN + " (0 to exit): ");
      }
    }
  }

  /**
   * Method to ensure right track or delay format and getting input when setting track or delay.
   *
   * @return The user input as an Integer.
   */

  public int validateAndGetNumericInput() {
    while (true) {
      String value = scanner.nextLine();
      try {
        // if input is valid (validation method does not throw exception), return new time
        return InputValidator.validateNumericInput(value);
      } catch (NumberFormatException e) {
        System.out.print(WRONG_FORMAT);
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
      }
    }
  }
}
