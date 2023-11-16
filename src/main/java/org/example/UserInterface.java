package org.example;

import java.time.LocalTime;
import java.util.Scanner;

public class UserInterface {

    private final TrainDispatch trainDispatch = new TrainDispatch();


// Constants representing the different menu choices

    private final int ADD_DEPARTURE = 1;
    private final int LIST_ALL_DEPARTURES = 2;
    private final int FIND_DEPARTURE_BY_NUMBER = 3;
    private final int FIND_DEPARTURE_BY_DESTINATION = 4;
    private final int SET_SPOR = 5;
    private final int SET_DELAY = 6;
    private final int UPDATE_TIME = 7;
    private final int EXIT = 8;

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
    private int userChoice(){
        Scanner sc = new Scanner(System.in);
        int menuChoice = 0;
        if (sc.hasNextInt()) {
            menuChoice = sc.nextInt();
        } else {
            System.out.println("You must enter a number, not text");
        }
        return menuChoice;
    }
    private void addDeparture(){
        Departure departure = typeInDepartureInfo();
        trainDispatch.registerDeparture(departure);
        System.out.print("n\"" + departure + " was added");
        if (trainDispatch.addedDepartureIsBeforeCurrentTime(departure)){
            System.out.println(", but the departure is before current time, and will not be shown");
        }
    }
    private void findDepartureByNumber(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Train number?");
        int trainNumber = scanner.nextInt();
        if (trainDispatch.findDepartureByNumber(trainNumber) == null) {
            System.out.println("Train number does not exist, try again");
        } else {
            System.out.println(trainDispatch.findDepartureByNumber(trainNumber));
        }
    }
    private void findDepartureByDestination(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Destination?");
        String destination = scanner.nextLine();
        if (trainDispatch.getDepartureList().isEmpty()){
            System.out.println("Destination does not exist, try again");
        }
        else{
            System.out.println(trainDispatch.findDepartureByDestination(destination));
        }
    }
    private boolean departureListIsEmpty(){
        return trainDispatch.getDepartureList().isEmpty();
    }
    private void setTrack(){
    if (departureListIsEmpty()) System.out.println("List is empty, add a new departure first");
    else {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Train number?");
      int trainNumber2 = scanner.nextInt();
      System.out.println("Track?");
      int track = scanner.nextInt();
      if (trainDispatch.findDepartureByNumber(trainNumber2) == null) {
        System.out.println("Train number does not exist, try again");
      } else {
        trainDispatch.setTrack(trainNumber2, track);
        System.out.println(
            "\n Track for departure with train number " + trainNumber2 + " was set to " + track);
      }
        }
    }
    private void setDelay(){
    if (departureListIsEmpty())
      System.out.println("Departure list is empty, add a departure first");
    else {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Train number?");
      int trainNumber3 = scanner.nextInt();
      System.out.println("Delay?");
      int delay = scanner.nextInt();
      if (trainDispatch.findDepartureByNumber(trainNumber3) == null) {
        System.out.println("Train number does not exist, try again");
      } else {
        trainDispatch.setDelay(trainNumber3, delay);
        System.out.println(
            "\n Delay for departure with train number " + trainNumber3 + " was set to " + delay);
      }
        }
    }
    private void updateTime(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("New time? In format hh:mm");
        String time = scanner.nextLine();
        LocalTime localTime = LocalTime.parse(time);
        if (localTime.isBefore(trainDispatch.getTime())){
            System.out.println("New time has to be after current time");
        }else {
            trainDispatch.setTime(localTime);
            System.out.println("New time is " + localTime);
        }
    }
    private Departure typeInDepartureInfo(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Name");
        String name = scanner.nextLine();

        scanner = new Scanner(System.in);
        System.out.println("Time, hh:mm");
        String tidspunkt = scanner.nextLine();
        LocalTime localTime = LocalTime.parse(tidspunkt);

        scanner = new Scanner(System.in);
        System.out.println("Line");
        String line = scanner.nextLine();

        scanner = new Scanner(System.in);
        System.out.println("Train number");
        int trainNumber = Integer.parseInt(scanner.nextLine());
        while (trainDispatch.findDuplicateTrainNumberWithNumber(trainNumber)){
            System.out.println("\nTrain number already exists, type new:");
            trainNumber = Integer.parseInt(scanner.nextLine());
        }

        scanner = new Scanner(System.in);
        System.out.println("Destination");
        String destination = scanner.nextLine();

        scanner = new Scanner(System.in);
        System.out.println("Track, type 0 if not existing yet");
        int track = Integer.parseInt(scanner.nextLine());
        if (track==0) track = -1;

        scanner = new Scanner(System.in);
        System.out.println("Delay");
        int delay = Integer.parseInt(scanner.nextLine());

        return new Departure(name,localTime,line,trainNumber,destination,track,delay);
    }
/**
* Starts the application. This is the main loop of the application,
* presenting the menu, retrieving the selected menu choice from the user,
* and executing the selected functionality.
*/
    public void start() {
        boolean finished = false;
// The while-loop will run as long as the user has not selected
// to quit the application
        while (!finished) {
            showMenu();
            int menuChoice = this.userChoice();
            switch (menuChoice){
                case ADD_DEPARTURE:
                    addDeparture();
                    break;
                case LIST_ALL_DEPARTURES:
                    System.out.println(trainDispatch.showAllDeparturesAfterTime().toString());
                    break;
                case FIND_DEPARTURE_BY_NUMBER:
                    findDepartureByNumber();
                    break;
                case FIND_DEPARTURE_BY_DESTINATION:
                    findDepartureByDestination();
                    break;
                case SET_SPOR:
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
                default:
                    System.out.println("Unrecognized menu selected...");
                    break;
                }
            }
        }
    }
