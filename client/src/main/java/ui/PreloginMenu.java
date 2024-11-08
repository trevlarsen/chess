package ui;

import model.AuthData;

import java.io.IOException;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.RESET_TEXT_COLOR;
import static ui.MenuManager.*;

public class PreloginMenu {


    private final int numPreloginOptions = 4;

    private final String preloginMenu = """
            \nYou are not logged in. What would you like to do?
            \t1. Login
            \t2. Register
            \t3. Help
            \t4. Quit
            """;


    public MenuState run() throws IOException {
        MenuState result = MenuState.PRELOGIN;
        System.out.print(preloginMenu);
        int input = MenuManager.getValidOption(numPreloginOptions);

        switch (input) {
            case 1 -> result = this.loginUI();
            case 2 -> result = this.registerUI();
            case 3 -> System.out.println(SET_TEXT_BOLD + SET_TEXT_COLOR_YELLOW +
                    "\nSelect an option below by entering its option number."
                    + RESET_TEXT_BOLD_FAINT + RESET_TEXT_COLOR);
            case 4 -> {
                printResult("Goodbye.");
                System.exit(0);
            }
            default -> System.out.println("Invalid option. Please try again.");
        }
        return result;
    }


    /**
     * Displays the login UI, prompts for username and password.
     * Attempts to log in and returns the next MenuState.
     */
    public MenuState loginUI() {
        try {
            String username = MenuManager.getValidStringInput("Enter username: ");
            String password = MenuManager.getValidStringInput("Enter password: ");
            AuthData authData = serverFacade.login(username, password);
            printResult("Login Successful for " + username + "!");
            loggedInUsername = authData.username();
            loggedInAuth = authData.authToken();
            return MenuState.POSTLOGIN;
        } catch (IOException e) {
            MenuManager.printError("Login", e.getMessage());
            return MenuState.PRELOGIN;
        }
    }

    /**
     * Displays the registration UI, prompts for username, password, and email.
     * Attempts to register and logs in upon success.
     *
     * @return the next MenuState based on registration success or failure.
     */
    public MenuState registerUI() {
        try {
            String username = MenuManager.getValidStringInput("Choose a username (no spaces, max 30 characters): ");
            String password = MenuManager.getValidStringInput("Choose a password (no spaces, max 30 characters): ");
            String email = MenuManager.getValidStringInput("Enter your email (no spaces, max 30 characters): ");
            serverFacade.register(username, password, email);
            printResult("Registration Successful!");
            // Login after successful registration
            AuthData authData = serverFacade.login(username, password);
            loggedInUsername = authData.username();
            loggedInAuth = authData.authToken();
            return MenuState.POSTLOGIN;
        } catch (IOException e) {
            MenuManager.printError("Registration", e.getMessage());
            return MenuState.PRELOGIN;
        }
    }
}
