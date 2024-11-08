package ui;

import chess.ChessGame;
import model.GameData;

import java.io.IOException;
import java.util.ArrayList;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.RESET_TEXT_BOLD_FAINT;
import static ui.MenuManager.*;

public class PostloginMenu {

    private final int numPostloginOptions = 6;
    private ArrayList<GameData> listedGames = new ArrayList<>();
    private int gameId = 1;

    private final String postloginMenu = """
            \nYou are logged in. What would you like to do?
            \t1. Logout
            \t2. List Games
            \t3. Create Game
            \t4. Play Game
            \t5. Observe Game
            \t6. Help
            """;

    /**
     * Runs the post-login menu, prompting for an option and executing the appropriate action.
     *
     * @return the next MenuState based on user input.
     * @throws IOException if there's an error with server communication.
     */
    public MenuState run() throws IOException {
        MenuState result = MenuState.POSTLOGIN;
        System.out.print(postloginMenu);
        int input = MenuManager.getValidOption(numPostloginOptions);

        switch (input) {
            case 1 -> result = this.logout();
            case 2 -> this.listGames();
            case 3 -> this.createGame();
            case 4 -> result = this.playGame();
            case 5 -> result = this.observeGame();
            case 6 -> System.out.print("Select an option by entering its option number.");
            default -> System.out.print("Invalid option. Please try again.");
        }
        return result;
    }

    /**
     * Logs the user out and returns to the pre-login menu.
     */
    private MenuState logout() {
        try {
            serverFacade.logout(loggedInAuth);
            printResult("Logout Successful.");
            return MenuState.PRELOGIN;
        } catch (IOException e) {
            MenuManager.printError("Logout", e.getMessage());
            return MenuState.GAME;
        }
    }

    /**
     * Lists available games for the user.
     */
    private void listGames() {
        try {
            var games = serverFacade.listGames(loggedInAuth);
            listedGames = new ArrayList<>(games);  // Store games for reference in Play/Observe
            if (games.isEmpty()) {
                printNoGamesMessage();
                return;
            }
            System.out.println(SET_TEXT_COLOR_BLUE);
            System.out.println("Available Games:");
            int index = 1;
            for (GameData game : listedGames) {
                System.out.printf("%d. '%s' - White: %s, Black: %s\n", index++, game.gameName(), game.whiteUsername(), game.blackUsername());
            }
            System.out.println(RESET_TEXT_COLOR);
        } catch (IOException e) {
            MenuManager.printError("List Games", e.getMessage());
        }
    }

    private void createGame() {
        try {
            String gameName = getValidStringInput("Enter a name for the new game: ");
            serverFacade.createGame(gameName, loggedInAuth);
            var currentGames = serverFacade.listGames(loggedInAuth);
            printResult("Game created with ID: " + currentGames.size());
        } catch (IOException e) {
            printError("Create Game", e.getMessage());
        }
    }

    private MenuState playGame() {
        if (listedGames.isEmpty()) {
            printNoGamesMessage();
            return MenuState.POSTLOGIN;
        }

        try {
            System.out.println("Which game would you like to play?");
            int gameIndex = MenuManager.getValidOption(listedGames.size()) - 1;
            GameData selectedGame = listedGames.get(gameIndex);

            String color;
            while (true) {
                System.out.print("Please select a color to play (white/black): ");
                color = scanner.nextLine().trim().toLowerCase();

                if (color.equals("white") || color.equals("black")) {
                    break;
                } else {
                    System.out.print("Invalid color. ");
                }
            }
            ChessGame.TeamColor playerColor = ChessGame.TeamColor.WHITE;
            if (color.equals("black")) {
                playerColor = ChessGame.TeamColor.BLACK;
            }

            serverFacade.joinGame(playerColor, selectedGame.gameID(), loggedInAuth);
            printResult("You joined the game '" + selectedGame.gameName() + "' as " + color + ".");
            return MenuState.GAME;
        } catch (IOException e) {
            MenuManager.printError("Join Game", e.getMessage());
            return MenuState.POSTLOGIN;
        }
    }

    private MenuState observeGame() {
        if (listedGames.isEmpty()) {
            printNoGamesMessage();
            return MenuState.POSTLOGIN;
        }

        System.out.println("Which game would you like to observe?");
        int gameIndex = getValidOption(listedGames.size()) - 1;
        GameData selectedGame = listedGames.get(gameIndex);

        printResult("You are now observing the game '" + selectedGame.gameName() + "'.");
        return MenuState.GAME;
    }
}
