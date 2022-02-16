import menu.Menu;
public class Launcher {
    // Launcher-class -> launches the menu
    public static void main(String[] args) {

        // TODO: find out which java version is needed and add to print-output
        System.err.println("Oracle OpenJDK version 17.0.2 needed");

        // defining game-version (which is shown in title)
        new Menu("V 1.3.1 - Facharbeit Pre-Release");
    }
}