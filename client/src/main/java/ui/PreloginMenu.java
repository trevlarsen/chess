package ui;

public class PreloginMenu {

    private final String preloginMenu = """
            You are not logged in. What would you like to do?
            \t1. Login
            \t2. Register
            \t3. Help
            \t4. Quit
            """;


    public MenuState run() {
        return MenuState.PRELOGIN;
    }
}
