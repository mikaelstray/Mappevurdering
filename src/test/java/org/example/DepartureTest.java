package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DepartureTest {

    private Departure departure;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        departure = new Departure("mikael", LocalTime.parse("12:45"), "3", 2, "e", 0, 0);
    }

    @org.junit.jupiter.api.Test
    void getName() {
        assertEquals("mikael", departure.getName());
    }

    @org.junit.jupiter.api.Test
    void testGetNameWhenNameIsSetThenReturnCorrectName() {
        // Test to verify that the 'getName' method returns the correct name.
        assertEquals("mikael", departure.getName());
    }

    @org.junit.jupiter.api.Test
    void testGetNameWhenNameIsNotSetThenReturnNull() {
        // Test to verify that the 'getName' method returns null when the name is not set.
        departure.setName(null);
        assertNull(departure.getName());
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        departure = null;
    }

}