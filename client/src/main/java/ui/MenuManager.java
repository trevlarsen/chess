package ui;

public class MenuManager {
    private MenuState currentState;
    private PreloginMenu preloginMenu;
    private PostloginMenu postloginMenu;
    private GameMenu gameMenu;

    public MenuManager() {
        currentState = MenuState.PRELOGIN;
        preloginMenu = new PreloginMenu();
        postloginMenu = new PostloginMenu();
        gameMenu = new GameMenu();
    }

    public void run() {
        while (true) {
            switch (currentState) {
                case PRELOGIN:
                    if (preloginMenu.run() == MenuState.POSTLOGIN) {
                        currentState = MenuState.POSTLOGIN;
                    }
                    break;
                case POSTLOGIN:
                    // Similar pattern for postlogin and game
                    break;
            }
        }
    }
}
