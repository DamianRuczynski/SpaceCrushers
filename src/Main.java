import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Thread gameThread = new Thread(() -> {
            try {
                System.out.println("Game is loading...");
                Thread.sleep(4000);
                GameConfig game = new GameConfig();
                game.configureGame();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        gameThread.start();
    }
}
