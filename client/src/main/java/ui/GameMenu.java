package ui;

import static ui.ChessBoardPrinter.tempPrint;
import static ui.MenuManager.*;

public class GameMenu {

    private final int numGameOptions = 2;

    private final String gameMenu = """
            \nGame functionality coming soon!
            \t1. Go back
            \t2. Quit
            """;


    public MenuState run() {
        MenuState result = MenuState.GAME;
        tempPrint();
        printGameMenu();
        int input = getValidOption(2);

        switch (input) {
            case 1 -> result = MenuState.POSTLOGIN;
            case 2 -> {
                printResult("Goodbye.");
                System.exit(0);
            }
            default -> System.out.println("Invalid option. Please try again.");
        }
        return result;
    }
}

