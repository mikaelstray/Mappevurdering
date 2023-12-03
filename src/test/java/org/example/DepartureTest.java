package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DepartureTest {

    private Departure departure;
    private LocalTime defaultTime;

    @BeforeEach
    void setUp() {
        defaultTime = LocalTime.of(12, 0);
        departure = new Departure(defaultTime, "Line", 123, "Destination", 1, 15);
    }

    @Test
    @DisplayName("Test constructor with valid arguments")
    void testConstructorWithValidArguments() {
        assertEquals(defaultTime, departure.getTime());
        assertEquals("Line", departure.getLine());
        assertEquals(123, departure.getTrainNumber());
        assertEquals("Destination", departure.getDestination());
        assertEquals(15, departure.getDelay());
        assertEquals(1, departure.getTrack());
    }

    @Test
    void createDepartureWithZeroTrack() {
        Departure departure = new Departure(defaultTime, "Line", 123, "Destination", 0, 0);

        assertEquals(-1, departure.getTrack());
    }

    @Test
    @DisplayName("Create departure with negative train number")
    void createDepartureWithNegativeTrainNumber() {
        assertThrows(IllegalArgumentException.class, () ->
                new Departure(defaultTime, "Line", -123, "Destination", 1, 0)
        );
    }

    @Test
    @DisplayName("Create departure with negative track")
    void createDepartureWithNegativeTrack() {
        assertThrows(IllegalArgumentException.class, () ->
                new Departure(defaultTime, "Line", 123, "Destination", -2, 0)
        );
    }

    @Test
    @DisplayName("Create departure with negative delay")
    void createDepartureWithNegativeDelay() {
        assertThrows(IllegalArgumentException.class, () ->
                new Departure(defaultTime, "Line", 123, "Destination", 1, -15)
        );
    }

    @Test
    @DisplayName("Create departure with null line")
    void createDepartureWithNullLine() {
        assertThrows(NullPointerException.class, () ->
                new Departure(defaultTime, null, 123, "Destination", 1, 0)
        );
    }

    @Test
    @DisplayName("Create departure with null time")
    void createDepartureWithNullTime() {
        assertThrows(NullPointerException.class, () ->
                new Departure(null, "Line", 123, "Destination", 1, 0)
        );
    }

    @Test
    @DisplayName("Create departure with null destination")
    void createDepartureWithNullDestination() {
        assertThrows(NullPointerException.class, () ->
                new Departure(defaultTime, "Line", 123, null, 1, 0)
        );
    }

    @Test
    @DisplayName("Create departure with empty line, should not throw")
    void createDepartureWithEmptyLine() {
        assertDoesNotThrow(() ->
                new Departure(defaultTime, "", 123, "Destination", 1, 0)
        );
    }

    @Test
    @DisplayName("Test getTimePlusDelay()")
    void testGetTimePlusDelay() {
        assertEquals(LocalTime.of(12, 15), departure.getTimePlusDelay());
    }

    @Test
    @DisplayName("Negative test of getTimePlusDelay()")
    void testGetTimePlusDelayNegative() {
        assertNotEquals(defaultTime, departure.getTimePlusDelay());
    }

    @Test
    @DisplayName("Test getTime()")
    void testGetTime() {
        assertEquals(defaultTime, departure.getTime());
    }

    @Test
    @DisplayName("Test getLine()")
    void testGetLine() {
        String line = "Line";
        Departure departure = new Departure(defaultTime, line, 123, "Destination", 1, 15);

        assertEquals(line, departure.getLine());
    }

    @Test
    @DisplayName("Test getTrainNumber()")
    void testGetTrainNumber() {
        int trainNumber = 123;
        Departure departure = new Departure(defaultTime, "Line", trainNumber, "Destination", 1, 15);

        assertEquals(trainNumber, departure.getTrainNumber());
    }

    @Test
    @DisplayName("Test getDestination()")
    void testGetDestination() {
        String destination = "Destination";
        Departure departure = new Departure(defaultTime, "Line", 123, destination, 1, 15);

        assertEquals(destination, departure.getDestination());
    }

    @Test
    @DisplayName("Test getDelay()")
    void testGetDelay() {
        int delay = 15;
        Departure departure = new Departure(defaultTime, "Line", 123, "Destination", 1, delay);

        assertEquals(delay, departure.getDelay());
    }

    @Test
    @DisplayName("Test setDelay()")
    void testSetDelay() {
        departure.setDelay(30);

        assertEquals(30, departure.getDelay());
    }

    @Test
    @DisplayName("Negative test of setDelay()")
    void testSetDelayNegative() {
        departure.setDelay(30);

        assertNotEquals(15, departure.getDelay());
    }

    @Test
    @DisplayName("Assert throws in setDelay()")
    void testSetDelayAssertThrows() {
        int delay = 15;
        Departure departure = new Departure(defaultTime, "Line", 123, "Destination", 1, delay);

        assertThrows(IllegalArgumentException.class, () -> departure.setDelay(-1));
    }

    @Test
    @DisplayName("Assert does not throw in setDelay()")
    void testSetDelayAssertDoesNotThrow() {
        int delay = 15;
        Departure departure = new Departure(defaultTime, "Line", 123, "Destination", 1, delay);

        assertDoesNotThrow(() -> departure.setDelay(0));
    }

    @Test
    @DisplayName("Test getTrack()")
    void testGetTrack() {
        int track = 1;
        Departure departure = new Departure(defaultTime, "Line", 123, "Destination", track, 15);

        assertEquals(track, departure.getTrack());
    }

    @Test
    @DisplayName("Negative test of getTrack()")
    void testGetTrackNegative() {
        int track = 1;
        Departure departure = new Departure(defaultTime, "Line", 123, "Destination", track, 15);

        assertNotEquals(2, departure.getTrack());
    }

    @Test
    @DisplayName("Test setTrack()")
    void testSetTrack() {
        departure.setTrack(2);

        assertEquals(2, departure.getTrack());
    }

    @Test
    @DisplayName("Negative test of setTrack()")
    void testSetTrackNegative() {
        departure.setTrack(2);

        assertNotEquals(1, departure.getTrack());
    }

    @Test
    @DisplayName("Assert throws in setTrack()")
    void testSetTrackAssertThrows() {
        int track = 1;
        Departure departure = new Departure(defaultTime, "Line", 123, "Destination", track, 15);

        assertThrows(IllegalArgumentException.class, () -> departure.setTrack(-1));
    }

    @Test
    @DisplayName("Assert does not throw in setTrack()")
    void testSetTrackAssertDoesNotThrow() {
        int track = 1;
        Departure departure = new Departure(defaultTime, "Line", 123, "Destination", track, 15);

        assertDoesNotThrow(() -> departure.setTrack(0));
    }

    @Test
    @DisplayName("Test equals()")
    void testEquals() {
        Departure departure1 = new Departure(defaultTime, "Line", 123, "Destination", 1, 15);
        Departure departure2 = new Departure(defaultTime, "Line", 123, "Destination", 1, 15);

        assertEquals(departure1, departure2);
    }

    @Test
    @DisplayName("Negative test of equals()")
    void testEqualsNegative() {
        Departure departure = new Departure(defaultTime, "Line", 456, "Destination", 1, 15);
        Departure departure1 = new Departure(defaultTime, "Line", 123, "Destination", 1, 15);

        assertNotEquals(departure, departure1);
    }

    @Test
    @DisplayName("Test hashCode()")
    void testHashCode() {
        Departure departure = new Departure(defaultTime, "Line", 123, "Destination", 1, 15);
        Departure departure1 = new Departure(defaultTime, "Line", 123, "Destination", 1, 15);

        assertEquals(departure.hashCode(), departure1.hashCode());
    }

    @Test
    @DisplayName("Negative test of hashcode()")
    void testHashCodeNegative() {
        Departure departure = new Departure(defaultTime, "Line", 123, "Destination", 1, 15);
        Departure departure1 = new Departure(defaultTime, "Line", 456, "Destination", 1, 15);

        assertNotEquals(departure.hashCode(), departure1.hashCode());
    }
}