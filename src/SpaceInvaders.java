import javax.swing.*;

public class SpaceInvaders extends JFrame {

    public SpaceInvaders() {

        initUI();
    }

    private void initUI() {

        add(new Game());

        setTitle("Space Invaders");
        setSize(1200, 800);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

}
