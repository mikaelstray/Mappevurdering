package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DepartureTest {

    private Departure departure;

    @BeforeEach
    void setUp() {
        departure = new Departure(LocalTime.of(12,30), "Line", 123, "Destination", 1, 15);
    }

    @Test
    @DisplayName("Test constructor with valid arguments")
    void createValidDepartureWithDelay() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        // Act & Assert
        assertEquals(time, departure.getTime());
        assertEquals("Line", departure.getLine());
        assertEquals(123, departure.getTrainNumber());
        assertEquals("Destination", departure.getDestination());
        assertEquals(15, departure.getDelay());
        assertEquals(1, departure.getTrack());
    }

    @Test
    @DisplayName("Test getTimePlusDelay()")
    void testGetTimePlusDelay() {
        assertEquals(LocalTime.of(12, 45), departure.getTimePlusDelay());
    }

    @Test
    @DisplayName("Negative test of getTimePlusDelay()")
    void testGetTimePlusDelayNegative() {
        assertNotEquals(LocalTime.of(12, 30), departure.getTimePlusDelay());
    }

    @Test
    @DisplayName("Test getTime()")
    void testGetTime() {
        LocalTime time = LocalTime.of(12, 30);
        assertEquals(time, departure.getTime());
    }

    @Test
    @DisplayName("Negative test of getTime()")
    void testGetTimeNegative() {
        assertNotEquals(LocalTime.of(12, 45), departure.getTime());
    }

    @Test
    @DisplayName("Test getLine()")
    void testGetLine() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        String line = "Line";
        Departure departure = new Departure(time, line, 123, "Destination", 1, 15);

        // Act & Assert
        assertEquals(line, departure.getLine());
    }

    @Test
    @DisplayName("Negative test of getLine()")
    void testGetLineNegative() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        String line = "Line";
        Departure departure = new Departure(time, line, 123, "Destination", 1, 15);

        // Act & Assert
        assertNotEquals("Line1", departure.getLine());
    }

    @Test
    @DisplayName("Test getTrainNumber()")
    void testGetTrainNumber() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        int trainNumber = 123;
        Departure departure = new Departure(time, "Line", trainNumber, "Destination", 1, 15);

        // Act & Assert
        assertEquals(trainNumber, departure.getTrainNumber());
    }

    @Test
    @DisplayName("Negative test of getTrainNumber()")
    void testGetTrainNumberNegative() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        int trainNumber = 123;
        Departure departure = new Departure(time, "Line", trainNumber, "Destination", 1, 15);

        // Act & Assert
        assertNotEquals(1234, departure.getTrainNumber());
    }

    @Test
    @DisplayName("Test getDestination()")
    void testGetDestination() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        String destination = "Destination";
        Departure departure = new Departure(time, "Line", 123, destination, 1, 15);

        // Act & Assert
        assertEquals(destination, departure.getDestination());
    }

    @Test
    @DisplayName("Negative test of getDestination()")
    void testGetDestinationNegative() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        String destination = "Destination";
        Departure departure = new Departure(time, "Line", 123, destination, 1, 15);

        // Act & Assert
        assertNotEquals("Destination1", departure.getDestination());
    }

    @Test
    @DisplayName("Test getDelay()")
    void testGetDelay() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        int delay = 15;
        Departure departure = new Departure(time, "Line", 123, "Destination", 1, delay);

        // Act & Assert
        assertEquals(delay, departure.getDelay());
    }

    @Test
    @DisplayName("Negative test of getDelay()")
    void testGetDelayNegative() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        int delay = 15;
        Departure departure = new Departure(time, "Line", 123, "Destination", 1, delay);

        // Act & Assert
        assertNotEquals(16, departure.getDelay());
    }

    @Test
    @DisplayName("Test setDelay()")
    void testSetDelay() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        int delay = 15;
        Departure departure = new Departure(time, "Line", 123, "Destination", 1, delay);

        // Act & Assert
        assertEquals(delay, departure.getDelay());
    }

    @Test
    @DisplayName("Negative test of setDelay()")
    void testSetDelayNegative() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        int delay = 15;
        Departure departure = new Departure(time, "Line", 123, "Destination", 1, delay);

        // Act & Assert
        assertNotEquals(16, departure.getDelay());
    }

    @Test
    @DisplayName("Assert throws in setDelay()")
    void testSetDelayAssertThrows() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        int delay = 15;
        Departure departure = new Departure(time, "Line", 123, "Destination", 1, delay);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> departure.setDelay(-1));
    }

    @Test
    @DisplayName("Assert does not throw in setDelay()")
    void testSetDelayAssertDoesNotThrow() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        int delay = 15;
        Departure departure = new Departure(time, "Line", 123, "Destination", 1, delay);

        // Act & Assert
        assertDoesNotThrow(() -> departure.setDelay(0));
    }

    @Test
    @DisplayName("Test getTrack()")
    void testGetTrack() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        int track = 1;
        Departure departure = new Departure(time, "Line", 123, "Destination", track, 15);

        // Act & Assert
        assertEquals(track, departure.getTrack());
    }

    @Test
    @DisplayName("Negative test of getTrack()")
    void testGetTrackNegative() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        int track = 1;
        Departure departure = new Departure(time, "Line", 123, "Destination", track, 15);

        // Act & Assert
        assertNotEquals(2, departure.getTrack());
    }

    @Test
    @DisplayName("Test setTrack()")
    void testSetTrack() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        int track = 1;
        Departure departure = new Departure(time, "Line", 123, "Destination", track, 15);

        // Act & Assert
        assertEquals(track, departure.getTrack());
    }

    @Test
    @DisplayName("Negative test of setTrack()")
    void testSetTrackNegative() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        int track = 1;
        Departure departure = new Departure(time, "Line", 123, "Destination", track, 15);

        // Act & Assert
        assertNotEquals(2, departure.getTrack());
    }

    @Test
    @DisplayName("Assert throws in setTrack()")
    void testSetTrackAssertThrows() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        int track = 1;
        Departure departure = new Departure(time, "Line", 123, "Destination", track, 15);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> departure.setTrack(-1));
    }

    @Test
    @DisplayName("Assert does not throw in setTrack()")
    void testSetTrackAssertDoesNotThrow() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        int track = 1;
        Departure departure = new Departure(time, "Line", 123, "Destination", track, 15);

        // Act & Assert
        assertDoesNotThrow(() -> departure.setTrack(0));
    }

    @Test
    @DisplayName("Test equals()")
    void testEquals() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        Departure departure = new Departure(time, "Line", 123, "Destination", 1, 15);
        Departure departure1 = new Departure(time, "Line", 123, "Destination", 1, 15);

        // Act & Assert
        assertEquals(departure, departure1);
    }

    @Test
    @DisplayName("Test hashCode()")
    void testHashCode() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        Departure departure = new Departure(time, "Line", 123, "Destination", 1, 15);
        Departure departure1 = new Departure(time, "Line", 123, "Destination", 1, 15);

        // Act & Assert
        assertEquals(departure.hashCode(), departure1.hashCode());
    }

    @Test
    @DisplayName("Negative test of hashcode()")
    void testHashCodeNegative() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        Departure departure = new Departure(time, "Line", 456, "Destination", 1, 15);
        Departure departure1 = new Departure(time, "Line", 123, "Destination", 1, 15);

        // Act & Assert
        assertNotEquals(departure.hashCode(), departure1.hashCode());
    }

    @Test
    void createDepartureWithZeroTrack() {
        // Arrange
        LocalTime time = LocalTime.of(12, 30);
        Departure departure = new Departure(time, "Line", 123, "Destination", 0, 0);

        // Act & Assert
        assertEquals(-1, departure.getTrack());
    }

    @Test
    @DisplayName("Create departure with negative train number")
    void createDepartureWithNegativeTrainNumber() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new Departure(LocalTime.of(12, 30), "Line", -123, "Destination", 1, 0)
        );
    }

    @Test
    void createDepartureWithNegativeTrack() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new Departure(LocalTime.of(12, 30), "Line", 123, "Destination", -1, 0)
        );
    }

    @Test
    void createDepartureWithNegativeDelay() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                new Departure(LocalTime.of(12, 30), "Line", 123, "Destination", 1, -15)
        );
    }

    @Test
    @DisplayName("Create departure with null line")
    void createDepartureWithNullLine() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () ->
                new Departure(LocalTime.of(12, 30), null, 123, "Destination", 1, 0)
        );
    }

    @Test
    void createDepartureWithNullTime() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () ->
                new Departure(null, "Line", 123, "Destination", 1, 0)
        );
    }

    @Test
    void createDepartureWithNullDestination() {
        // Arrange & Act & Assert
        LocalTime time = LocalTime.of(12, 30);
        assertThrows(NullPointerException.class, () ->
                new Departure(time, "Line", 123, null, 1, 0)
        );
    }

    @Test
    @DisplayName("Create departure with empty line, should not throw")
    void createDepartureWithEmptyLine() {
        // Arrange & Act & Assert
        assertDoesNotThrow(() ->
                new Departure(LocalTime.of(12, 30), "", 123, "Destination", 1, 0)
        );
    }

}