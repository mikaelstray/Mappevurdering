package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserInterface {
  private static TrainDispatch trainDispatch;
  private static Scanner scanner;
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
  private static final String TRAIN_NUMBER_QUESTION = "Train number?";
  private static final String TRAIN_NUMBER_NON_EXISTING = "Train number does not exist, try again";
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

    Departure departure1 = new Departure("Lillestrom", LocalTime.of(17,45), "F4", 123, "Lillestrom", 1, 0);
    Departure departure2 = new Departure("Oslo S", LocalTime.of(19, 0), "L1", 456, "Oslo S", 2, 31);
    Departure departure3 = new Departure("Trondheim", LocalTime.of(16, 12), "E5", 789, "Trondheim", 0, 0);

    trainDispatch.registerDeparture(departure1);
    trainDispatch.registerDeparture(departure2);
    trainDispatch.registerDeparture(departure3);
  }

  /**
   * Prints the menu choices to the console.
   */

  private static void showMenu() {
    System.out.println("\n***** Property Register Application v0.1 *****\n");
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
      int choice = scanner.nextInt();
      if (choice >= 1 && choice <= 9) {
        return choice;
      } else {
        System.out.print("Has to be a number between 1 and 9");
        return 0;
      }
    } else {
      System.out.print("Unrecognized input");
      scanner.next();
      return 0;
    }
  }

  private boolean departureListIsEmpty() {
    return trainDispatch.checkIfDepartureListAfterTimeIsEmpty();
  }

  private boolean trainNumberDoesNotExist(int trainNumber) {
    return trainDispatch.findDepartureByNumber(trainNumber) == null;
  }

  private void addDeparture() {
    Departure departure = typeInDepartureInfo();
    trainDispatch.registerDeparture(departure);
    System.out.print("\n " + departure + " was added");
  }

  private void removeDeparture() {
    if (this.departureListIsEmpty()) {
      System.out.println("List is empty, add a new departure first");
    } else {
      System.out.println(TRAIN_NUMBER_QUESTION);
      int trainNumber = scanner.nextInt();
      if (this.trainNumberDoesNotExist(trainNumber)) {
        System.out.println(TRAIN_NUMBER_NON_EXISTING);
      } else {
        trainDispatch.removeDeparture(trainDispatch.findDepartureByNumber(trainNumber));
        System.out.println("\n Departure with train number " + trainNumber + " was removed");
      }
    }
  }

  private void findDepartureByNumber() {
    System.out.println(TRAIN_NUMBER_QUESTION);
    int trainNumber = scanner.nextInt();
    if (this.trainNumberDoesNotExist(trainNumber)) {
      System.out.println(TRAIN_NUMBER_NON_EXISTING);
    } else {
      System.out.println(trainDispatch.findDepartureByNumber(trainNumber));
    }
  }

  private void findDepartureByDestination() {
    System.out.println("Destination?");
    String destination = scanner.next();
    if (trainDispatch.findDepartureByDestination(destination).isEmpty()) {
      System.out.println("Destination does not exist, try again");
    } else {
      System.out.println(trainDispatch.findDepartureByDestination(destination));
    }
  }

  private void setTrack() {
    if (this.departureListIsEmpty()) {
      System.out.println("List is empty, add a new departure first");
    } else {
      System.out.println(TRAIN_NUMBER_QUESTION);
      int trainNumber = scanner.nextInt();
      System.out.println("Track?");
      int track = scanner.nextInt();
      if (!trainDispatch.findDuplicateTrainNumberWithNumber(trainNumber)) {
        System.out.println(TRAIN_NUMBER_NON_EXISTING);
      } else {
        trainDispatch.setTrack(trainNumber, track);
        System.out.println("\n Track for departure with train number " + trainNumber + " was set to " + track);
      }
    }
  }

  private void setDelay() {
    if (this.departureListIsEmpty()) {
      System.out.println("Departure list is empty, add a departure first");
    } else {
      System.out.println(TRAIN_NUMBER_QUESTION);
      int trainNumber = scanner.nextInt();
      System.out.println("Delay?");
      int delay = scanner.nextInt();
      if (this.trainNumberDoesNotExist(trainNumber)) {
        System.out.println(TRAIN_NUMBER_NON_EXISTING);
      } else {
        trainDispatch.setDelay(trainNumber, delay);
        System.out.println("\n Delay for departure with train number " + trainNumber + " was set to " + delay);
      }
    }
  }

  private void updateTime() {
    LocalTime currentTime = trainDispatch.getTime();
    System.out.println("New time? In format hh:mm. Has to be after current time: " + formatter.format(currentTime));
    LocalTime newTime = LocalTime.parse(ensureRightTimeFormat());
    trainDispatch.setTime(newTime);
    System.out.println("New time is " + newTime);
}

  private String ensureNotNullAndGetInput() {
    boolean validInput = false;
    String input = scanner.nextLine();
    while (!validInput) {
      try {
        if (input.isEmpty()) {
          throw new IllegalArgumentException("Input cannot be empty.");
        }
        validInput = true;
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + (PLEASE_TRY_AGAIN));
        input = scanner.nextLine();
      }
    }
    return input;
  }

  private String ensureRightTimeFormat() {
    String time = scanner.nextLine();
    boolean validInput = false;
    while (!validInput) {
      try {
        if (time.isEmpty()) {
          throw new IllegalArgumentException("Input cannot be empty.");
        }
        String[] timeArray = time.split(":");
        int hours = Integer.parseInt(timeArray[0]);
        int minutes = Integer.parseInt(timeArray[1]);

        boolean hoursCorrectRange = hours >= 0 && hours <= 23;
        boolean minutesCorrectRange = minutes >= 0 && minutes <= 59;

        if (!hoursCorrectRange || !minutesCorrectRange) {
          throw new IllegalArgumentException("Wrong format. Hours has to be between 00-23 and minutes 00-59.");
        } else if (LocalTime.parse(time).isBefore(trainDispatch.getTime())) {
          throw new IllegalArgumentException("Time cannot be before current time.");
        }
        validInput = true;
      } catch (NumberFormatException e) {
        System.out.print("Wrong format. Has to be a number. Please try again: ");
        time = scanner.nextLine();
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
        time = scanner.nextLine();
      } catch (Exception e) {
        System.out.print("Invalid time format. Please try again: ");
        time = scanner.nextLine();
      }
    }
    return time;
  }

  private String ensureRightLineFormat() {
    String line = scanner.nextLine();
    boolean validInput = false;
    while (!validInput) {
      try {
        if (line.isEmpty() || line.equals("0")) {
          throw new IllegalArgumentException("Input cannot be empty.");
        } else if (line.length() > 5) {
          throw new IllegalArgumentException("Line cannot be longer than 5 characters.");
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
    int trainNumber = scanner.nextInt();
    boolean validInput = false;
    while (!validInput) {
      try {
        if (trainNumber <= 0 || String.valueOf(trainNumber).isEmpty()) {
          throw new IllegalArgumentException("Train number cannot be empty, 0 or negative.");
        } else if (trainNumber > 9999) {
          throw new IllegalArgumentException("Train number cannot be longer than 4 digits.");
        } else if (trainDispatch.findDuplicateTrainNumberWithNumber(trainNumber)) {
          throw new IllegalArgumentException("Train number already exists.");
        }
        validInput = true;
      } catch (NumberFormatException e) {
        System.out.print("Wrong format. Has to be a number. Please try again: ");
        trainNumber = scanner.nextInt();
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
        trainNumber = scanner.nextInt();
      }
    }
    return trainNumber;
  }

  private int ensureRightTrackAndDelayFormat() {
    boolean validInput = false;
    int trackOrDelay = scanner.nextInt();
    while (!validInput) {
      try {
        if (trackOrDelay < 0) {
          throw new IllegalArgumentException("Track or delay cannot be negative.");
        } else if (trackOrDelay > 999) {
          throw new IllegalArgumentException("Track or delay cannot be longer than 3 digits.");
        }
        validInput = true;
      } catch (NumberFormatException e) {
        System.out.print("Wrong format. Has to be a number. Please try again: ");
        trackOrDelay = scanner.nextInt();
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
        trackOrDelay = scanner.nextInt();
      }
    }
    return trackOrDelay;
  }

  private Departure typeInDepartureInfo() { //TODO: null validation and change input method?
      System.out.print("Name: ");
      scanner.nextLine();
      String name = ensureNotNullAndGetInput();

      System.out.print("\nTime in format (hh:mm): ");
      String time = ensureRightTimeFormat();
      LocalTime localTime = LocalTime.parse(time);

      System.out.print("\nLine: ");
      String line = ensureRightLineFormat();

      System.out.print("\nTrain number (0 to exit to menu): ");
      int trainNumber = ensureRightTrainNumberFormat();

      System.out.print("\nDestination: ");
      String destination = ensureNotNullAndGetInput();

      System.out.print("\nTrack, type 0 if not existing yet: ");
      int track = ensureRightTrackAndDelayFormat();

      System.out.print("\nDelay: ");
      int delay = ensureRightTrackAndDelayFormat();

      return new Departure(name, localTime, line, trainNumber, destination, track, delay);
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
    private static void printDepartureInfo(Departure departure) {
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

  private static void printTrainDispatch() {
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
    for (Departure departure : trainDispatch.getDepartureList()) {
            printDepartureInfo(departure);
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
          findDepartureByDestination();
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