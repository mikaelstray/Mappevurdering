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
    void createValidDeparture() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        Departure departure = new Departure("Name", time, "Line", 123, "Destination", 1, 0);

        // Act & Assert
        assertEquals("Name", departure.getName());
        assertEquals(time, departure.getTime());
        assertEquals("Line", departure.getLine());
        assertEquals(123, departure.getTrainNumber());
        assertEquals("Destination", departure.getDestination());
        assertEquals(0, departure.getDelay());
        assertEquals(1, departure.getTrack());
    }

    @Test
    void createDepartureWithZeroTrack() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        Departure departure = new Departure("Name", time, "Line", 123, "Destination", 0, 0);

        // Act & Assert
        assertEquals(-1, departure.getTrack());
    }

    @Test
    void createDepartureWithDelay() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        Departure departure = new Departure("Name", time, "Line", 123, "Destination", 1, 15);

        // Act & Assert
        assertEquals(15, departure.getDelay());
        assertEquals(LocalTime.of(12, 45), departure.getTimePlusDelay());
    }

    // Negative tests

    @Test
    void createDepartureWithNegativeTrainNumber() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new Departure("Name", LocalTime.of(12, 30), "Line", -1, "Destination", 1, 0)
        );
    }

    @Test
    void createDepartureWithNegativeTrack() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new Departure("Name", LocalTime.of(12, 30), "Line", 123, "Destination", -1, 0)
        );
    }

    @Test
    void createDepartureWithNegativeDelay() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new Departure("Name", LocalTime.of(12, 30), "Line", 123, "Destination", 1, -15)
        );
    }

    @Test
    void createDepartureWithNullName() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () ->
                new Departure(null, LocalTime.of(12, 30), "Line", 123, "Destination", 1, 0)
        );
    }

    @Test
    void createDepartureWithEmptyLine() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () ->
                new Departure("Name", LocalTime.of(12, 30), "", 123, "Destination", 1, 0)
        );
    }

    @Test
    void createDepartureWithNullTime() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () ->
                new Departure("Name", null, "Line", 123, "Destination", 1, 0)
        );
    }

    @Test
    void createDepartureWithNullDestination() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () ->
                new Departure("Name", LocalTime.of(12, 30), "Line", 123, null, 1, 0)
        );
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
    void testEqualsWhenEqualDepartureThenReturnTrue() {
        Departure equalDeparture = new Departure("Test", time, "Line1", 1, "Destination", 1, 0);
        assertEquals(departure, equalDeparture);
    }

    @Test
    void testEqualsWhenDifferentDepartureThenReturnFalse() {
        Departure differentDeparture = new Departure("Test", time, "Line1", 1, "Destination", 1, 0);
        differentDeparture.setDelay(10);
        assertNotEquals(departure, differentDeparture);
    }

    @Test
    void testEqualsWhenDifferentObjectThenReturnFalse() {
        assertNotEquals(departure, new Object());
    }

@Test
    void testEqualsWhenNullThenReturnFalse() {
        assertNotEquals(departure, null);
    }

    @Test
    void testHashCodeWhenEqualDepartureThenReturnTrue() {
        Departure equalDeparture = new Departure("Test", time, "Line1", 1, "Destination", 1, 0);
        assertEquals(departure.hashCode(), equalDeparture.hashCode());
    }

    @Test
    void testHashCodeWhenDifferentDepartureThenReturnFalse() {
        Departure differentDeparture = new Departure("Test", time, "Line1", 1, "Destination", 1, 0);
        differentDeparture.setDelay(10);
        assertNotEquals(departure.hashCode(), differentDeparture.hashCode());
    }

    @Test
    void testHashCodeWhenDifferentObjectThenReturnFalse() {
        assertNotEquals(departure.hashCode(), new Object().hashCode());
    }

    @Test
    void testToStringWhenCalledThenReturnString() {
        String expected = "Test, 12:24, Line1, 1, Destination, 1, 0";
        assertEquals(expected, departure.toString());
    }

    @Test
void testToStringWhenCalledThenReturnStringWithDelay() {
        departure.setDelay(10);
        String expected = "Test, 12:24, Line1, 1, Destination, 1, 10";
        assertEquals(expected, departure.toString());
    }


}