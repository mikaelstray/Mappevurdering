package org.example;

import java.time.LocalTime;
import java.util.Objects;

/**
 * This class represents a departure.

 * @author Mikael Stray Froeyshov
 * @version 1.0
 * @since 2023-11-02
 */

public class Departure {

        private final String name;
        private LocalTime time;
        private final String line;
        private final int trainNumber;
        private String destination;
        private int delay;
        private int track;

    /**
     * Constructs a Departure object with the specified information.
     *
     * @param name          Name of the departure.
     * @param time          Time of the departure.
     * @param line          Line or route of the departure.
     * @param trainNumber   Train number of the departure.
     * @param destination   Destination of the departure.
     * @param track         Track or platform number of the departure.
     * @param delay         Delay (in minutes) of the departure.
     * @throws IllegalArgumentException if the input parameters are invalid or missing.
     */

    public Departure(String name, LocalTime time, String line, int trainNumber, String destination, int track, int delay) throws IllegalArgumentException{
        // Validation for input parameters
        if (name == null && name.trim() == "" || line == null && line.trim() == "" || trainNumber == 0 || destination == null && destination.trim() == ""){
            throw new IllegalArgumentException("Wrong input");
        }
        this.name = name;
        this.time = time;
        this.line = line;
        this.trainNumber = trainNumber;
        this.destination = destination;
        this.track = track;
        this.delay = delay;
    } //Trenger flere konstruktører

     /**
     * Creates and returns a clone of the current Departure object.
     *
     * @return A new Departure object with the same information as the current object.
     */

    public Departure clone(){
        return new Departure(this.getName(),this.getTime(),this.getLine(),this.getTrainNumber(),this.getDestination(),this.getTrack(),this.getDelay());
    }

 /**
     * Gets the name of the departure.
     *
     * @return The name of the departure.
     */
    public String getName() {
        return name;
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
     * Sets the time of the departure.
     *
     * @param time The new time for the departure.
     */
    public void setTime(LocalTime time) {
        this.time = time;
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
     * Sets the destination of the departure.
     *
     * @param destination The new destination for the departure.
     */
    public void setDestination(String destination) {
        this.destination = destination;
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
        if (this == o) return true;
        if (!(o instanceof Departure departure)) return false;
        return getTime() == departure.getTime() && getTrainNumber() == departure.getTrainNumber() && getTrack() == departure.getTrack() && Objects.equals(getName(), departure.getName()) && Objects.equals(getLine(), departure.getLine()) && Objects.equals(getDestination(), departure.getDestination());
    }

    /**
     * Compares this Departure object with another based on train numbers.
     *
     * @param otherDeparture The Departure object to compare with.
     * @return A negative integer, zero, or a positive integer as this object is less than, equal to,
     * or greater than the specified object.
     */
    public int compareTo(Departure otherDeparture){
        return Integer.compare(this.getTrainNumber(), otherDeparture.getTrainNumber());
    }
    @Override
    public String toString() {
        String output = "";
        output += "- "+ time + " | " + line + " | " + trainNumber + " | " + destination;
        if (delay > 0) output += " | " + delay;
        if (track != -1) output += " | " + track;
        return output;
    }
}
