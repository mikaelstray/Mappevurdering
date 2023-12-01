package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * <h1>UserInterface</h1>
 * <p>
 * The UserInterface class manages the interaction with the user in the Train Dispatch application.
 * It presents a menu, retrieves user input, and calls appropriate methods.
 * </p>
 * <p>
 * This class is responsible for handling user choices such as listing departures, adding or removing departures,
 * and updating departure information with validation of inputs.
 * It uses the TrainDispatch class to retrieve and manipulate departure data.
 * </p>
 * <p>
 * The class also has methods for printing the train dispatch and the departures.
 * </p>
 *
 * @author Mikael Stray Froeyshov
 * @version 1.0
 * @since 2023-11-02
 */

public class UserInterface {

  // Instance variables
  private TrainDispatch trainDispatch;
  private Scanner scanner;
  private DateTimeFormatter formatter;

  // Constants representing the different error messages
  private static final String INPUT_CANNOT_BE_EMPTY = "Input cannot be empty. ";
  private static final String WRONG_FORMAT = "Wrong format, please try again: ";
  private static final String PLEASE_TRY_AGAIN = "Please try again: ";

  // Constants representing the different menu choices
  private static final int LIST_ALL_DEPARTURES = 1;
  private static final int ADD_DEPARTURE = 2;
  private static final int REMOVE_DEPARTURE = 3;
  private static final int FIND_DEPARTURE_BY_NUMBER = 4;
  private static final int FIND_DEPARTURE_BY_DESTINATION = 5;
  private static final int SET_TRACK = 6;
  private static final int SET_DELAY = 7;
  private static final int UPDATE_TIME = 8;
  private static final int EXIT = 9;

  /**
   * Initializes the user interface, creates necessary objects and registers.
   */

  public void init() {
    trainDispatch = new TrainDispatch();
    scanner = new Scanner(System.in);
    formatter = DateTimeFormatter.ofPattern("HH:mm");

    // Register some departures
    trainDispatch.registerDeparture(new Departure(LocalTime.of(17,45), "F4",
            123, "Lillestrom", 1, 0));
    trainDispatch.registerDeparture(new Departure(LocalTime.of(19, 0), "L1",
            456, "Oslo S", 2, 65));
    trainDispatch.registerDeparture(new Departure(LocalTime.of(16, 12), "E5",
            789, "Trondheim", 0, 0));
  }

  /**
   * Starts the user interface, and keeps it running until the user chooses to quit.
   */

  public void start() {
    boolean finished = false;
    // The while-loop will run as long as the user has not selected to quit the application
    while (!finished) {
      // Present the menu to the user, and retrieve the user's choice
      showMenu();
      int menuChoice = this.userChoice();
      switch (menuChoice) {
        case LIST_ALL_DEPARTURES:
          printTrainDispatch();
          break;
        case ADD_DEPARTURE:
          addDeparture();
          break;
        case REMOVE_DEPARTURE:
          removeDeparture();
          break;
        case FIND_DEPARTURE_BY_NUMBER:
          findDepartureByNumber();
          break;
        case FIND_DEPARTURE_BY_DESTINATION:
          findDeparturesByDestination();
          break;
        case SET_TRACK:
          setTrack();
          break;
        case SET_DELAY:
          setDelay();
          break;
        case UPDATE_TIME:
          updateTime();
          break;
        case EXIT:
          System.out.println("Thank you for using the Properties app!\n");
          finished = true;
          break;
      }
    }
  }

  /**
   * Prints the menu choices to the console.
   */

  private static void showMenu() {
    System.out.println("\n******* Train Dispatch Application v0.1 *******\n");
    System.out.println("1. List all departures                   ++      +------");
    System.out.println("2. Add departure                         ||      |+-+ | ");
    System.out.println("3. Remove departure                      ||      || | | ");
    System.out.println("4. Find departure by number            /---------|| | | ");
    System.out.println("5. Find departure by destination      + ========  +-+ | ");
    System.out.println("6. Set track                       _|--/~\\------/~\\-+ ");
    System.out.println("7. Set delay                       //// \\_/      \\_/  ");
    System.out.println("8. Update time");
    System.out.println("9. Quit");
    System.out.println("\nPlease enter a number between 1 and 9:");
  }
  /**
   * Presents the menu for the user, and awaits input from the user. The menu
   * choice selected by the user is being returned.
   *
   * @return the menu choice by the user as a positive number starting from 1.
   * If 0 is returned, the user has entered a wrong value
   */

  private int userChoice() {
    if (scanner.hasNextInt()) {
      int choice = Integer.parseInt(scanner.nextLine());
      if (choice >= 1 && choice <= 9) {
        return choice;
      } else {
        System.out.print("Has to be a number between 1 and 9");
        return 0;
      }
    } else {
      System.out.print("Unrecognized input");
      scanner.nextLine();
      return 0;
    }
  }

  /**
   * Creates a new departure from user input. Validates every input.
   *
   * @return The new departure.
   */

  private Departure createDepartureFromUserInput() { //TODO: null validation and change input method?

    LocalTime time = validateAndGetTime();

    String line = validateAndGetLine();

    int trainNumber = validateAndGetTrainNumber();

    String destination = validateAndGetDestination();

    System.out.print("\nTrack (type 0 if not existing yet), max 3 digits: ");
    int track = validateAndGetTrackOrDelay();

    System.out.print("\nDelay, max 3 digits: ");
    int delay = validateAndGetTrackOrDelay();

    return new Departure(time, line, trainNumber, destination, track, delay);
  }

  /**
   * Checks if the list is empty.
   */

  private boolean listIsEmpty() {
    if (trainDispatch.checkIfListIsEmpty()) {
      System.out.println("List is empty, add a new departure first");
      return true;
    }
    return false;
  }

  /**
   * Adds a departure to the list.
   */

  private void addDeparture() {
    // validation methods are in createDepartureFromUserInput(), so no need to validate here
    trainDispatch.registerDeparture(createDepartureFromUserInput());
    System.out.print("\nDeparture was successfully added");
  }

  /**
   * Removes a departure from the list.
   */

  private void removeDeparture() {
    // if list is empty, print message and return to menu
    if (listIsEmpty()) {
      return;
    }
    int trainNumber = ensureRightTrainNumberToFindDeparture();

    // if user chose 0 to exit when choosing train number, return to menu
    if (trainNumber == 0) {
      return;
    }
    Departure departureToRemove = trainDispatch.findDepartureByNumber(trainNumber);
    trainDispatch.removeDeparture(departureToRemove);
    System.out.println("\nDeparture with train number " + trainNumber + " was removed");
  }

  /**
   * Finds a departure by train number.
   */

  private void findDepartureByNumber() {
    int trainNumber = ensureRightTrainNumberToFindDeparture();

    // if user chose 0 to exit when choosing train number, return to menu
    if (trainNumber == 0) {
      return;
    }
    printHeader();
    System.out.println((trainDispatch.findDepartureByNumber(trainNumber)));
  }

  /**
   * Finds departures by destination.
   */

  private void findDeparturesByDestination() {
    String destination = ensureRightDestinationToFindDeparture();

    // if user chose 0 to exit when choosing destination, return to menu
    if (destination == null) {
      return;
    }

    Departure[] departures = trainDispatch.findDeparturesByDestination(destination);
    // print header and a list of departures with the specified destination
    printHeader();
    Stream.of(departures)
            .forEach(System.out::println);
  }

  /**
   * Ensures right track format and sets the track of a departure.
   */

  private void setTrack() {
    // if list is empty, print message and return to menu
    if (listIsEmpty()) {
      return;
    }
    int trainNumber = ensureRightTrainNumberToFindDeparture();

    // if user wants to exit, return to menu
    if (trainNumber == 0) {
      return;
    }

    System.out.println("Track?");
    int track = validateAndGetTrackOrDelay();
    trainDispatch.setTrack(trainNumber, track);
    System.out.println("\n Track for departure with train number " + trainNumber + " was set to " + track);
  }

  /**
   * Ensures right train number format and sets the delay of a departure.
   */

  private void setDelay() {
    // if list is empty, print message and return to menu
    if (listIsEmpty()) {
      return;
    }
    int trainNumber = ensureRightTrainNumberToFindDeparture();

    // if user wants to exit
    if (trainNumber == 0) { // if user wants to exit
      return;
    }

    System.out.println("Delay?");
    int delay = validateAndGetTrackOrDelay();
    trainDispatch.setDelay(trainNumber, delay);
    System.out.println("\n Delay for departure with train number " + trainNumber + " was set to " + delay);
  }

  /**
   * Updates the time of the train dispatch.
   */

  private void updateTime() {
    LocalTime newTime = validateAndGetTime();
    trainDispatch.setTime(newTime);
    System.out.println("\n Time was updated to " + formatter.format(newTime));
  }

  // Following methods validate user inputs

  /**
   * Method to ensure right destination format when creating a new departure.
   * Ensures that the destination user input is not empty and only contains letters.
   *
   * @return The user input as a String.
   */

  private String validateAndGetDestination() {
    System.out.println("\nDestination: ");
    String input = scanner.nextLine().trim();

    boolean validInput = false;
    while (!validInput) {
      // try-catch block to catch invalid input
      try {
        if (input.isEmpty()) {
          throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
        } else if (!input.matches("^[ A-Za-z]+$")) {
          throw new IllegalArgumentException("Destination can only contain letters. ");
        }
        // if input is valid, set validInput to true to exit while loop
        validInput = true;

        // if input is invalid, print error message and ask user to try again
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + (PLEASE_TRY_AGAIN));
        input = scanner.nextLine().trim();
      }
    }
    return input;
  }

  /**
   * Method to ensure right time format when creating a new departure.
   * Ensures that the time user input is not empty or before the current time and is in the format hh:mm.
   *
   * @return The user input as a LocalTime object.
   */

  private LocalTime validateAndGetTime() {
    System.out.println("Time? In format (hh:mm): ");
    String newTime = scanner.nextLine();
    LocalTime timeNow = trainDispatch.getTime();

    boolean validInput = false;
    while (!validInput) {
      // try-catch block to catch invalid input
      try {
        if (newTime.isEmpty()) {
          throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
        }
        if (LocalTime.parse(newTime).isBefore(timeNow)) {
          throw new IllegalArgumentException("Time cannot be before current time: "
                  + formatter.format(timeNow) + ". ");
        }
        // if input is valid, set validInput to true to exit while loop
        validInput = true;

        // if input is invalid, print error message and ask user to try again
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
        newTime = scanner.nextLine();
      } catch (Exception e) {
        System.out.print("Wrong format. Should be [00-23]:[00-59], please try again: ");
        newTime = scanner.nextLine();
      }
    }
    return LocalTime.parse(newTime);
  }

  /**
   * Method to ensure right line format when creating a new departure.
   * Ensures that the line user input is not empty or 0 and only contains letters and numbers.
   *
   * @return The user input as an integer.
   */

  private String validateAndGetLine() {
    System.out.print("\nLine, max 5 digits: ");
    String line = scanner.nextLine().trim();

    boolean validInput = false;
    while (!validInput) {
      // try-catch block to catch invalid input
      try {
        if (line.isEmpty() || line.equals("0")) {
          throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
        } else if (line.length() > 5) {
          throw new IllegalArgumentException("Line cannot be longer than 5 characters.");
        } else if (!line.matches("^[a-zA-Z0-9]+$")) {
          throw new IllegalArgumentException("Line can only contain letters and numbers.");
        }
        // if input is valid, set validInput to true to exit while loop
        validInput = true;

        // if input is invalid, print error message and ask user to try again
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + (PLEASE_TRY_AGAIN));
        line = scanner.nextLine().trim();
      }
    }
    return line;
  }

  /**
   * Method to ensure right train number format when creating a new departure.
   * Ensures that the train number user input is not empty or a duplicate and is a positive number between 1 and 9999.
   *
   * @return The user input as an integer.
   */

  private int validateAndGetTrainNumber() {
    System.out.print("\nTrain number, max 4 digits: ");
    String trainNumber = scanner.nextLine();

    int trainNumberInt;
    boolean validInput = false;
    while (!validInput) {
      // try-catch block to catch invalid input
      try {
        if (trainNumber.isEmpty()) {
        throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
        }
        trainNumberInt = Integer.parseInt(trainNumber);
        if (trainNumberInt <= 0 || trainNumberInt > 9999) {
          throw new IllegalArgumentException("Train number has to be positive and max 4 digits.");
        } else if (trainDispatch.findDuplicateTrainNumber(trainNumberInt)) {
          throw new IllegalArgumentException("Train number already exists.");
        }
        // if input is valid, set validInput to true to exit while loop
        validInput = true;

        // if input is invalid, print error message and ask user to try again
      } catch (NumberFormatException e) {
        System.out.print(WRONG_FORMAT);
        trainNumber = scanner.nextLine();
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
        trainNumber = scanner.nextLine();
      }
    }
    return Integer.parseInt(trainNumber);
  }

  /**
   * Method to ensure right train number format when finding a departure.
   * Ensures that the train number user input is not empty, consists and is a positive number between 1 and 9999.
   *
   * @return The user input as an integer.
   */

  private int ensureRightTrainNumberToFindDeparture() {
    System.out.print("\nTrain number, max 4 digits. Press 0 to exit: ");
    // gets string input first to check if input is empty
    String trainNumber = scanner.nextLine();
    int trainNumberInt;

    boolean validInput = false;
    while (!validInput) {
      // if user wants to exit
      if (trainNumber.equals("0")) {
        return 0;
      }
      try {
        if (trainNumber.isEmpty()) {
        throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
        }
        // if input is not empty, parse to int
        trainNumberInt = Integer.parseInt(trainNumber);
        if (trainNumberInt <= 0 || trainNumberInt > 9999) {
          throw new IllegalArgumentException("Train number has to be positive and max 4 digits. ");
        } else if (!trainDispatch.findDuplicateTrainNumber(trainNumberInt)) {
          throw new IllegalArgumentException("Train number does not exist. ");
        }
        // if input is valid, set validInput to true to exit while loop
        validInput = true;

        // if input is invalid, print error message and ask user to try again
      } catch (NumberFormatException e) {
        System.out.print(WRONG_FORMAT);
        trainNumber = scanner.nextLine();
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN + " (0 to exit): ");
        trainNumber = scanner.nextLine();
      }
    }
    return Integer.parseInt(trainNumber);
  }

  /**
   * Method to ensure right destination format when finding a departure.
   * Ensures that the destination user input is not empty, consists in the list and consists of only letters.
   *
   * @return The user input as an integer.
   */

  private String ensureRightDestinationToFindDeparture() {
    System.out.print("\nDestination (0 to exit): ");
    String destination = scanner.nextLine().trim();

    boolean validInput = false;
    while (!validInput) {
      // if user wants to exit
      if (destination.trim().equals("0")) {
          return null;
      }
      // try-catch block to catch invalid input
      try {
        if (destination.isEmpty()) {
          throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
        } else if (!destination.matches("^[ A-Za-z0]+$")) {
          throw new IllegalArgumentException("Destination can only contain letters. ");
        } else if (trainDispatch.findDeparturesByDestination(destination).length == 0) {
          throw new IllegalArgumentException("Destination does not exist. ");
        }
        // if input is valid, set validInput to true to exit while loop
        validInput = true;

        // if input is invalid, print error message and ask user to try again
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN + " (0 to exit): ");
        destination = scanner.nextLine().trim();
      }
    }
    return destination;
  }

  /**
   * Method to ensure right track or delay format when creating a new departure.
   * Ensures that the track or delay user input is not empty and a positive number between 0 and 999.
   *
   * @return The user input as an integer.
   */

  private int validateAndGetTrackOrDelay() {
    // gets string input first to check if input is empty
    String value = scanner.nextLine();
    int valueInt;

    boolean validInput = false;
    while (!validInput) {
      try {
        if (value.isEmpty()) {
        throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
        }
        // if input is not empty, parse to int
        valueInt = Integer.parseInt(value);
        if (valueInt < 0 || valueInt > 999) {
          throw new IllegalArgumentException("Number has to be positive and max 3 digits.");
        }
        // if input is valid, set validInput to true to exit while loop
        validInput = true;

        // if input is invalid, print error message and ask user to try again
      } catch (NumberFormatException e) {
        System.out.print(WRONG_FORMAT);
        value = scanner.nextLine();
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
        value = scanner.nextLine();
      }
    }
    return Integer.parseInt(value);
  }

  /**
   * Prints the header of the departure list.
   */

  private static void printHeader() {
    System.out.printf("\n%-12s %-7s %-18s %-15s %-12s %-10s%n",
              "| Time", "Line", "Train Number", "Destination", "Delay", "Track     |");
    System.out.println("-".repeat(80));
  }

  /**
   * Prints the train dispatch.
   */

  private void printTrainDispatch() {
    // if list is empty, print message and return to menu
    if (listIsEmpty()) {
      return;
    }
    System.out.println(trainDispatch);
  }
}