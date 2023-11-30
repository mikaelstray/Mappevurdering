package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * This class represents the user interface of the application.
 * It is responsible for presenting the menu to the user, and
 * for retrieving the user's menu choice.
 * It is also responsible for presenting the result of the user's
 * menu choice.
 * The class is dependent on the TrainDispatch class, and uses
 * its methods to retrieve information about departures.
 * @author Mikael Stray Froeyshov
 * @version 1.0
 *
 * @since 2023-11-02
 */

public class UserInterface {
  private static TrainDispatch trainDispatch;
  private static Scanner scanner;
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
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

  public static void init() {
    trainDispatch = new TrainDispatch();
    scanner = new Scanner(System.in);

    trainDispatch.registerDeparture(new Departure(LocalTime.of(17,45), "F4",
            123, "Lillestrom", 1, 0));
    trainDispatch.registerDeparture(new Departure(LocalTime.of(19, 0), "L1",
            456, "Oslo S", 2, 31));
    trainDispatch.registerDeparture(new Departure(LocalTime.of(16, 12), "E5",
            789, "Trondheim", 0, 0));
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

  private boolean listIsEmpty() {
    if (trainDispatch.checkIfListIsEmpty()) {
      System.out.println("List is empty, add a new departure first");
      return true;
    }
    return false;
  }

  private void addDeparture() {
    trainDispatch.registerDeparture(typeInDepartureInfo());
    System.out.print("\nDeparture was successfully added");
  }

  private void removeDeparture() {
    if (listIsEmpty()) {
      return;
    }
    int trainNumber = ensureRightTrainNumberToFindDeparture();
    if (trainNumber == 0) {
      return;
    }
    Departure departureToRemove = trainDispatch.findDepartureByNumber(trainNumber);
    trainDispatch.removeDeparture(departureToRemove);
    System.out.println("\nDeparture with train number " + trainNumber + " was removed");
  }


  private void findDepartureByNumber() {
    int trainNumber = ensureRightTrainNumberToFindDeparture();
    if (trainNumber == 0) {
      return;
    }
    printHeader();
    printSingleDepartureInfo(trainDispatch.findDepartureByNumber(trainNumber));
    }

  private void findDeparturesByDestination() {
    String destination = ensureRightDestinationToFindDeparture();
    if (destination == null) {
      return;
    }
    printHeader();
    for (Departure departure : trainDispatch.findDeparturesByDestination(destination)) {
      printSingleDepartureInfo(departure);
    }
  }


  private void setTrack() {
    if (listIsEmpty()) {
      return;
    }
    int trainNumber = ensureRightTrainNumberToFindDeparture();
    if (trainNumber == 0) {
      return;
    }
    System.out.println("Track?");
    int track = ensureRightTrackAndDelayFormat();
    trainDispatch.setDelay(trainNumber, track);
    System.out.println("\n Delay for departure with train number " + trainNumber + " was set to " + track);
  }

    private void setDelay() {
    if (listIsEmpty()) {
      return;
    }
    int trainNumber = ensureRightTrainNumberToFindDeparture();
    if (trainNumber == 0) {
      return;
    }
    System.out.println("Delay?");
    int delay = ensureRightTrackAndDelayFormat();
    trainDispatch.setDelay(trainNumber, delay);
    System.out.println("\n Delay for departure with train number " + trainNumber + " was set to " + delay);
  }

  private void updateTime() {
    LocalTime newTime = ensureRightTimeFormat();
    trainDispatch.setTime(newTime);
    System.out.println("\n Time was updated to " + formatter.format(newTime));
  }

  private String ensureRightDestinationFormat() {
    System.out.println("\nDestination: ");
    String input = scanner.nextLine();
    boolean validInput = false;
    while (!validInput) {
      try {
        if (input.isEmpty()) {
          throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
        } else if (!input.matches("[a-zA-Z]+")) {
          throw new IllegalArgumentException("Destination can only contain letters. ");
        }
        validInput = true;
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + (PLEASE_TRY_AGAIN));
        input = scanner.nextLine();
      }
    }
    return input;
  }

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

  private String ensureRightLineFormat() {
    System.out.print("\nLine, max 5 digits: ");
    String line = scanner.nextLine();
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
        line = scanner.nextLine();
      }
    }
    return line;
  }

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

  private String ensureRightDestinationToFindDeparture() {
    System.out.print("\nDestination: ");
    String destination = scanner.nextLine();
    boolean validInput = false;
    while (!validInput) {
      if (destination.equals("0")) {
        return null;
      }
      try {
        if (destination.isEmpty()) {
        throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
        } else if (!destination.matches("[a-zA-Z]+")) {
          throw new IllegalArgumentException("Destination can only contain letters. ");
        } else if (trainDispatch.findDeparturesByDestination(destination).length == 0) {
          throw new IllegalArgumentException("Destination does not exist. ");
        }
        validInput = true;
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN + " (0 to exit): ");
        destination = scanner.nextLine();
      }
    }
    return destination;
  }
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
          throw new IllegalArgumentException("Number has to be 1 or more and max 3 digits.");
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

  private Departure typeInDepartureInfo() { //TODO: null validation and change input method?

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
   * Starts the application. This is the main loop of the application,
   * presenting the menu, retrieving the selected menu choice from the user,
   * and executing the selected functionality.
   */

  private static void centerText(String centerText, int width) {
    // Calculate the number of spaces on each side to center the text
    int padding = (width - centerText.length()) / 2;
    // Print the centered text
    System.out.printf("%" + padding + "s%s%" + padding + "s%n", "", centerText, "");
  }

  private static void printLeftRightAligned(String leftText, String rightText, int width) {
    // Calculate the space available for the right-aligned text
    int availableSpace = Math.max(0, width - leftText.length());
    // Print the left and right-aligned text using format specifiers
    System.out.printf("%s%" + availableSpace + "s%n", leftText, rightText);
}
    private static void printSingleDepartureInfo(Departure departure) {
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

  private void printHeader() {
    System.out.printf("\n%-12s %-7s %-18s %-15s %-12s %-10s%n",
              "| Time", "Line", "Train Number", "Destination", "Delay", "Track      |");
    System.out.println("-".repeat(80));
  }

  private static void printTrainDispatch() {
    if (trainDispatch.checkIfListIsEmpty()) {
      System.out.println("List is empty, add a new departure first");
      return;
    }
    System.out.println("\n");
    System.out.println("-".repeat(80));
    centerText("Train Dispatch", 80);
    printLeftRightAligned("   " + trainDispatch.getNumberOfDepartures() + " departures"
            ,formatter.format(trainDispatch.getTime()) + "   ",80);
    System.out.println("-".repeat(80));
    System.out.println();
    System.out.printf("%-12s %-7s %-18s %-15s %-12s %-10s%n",
                "| Time", "Line", "Train Number", "Destination", "Delay", "Track      |");
    System.out.println("-".repeat(80));
    for (Departure departure : trainDispatch.departureListAfterCurrentTimeAndDelay()) {
            printSingleDepartureInfo(departure);
    }
    System.out.println("-".repeat(80));
    System.out.println("\n");
  }

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
}