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

 * @author Mikael Stray Froeyshov
 * @version 1.0
 * @since 2023-11-02
 */
public class TrainDispatch {

  /** List of train departures. */
  private final ArrayList<Departure> departureList = new ArrayList<>();

  /** Number of registered departures. */
  private int numberOfDepartures = 0;

  /** The current time used for various time-based operations. */

  private LocalTime time;

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

  public LocalTime getTime() {
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

  public void registerDeparture(Departure departure) {
    departureList.add(departure);
    numberOfDepartures++;
  } // Composition, lagre objektet et annet sted enn departureList


    /*
    public ArrayList<Departure> showAllDepartures() {
        ArrayList<Departure> sortedListOfDepartures = new ArrayList<>(departureList.size());
        for (Departure d: departureList) {
            sortedListOfDepartures.add(d.clone());
        }
        sortedListOfDepartures.sort(Comparator.comparing(Departure::getTidspunkt));
        return sortedListOfDepartures;
    }*/
  public List<Departure> showAllDeparturesAfterTime() {
    return departureList.stream()
            .filter(departure -> departure.getTime().plusMinutes(departure.getDelay()).isAfter(time))
            .sorted(Comparator.comparing(Departure::getTimePlusDelay))
            .collect(Collectors.toCollection(ArrayList::new));
  }

  public boolean checkIfDepartureListAfterTimeIsEmpty() {
    return showAllDeparturesAfterTime().isEmpty();
  }

  public boolean addedDepartureIsBeforeCurrentTime(Departure departure) {
    return departure.getTime().isBefore(time);
  }

  private boolean findDuplicateTrainNumberWithObject(Departure departure) {
    return departureList.stream()
            .anyMatch(d -> d.getTrainNumber() == (departure.getTrainNumber()));
  }

  public boolean findDuplicateTrainNumberWithNumber(int trainNumber) {
    return departureList.stream()
            .anyMatch(d -> d.getTrainNumber() == trainNumber);
  }

  public Departure findDepartureByNumber(int number) {
    return departureList.stream()
            .filter(d -> d.getTrainNumber() == number)
            .findFirst()
            .orElse(null);
  }

  public List<Departure> findDepartureByDestination(String destination) {
    return departureList.stream()
          .filter(d -> d.getDestination().trim().equalsIgnoreCase(destination.trim()))
          .collect(Collectors.toCollection(ArrayList::new));
  }

  public void setTrack(int number, int track) {
    Departure departure = findDepartureByNumber(number);
    departure.setTrack(track);
  }

  public void setDelay(int number, int delay) {
    Departure departure = findDepartureByNumber(number);
    departure.setDelay(delay);
  }
}
