package org.example;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
  private int numberOfDepartures = 0;

  /**
   * The current time used for various time-based operations.
   */

  private LocalTime time = LocalTime.now();

  /**
   * Sets the current time used for various time-based operations.
   *
   * @param time The new current time.
   */
  public void setTime(LocalTime time) {
    this.time = time;
  }

  /**
   * Gets the number of registered departures.
   *
   * @return The number of registered departures.
   */
  public int getNumberOfDepartures() {
    return numberOfDepartures;
  }

  public LocalTime getTime() {
    return time;
  }

  /**
   * Registers a new departure, checking for duplicate train numbers.
   *
   * @param departure The departure to register.
   */

  public void registerDeparture(Departure departure) {
    departureList.add(departure);
    numberOfDepartures = departureList.size();
  }

  public void removeDeparture(Departure departure) {
    departureList.remove(departure);
    numberOfDepartures = departureList.size();
  }

  /**
   * Shows all departures after the current time.
   *
   * @return A list of departures after the current time.
   */

  public Departure[] departureListAfterCurrentTimeAndDelay() {
    departureList.removeIf(departure -> departure.getTime().plusMinutes(departure.getDelay())
            .isBefore(time));
    numberOfDepartures = departureList.size();
    return departureList.stream()
            .sorted(Comparator.comparing(Departure::getTimePlusDelay))
            .toArray(Departure[]::new);
  }

  /**
   * Checks if the list of departures after the current time is empty.
   *
   * @return True if the list of departures after the current time is empty, false otherwise.
   */

  public boolean checkIfListIsEmpty() {
    return departureListAfterCurrentTimeAndDelay().length == 0;
  }

  /**
   * Checks if the specific train number is a duplicate.
   *
   * @param trainNumber The train number to check.
   * @return True if the train number is a duplicate, false otherwise.
   */

  public boolean findDuplicateTrainNumber(int trainNumber) {
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

  public Departure[] findDeparturesByDestination(String destination) {
    return departureList.stream()
            .filter(d -> d.getDestination().trim().equalsIgnoreCase(destination.trim()))
            .toArray(Departure[]::new);
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
