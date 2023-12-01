package org.example;

import java.time.LocalTime;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * This class represents a departure.
 *
 * @author Mikael Stray Froeyshov
 * @version 1.0
 * @since 2023-11-02
 */

public class Departure {

  private final LocalTime time;
  private final String line;
  private final int trainNumber;
  private final String destination;
  private int delay;
  private int track;

  /**
   * Constructs a Departure object with the specified information.
   *
   * @param time        Time of the departure.
   * @param line        Line or route of the departure.
   * @param trainNumber Train number of the departure.
   * @param destination Destination of the departure.
   * @param track       Track or platform number of the departure.
   * @param delay       Delay (in minutes) of the departure.
   * @throws IllegalArgumentException if the input parameters are invalid or missing.
   */

  public Departure(LocalTime time, String line, int trainNumber, String destination,
                   int track, int delay) throws IllegalArgumentException {

    checkNegativeNumbers(trainNumber, "Train number");

    this.time = requireNonNull(time, "Time cannot be null");
    this.line = requireNonNull(line, "Line cannot be null");
    this.trainNumber = trainNumber;
    this.destination = requireNonNull(destination, "Destination cannot be null");
    setTrack(track);
    setDelay(delay);
  }

  /**
   * Checks if the value of the input parameter is negative.
   *
   * @param parameter     The parameter to check.
   * @param parameterName The name of the parameter.
   * @throws IllegalArgumentException if the input parameter is negative.
   */

  private void checkNegativeNumbers(int parameter, String parameterName)
          throws IllegalArgumentException {
    if (parameter < 0) {
      throw new IllegalArgumentException(parameterName + " cannot be negative");
    }
  }

  /**
   * Gets the time of the departure plus the delay.
   *
   * @return The time of the departure plus the delay.
   */
  public LocalTime getTimePlusDelay() {
    return time.plusMinutes(delay);
  }

  /**
   * Gets the time of the departure.
   *
   * @return The time of the departure.
   */
  public LocalTime getTime() {
    return time;
  }

  /**
   * Gets the line of the departure.
   *
   * @return The line or route of the departure.
   */
  public String getLine() {
    return line;
  }

  /**
   * Gets the train number of the departure.
   *
   * @return The train number of the departure.
   */
  public int getTrainNumber() {
    return trainNumber;
  }

  /**
   * Gets the destination of the departure.
   *
   * @return The destination of the departure.
   */

  public String getDestination() {
    return destination;
  }

  /**
   * Gets the delay (in minutes) of the departure.
   *
   * @return The delay (in minutes) of the departure.
   */
  public int getDelay() {
    return delay;
  }

  /**
   * Validates and sets the delay (in minutes) of the departure.
   *
   * @param delay The new delay (in minutes) for the departure.
   */
  public void setDelay(int delay) {
    checkNegativeNumbers(delay, "Delay");
    this.delay = delay;
  }

  /**
   * Gets the track of the departure.
   *
   * @return The track or platform number of the departure.
   */
  public int getTrack() {
    return track;
  }

  /**
   * Validates and sets the track of the departure.
   *
   * @param track The new track or platform number for the departure.
   */
  public void setTrack(int track) {
    checkNegativeNumbers(track, "Track");
    this.track = (track == 0) ? -1 : track;
  }

  /**
   * Checks if this Departure object is equal to another object.
   *
   * @param o The object to compare with.
   * @return true if the objects are equal, false otherwise.
   */

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Departure departure)) {
      return false;
    }
    return getTime() == departure.getTime()
            && getTrainNumber() == departure.getTrainNumber()
            && getTrack() == departure.getTrack()
            && Objects.equals(getLine(), departure.getLine())
            && Objects.equals(getDestination(), departure.getDestination());
  }

  /**
   * Returns the hash code of this Departure object.
   *
   * @return The hash code of this Departure object.
   */

  @Override
  public int hashCode() {
    return Objects.hash(getTime(), getLine(), getTrainNumber(), getDestination(),
            getDelay(), getTrack());
  }
}