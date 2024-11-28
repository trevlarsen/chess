package ui;

import chess.ChessGame;
import model.GameData;

import java.io.IOException;

import static ui.EscapeSequences.*;
import static ui.MenuManager.*;

public class PostloginMenu {

    private final MenuManager ui;

    // Constructor that accepts MenuManager instance
    public PostloginMenu(MenuManager ui) {
        this.ui = ui;
    }

    public MenuState run() {
        MenuState result = MenuState.POSTLOGIN;
        printPostloginMenu();
        int input = getValidOption(6);

        switch (input) {
            case 1 -> result = this.logout();
            case 2 -> this.listGames();
            case 3 -> this.createGame();
            case 4 -> result = this.playGame();
            case 5 -> result = this.observeGame();
            case 6 -> printHelp();
            default -> System.out.print("Invalid option. Please try again.");
        }
        return result;
    }

    private MenuState logout() {
        try {
            // Use the instance variable to access loggedInAuth
            ui.serverFacade.logout(ui.loggedInAuth);
            printResult("Logout Successful.");
            return MenuState.PRELOGIN;
        } catch (IOException e) {
            printError("Logout", e.getMessage());
            return MenuState.GAME;
        }
    }

    private void listGames() {
        try {
            ui.refreshGames();

            if (ui.listedGames.isEmpty()) {
                printNoGamesMessage();
                return;
            }
            System.out.println(SET_TEXT_COLOR_BLUE);
            System.out.println("Available Games:");
            int index = 1;
            for (GameData game : ui.listedGames) {
                System.out.printf("%d. '%s' - White: %s, Black: %s\n", index++, game.gameName(),
                        game.whiteUsername(), game.blackUsername());
            }
            System.out.println(RESET_TEXT_COLOR);
        } catch (IOException e) {
            printError("List Games", e.getMessage());
        }
    }

    private void createGame() {
        try {
            String gameName = getValidStringInput("Enter a name for the new game: ");
            // Use the instance variable to access loggedInAuth
            ui.serverFacade.createGame(gameName, ui.loggedInAuth);
            var currentGames = ui.serverFacade.listGames(ui.loggedInAuth);
            printResult("Game created with ID: " + currentGames.size());
        } catch (IOException e) {
            printError("Create Game", e.getMessage());
        }
    }

    private MenuState playGame() {
        try {
            ui.refreshGames();

            if (ui.listedGames.isEmpty()) {
                printNoGamesMessage();
                return MenuState.POSTLOGIN;
            }

            System.out.println("Which game would you like to play?");
            int gameIndex = getValidOption(ui.listedGames.size()) - 1;
            GameData selectedGame = ui.listedGames.get(gameIndex);

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

            // Use the instance variable to access loggedInAuth
            ui.serverFacade.joinGame(playerColor, selectedGame.gameID(), ui.loggedInAuth);
            ui.currentGame = selectedGame.game();
            printResult("You joined the game '" + selectedGame.gameName() + "' as " + color + ".");
            return MenuState.GAME;
        } catch (IOException e) {
            printError("Join Game", e.getMessage());
            return MenuState.POSTLOGIN;
        }
    }

    private MenuState observeGame() {
        try {
            ui.refreshGames();

            if (ui.listedGames.isEmpty()) {
                printNoGamesMessage();
                return MenuState.POSTLOGIN;
            }

            System.out.println("Which game would you like to observe?");
            int gameIndex = getValidOption(ui.listedGames.size()) - 1;
            GameData selectedGame = ui.listedGames.get(gameIndex);
            ui.currentGame = selectedGame.game();

            printResult("You are now observing the game '" + selectedGame.gameName() + "'.");
            return MenuState.GAME;
        } catch (IOException e) {
            printError("Observe Game", e.getMessage());
            return MenuState.POSTLOGIN;
        }
    }
}
