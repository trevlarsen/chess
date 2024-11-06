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
}
