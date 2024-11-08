package ui;

import java.io.IOException;

import static ui.MenuManager.serverFacade;

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
            case 3 -> System.out.println("Select an option below by entering its option number.");
            case 4 -> {
                System.out.print("Goodbye.");
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
            serverFacade.login(username, password);
            System.out.println("Login Successful!");
            return MenuState.POSTLOGIN;
        } catch (IOException e) {
            System.out.println("Login failed: " + e.getMessage());
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
            System.out.println("Registration Successful!");
            // Login after successful registration
            serverFacade.login(username, password);
            return MenuState.POSTLOGIN;
        } catch (IOException e) {
            System.out.println("Registration failed: " + e.getMessage());
            return MenuState.PRELOGIN;
        }
    }
}
