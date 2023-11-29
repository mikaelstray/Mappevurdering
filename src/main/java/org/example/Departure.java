package org.example;

import java.time.LocalTime;
import java.util.Objects;

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
   *
   * @throws IllegalArgumentException if the input parameters are invalid or missing.
   * @throws NullPointerException     if the input parameters are null.
   */

  public Departure(LocalTime time, String line, int trainNumber, String destination,
                   int track, int delay) throws IllegalArgumentException, NullPointerException {
    checkNull(line, "Line");
    checkNull(time.toString(), "Time");
    checkNegativeNumbers(trainNumber, "Train number");
    checkNull(destination, "Destination");
    checkNegativeNumbers(track, "Track");
    checkNegativeNumbers(delay, "Delay");

    this.time = time;
    this.line = line.trim();
    this.trainNumber = trainNumber;
    this.destination = destination.trim();
    this.track = track;
    this.delay = delay;

    if (track == 0) {
      this.track = -1;
    }
  }

  /**
   * Checks if the value of the input parameter is negative.
   *
   * @param parameter    The parameter to check.
   * @param parameterName The name of the parameter.
   * @throws IllegalArgumentException if the input parameter is negative.
   */

  private void checkNegativeNumbers(int parameter, String parameterName) throws IllegalArgumentException {
    if (parameter < 0) {
      throw new IllegalArgumentException(parameterName + " cannot be negative");
    }
  }

  /**
   * Checks if the value of the input parameter is null or empty.
   *
   * @param parameter    The parameter to check.
   * @param parameterName The name of the parameter.
   * @throws NullPointerException if the input parameter is null or empty.
   */

  private void checkNull(String parameter, String parameterName) throws NullPointerException {
    if (parameter == null || parameter.trim().isEmpty()) {
      throw new NullPointerException(parameterName + " cannot be null");
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
   * Gets the line or route of the departure.
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
   * Sets the delay (in minutes) of the departure.
   *
   * @param delay The new delay (in minutes) for the departure.
   */
  public void setDelay(int delay) {
    this.delay = delay;
  }

  /**
   * Gets the track or platform number of the departure.
   *
   * @return The track or platform number of the departure.
   */
  public int getTrack() {
    return track;
  }

  /**
   * Sets the track or platform number of the departure.
   *
   * @param track The new track or platform number for the departure.
   */
  public void setTrack(int track) {
    this.track = track;
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
    return Objects.hash(getTime(), getLine(), getTrainNumber(), getDestination(), getDelay(), getTrack());
  }

  /**
   * Compares this Departure object with another based on train numbers.
   *
   * @param otherDeparture The Departure object to compare with.
   * @return A negative integer, zero, or a positive integer as this object is less than, equal to,
   * or greater than the specified object.
   */
  public int compareTo(Departure otherDeparture) {
    return Integer.compare(this.getTrainNumber(), otherDeparture.getTrainNumber());
  }
}
