package ui;

public class GameMenu {

    private final String postloginMenu = """
            You are not logged in. What would you like to do?
            \t1. Logout
            \t2. List Games
            \t3. Create Game
            \t4. Play Game
            \t5. Observe Game
            \t6. Help
            """;


    public MenuState run() {
        return MenuState.POSTLOGIN;
    }
}
