package org.example;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
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
    ArrayList<Departure> departureList = new ArrayList<>();

    /** Number of registered departures. */
    int numberOfDepartures = 0;

    /** The current time used for various time-based operations. */

    LocalTime time;

    /**
     * Constructs a TrainDispatch object with the current time.
     */
    public TrainDispatch(){
        time = LocalTime.now();
    }

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
     * Gets the list of train departures.
     *
     * @return The list of train departures.
     */
    public ArrayList<Departure> getDepartureList() {
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

     /**
     * Returns a string representation of the TrainDispatch object.
     *
     * @return A string representation of the TrainDispatch object.
     */
    @Override
    public String toString() {
        return "TrainDispatch{" +
                "departureList=" + departureList +
                ", numberOfDepartures=" + numberOfDepartures +
                '}';
    }

     /**
     * Registers a new departure, checking for duplicate train numbers.
     *
     * @param departure The departure to register.
     * @return true if the departure is successfully registered, false if the train number is a duplicate.
     */

    public boolean registerDeparture(Departure departure){
        if (findDuplicateWithObject(departure)){
            return false;
        } else {
            departureList.add(departure);
            numberOfDepartures++;
            return true;
        }
    } // Composition, lagre objektet et annet sted enn departureList
    /*
    public ArrayList<Departure> showAllDepartures() {
        ArrayList<Departure> sortedListOfDepartures = new ArrayList<>(departureList.size());
        for (Departure d : departureList) {
            sortedListOfDepartures.add(d.clone());
        }
        sortedListOfDepartures.sort(Comparator.comparing(Departure::getTidspunkt));
        return sortedListOfDepartures;
    }*/
    public ArrayList<Departure> showAllDeparturesAfterTime(){
        return departureList.stream()
                .filter(departure -> departure.getTime().plusMinutes(departure.getDelay()).isAfter(time))
                .sorted(Comparator.comparing(Departure::getTimePlusDelay))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    private boolean findDuplicateWithObject(Departure departure){
        return departureList.stream()
                .anyMatch(d -> d.getTrainNumber() == (departure.getTrainNumber()));
    }
    public boolean findDuplicateWithNumber(int trainNumber){
        for (Departure d: departureList){
            if (Integer.valueOf(d.getTrainNumber()) == trainNumber){
                return true;
            }
        }
        return false;
    }
    public Departure findDepartureByNumber(int number){
        return departureList.stream()
                .filter(d -> d.getTrainNumber() == number)
                .findFirst()
                .orElse(null);
    }
    public ArrayList<Departure> findDepartureByDestination(String destination){
        return departureList.stream()
                .filter(d -> d.getDestination().equals(destination))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public boolean setTrack(int number, int track){
        Departure departure = findDepartureByNumber(number);
        departure.setTrack(track);
        return true;
    }
    public boolean setDelay(int number, int delay){
        Departure departure = findDepartureByNumber(number);
        departure.setDelay(delay);
        return true;
    }
    /* public void catchMethod(){
    try {
        setDelay(-1,34);
    }
    catch (Exception e){

    }
    }*/
}
