import javax.swing.*;
import java.awt.event.KeyEvent;

public class Player extends Sprite {
    private int width;
    public Player() {
        initPlayer();
    }

    private void initPlayer() {

        ImageIcon playerImage = new ImageIcon(Constants.PLAYER_ICON);

        width = playerImage.getImage().getWidth(null);
        setImage(playerImage.getImage());
        setX(Constants.PLAYER_START_X);
        setY(Constants.PLAYER_START_Y);
    }

    public void act() {
        x += dx;
        if (x <= 2) {
            x = 2;
        }
        if (x >= Constants.BOARD_WIDTH - 2 * width) {
            x = Constants.BOARD_WIDTH - 2 * width;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }
    }
}