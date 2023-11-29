package org.example;

import org.junit.jupiter.api.BeforeEach;
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
void testRegisterDeparture() {
    Departure departure = new Departure("Train 1", LocalTime.of(9, 0), "Line A", 123, "Destination A", 1, 0);
    trainDispatch.registerDeparture(departure);

    List<Departure> departureList = trainDispatch.getDepartureList();
    assertEquals(1, departureList.size());
    assertEquals(departure, departureList.get(0));
}

@Test
void testShowAllDeparturesAfterTime() {
    Departure departure1 = new Departure("Train 1", LocalTime.of(9, 0), "Line A", 123, "Destination A", 1, 0);
    Departure departure2 = new Departure("Train 2", LocalTime.of(10, 0), "Line B", 456, "Destination B", 2, 31);
    Departure departure3 = new Departure("Train 3", LocalTime.of(11, 0), "Line C", 789, "Destination C", 3, 0);

    trainDispatch.registerDeparture(departure1);
    trainDispatch.registerDeparture(departure2);
    trainDispatch.registerDeparture(departure3);

    trainDispatch.setTime(LocalTime.of(10, 30));

    //TODO fikse i forhold til array[]
}

@Test
void testFindDepartureByNumber() {
    Departure departure1 = new Departure("Train 1", LocalTime.of(9, 0), "Line A", 123, "Destination A", 1, 0);
    Departure departure2 = new Departure("Train 2", LocalTime.of(10, 0), "Line B", 456, "Destination B", 2, 0);
    Departure departure3 = new Departure("Train 3", LocalTime.of(11, 0), "Line C", 789, "Destination C", 3, 0);

    trainDispatch.registerDeparture(departure1);
    trainDispatch.registerDeparture(departure2);
    trainDispatch.registerDeparture(departure3);

    Departure foundDeparture = trainDispatch.findDepartureByNumber(456);
    assertEquals(departure2, foundDeparture);
}

@Test
void testFindDepartureByDestination() {
    Departure departure1 = new Departure("Train 1", LocalTime.of(9, 0), "Line A", 123, "Destination A", 1, 0);
    Departure departure2 = new Departure("Train 2", LocalTime.of(10, 0), "Line B", 456, "Destination B", 2, 0);
    Departure departure3 = new Departure("Train 3", LocalTime.of(11, 0), "Line C", 789, "Destination C", 3, 0);

    trainDispatch.registerDeparture(departure1);
    trainDispatch.registerDeparture(departure2);
    trainDispatch.registerDeparture(departure3);

    Departure[] departuresToDestinationB = trainDispatch.findDeparturesByDestination("Destination B");
    assertEquals(1, departuresToDestinationB.length);
    assertEquals(departure2, departuresToDestinationB[0]);
}

@Test
void testSetTrack() {
    Departure departure = new Departure("Train 1", LocalTime.of(9, 0), "Line A", 123, "Destination A", 1, 0);
    trainDispatch.registerDeparture(departure);

    trainDispatch.setTrack(123, 5);

    assertEquals(5, departure.getTrack());
}

@Test
void testSetDelay() {
    Departure departure = new Departure("Train 1", LocalTime.of(9, 0), "Line A", 123, "Destination A", 1, 0);
    trainDispatch.registerDeparture(departure);

    trainDispatch.setDelay(123, 10);

    assertEquals(10, departure.getDelay());
}
}