package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.List;
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
        Departure departure = new Departure(LocalTime.of(11, 59), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        trainDispatch.setTime(LocalTime.of(12, 01));
        Departure[] filteredDepartureList = trainDispatch.departureListAfterCurrentTimeAndDelay();
        assertEquals(0, filteredDepartureList.length);
    }

    @Test
    @DisplayName("Negative departureListAfterCurrentTimeAndDelay() test")
    void testNegativeDepartureListAfterCurrentTimeAndDelayMethod() {
        Departure departure = new Departure(LocalTime.of(11, 59), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        trainDispatch.setTime(LocalTime.of(12, 01));
        Departure[] filteredDepartureList = trainDispatch.departureListAfterCurrentTimeAndDelay();
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
    @DisplayName("Negative numberOfDepartures test")
    void testNegativeNumberOfDepartures() {
        Departure departure = new Departure(LocalTime.of(11, 59), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        trainDispatch.setTime(LocalTime.of(11, 58));
        assertNotEquals(0, trainDispatch.getNumberOfDepartures());
    }



}