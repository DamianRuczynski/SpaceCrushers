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

        // Create buttons
        leftButton = new JButton("Left");
        rightButton = new JButton("Right");
        shotButton = new JButton("Shot");

        // Set font color
        leftButton.setForeground(Color.WHITE);
        rightButton.setForeground(Color.WHITE);
        shotButton.setForeground(Color.WHITE);

        // Set button background color
        leftButton.setBackground(Color.BLACK);
        rightButton.setBackground(Color.BLACK);
        shotButton.setBackground(Color.BLACK);

        // Set font style
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        leftButton.setFont(buttonFont);
        rightButton.setFont(buttonFont);
        shotButton.setFont(buttonFont);

        // Add buttons to the panel
        add(shotButton);
        add(leftButton);
        add(rightButton);

        // Add action listeners
        leftButton.addActionListener(this);
        rightButton.addActionListener(this);
        shotButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == leftButton) {
            // Handle left button click
            player.x -= 2;
            // Implement your logic here
        } else if (e.getSource() == rightButton) {
            // Handle right button click
            player.x += 2;
            // Implement your logic here
        } else if (e.getSource() == shotButton) {
            // Handle shot button click
            // Implement your logic here
        }
    }
}
