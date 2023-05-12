import java.awt.*;

public class Main {
//    https://zetcode.com/javagames/spaceinvaders/
    public static void main(String[] args) {
//        System.out.println("Hello world!");
////        SpaceInvaders screen = new SpaceInvaders();
////        Player player = new Player();
//////        screen.add(player)
////        screen.setVisible(true);

        EventQueue.invokeLater(() -> {

            var ex = new SpaceInvaders();
            ex.setVisible(true);
        });
    }
}