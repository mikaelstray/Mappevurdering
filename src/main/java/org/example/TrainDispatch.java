package org.example;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The TrainDispatch class manages the dispatch of train departures, including registration,
 * retrieval, and modification of departure information. It also keeps count of the number of
 * departures and the current local time.
 *
 * @author Mikael Stray Froeyshov
 * @version 1.0
 * @since 2023-11-02
 */
public class TrainDispatch {

  /**
   * List of train departures.
   */
  private final ArrayList<Departure> departureList = new ArrayList<>();

  /**
   * Number of registered departures.
   */
  private static int numberOfDepartures = 0;

  /**
   * The current time used for various time-based operations.
   */

  private static LocalTime time;

  /**
   * Constructs a TrainDispatch object with the current time.
   */
  public TrainDispatch() {
    time = LocalTime.now();
  }

  /**
   * Sets the current time used for various time-based operations.
   *
   * @param time The new current time.
   */
  public void setTime(LocalTime time) {
    this.time = time;
  }

  /**
   * Gets the list of train departures.
   *
   * @return The list of train departures.
   */
  public List<Departure> getDepartureList() {
    return departureList;
  }

  /**
   * Gets the number of registered departures.
   *
   * @return The number of registered departures.
   */
  public int getNumberOfDepartures() {
    return numberOfDepartures;
  }

  public static LocalTime getTime() {
    return time;
  }

  /**
   * Returns a string representation of the TrainDispatch object.
   *
   * @return A string representation of the TrainDispatch object.
   */
  @Override
  public String toString() {
    return "TrainDispatch{"
            + "departureList=" + departureList
            + ", numberOfDepartures=" + numberOfDepartures
            + '}';
  }

  /**
   * Registers a new departure, checking for duplicate train numbers.
   *
   * @param departure The departure to register.
   */

  public void registerDeparture(Departure departure) { // Make method with all parameters &
    // change way of user input?
    departureList.add(departure);
    numberOfDepartures++;
  } // Composition, lagre objektet et annet sted enn departureList

  public void removeDeparture(Departure departure) {
    departureList.remove(departure);
    numberOfDepartures--;
  }

  /**
   * Shows all departures after the current time.
   *
   * @return A list of departures after the current time.
   */

  public List<Departure> departureListAfterCurrentTime() {
    return departureList.stream()
            .filter(departure -> departure.getTime().plusMinutes(departure.getDelay()).isAfter(time))
            .sorted(Comparator.comparing(Departure::getTimePlusDelay))
            .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Checks if the list of departures after the current time is empty.
   *
   * @return True if the list of departures after the current time is empty, false otherwise.
   */

  public boolean checkIfDepartureListAfterTimeIsEmpty() {
    return departureListAfterCurrentTime().isEmpty();
  }

  /**
   * Checks if the added departure is before the current time.
   *
   * @param departure The departure to check.
   * @return True if the added departure is before the current time, false otherwise.
   */

  public boolean addedDepartureIsBeforeCurrentTime(Departure departure) {
    return departure.getTime().isBefore(time);
  }

  /**
   * Checks if the specific departure is a duplicate.
   *
   * @param departure The departure to check.
   * @return True if the departure is a duplicate, false otherwise.
   */

  private boolean findDuplicateTrainNumberWithObject(Departure departure) {
    return departureList.stream()
            .anyMatch(d -> d.getTrainNumber() == (departure.getTrainNumber()));
  }

  /**
   * Checks if the specific train number is a duplicate.
   *
   * @param trainNumber The train number to check.
   * @return True if the train number is a duplicate, false otherwise.
   */

  public boolean findDuplicateTrainNumberWithNumber(int trainNumber) {
    return departureList.stream()
            .anyMatch(d -> d.getTrainNumber() == trainNumber);
  }

  /**
   * Finds a departure by its train number.
   *
   * @param number The train number to search for.
   * @return The departure with the specified train number, or null if not found.
   */

  public Departure findDepartureByNumber(int number) {
    return departureList.stream()
            .filter(d -> d.getTrainNumber() == number)
            .findFirst()
            .orElse(null);
  }

  /**
   * Finds a departure by its destination.
   *
   * @param destination The destination to search for.
   * @return A list of departures with the specified destination, or an empty list if not found.
   */

  public List<Departure> findDepartureByDestination(String destination) {
    return departureList.stream()
            .filter(d -> d.getDestination().trim().equalsIgnoreCase(destination.trim()))
            .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Sets the track or platform number of a departure.
   *
   * @param number The train number of the departure to modify.
   * @param track  The new track or platform number for the departure.
   */

  public void setTrack(int number, int track) {
    Departure departure = findDepartureByNumber(number);
    departure.setTrack(track);
  }

  /**
   * Sets the delay (in minutes) of a departure.
   *
   * @param number The train number of the departure to modify.
   * @param delay  The new delay (in minutes) for the departure.
   */

  public void setDelay(int number, int delay) {
    Departure departure = findDepartureByNumber(number);
    departure.setDelay(delay);
  }
}
