package org.example;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <h1>TrainDispatch.</h1>
 * <p>
 * The TrainDispatch class manages the dispatch of train departures, including registration,
 * retrieval, and modification of departure information.
 * </p>
 * <p>
 * It also keeps count of the number of
 * departures and the current local time. Validations are performed in the UserInterface class.
 * </p>
 * <p>
 * <b>Note:</b> The parameters in each method are assumed to be validated in the UI class.
 * </p>
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
   * Registers the specified departure and updates the number of departures.
   *
   * @param departure The departure to be registered.
   */
  public void registerDeparture(Departure departure) {
    departureList.add(departure);

    // Update the count of registered departures
    numberOfDepartures = departureList.size();
  }

  /**
   * Removes the specified departure and updates the number of departures.
   *
   * @param departure The departure to be removed.
   */
  public void removeDeparture(Departure departure) {
    departureList.remove(departure);

    // Update the number of registered departures
    numberOfDepartures = departureList.size();
  }

  /**
   * Removes the departures from the departure list that are before the current time, updates the
   * number of departures and sorts the list by time plus delay.
   *
   * @return A new array of departures sorted by time plus delay.
   */

  public List<Departure> sortedList() {
    // Remove the departures that are before the current time
    departureList.removeIf(departure -> departure.getTime().plusMinutes(departure.getDelay())
            .isBefore(time));

    // Update the number of departures
    numberOfDepartures = departureList.size();

    // Sort the list by time plus delay and return an array
    return departureList.stream()
            .sorted(Comparator.comparing(Departure::getScheduledArrival))
            .toList();
  }

  /**
   * Checks if the list of departures after the current time is empty.
   *
   * @return True if the list of departures after the current time is empty, false otherwise.
   */

  public boolean checkIfListIsEmpty() {
    return sortedList().isEmpty();
  }

  /**
   * Checks if the specified train number is a duplicate.
   *
   * @param trainNumber The train number to check for duplication.
   * @return True if the train number is a duplicate, false otherwise.
   */
  public boolean findDuplicateTrainNumber(int trainNumber) {
    return sortedList().stream()
            .anyMatch(d -> d.getTrainNumber() == trainNumber);
  }


  /**
   * Finds a departure by its train number.
   *
   * @param number The train number to search for.
   * @return The departure with the specified train number, or null if not found.
   */

  public Departure findDepartureByNumber(int number) {
    return sortedList().stream()
            .filter(d -> d.getTrainNumber() == number)
            .findFirst()
            .orElse(null);
  }

  /**
   * Finds a departure by its destination.
   * Note: The destination is assumed to be validated before calling this method in the UI class.
   *
   * @param destination The destination to search for.
   * @return A list of departures with the specified destination, or an empty list if not found.
   */

  public List<Departure> findDeparturesByDestination(String destination) {
    return sortedList().stream()
            .filter(d -> d.getDestination().trim().equalsIgnoreCase(destination.trim()))
            .toList();
  }

  /**
   * Finds a departure by its train number and sets the track for that departure.
   * Both the train number and the track are assumed to be validated before calling
   * this method in the UI class.
   *
   * @param number The train number of the departure to modify.
   * @param track  The new track or platform number for the departure.
   */
  public void setTrack(int number, int track) {
    Departure departure = this.findDepartureByNumber(number);
    departure.setTrack(track);
  }


  /**
   * Finds a departure by its train number and sets the delay (in minutes) for that departure.
   * Both the parameters are assumed to be validated before calling this method in the UI class.
   *
   * @param number The train number of the departure to modify.
   * @param delay  The new delay (in minutes) for the departure.
   */
  public void setDelay(int number, int delay) {
    Departure departure = this.findDepartureByNumber(number);
    departure.setDelay(delay);
  }

  /**
   * Returns a formatted string representation of the Train Dispatch.
   * Includes information in a tabular format.
   *
   * @return a string representation of the Train Dispatch.
  */

  @Override
  public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    StringBuilder sb = new StringBuilder();
    // numberOfDepartures string with the correct plural form
    String departureInfo = (numberOfDepartures == 1) ? " departure" : " departures";

    sb.append("-".repeat(80)).append("\n");
    sb.append("|").append(" ".repeat(32)).append("Train Dispatch");
    sb.append(" ".repeat(32)).append("|\n");
    sb.append("|  ").append(numberOfDepartures).append(departureInfo);
    sb.append(" ".repeat(57)).append(formatter.format(time)).append("   |\n");
    sb.append("-".repeat(80)).append("\n");
    sb.append("|  Time   |   Line  |   Train number  |   Destination   |   Delay   |   Track  |\n");
    sb.append("-".repeat(80)).append("\n");
    // Append each departure to the string builder from the sorted list
    sortedList().forEach(sb::append);
    sb.append("\n").append("-".repeat(80)).append("\n\n");

    return sb.toString();
  }
}
