package ui;

import java.io.IOException;
import java.util.Scanner;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.RESET_TEXT_BOLD_FAINT;

public class MenuManager {

    public static ServerFacade serverFacade;
    public static Scanner scanner = new Scanner(System.in);
    private static final int MAX_LENGTH = 30;
    public static String loggedInUsername = null;
    public static String loggedInAuth = null;

    private MenuState currentState;
    private final PreloginMenu preloginMenu;
    private final PostloginMenu postloginMenu;
    private final GameMenu gameMenu;


    public MenuManager(String url) {
        serverFacade = new ServerFacade(url);
        currentState = MenuState.PRELOGIN;
        preloginMenu = new PreloginMenu();
        postloginMenu = new PostloginMenu();
        gameMenu = new GameMenu();
    }


    public void run() throws IOException {
        while (currentState != MenuState.QUIT) {
            switch (currentState) {
                case PRELOGIN:
                    currentState = preloginMenu.run();
                    break;
                case POSTLOGIN:
                    currentState = postloginMenu.run();
                    break;
                case GAME:
                    currentState = gameMenu.run();
                    break;
            }
        }
    }

    public static int getValidOption(int numOptions) {
        int option = -1;

        while (true) {
            System.out.print("Enter a number between 1 and " + numOptions + ": ");
            String input = scanner.nextLine();

            try {
                option = Integer.parseInt(input.trim());

                if (option >= 1 && option <= numOptions) {
                    break;
                } else {
                    System.out.print(option + " is not an available option. ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. ");
            }
        }

        return option;
    }


    public static String getValidStringInput(String prompt) {
        String input;

        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();  // Read and trim the input

            // Check if input is empty
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
            // Check if input exceeds the maximum length
            else if (input.length() > MAX_LENGTH) {
                System.out.println("Input cannot exceed " + MAX_LENGTH + " characters. Please try again.");
            }
            // Check if input contains spaces
            else if (input.contains(" ")) {
                System.out.println("Input cannot contain spaces. Please try again.");
            } else {
                break;  // Input is valid, exit loop
            }
        }

        return input;
    }

    public static void printError(String operationName, String message) {
        System.out.println("\n" + operationName + " failed: " +
                SET_TEXT_COLOR_RED + SET_TEXT_BOLD +
                message +
                RESET_TEXT_COLOR + RESET_TEXT_BOLD_FAINT);
    }

    public static void printNoGamesMessage() {
        System.out.println("\n" + SET_TEXT_COLOR_YELLOW + SET_TEXT_BOLD +
                "There are no current games. Create a game to get started." +
                RESET_TEXT_COLOR + RESET_TEXT_BOLD_FAINT);
    }

    public static void printResult(String result) {
        System.out.println("\n" + SET_TEXT_COLOR_LG + SET_TEXT_BOLD + result + RESET_TEXT_BOLD_FAINT + RESET_TEXT_COLOR);
    }
}
