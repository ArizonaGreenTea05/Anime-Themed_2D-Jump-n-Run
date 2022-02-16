import menu.Menu;
public class Launcher {
    // Launcher-class -> launches the menu
    public static void main(String[] args) {

        System.err.println("requirements: \n  JRE 1.8.0_321-b07 \n  JDK 17.0.2");

        // defining game-version (which is shown in title)
        new Menu("V 1.3.1 - Facharbeit Pre-Release");
    }
}