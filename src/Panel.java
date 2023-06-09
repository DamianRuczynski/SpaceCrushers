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
    private int score = 0;
    private int direction = -1;
    private int deaths = 0;
    private String message = "Game Over";
    private String explosionImage = Constants.EXPLOSION_ICON;
    private String nickname;
    private Dimension dimension;
    private List<Alien> aliens;
    private Player player;
    ;
    private Shot shot;

    private Timer timer;
    private GameMenu gameMenu;


    public Panel() {
        this.initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        dimension = new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        setBackground(Color.black);
        gameMenu = new GameMenu();

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

        JButton shotButton = new JButton("Shot");
        shotButton.addActionListener(new ShotButtonListener());
        add(shotButton);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


//DRAWING

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        drawScore(g);
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

    private void drawScore(Graphics g) {
        String scoreText = "Score: " + score;
        Font font = new Font("Arial", Font.BOLD, 16);
        g.setFont(font);
        g.setColor(Color.white);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = getWidth() - metrics.stringWidth(scoreText) - 10;
        int y = 20;
        g.drawString(scoreText, x, y);
    }
//    GAME OVER

    private void gameOver(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, Constants.BOARD_WIDTH / 2 - 30, Constants.BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, Constants.BOARD_WIDTH / 2 - 30, Constants.BOARD_WIDTH - 100, 50);
        g.drawString(message, (Constants.BOARD_WIDTH - 30) / 2,
                Constants.BOARD_WIDTH / 2);

        int dialogResult = JOptionPane.showConfirmDialog(
                this,
                "Do you want to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION
        );

        if (dialogResult == JOptionPane.YES_OPTION) {
            startNewGame();
        } else {
            showTopTenScores();
            setVisible(false);
        }
    }

    private void startNewGame() {
        score = 0;
        deaths = 0;
        message = "Game Over";
        explosionImage = Constants.EXPLOSION_ICON;
        inGame = true;
        timer.start();
        gameInit();
    }
    private void showMenu() {
        timer.stop();
        gameMenu.setVisible(true);
        if (!gameMenu.isOptionSelected()) {
            timer.start();
        }
    }

    private void showTopTenScores() {
        List<ScoreEntry> topTenScores = BestPlayerList.getTopTenScores();

        JFrame scoresFrame = new JFrame("Top 10 Scores");
        scoresFrame.setSize(300, 300);

        JPanel scoresPanel = new JPanel();
        scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.Y_AXIS));

        JLabel userScore = new JLabel();
        userScore.setText("Your Score: " + score);
        scoresPanel.add(userScore);

        JLabel titleLabel = new JLabel("Top 10 Scores:");
        scoresPanel.add(titleLabel);


        topTenScores.stream()
                .map(user -> (topTenScores.indexOf(user) + 1) + ". " + user.getNickname() + " - " + user.getScore())
                .forEach(scoreText -> {
                    JLabel scoreLabel = new JLabel(scoreText);
                    scoresPanel.add(scoreLabel);
                });

        scoresFrame.getContentPane().add(scoresPanel);
        scoresFrame.setLocationRelativeTo(null);
        scoresFrame.setVisible(true);
        scoresFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    // ON GAME METHODS

    private void doGameCycle() {
        update();
        repaint();
    }
    private void update() {
        if (deaths == Constants.NUMBER_OF_ALIENS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "There is a winner. Congratulations!";
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
                        score++;
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
        checkAlienBoundary(aliens);


        checkAlienPosition(aliens);

        for (Alien alien : aliens) {
           dropBomb(alien);
        }

        checkTopTen();
    }

    private void checkTopTen() {
        if (score >= Constants.SCORE_THRESHOLD) {
            BestPlayerList.addScore(nickname, score);
            JOptionPane.showMessageDialog(this, "Congratulations! You made it to the top 10!");
        }
    }

//    ALIEN MOVEMENT AND ACTING

    private void checkAlienPosition(List<Alien> aliens) {
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
    }

    private void dropBomb(Alien alien) {
        int shot = (int) (Math.random() * 50);
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
                ImageIcon explosion = new ImageIcon(explosionImage);
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

    private void checkAlienBoundary(List<Alien> aliens) {
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
    }

    private void moveAliensDown(List<Alien> aliens) {
        Iterator<Alien> aliensIterator = aliens.iterator();
        while (aliensIterator.hasNext()) {
            Alien alien = aliensIterator.next();
            alien.setY(alien.getY() + Constants.GO_DOWN);
        }
    }

    //GAME STEERAGE METHODS



    private void fireShot() {
        int x = player.getX();
        int y = player.getY();
        if (inGame) {
            if (!shot.isVisible()) {
                shot = new Shot(x, y);
            }
        }
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
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                fireShot();
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                showMenu();
            }
        }
    }

    private class ShotButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fireShot();
        }
    }

    private class GameMenu extends JFrame {
        private boolean optionSelected = false;

        public GameMenu() {
            setTitle("Menu");
            setSize(300, 200);
            setLayout(new BorderLayout());
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            JButton resumeButton = new JButton("Resume Game");
            JButton rulesButton = new JButton("Show Rules");
            JButton restartButton = new JButton("Restart Game");
            JButton exitButton = new JButton("Exit");

            resumeButton.addActionListener(e -> {
                optionSelected = true;
                setVisible(false);
                timer.start();
            });

            rulesButton.addActionListener(e -> {
                optionSelected = true;
                JOptionPane.showMessageDialog(this, Constants.GAME_RULES);
                setVisible(false);
                timer.start();
            });

            restartButton.addActionListener(e -> {
                optionSelected = true;
                startNewGame();
                setVisible(false);
            });

            exitButton.addActionListener(e -> {
                optionSelected = true;
                showTopTenScores();
                setVisible(false);
            });

            JPanel menuPanel = new JPanel();
            menuPanel.setLayout(new GridLayout(4, 1));
            menuPanel.add(resumeButton);
            menuPanel.add(rulesButton);
            menuPanel.add(restartButton);
            menuPanel.add(exitButton);

            add(menuPanel, BorderLayout.CENTER);
        }

        public boolean isOptionSelected() {
            return optionSelected;
        }
    }


}