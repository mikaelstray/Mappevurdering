package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

  InputValidator inputValidator;
  TrainDispatch trainDispatch;

  @BeforeEach
  void setUp() {
    inputValidator = new InputValidator();
    trainDispatch = new TrainDispatch();
  }

  @Test
  void validateTimeInput_validTime_returnsTime() {
    LocalTime result = InputValidator.validateTimeInput("12:00", LocalTime.of(11, 0));

    assertEquals(LocalTime.of(12, 0), result);
  }

  @Test
  void validateTimeInput_emptyTime_throwsException() {
    LocalTime timeNow = LocalTime.of(11, 0);

    assertThrows(IllegalArgumentException.class, () -> InputValidator.validateTimeInput("", timeNow));
  }

  @Test
  void validateTimeInput_pastTime_throwsException() {
    LocalTime timeNow = LocalTime.of(11, 0);

    assertThrows(IllegalArgumentException.class, () -> InputValidator.validateTimeInput("10:00", timeNow));
  }

  @Test
  void validateLineInput_validLine_returnsLine() {
    String result = InputValidator.validateLineInput("F4");

    assertEquals("F4", result);
  }

  @Test
  void validateLineInput_emptyLine_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> InputValidator.validateLineInput(""));
    assertThrows(IllegalArgumentException.class, () -> InputValidator.validateLineInput("0"));
  }

  @Test
  void validateLineInput_longLine_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> InputValidator.validateLineInput("A123456"));
  }

  @Test
  void validateLineInput_invalidCharacters_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> InputValidator.validateLineInput("F4-"));
  }

  @Test
  void validateTrainNumber_validNumber_returnsNumber() {
    int result = inputValidator.validateTrainNumber("321", trainDispatch);

    assertEquals(321, result);
  }

  @Test
  void validateTrainNumber_emptyNumber_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> inputValidator.validateTrainNumber("", trainDispatch));
  }

  @Test
  void validateTrainNumber_largeNumber_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> inputValidator.validateTrainNumber("10000", trainDispatch));
  }

  @Test
  void validateTrainNumber_negativeNumber_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> inputValidator.validateTrainNumber("-1", trainDispatch));
  }

  @Test
  void validateTrainNumber_duplicateNumber_throwsException() {
    Departure departure = new Departure(LocalTime.of(12, 1), "A", 1234, "B", 1, 0);
    trainDispatch.registerDeparture(departure);
    trainDispatch.setTime(LocalTime.of(12, 0));
    assertThrows(IllegalArgumentException.class, () -> inputValidator.validateTrainNumber("1234", trainDispatch));
  }

  @Test
  void validateTrainNumberToFind_nonDuplicateNumber_throwsException() {
    Departure departure = new Departure(LocalTime.of(12, 1), "A", 1234, "B", 1, 0);
    trainDispatch.registerDeparture(departure);
    trainDispatch.setTime(LocalTime.of(12, 0));
    assertThrows(IllegalArgumentException.class, () -> inputValidator.validateTrainNumberToFind("123", trainDispatch));
    assertDoesNotThrow(() -> inputValidator.validateTrainNumberToFind("1234", trainDispatch));
  }

  @Test
  void validateDestination_validDestination_returnsDestination() {
    String result = InputValidator.validateDestination("Oslo");
    assertEquals("Oslo", result);
  }

  @Test
  void validateDestination_emptyDestination_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> InputValidator.validateDestination(""));
  }

  @Test
  void validateDestination_invalidCharacters_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> InputValidator.validateDestination("Oslo1"));
  }

  @Test
  void validateDestinationToFind_nonDuplicateDestination_throwsException() {
    trainDispatch.setTime(LocalTime.of(12, 0));
    Departure departure = new Departure(LocalTime.of(12, 1), "A", 1234, "Bergen", 1, 0);
    trainDispatch.registerDeparture(departure);
    trainDispatch.setTime(LocalTime.of(12, 0));
    assertThrows(IllegalArgumentException.class, () -> inputValidator.validateDestinationToFind("Oslo", trainDispatch));
    assertDoesNotThrow(() -> inputValidator.validateDestinationToFind("Bergen", trainDispatch));
  }

  @Test
  void validateNumericInput_validNumber_returnsNumber() {
    int result = InputValidator.validateNumericInput("123");
    assertEquals(123, result);
  }

  @Test
  void validateNumericInput_emptyNumber_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> InputValidator.validateNumericInput(""));
  }

  @Test
  void validateNumericInput_largeNumber_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> InputValidator.validateNumericInput("1000"));
  }

  @Test
  void validateNumericInput_negativeNumber_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> InputValidator.validateNumericInput("-1"));
  }
}
