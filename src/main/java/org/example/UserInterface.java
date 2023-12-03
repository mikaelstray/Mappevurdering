package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

/**
 * <h1>UserInterface.</h1>
 * <p>
 * The UserInterface class manages the interaction with the user in the Train Dispatch application.
 * It presents a menu, retrieves user input, and calls appropriate methods.
 * </p>
 * <p>
 * This class is responsible for handling user choices such as listing departures,
 * adding or removing departures, and updating departure information with validation of inputs.
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

  /**
   * Initializes the user interface, creates necessary objects and registers.
   */

  public void init() {
    trainDispatch = new TrainDispatch();
    scanner = new Scanner(System.in);
    formatter = DateTimeFormatter.ofPattern("HH:mm");

    trainDispatch.registerDeparture(new Departure(LocalTime.of(17, 45), "F4",
            123, "Lillestrom", 1, 0));
    trainDispatch.registerDeparture(new Departure(LocalTime.of(23, 55), "L1",
            456, "Oslo S", 2, 0));
    trainDispatch.registerDeparture(new Departure(LocalTime.of(12, 12), "E5",
            789, "Trondheim", 0, 0));
  }

  /**
   * Enum representing the different menu choices.
   */

  private enum MenuChoice {
    LIST_ALL_DEPARTURES(1),
    ADD_DEPARTURE(2),
    REMOVE_DEPARTURE(3),
    FIND_DEPARTURE_BY_NUMBER(4),
    FIND_DEPARTURE_BY_DESTINATION(5),
    SET_TRACK(6),
    SET_DELAY(7),
    UPDATE_TIME(8),
    EXIT(9);

    private final int value;

    MenuChoice(int value) {
     this.value = value;
    }

    private int getValue() {
      return value;
    }
  }

  /**
   * Gets the user's choice from the console.
   *
   * @return The user's choice as an enum constant (MenuChoice).
   */

  private MenuChoice getUserChoice() {
    System.out.print("Enter your choice (1-9): ");
    while (true) {
      try {
        int choice = Integer.parseInt(scanner.nextLine());

        // Find the corresponding enum constant based on the user's choice, or throw an exception
        return EnumSet.allOf(MenuChoice.class).stream()
                .filter(menuChoice -> menuChoice.getValue() == choice)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Number must be between 1 and 9."));

      } catch (NumberFormatException e) {
        System.out.print("Invalid input. Please enter a valid number: ");
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + " Please try again: ");
      }
    }
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
      MenuChoice menuChoice = getUserChoice();
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
        // User choice input is validated in userChoice() method,
        // but default case is added for robustness
        default:
          System.out.println("Invalid choice. Please try again.");
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
  }

  /**
   * Creates a new departure from user input. Validates every input.
   *
   * @return The new departure.
   */

  private Departure createDepartureFromUserInput() {

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

  /**
   * Adds a departure to the list.
   */

  private void addDeparture() {
    // departure is already validated in createDepartureFromUserInput()
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

    List<Departure> departures = trainDispatch.findDeparturesByDestination(destination);
    printHeader();
    departures.forEach(System.out::println);
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
    System.out.println("\n Track for departure with train number "
            + trainNumber + " was set to " + track);
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
    System.out.println("\n Delay for departure with train number "
            + trainNumber + " was set to " + delay);
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
        // if input is valid, return input
        return validateDestination(input);
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
      }
    }
  }

  /**
   * Method to ensure right destination format when creating a new departure.
   * Ensures that the destination user input is not empty and only contains letters.
   *
   * @param input The user input as a String.
   * @return The user input as a String.
   * @throws IllegalArgumentException if the input is empty or is not only letters.
   */

  private String validateDestination(String input) {
    if (input.isEmpty()) {
      throw new IllegalArgumentException(INPUT_CANNOT_BE_EMPTY);
    } else if (!input.matches("^[ A-Za-z]+$")) {
      throw new IllegalArgumentException("Destination can only contain letters. ");
    }
    return input;
  }

  /**
   * Method to ensure right time format and getting input when creating a new departure.
   *
   * @return The user input as a LocalTime object.
   */

  private LocalTime validateAndGetTime() {
    System.out.println("Time? In format (hh:mm): ");
    // asks for right format until user input is valid
    while (true) {
      String newTime = scanner.nextLine();
      LocalTime timeNow = trainDispatch.getTime();
      try {
        // if input is valid, return new time
        return validateTimeInput(newTime, timeNow);
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
      } catch (Exception e) {
        System.out.print("Wrong format. Should be in the format [00-23]:[00-59]."
                + PLEASE_TRY_AGAIN);
      }
    }
  }

  /**
   * Method to ensure right time format when creating a new departure.
   * Ensures that the time user input is not empty or before the current time.
   *
   * @param newTime The user input as a String.
   * @param timeNow The current time as a LocalTime object.
   * @return The user input as a LocalTime object.
   * @throws IllegalArgumentException if the input is empty or before the current time.
   */

  private LocalTime validateTimeInput(String newTime, LocalTime timeNow) {
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
   * Method to ensure right line format and getting input when creating a new departure.
   *
   * @return The user input as an integer.
   */

  private String validateAndGetLine() {
    System.out.print("\nLine, max 5 digits: ");
    // asks for right format until user input is valid
    while (true) {
      String line = scanner.nextLine().trim();
      try {
        // if input is valid, return input
        return validateLineInput(line);
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
      }
    }
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

  private String validateLineInput(String line) {
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
   * Method to ensure right train number format and getting input when creating a new departure.
   *
   * @return The user input as an integer.
   */

  private int validateAndGetTrainNumber() {
    System.out.print("\nTrain number, max 4 digits: ");
    // asks for right format until user input is valid
    while (true) {
      String trainNumber = scanner.nextLine();
      try {
        // return input if input is valid
        return validateTrainNumber(trainNumber);
      } catch (NumberFormatException e) {
        System.out.print(WRONG_FORMAT);
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
      }
    }
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

  private int validateTrainNumber(String trainNumber) {
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
   * Method to ensure right train number format and getting input when finding a departure.
   *
   * @return The user input as an integer.
   */

  private int ensureRightTrainNumberToFindDeparture() {
    System.out.print("\nTrain number, max 4 digits. Press 0 to exit: ");
    // asks for right format until user input is valid
    while (true) {
      String trainNumber = scanner.nextLine();
      try {
        // Check if the user wants to exit
        if (trainNumber.equals("0")) {
          return 0;
        }
        // return input if input is valid
        return validateTrainNumberToFindDeparture(trainNumber);
      } catch (NumberFormatException e) {
        System.out.print(WRONG_FORMAT);
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN + " (0 to exit): ");
      }
    }
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

  private int validateTrainNumberToFindDeparture(String trainNumber) {
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
   * Method to ensure right destination format and getting input when finding a departure.
   *
   * @return The user input as an integer.
   */

  private String ensureRightDestinationToFindDeparture() {
    System.out.print("\nDestination (0 to exit): ");
    // asks for right format until user input is valid
    while (true) {
      String destination = scanner.nextLine().trim();
      try {
        // Check if the user wants to exit
        if (destination.equals("0")) {
          return null;
        }
        return validateDestinationToFindDeparture(destination);
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN + " (0 to exit): ");
      }
    }
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

  private String validateDestinationToFindDeparture(String destination) {
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
   * Method to ensure right track or delay format and getting input when setting track or delay.
   *
   * @return The user input as an integer.
   */

  private int validateAndGetTrackOrDelay() {
    while (true) {
      String value = scanner.nextLine();
      try {
        return validateNumericInput(value);
      } catch (NumberFormatException e) {
        System.out.print(WRONG_FORMAT);
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage() + PLEASE_TRY_AGAIN);
      }
    }
  }

  /**
   * Method to ensure right track or delay format when setting track or delay.
   * Ensures that the user input is not empty, consists and is a positive number between 1 and 999.
   *
   * @param value The user input as a String.
   * @return The user input as an integer.
   * @throws IllegalArgumentException if the input is empty, not a number or not between 1 and 999.
   */

  private int validateNumericInput(String value) {
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