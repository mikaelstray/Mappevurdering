package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DepartureTest {

    private Departure departure;
    private LocalTime time = LocalTime.now();

    @BeforeEach
    void setUp() {
        departure = new Departure("Test", time, "Line1", 1, "Destination", 1, 0);
    }

    @Test
    void testCloneWhenCalledThenReturnClonedDepartureObject() {
        Departure clonedDeparture = departure.clone();
        assertNotSame(departure, clonedDeparture);
        assertEquals(departure.getName(), clonedDeparture.getName());
        assertEquals(departure.getTime(), clonedDeparture.getTime());
        assertEquals(departure.getLine(), clonedDeparture.getLine());
        assertEquals(departure.getTrainNumber(), clonedDeparture.getTrainNumber());
        assertEquals(departure.getDestination(), clonedDeparture.getDestination());
        assertEquals(departure.getTrack(), clonedDeparture.getTrack());
        assertEquals(departure.getDelay(), clonedDeparture.getDelay());
    }

    @Test
    void testSetNameWhenCalledThenNameIsUpdated() {
        String newName = "New Name";
        departure.setName(newName);
        assertEquals(newName, departure.getName());
    }

    @Test
    void testSetDelayWhenCalledThenDelayIsUpdated() {
        int newDelay = 10;
        departure.setDelay(newDelay);
        assertEquals(newDelay, departure.getDelay());
    }

    @Test
    void testSetTrackWhenCalledThenTrackIsUpdated() {
        int newTrack = 2;
        departure.setTrack(newTrack);
        assertEquals(newTrack, departure.getTrack());
    }

    @Test
    void testEqualsWhenSameObjectThenReturnTrue() {
        assertTrue(departure.equals(departure));
    }

    @Test
    void testEqualsWhenDifferentClassThenReturnFalse() {
        assertFalse(departure.equals("Test"));
    }

    @Test
    void testEqualsWhenEqualDepartureThenReturnTrue() {
        Departure equalDeparture = new Departure("Test", time, "Line1", 1, "Destination", 1, 0);
        assertTrue(departure.equals(equalDeparture));
    }

    @Test
    void testEqualsWhenDifferentNameThenReturnFalse() {
        Departure differentDeparture = new Departure("Different", LocalTime.now(), "Line1", 1, "Destination", 1, 0);
        assertFalse(departure.equals(differentDeparture));
    }

    @Test
    void testEqualsWhenDifferentTimeThenReturnFalse() {
        Departure differentDeparture = new Departure("Test", LocalTime.now().plusMinutes(1), "Line1", 1, "Destination", 1, 0);
        assertFalse(departure.equals(differentDeparture));
    }

    @Test
    void testEqualsWhenDifferentTrainNumberThenReturnFalse() {
        Departure differentDeparture = new Departure("Test", LocalTime.now(), "Line1", 2, "Destination", 1, 0);
        assertFalse(departure.equals(differentDeparture));
    }

    @Test
    void testEqualsWhenDifferentTrackThenReturnFalse() {
        Departure differentDeparture = new Departure("Test", LocalTime.now(), "Line1", 1, "Destination", 2, 0);
        assertFalse(departure.equals(differentDeparture));
    }

    @Test
  void testEqualsWhenDifferentDestinationThenReturnFalse() {
    Departure differentDeparture = new Departure("Test", LocalTime.now(), "Line1", 1, "Different", 1, 0);
    assertFalse(departure.equals(differentDeparture));
  }

  @Test
  void testEqualsWhenNullThenReturnFalse() {
    assertFalse(departure.equals(null));
  }
}