import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel implements ActionListener {
    private Player player;
    private JButton leftButton;
    private JButton rightButton;
    private JButton shotButton;

    public ButtonPanel(Player player) {
        this.player = player;
        setBackground(Color.BLACK);

        leftButton = new JButton("Left");
        rightButton = new JButton("Right");
        leftButton.setForeground(Color.WHITE);
        rightButton.setForeground(Color.WHITE);
        leftButton.setBackground(Color.BLACK);
        rightButton.setBackground(Color.BLACK);

        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        leftButton.setFont(buttonFont);
        rightButton.setFont(buttonFont);

        add(leftButton);
        add(rightButton);
        leftButton.addActionListener(this);
        rightButton.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == leftButton) {
            player.x -= 6;
        } else if (e.getSource() == rightButton) {
            player.x += 6;
        }
    }
}
