import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Panel extends JPanel {
    private boolean inGame = true;
    private int direction = -1;
    private int deaths = 0;
    private String message = "Game Over";
    private String explosionImage = Constants.EXPLOSION_ICON;
    private Dimension dimension;
    private List<Alien> aliens;
    private Player player;
    private Shot shot;

    private Timer timer;


    public Panel() {
        this.initBoard();
//        this.gameInit();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        dimension = new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        setBackground(Color.black);




        timer = new Timer(Constants.DELAY, new GameCycle());
        timer.start();
        this.gameInit();
    }


    private void gameInit() {
        aliens = new ArrayList<>();
        for (int i = 0; i < Constants.LINES_OF_ENEMIES; i++) {
            for (int j = 0; j < Constants.COLUMNS_OF_ENEMIES; j++) {
                Alien alien = new Alien(Constants.ALIEN_INIT_X + 18 * j,
                        Constants.ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);
            }
        }
        player = new Player();
        shot = new Shot();

        ButtonPanel buttonField = new ButtonPanel(player);
        add(buttonField);
    }

    private void drawAliens(Graphics g) {
        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
            if (alien.isDying()) {
                alien.die();
            }
        }
    }

    private void drawPlayer(Graphics g) {
        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }
        if (player.isDying()) {
            player.die();
            inGame = false;
        }
    }

    private void drawShot(Graphics g) {
        if (shot.isVisible()) {
            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    private void drawBombing(Graphics g) {
        for (Alien a : aliens) {
            Alien.Bomb b = a.getBomb();
            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, dimension.width, dimension.height);
        g.setColor(Color.green);
        if (inGame) {
            g.drawLine(0, Constants.GROUND_Y,
                    Constants.BOARD_WIDTH, Constants.GROUND_Y);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
        } else {
            if (timer.isRunning()) {
                timer.stop();
            }
            gameOver(g);
        }
//        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, Constants.BOARD_WIDTH / 2 - 30, Constants.BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Constants.BOARD_WIDTH / 2 - 30, Constants.BOARD_WIDTH - 100, 50);
        g.drawString(message, (Constants.BOARD_WIDTH - 30) / 2,
                Constants.BOARD_WIDTH / 2);
    }

    private void update() {
        if (deaths == Constants.NUMBER_OF_ALIENS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Game won!";
        }
        player.act();
        if (shot.isVisible()) {
            int shotX = shot.getX();
            int shotY = shot.getY();
            for (Alien alien : aliens) {
                int alienX = alien.getX();
                int alienY = alien.getY();
                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + Constants.ALIEN_WIDTH)
                            && shotY >= (alienY)
                            && shotY <= (alienY + Constants.ALIEN_HEIGHT)) {
                        ImageIcon explosion = new ImageIcon(explosionImage);
                        alien.setImage(explosion.getImage());
                        alien.setDying(true);
                        deaths++;
                        shot.die();
                    }
                }
            }
            int shotPosition = shot.getY();
            shotPosition -= 4;
            if ((shotPosition < 0)) {
                shot.die();
            } else {
                shot.setY(shotPosition);
            }
        }
        for (Alien alien : aliens) {
            int x = alien.getX();
            if (x >= (Constants.BOARD_WIDTH - Constants.BORDER_RIGHT) && direction != -1) {
                direction = -1;
                moveAliensDown(aliens);
            }
            if (x <= Constants.BORDER_LEFT && direction != 1) {
                direction = 1;
                moveAliensDown(aliens);
            }
        }

        Iterator<Alien> iterator = aliens.iterator();
        while (iterator.hasNext()) {
            Alien alien = iterator.next();
            if (alien.isVisible()) {
                int y = alien.getY();
                if (y > Constants.GROUND_Y - Constants.ALIEN_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                }
                alien.act(direction);
            }
        }
        Random bombPositionGenerator = new Random();
        for (Alien alien : aliens) {
            int shot = bombPositionGenerator.nextInt(50);
            Alien.Bomb bomb = alien.getBomb();
            if (shot == Constants.CHANCE && alien.isVisible() && bomb.isDestroyed()) {
                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }
            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = player.getX();
            int playerY = player.getY();
            if (player.isVisible() && !bomb.isDestroyed()) {
                if (bombX >= (playerX)
                        && bombX <= (playerX + Constants.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Constants.PLAYER_HEIGHT)) {
                    var explosion = new ImageIcon(explosionImage);
                    player.setImage(explosion.getImage());
                    player.setDying(true);
                    bomb.setDestroyed(true);
                }
            }
            if (!bomb.isDestroyed()) {
                bomb.setY(bomb.getY() + 1);
                if (bomb.getY() >= Constants.GROUND_Y - Constants.BOMB_HEIGHT) {
                    bomb.setDestroyed(true);
                }
            }
        }
    }

    private void moveAliensDown(List<Alien> aliens) {
        Iterator<Alien> aliensIterator = aliens.iterator();
        while (aliensIterator.hasNext()) {
            Alien alien = aliensIterator.next();
            alien.setY(alien.getY() + Constants.GO_DOWN);
        }
    }

    private void doGameCycle() {
        update();
        repaint();
    }

    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
            int x = player.getX();
            int y = player.getY();
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                if (inGame) {
                    if (!shot.isVisible()) {
                        shot = new Shot(x, y);
                    }
                }
            }
        }
    }
}