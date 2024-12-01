import chess.*;
import ui.MenuManager;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Initializing local chess client...");
            MenuManager ui = new MenuManager("http://localhost:" + 8080);
            ui.run();
        } catch (Exception e) {
            System.out.println("UI failed to run: " + e.getMessage());
        }
    }
}