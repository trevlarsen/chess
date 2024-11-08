package ui;

public class GameMenu {

    private final int numGameOptions = 2;

    private final String gameMenu = """
            \nGame functionality coming soon!
            \t1. Go back
            \t2. Quit
            """;


    public MenuState run() {
        MenuState result = MenuState.GAME;
        System.out.print(gameMenu);
        int input = MenuManager.getValidOption(numGameOptions);

        if (input == 1) {
            result = MenuState.POSTLOGIN;
        } else {
            System.out.print("Invalid option. Please try again.");
        }
        return result;
    }
}

