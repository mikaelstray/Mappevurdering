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
 * It uses the TrainDispatch class to retrieve and manipulate departure data, and the
 * UserInputHandler class to get and validate user input.
 * </p>
 * <p>
 * The class also has methods for printing the train dispatch and the departures.
 * </p>
 *
 * @author Mikael Stray Froeyshov
 * @version 2.1
 * @since 2023-11-02
 */

public class UserInterface {

  // Instance variables
  private TrainDispatch trainDispatch;
  private UserInputHandler userInputHandler;
  private Scanner scanner;
  private DateTimeFormatter formatter;

  /**
   * Initializes the user interface, creates necessary objects and registers.
   */

  public void init() {
    trainDispatch = new TrainDispatch(LocalTime.parse("12:00"));
    userInputHandler = new UserInputHandler();
    scanner = new Scanner(System.in);
    formatter = DateTimeFormatter.ofPattern("HH:mm");

    trainDispatch.registerDeparture(new Departure(LocalTime.of(17, 45), "F4",
            123, "Lillestrom", 1, 0));
    trainDispatch.registerDeparture(new Departure(LocalTime.of(23, 55), "L1",
            456, "Oslo S", 2, 4));
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
    return userInputHandler.createDepartureFromUserInput(trainDispatch);
  }

  /**
   * Method to ensure right train number format and getting input when finding a departure.
   * <b>Note:</b> Made to avoid code duplication.
   * @return The user input as an integer.
   */

  private int validateAndGetTrainNumberToFindDeparture() {
    return userInputHandler.validateTrainNumberToFindDeparture(trainDispatch);
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
    int trainNumber = validateAndGetTrainNumberToFindDeparture();

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
    int trainNumber = validateAndGetTrainNumberToFindDeparture();

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
    String destination = userInputHandler.validateDestinationToFindDeparture(trainDispatch);

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
    int trainNumber = validateAndGetTrainNumberToFindDeparture();

    // if user wants to exit, return to menu
    if (trainNumber == 0) {
      return;
    }

    System.out.println("Track?");
    int track = userInputHandler.validateNumericInput();
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
    int trainNumber = validateAndGetTrainNumberToFindDeparture();

    // if user wants to exit
    if (trainNumber == 0) { // if user wants to exit
      return;
    }
    System.out.println("Delay?");
    int delay = userInputHandler.validateNumericInput();

    trainDispatch.setDelay(trainNumber, delay);
    System.out.println("\n Delay for departure with train number "
            + trainNumber + " was set to " + delay);
  }

  /**
   * Updates the time of the train dispatch.
   */

  private void updateTime() {
    LocalTime newTime = userInputHandler.validateAndGetTime(trainDispatch);

    trainDispatch.setTime(newTime);
    System.out.println("\n Time was updated to " + formatter.format(newTime));
  }
}