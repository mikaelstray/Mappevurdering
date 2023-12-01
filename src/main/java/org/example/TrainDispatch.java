package org.example;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * The TrainDispatch class manages the dispatch of train departures, including registration,
 * retrieval, and modification of departure information. It also keeps count of the number of
 * departures and the current local time. Validations are performed in the UserInterface class.
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
   * Gets the current time used for various time-based operations.
   *
   * @return The current time.
   */
  public LocalTime getTime() {
    return time;
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
   * Gets the number of registered departures.
   *
   * @return The number of registered departures.
   */
  public int getNumberOfDepartures() {
    return numberOfDepartures;
  }

  /**
   * Registers a new departure. The departure is added to the list of departures, and the number of
   * departures is updated. The departure/departure inputs are already validated.
   *
   * @param departure The departure to register.
   */

  public void registerDeparture(Departure departure) {
    departureList.add(departure);
    numberOfDepartures = departureList.size();
  }

  /**
   * Removes a departure from the list of departures. The number of departures is updated.
   *
   * @param departure The departure to remove.
   */

  public void removeDeparture(Departure departure) {
    departureList.remove(departure);
    numberOfDepartures = departureList.size();
  }

  /**
   * Removes the departures from the departure list that are before the current time, updates the
   * number of departures and sorts the list by time plus delay.
   *
   * @return A new array of departures sorted by time plus delay.
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
   * Finds a departure by its train number and sets the track of that departure.
   *
   * @param number The train number of the departure to modify.
   * @param track  The new track or platform number for the departure.
   */

  public void setTrack(int number, int track) {
    Departure departure = findDepartureByNumber(number);
    departure.setTrack(track);
  }

  /**
   * Finds a departure by its train number and sets the delay (in minutes) of that departure.
   *
   * @param number The train number of the departure to modify.
   * @param delay  The new delay (in minutes) for the departure.
   */

  public void setDelay(int number, int delay) {
    Departure departure = findDepartureByNumber(number);
    departure.setDelay(delay);
  }
}
