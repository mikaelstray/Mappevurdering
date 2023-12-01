package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
class TrainDispatchTest {
    private TrainDispatch trainDispatch;

    @BeforeEach
    void setUp() {
    trainDispatch = new TrainDispatch();
    }

    @Test
    @DisplayName("Test that the number of departures is zero when no departures have been registered")
    void testNumberOfDeparturesIsZeroWhenNoDeparturesHaveBeenRegistered() {
        assertEquals(0, trainDispatch.getNumberOfDepartures());
    }

    @Test
    @DisplayName("Test setTime() method")
    void testSetTimeMethod() {
        LocalTime time = LocalTime.of(12, 0);
        trainDispatch.setTime(time);
        assertEquals(time, trainDispatch.getTime());
    }

    @Test
    @DisplayName("Negative setTime() test")
    void testNegativeSetTimeMethod() {
        LocalTime time = LocalTime.of(12, 0);
        trainDispatch.setTime(time);
        assertNotEquals(LocalTime.of(12, 1), trainDispatch.getTime());
    }

    @Test
    @DisplayName("Test getNumberOfDepartures() method")
    void testGetNumberOfDeparturesMethod() {
        Departure departure = new Departure(LocalTime.of(12, 0), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        assertEquals(1, trainDispatch.getNumberOfDepartures());
    }

    @Test
    @DisplayName("Negative getNumberOfDepartures() test")
    void testNegativeGetNumberOfDeparturesMethod() {
        Departure departure = new Departure(LocalTime.of(12, 0), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        assertNotEquals(0, trainDispatch.getNumberOfDepartures());
    }

    @Test
    @DisplayName("Test getTime() method")
    void testGetTimeMethod() {
        LocalTime time = LocalTime.of(12, 0);
        trainDispatch.setTime(time);
        assertEquals(time, trainDispatch.getTime());
    }

    @Test
    @DisplayName("Negative getTime() test")
    void testNegativeGetTimeMethod() {
        LocalTime time = LocalTime.of(12, 0);
        trainDispatch.setTime(time);
        assertNotEquals(LocalTime.of(12, 1), trainDispatch.getTime());
    }

    @Test
    @DisplayName("Test registerDeparture() method")
    void testRegisterDepartureMethod() {
        Departure departure = new Departure(LocalTime.of(12, 0), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        assertEquals(1, trainDispatch.getNumberOfDepartures());
    }

    @Test
    @DisplayName("Test removeDeparture() method")
    void testRemoveDepartureMethod() {
        Departure departure = new Departure(LocalTime.of(12, 0), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        trainDispatch.removeDeparture(departure);
        assertEquals(0, trainDispatch.getNumberOfDepartures());
    }

    @Test
    @DisplayName("Test departureListAfterCurrentTimeAndDelay() method")
    void testDepartureListAfterCurrentTimeAndDelayMethod() {
        Departure departure = new Departure(LocalTime.of(12,01 ), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        trainDispatch.setTime(LocalTime.of(12, 02));
        Departure[] filteredDepartureList = trainDispatch.sortedList();
        assertEquals(0, filteredDepartureList.length);
    }

    @Test
    @DisplayName("Negative departureListAfterCurrentTimeAndDelay() test")
    void testNegativeDepartureListAfterCurrentTimeAndDelayMethod() {
        Departure departure = new Departure(LocalTime.of(11, 59), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        trainDispatch.setTime(LocalTime.of(12, 01));
        Departure[] filteredDepartureList = trainDispatch.sortedList();
        assertNotEquals(1, filteredDepartureList.length);
    }

    @Test
    @DisplayName("Test that numberOfDepartures is updated in departureListAfterCurrentTimeAndDelay() method")
    void testNumberOfDeparturesIsUpdatedInDepartureListAfterCurrentTimeAndDelayMethod() {
        Departure departure = new Departure(LocalTime.of(11, 59), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        trainDispatch.setTime(LocalTime.of(11, 58));
        assertEquals(1, trainDispatch.getNumberOfDepartures());
    }

    @Test
    @DisplayName("Test checkIfListIsEmpty() method")
    void testCheckIfListIsEmptyMethod() {
        Departure departure = new Departure(LocalTime.of(11, 59), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        trainDispatch.setTime(LocalTime.of(12, 01));
        assertTrue(trainDispatch.checkIfListIsEmpty());
    }

    @Test
    @DisplayName("Negative checkIfListIsEmpty() test")
    void testNegativeCheckIfListIsEmptyMethod() {
        Departure departure = new Departure(LocalTime.of(11, 59), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        trainDispatch.setTime(LocalTime.of(11, 58));
        assertFalse(trainDispatch.checkIfListIsEmpty());
    }

    @Test
    @DisplayName("Test findDuplicateTrainNumber() method")
    void testFindDuplicateTrainNumberMethod() {
        Departure departure1 = new Departure(LocalTime.of(11, 59), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure1);
        boolean duplicateTrainNumber = trainDispatch.findDuplicateTrainNumber(123);
        assertTrue(duplicateTrainNumber);
    }

    @Test
    @DisplayName("Negative findDuplicateTrainNumber() test")
    void testNegativeFindDuplicateTrainNumberMethod() {
        Departure departure1 = new Departure(LocalTime.of(11, 59), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure1);
        boolean duplicateTrainNumber = trainDispatch.findDuplicateTrainNumber(124);
        assertFalse(duplicateTrainNumber);
    }

    @Test
    @DisplayName("Test findDepartureByNumber method")
    void testFindDepartureByNumberMethod() {
        Departure departure = new Departure(LocalTime.of(11, 59), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        Departure foundDeparture = trainDispatch.findDepartureByNumber(123);
        assertEquals(departure, foundDeparture);
    }

    @Test
    @DisplayName("Negative findDepartureByNumber method")
    void testNegativeFindDepartureByNumberMethod() {
        Departure departure = new Departure(LocalTime.of(11, 59), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        Departure foundDeparture = trainDispatch.findDepartureByNumber(124);
        assertNull(foundDeparture);
    }

    @Test
    @DisplayName("Test findDepartureByDestination method")
    void testFindDepartureByDestinationMethod() {
        Departure departure = new Departure(LocalTime.of(11, 59), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        Departure[] foundDepartures = trainDispatch.findDeparturesByDestination("B");
        assertEquals(departure, foundDepartures[0]);
        assertEquals(1, foundDepartures.length);
    }

    @Test
    @DisplayName("Negative findDepartureByDestination method")
    void testNegativeFindDepartureByDestinationMethod() {
        Departure departure = new Departure(LocalTime.of(11, 59), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        Departure[] foundDepartures = trainDispatch.findDeparturesByDestination("C");
        assertEquals(0, foundDepartures.length);
    }

    @Test
    @DisplayName("Test setTrack method")
    void testSetTrackMethod() {
        Departure departure = new Departure(LocalTime.of(11, 59), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        trainDispatch.setTrack(123, 2);
        assertEquals(2, departure.getTrack());
    }

    @Test
    @DisplayName("Test setDelay method")
    void testSetDelayMethod() {
        Departure departure = new Departure(LocalTime.of(11, 59), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        trainDispatch.setDelay(123, 2);
        assertEquals(2, departure.getDelay());
    }

}