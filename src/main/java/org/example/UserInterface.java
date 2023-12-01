package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * This class represents the user interface of the application.
 * It is responsible for presenting the menu to the user, and
 * for retrieving the user's menu choice.
 * It is also responsible for presenting the result of the user's
 * menu choice, by validating the user's input and calling the appropriate
 * methods.
 * The class is dependent on the TrainDispatch class, and uses
 * its methods to retrieve information about departures.
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

    trainDispatch.registerDeparture(new Departure(LocalTime.of(17,45), "F4",
            123, "Lillestrom", 1, 0));
    trainDispatch.registerDeparture(new Departure(LocalTime.of(19, 0), "L1",
            456, "Oslo S", 2, 31));
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

    LocalTime time = ensureRightTimeFormat();

    String line = ensureRightLineFormat();

    int trainNumber = ensureRightTrainNumberFormat();

    String destination = ensureRightDestinationFormat();

    System.out.print("\nTrack (type 0 if not existing yet), max 3 digits: ");
    int track = ensureRightTrackAndDelayFormat();

    System.out.print("\nDelay, max 3 digits: ");
    int delay = ensureRightTrackAndDelayFormat();

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
    printSingleDepartureInfo(trainDispatch.findDepartureByNumber(trainNumber));
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
    // print header and all departures with the specified destination
    printHeader();
    Stream.of(departures)
            .forEach(this::printSingleDepartureInfo);
  }

  /**
   * Ensures right track format and sets the track of a departure.
   */

  private void setTrack() {
    if (listIsEmpty()) {
      return;
    }
    int trainNumber = ensureRightTrainNumberToFindDeparture();
    // if user wants to exit
    if (trainNumber == 0) {
      return;
    }
    System.out.println("Track?");
    int track = ensureRightTrackAndDelayFormat();
    trainDispatch.setTrack(trainNumber, track);
    System.out.println("\n Delay for departure with train number " + trainNumber + " was set to " + track);
  }

  /**
   * Ensures right train number format and sets the delay of a departure.
   */

  private void setDelay() {
    if (listIsEmpty()) {
      return;
    }
    int trainNumber = ensureRightTrainNumberToFindDeparture();
    // if user wants to exit
    if (trainNumber == 0) { // if user wants to exit
      return;
    }
    System.out.println("Delay?");
    int delay = ensureRightTrackAndDelayFormat();
    trainDispatch.setDelay(trainNumber, delay);
    System.out.println("\n Delay for departure with train number " + trainNumber + " was set to " + delay);
  }

  /**
   * Updates the time of the train dispatch.
   */

  private void updateTime() {
    LocalTime newTime = ensureRightTimeFormat();
    trainDispatch.setTime(newTime);
    System.out.println("\n Time was updated to " + formatter.format(newTime));
  }

  // Following methods ensure right format of user input

  /**
   * Method to ensure right destination format when creating a new departure.
   * Ensures that the destination user input is not empty and only contains letters.
   *
   * @return The user input as a String.
   */

  private String ensureRightDestinationFormat() {
    System.out.println("\nDestination: ");
    String input = scanner.nextLine().trim();
    boolean validInput = false;
    while (!validInput) {
      try {
        if (input.isEmpty()) {
          throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
        } else if (!input.matches("^[ A-Za-z]+$")) {
          throw new IllegalArgumentException("Destination can only contain letters. ");
        }
        validInput = true;
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

  private LocalTime ensureRightTimeFormat() {
    System.out.println("Time? In format (hh:mm): ");
    String time = scanner.nextLine();
    boolean validInput = false;
    while (!validInput) {
      try {
        if (time.isEmpty()) {
          throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
        } else if (LocalTime.parse(time).isBefore(trainDispatch.getTime())) {
          throw new IllegalArgumentException("Time cannot be before current time: "
                  + formatter.format(trainDispatch.getTime()) + ". ");
        }
        validInput = true;
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
        time = scanner.nextLine();
      } catch (Exception e) {
        System.out.print("Wrong format. Should be [00-23]:[00-59], please try again: ");
        time = scanner.nextLine();
      }
    }
    return LocalTime.parse(time);
  }

  /**
   * Method to ensure right line format when creating a new departure.
   * Ensures that the line user input is not empty or 0 and only contains letters and numbers.
   *
   * @return The user input as an integer.
   */

  private String ensureRightLineFormat() {
    System.out.print("\nLine, max 5 digits: ");
    String line = scanner.nextLine().trim();
    boolean validInput = false;
    while (!validInput) {
      try {
        if (line.isEmpty() || line.equals("0")) {
          throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
        } else if (line.length() > 5) {
          throw new IllegalArgumentException("Line cannot be longer than 5 characters.");
        } else if (!line.matches("^[a-zA-Z0-9]+$")) {
          throw new IllegalArgumentException("Line can only contain letters and numbers.");
        }
        validInput = true;
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

  private int ensureRightTrainNumberFormat() {
    System.out.print("\nTrain number, max 4 digits: ");
    String trainNumber = scanner.nextLine();
    int trainNumberInt;
    boolean validInput = false;
    while (!validInput) {
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
        validInput = true;
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
    String trainNumber = scanner.nextLine();
    int trainNumberInt;
    boolean validInput = false;
    while (!validInput) {
      if (trainNumber.equals("0")) {
        return 0;
      }
      try {
        if (trainNumber.isEmpty()) {
        throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
        }
        trainNumberInt = Integer.parseInt(trainNumber);
        if (trainNumberInt <= 0 || trainNumberInt > 9999) {
          throw new IllegalArgumentException("Train number has to be positive and max 4 digits. ");
        } else if (!trainDispatch.findDuplicateTrainNumber(trainNumberInt)) {
          throw new IllegalArgumentException("Train number does not exist. ");
        }
        validInput = true;
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

  private String ensureRightDestinationToFindDeparture() { // TODO: 0 to exit
    System.out.print("\nDestination (0 to exit): ");
    String destination = scanner.nextLine().trim();
    boolean validInput = false;
    while (!validInput) {
      if (destination.trim().equals("0")) {
          return null;
      }
      try {
        if (destination.isEmpty()) {
          throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
        } else if (!destination.matches("^[ A-Za-z0]+$")) {
          throw new IllegalArgumentException("Destination can only contain letters. ");
        } else if (trainDispatch.findDeparturesByDestination(destination).length == 0) {
          throw new IllegalArgumentException("Destination does not exist. ");
        }
        validInput = true;
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

  private int ensureRightTrackAndDelayFormat() {
    String value = scanner.nextLine();
    int valueInt;
    boolean validInput = false;
    while (!validInput) {
      try {
        if (value.isEmpty()) {
        throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
        }
        valueInt = Integer.parseInt(value);
        if (valueInt < 0 || valueInt > 999) {
          throw new IllegalArgumentException("Number has to be positive and max 3 digits.");
        }
        validInput = true;
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

  // Following methods print the result of the user's menu choice

  /**
   * Prints the specified text centered on the screen.
   *
   * @param centerText The text to center.
   * @param width      The width of the screen.
   */

  private static void centerText(String centerText, int width) {
    // Calculate the number of spaces on each side to center the text
    int padding = (width - centerText.length()) / 2;
    // Print the centered text
    System.out.printf("%" + padding + "s%s%" + padding + "s%n", "", centerText, "");
  }

  /**
   * Prints the specified left-aligned and right-aligned text on the same line.
   *
   * @param leftText  The left-aligned text.
   * @param rightText The right-aligned text.
   * @param width     The width of the screen.
   */

  private static void printLeftRightAligned(String leftText, String rightText, int width) {
    // Calculate the space available for the right-aligned text
    int availableSpace = Math.max(0, width - leftText.length());
    // Print the left and right-aligned text using format specifiers
    System.out.printf("%s%" + availableSpace + "s%n", leftText, rightText);
  }

  /**
   * Prints the information of a single departure.
   *
   * @param departure The departure to print.
   */

  private void printSingleDepartureInfo(Departure departure) {
    String delay = Integer.toString(departure.getDelay());
    String track = Integer.toString(departure.getTrack());
    if (departure.getDelay() == 0) {
      delay = "";
    }
    if (departure.getTrack() == -1) {
      track = "";
    }
    System.out.printf("%-13s %-10s %-14d %-15s %-14s %-8s %-6s",
              "| " + departure.getTime(), departure.getLine(), departure.getTrainNumber(),
              departure.getDestination(), " " + delay, track, "|");
    System.out.println(); // Move to the next line
  }

  /**
   * Prints the header of the departure list.
   */

  private static void printHeader() {
    System.out.printf("\n%-12s %-7s %-18s %-15s %-12s %-10s%n",
              "| Time", "Line", "Train Number", "Destination", "Delay", "Track      |");
    System.out.println("-".repeat(80));
  }

  /**
   * Prints the train dispatch.
   */

  private void printTrainDispatch() {
    if (listIsEmpty()) {
      return;
    }
    System.out.println("\n" + "-".repeat(80));
    centerText("Train Dispatch", 80);
    printLeftRightAligned("   " + trainDispatch.getNumberOfDepartures() + " departures"
            ,formatter.format(trainDispatch.getTime()) + "   ",80);
    System.out.println("-".repeat(80));
    printHeader();
    Stream.of(trainDispatch.departureListAfterCurrentTimeAndDelay())
            .forEach(this::printSingleDepartureInfo);
    System.out.println("-".repeat(80) + "\n");
  }
}