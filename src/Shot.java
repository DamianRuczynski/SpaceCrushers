import javax.swing.ImageIcon;

public class Shot extends Sprite {

    public Shot() {
    }

    public Shot(int x, int y) {
        initShot(x, y);
    }

    private void initShot(int x, int y) {
        ImageIcon shotImage = new ImageIcon(Constants.SHOT_ICON);
        setImage(shotImage.getImage());
        int H_SPACE = 6;
        setX(x + H_SPACE);
        int V_SPACE = 1;
        setY(y - V_SPACE);
    }
}