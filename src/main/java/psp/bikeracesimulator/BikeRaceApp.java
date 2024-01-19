package psp.bikeracesimulator;
import javax.swing.*;
import java.awt.*;

public class BikeRaceApp {
    private JFrame frame;

    public BikeRaceApp() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        BikeRacePanel racePanel = new BikeRacePanel(frame);
        mainPanel.add(racePanel.getPanel(), BorderLayout.CENTER);

        frame.setContentPane(mainPanel);
    }

    public JFrame getFrame() {
        return frame;
    }
}
