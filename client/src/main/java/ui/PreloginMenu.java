package ui;

import model.AuthData;

import java.io.IOException;

import static ui.MenuManager.*;

public class PreloginMenu {

    private final MenuManager ui;

    // Constructor that accepts MenuManager instance
    public PreloginMenu(MenuManager ui) {
        this.ui = ui;
    }

    public MenuState run() throws IOException {
        MenuState result = MenuState.PRELOGIN;
        printPreloginMenu();
        int input = getValidOption(4);

        switch (input) {
            case 1 -> result = this.loginUI();
            case 2 -> result = this.registerUI();
            case 3 -> printHelp();
            case 4 -> {
                printResult("Goodbye.");
                System.exit(0);
            }
            default -> System.out.println("Invalid option. Please try again.");
        }
        return result;
    }

    public MenuState loginUI() {
        try {
            String username = getValidStringInput("Enter username: ");
            String password = getValidStringInput("Enter password: ");
            AuthData authData = ui.serverFacade.login(username, password);
            printResult("Login Successful. Welcome back " + username + "!");
            ui.loggedInUsername = authData.username();
            ui.loggedInAuth = authData.authToken();
            return MenuState.POSTLOGIN;

        } catch (IOException e) {
            printError("Login", e.getMessage());
            return MenuState.PRELOGIN;
        }
    }

    public MenuState registerUI() {
        try {
            String username = getValidStringInput("Choose a username (no spaces, max 30 characters): ");
            String password = getValidStringInput("Choose a password (no spaces, max 30 characters): ");
            String email = getValidStringInput("Enter your email (no spaces, max 30 characters): ");
            ui.serverFacade.register(username, password, email);
            printResult("Registration Successful. Welcome " + username + "!");

            // Login after successful registration
            AuthData authData = ui.serverFacade.login(username, password);
            ui.loggedInUsername = authData.username();
            ui.loggedInAuth = authData.authToken();
            return MenuState.POSTLOGIN;

        } catch (IOException e) {
            printError("Registration", e.getMessage());
            return MenuState.PRELOGIN;
        }
    }
}
