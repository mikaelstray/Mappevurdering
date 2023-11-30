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
    @DisplayName("Test that the number of departures is one when one departure has been registered")
    void testNumberOfDeparturesIsOneWhenOneDepartureHasBeenRegistered() {
        Departure departure = new Departure(LocalTime.of(12, 0), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        assertEquals(1, trainDispatch.getNumberOfDepartures());
    }

    @Test
    @DisplayName("Test that the number of departures of the list after filtering is zero after adding departure before current time")
    void testNumberOfDeparturesOfTheListAfterFilteringIsZeroAfterAddingDepartureBeforeCurrentTime() {
        Departure departure = new Departure(LocalTime.of(12, 0), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        trainDispatch.setTime(LocalTime.of(12, 1));
        Departure[] filteredDepartureList = trainDispatch.departureListAfterCurrentTimeAndDelay();
        assertEquals(0, filteredDepartureList.length);
    }

}