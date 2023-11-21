package org.example;

/**
 * The Main class serves as the entry point for the application.
 * It initializes and starts the user interface.

 * @author Mikael Stray Froeyshov
 * @version 1.0
 * @since 2023-11-02
 */

public class Main {
  public static void main(String[] args) {
    // Create an instance of the UserInterface
    UserInterface userInterface = new UserInterface();
    // Start the user interface
    userInterface.start();
  }
}