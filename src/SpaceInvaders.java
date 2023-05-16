//import javax.swing.*;
//
//public class SpaceInvaders extends JFrame {
//
//    public SpaceInvaders() {
//
//        initUI();
//    }
//
//    private void initUI() {
//
//        add(new Game());
//
//        setTitle("Space Invaders");
//        setSize(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
//
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setResizable(false);
//        setLocationRelativeTo(null);
//    }
//
//}

import javax.swing.JFrame;

public class SpaceInvaders extends JFrame  {

    public SpaceInvaders() {

        initUI();
    }

    private void initUI() {

        add(new Panel());

        setTitle("Space Invaders");
        setSize(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
