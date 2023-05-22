import javax.swing.*;
import java.awt.*;

public class Main {
//    https://zetcode.com/javagames/spaceinvaders/
    public static void main(String[] args) {
        SpaceInvaders game = new SpaceInvaders();
        game.setVisible(true);
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}