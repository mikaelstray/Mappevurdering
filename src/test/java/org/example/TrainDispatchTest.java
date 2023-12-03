package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class TrainDispatchTest {
    private TrainDispatch trainDispatch;
    LocalTime defaultTime = LocalTime.of(12, 0);

    @BeforeEach
    void setUp() {
    trainDispatch = new TrainDispatch();
    trainDispatch.setTime(defaultTime);
    }

    @Test
    @DisplayName("Test that departureList is empty when no departures have been registered")
    void testEmptyDepartureListAfterInitialization() {
        assertEquals(0, trainDispatch.getNumberOfDepartures());
    }

    @Test
    @DisplayName("Test setTime() method")
    void testSetTimeMethod() {
        LocalTime time2 = LocalTime.of(12, 56);
        trainDispatch.setTime(time2);

        assertEquals(time2, trainDispatch.getTime());
    }

    @Test
    @DisplayName("Test getNumberOfDepartures() method")
    void testGetNumberOfDeparturesMethod() {
        Departure departure = new Departure(defaultTime, "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);

        assertEquals(1, trainDispatch.getNumberOfDepartures());
    }

    @Test
    @DisplayName("Test getTime() method")
    void testGetTimeMethod() {
        trainDispatch.setTime(defaultTime);

        assertEquals(defaultTime, trainDispatch.getTime());
    }

    @Test
    @DisplayName("Test registerDeparture() method")
    void testRegisterDepartureMethod() {
        Departure departure1 = new Departure(defaultTime, "A", 123, "B", 1, 0);
        Departure departure2 = new Departure( defaultTime, "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure1);
        trainDispatch.registerDeparture(departure2);

        assertEquals(2, trainDispatch.getNumberOfDepartures());
        assertTrue(trainDispatch.sortedList().contains(departure1));
        assertTrue(trainDispatch.sortedList().contains(departure2));
    }

    @Test
    @DisplayName("Test removeDeparture() method")
    void testRemoveDepartureMethod() {
        Departure departure1 = new Departure(defaultTime, "A", 123, "B", 1, 0);
        Departure departure2 = new Departure(defaultTime, "B", 456, "B", 1, 0);
        trainDispatch.registerDeparture(departure1);
        trainDispatch.registerDeparture(departure2);
        trainDispatch.removeDeparture(departure1);

        assertEquals(1, trainDispatch.getNumberOfDepartures());
        assertTrue(trainDispatch.sortedList().contains(departure2));
        assertFalse(trainDispatch.sortedList().contains(departure1));
    }

    @Test
    @DisplayName("Test sortedList() method")
    void testSortedList() {
        Departure departureAfterTime = new Departure(LocalTime.of(12, 1 ), "A", 123, "B", 1, 0);
        Departure departureBeforeTime = new Departure(LocalTime.of(11, 59 ), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departureAfterTime);
        trainDispatch.registerDeparture(departureBeforeTime);
        List<Departure> filteredDepartureList = trainDispatch.sortedList();

        assertEquals(1, filteredDepartureList.size());
        assertTrue(filteredDepartureList.contains(departureAfterTime));
        assertFalse(filteredDepartureList.contains(departureBeforeTime));
    }

    @Test
    @DisplayName("Test that sortedList() is sorted right")
    void testSortedListIsSortedRight() {
        Departure departure1 = new Departure(LocalTime.of(12, 0), "A", 123, "B", 1, 0);
        Departure departure2 = new Departure(LocalTime.of(12, 1), "A", 123, "B", 1, 0);
        Departure departure3 = new Departure(LocalTime.of(12, 2), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure2);
        trainDispatch.registerDeparture(departure1);
        trainDispatch.registerDeparture(departure3);
        List<Departure> filteredDepartureList = trainDispatch.sortedList();

        assertEquals(3, filteredDepartureList.size());
        assertEquals(departure1, filteredDepartureList.get(0));
        assertEquals(departure2, filteredDepartureList.get(1));
        assertEquals(departure3, filteredDepartureList.get(2));
    }

    @Test
    @DisplayName("Test checkIfListIsEmpty() with departure before current time")
    void testCheckIfListIsEmptyMethod() {
        Departure departure = new Departure(LocalTime.of(11, 59), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);

        assertTrue(trainDispatch.checkIfListIsEmpty());
    }

    @Test
    @DisplayName("Negative checkIfListIsEmpty() test with departure after current time")
    void testNegativeCheckIfListIsEmptyMethod() {
        Departure departure = new Departure(LocalTime.of(12, 1), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);

        assertFalse(trainDispatch.checkIfListIsEmpty());
    }

    @Test
    @DisplayName("Test findDuplicateTrainNumber() method")
    void testFindDuplicateTrainNumberMethod() {
        Departure departure1 = new Departure(LocalTime.of(12, 1), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure1);
        boolean duplicateTrainNumber = trainDispatch.findDuplicateTrainNumber(123);

        assertTrue(duplicateTrainNumber);
    }

    @Test
    @DisplayName("Negative findDuplicateTrainNumber() test")
    void testNegativeFindDuplicateTrainNumberMethod() {
        Departure departure1 = new Departure(LocalTime.of(12,1), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure1);
        boolean duplicateTrainNumber = trainDispatch.findDuplicateTrainNumber(124);

        assertFalse(duplicateTrainNumber);
    }

    @Test
    @DisplayName("Test findDepartureByNumber method")
    void testFindDepartureByNumberMethod() {
        Departure departure = new Departure(LocalTime.of(12, 1), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        Departure foundDeparture = trainDispatch.findDepartureByNumber(123);

        assertEquals(departure, foundDeparture);
    }

    @Test
    @DisplayName("Negative findDepartureByNumber method")
    void testNegativeFindDepartureByNumberMethod() {
        Departure departure = new Departure(LocalTime.of(12, 1), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        Departure foundDeparture = trainDispatch.findDepartureByNumber(1234);

        assertNull(foundDeparture);
    }

    @Test
    @DisplayName("Test findDepartureByDestination method")
    void testFindDepartureByDestinationMethod() {
        Departure departure1 = new Departure(LocalTime.of(12, 1), "A", 123, "B", 1, 0);
        Departure departure2 = new Departure(LocalTime.of(12, 1), "A", 123, "B", 1, 0);
        Departure departure3 = new Departure(LocalTime.of(12, 1), "A", 123, "C", 1, 0);
        trainDispatch.registerDeparture(departure1);
        trainDispatch.registerDeparture(departure2);
        trainDispatch.registerDeparture(departure3);
        List<Departure> foundDepartures = trainDispatch.findDeparturesByDestination("B");

        assertEquals(2, foundDepartures.size());
        assertTrue(foundDepartures.contains(departure1));
        assertTrue(foundDepartures.contains(departure2));
        assertFalse(foundDepartures.contains(departure3));
    }

    @Test
    @DisplayName("Test setTrack method")
    void testSetTrackMethod() {
        Departure departure = new Departure(LocalTime.of(12,1), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        trainDispatch.setTrack(123, 2);
        assertEquals(2, departure.getTrack());
    }

    @Test
    @DisplayName("Test setDelay method")
    void testSetDelayMethod() {
        Departure departure = new Departure(LocalTime.of(12,1), "A", 123, "B", 1, 0);
        trainDispatch.registerDeparture(departure);
        trainDispatch.setDelay(123, 2);
        assertEquals(2, departure.getDelay());
    }

}