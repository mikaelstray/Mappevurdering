package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserInterface {

  private TrainDispatch trainDispatch;
  private Scanner scanner;


  // Constants representing the different menu choices

  private final int addDeparture = 1;
  private final int listAllDepartures = 2;
  private final int findDepartureByNumber = 3;
  private final int findDepartureByDestination = 4;
  private final int setTrack = 5;
  private final int setDelay = 6;
  private final int updateTime = 7;
  private final int exit = 8;

  public void init() {
    trainDispatch = new TrainDispatch();
    scanner = new Scanner(System.in);

    Departure departure1 = new Departure("Lillestrom", LocalTime.of(17,45), "F4", 123, "Lillestrom", 1, 0);
    Departure departure2 = new Departure("Oslo S", LocalTime.of(19, 0), "L1", 456, "Oslo S", 2, 31);
    Departure departure3 = new Departure("Trondheim", LocalTime.of(16, 12), "E5", 789, "Trondheim", 0, 0);

    trainDispatch.registerDeparture(departure1);
    trainDispatch.registerDeparture(departure2);
    trainDispatch.registerDeparture(departure3);
  }

  private static void showMenu() {
    System.out.println("\n***** Property Register Application v0.1 *****\n");
    System.out.println("1. Add departure                         ++      +------");
    System.out.println("2. List all departures                   ||      |+-+ | ");
    System.out.println("3. Find departure by number            /---------|| | | ");
    System.out.println("4. Find departure by destination      + ========  +-+ | ");
    System.out.println("5. Set track                       _|--/~\\------/~\\-+ ");
    System.out.println("6. Set delay                       //// \\_/      \\_/  ");
    System.out.println("7. Update time");
    System.out.println("8. Quit");
    System.out.println("\nPlease enter a number between 1 and 8:");
  }
  /**
   * Presents the menu for the user, and awaits input from the user. The menu
   * choice selected by the user is being returned.
   *
   * @return the menu choice by the user as a positive number starting from 1.
   * If 0 is returned, the user has entered a wrong value
   */

  private int userChoice() {
    int menuChoice = 0;
    if (scanner.hasNextInt()) {
      menuChoice = scanner.nextInt();
    } else {
      System.out.println("You must enter a number, not text");
    }
    return menuChoice;
  }

  private boolean departureListIsEmpty() {
    return trainDispatch.checkIfDepartureListAfterTimeIsEmpty();
  }

  private boolean trainNumberDoesNotExist(int trainNumber) {
    return trainDispatch.findDepartureByNumber(trainNumber) == null;
  }

  private void addDeparture() {
    Departure departure = typeInDepartureInfo();
    if (departure == null) {
      System.out.println("Something when wrong, departure was not added");
    } else {
      trainDispatch.registerDeparture(departure);
      System.out.print("n\"" + departure + " was added");
      if (trainDispatch.addedDepartureIsBeforeCurrentTime(departure)) {
        System.out.println(", but the departure is before current time, and will not be shown");
      }
    }
  }

  private void findDepartureByNumber() {
    System.out.println("Train number?");
    int trainNumber = scanner.nextInt();
    if (this.trainNumberDoesNotExist(trainNumber)) {
      System.out.println("Train number does not exist, try again");
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
      System.out.println("Train number?");
      int trainNumber = scanner.nextInt();
      System.out.println("Track?");
      int track = scanner.nextInt();
      if (!trainDispatch.findDuplicateTrainNumberWithNumber(trainNumber)) {
        System.out.println("Train number does not exist, try again");
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
      System.out.println("Train number?");
      int trainNumber = scanner.nextInt();
      System.out.println("Delay?");
      int delay = scanner.nextInt();
      if (this.trainNumberDoesNotExist(trainNumber)) {
        System.out.println("Train number does not exist, try again");
      } else {
        trainDispatch.setDelay(trainNumber, delay);
        System.out.println("\n Delay for departure with train number " + trainNumber + " was set to " + delay);
      }
    }
  }

  private void updateTime() {
    LocalTime currentTime = trainDispatch.getTime();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    System.out.println("New time? In format hh:mm. Has to be after current time: " + formatter.format(currentTime));
    String time = scanner.next();
    LocalTime newTime = LocalTime.parse(time);
    if (newTime.isBefore(currentTime)) {
      System.out.println("New time has to be after current time");
    } else {
      trainDispatch.setTime(newTime);
      System.out.println("New time is " + newTime);
    }
  }

  private Departure typeInDepartureInfo() {
    System.out.println("Name");
    String name;
    name = scanner.next();

    System.out.println("Time, hh:mm");
    String time = scanner.next();
    LocalTime localTime = LocalTime.parse(time);

    System.out.println("Line");
    String line = scanner.next();

    System.out.println("Train number");
    int trainNumber = Integer.parseInt(scanner.next());
    while (trainDispatch.findDuplicateTrainNumberWithNumber(trainNumber)) {
      System.out.println("\nTrain number already exists, type new. 0 to cancel and go back to menu");
      trainNumber = Integer.parseInt(scanner.next());
      if (trainNumber == 0) {
        return null;
      }
    }

    System.out.println("Destination");
    String destination = scanner.next();

    System.out.println("Track, type 0 if not existing yet");
    int track = Integer.parseInt(scanner.next());
    if (track == 0) {
      track = -1;
    }

    System.out.println("Delay");
    int delay = Integer.parseInt(scanner.next());

    return new Departure(name, localTime, line, trainNumber, destination, track, delay);
  }
  /**
   * Starts the application. This is the main loop of the application,
   * presenting the menu, retrieving the selected menu choice from the user,
   * and executing the selected functionality.
   */

  public void start() {
    boolean finished = false;
        // The while-loop will run as long as the user has not selected to quit the application
    while (!finished) {
      showMenu();
      int menuChoice = this.userChoice();
      switch (menuChoice) {
        case addDeparture:
          addDeparture();
          break;
        case listAllDepartures:
          System.out.println(trainDispatch.departureListAfterCurrentTime().toString());
          break;
        case findDepartureByNumber:
          findDepartureByNumber();
          break;
        case findDepartureByDestination:
          findDepartureByDestination();
          break;
        case setTrack:
          setTrack();
          break;
        case setDelay:
          setDelay();
          break;
        case updateTime:
          updateTime();
          break;
        case exit:
          System.out.println("Thank you for using the Properties app!\n");
          finished = true;
          break;
        default:
          System.out.println("Unrecognized menu selected...");
          break;
      }
    }
  }
}