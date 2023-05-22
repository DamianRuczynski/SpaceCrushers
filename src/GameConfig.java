import javax.swing.*;

public class GameConfig {

    public static void configureGame() {
        String nickname = JOptionPane.showInputDialog("Enter your nickname:");
        String[] difficultyOptions = { "1", "2", "3" };
        int selectedDifficulty = JOptionPane.showOptionDialog(null, "Choose difficulty level:", "Difficulty Level",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, difficultyOptions, difficultyOptions[0]);
        int difficultyLevel = selectedDifficulty + 1;

        Constants.setDifficultyLevel(difficultyLevel);

        Panel panel = new Panel();
        panel.setNickname(nickname);

        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setTitle("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
