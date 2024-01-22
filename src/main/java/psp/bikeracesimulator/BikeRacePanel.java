package psp.bikeracesimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

/**
 * Panel for the bike race simulation.
 */
public class BikeRacePanel {
    // Constants for team colors and names
    private static final Color YAMAHA_COLOR = new Color(3, 24, 98);
    private static final Color DUCATI_COLOR = new Color(254, 45, 20);
    private static final Color HONDA_COLOR = new Color(255, 119, 37);
    private static final Color SUZUKI_COLOR = new Color(37, 195, 255);

    private static final String[] TEAM_NAMES = {"Yamaha", "Ducati", "Honda", "Suzuki"};

    // Member variables for the panel and race components
    private JPanel panel;
    private JProgressBar[] motos = new JProgressBar[4];
    private JLabel msg = new JLabel("");
    private boolean startRaceIsPressed = false;
    private boolean resetRaceIsPressed = false;
    private boolean winner = false;


    /**
     * Constructor for the BikeRacePanel class.
     * @param frame the frame to which the panel belongs
     */
    public BikeRacePanel(JFrame frame) {
        panel = new JPanel(new GridLayout(5, 1));

        Color[] teamColors = {YAMAHA_COLOR, DUCATI_COLOR, HONDA_COLOR, SUZUKI_COLOR};

        for (int i = 0; i < motos.length; i++) {
            motos[i] = new JProgressBar(0, 100);
            motos[i].setStringPainted(true);
            motos[i].setForeground(teamColors[i]);
            panel.add(motos[i]);
        }

        msg.setVisible(false);
        panel.add(msg);

        JButton btnStart = new JButton("START");
        btnStart.setFont(new Font("Arial", Font.BOLD, 18));
        btnStart.addActionListener(new StartRace());
        panel.add(btnStart);

        JButton btnReset = new JButton("RESET");
        btnReset.setFont(new Font("Arial", Font.BOLD, 18));
        btnReset.addActionListener(new ResetRace());
        panel.add(btnReset);
    }

    /**
     * Getter for the panel.
     * @return the panel
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     *
     */

    private class StartRace implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!startRaceIsPressed) {
                msg.setVisible(false);
                resetRaceIsPressed = false;
                startRaceIsPressed = true;
                for (int i = 0; i < motos.length; i++) {
                    new Moto(motos[i], msg, i + 1).start();
                }
            }
        }
    }

    /**
     *
     * @param i
     * @param winnerMoto
     */

    private synchronized void endRace(int i, int winnerMoto) {
        msg.setVisible(true);
        msg.setText(TEAM_NAMES[winnerMoto - 1] + " ha ganado la carrera");
        msg.setFont(new Font("Arial", Font.BOLD, 18));
        if (i == 100) {
            winner = true;
        }
        SwingUtilities.invokeLater(() -> panel.add(msg));
    }

    /**
     *
     */

    private class ResetRace implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!resetRaceIsPressed) {
                msg.setVisible(false);
                resetRaceIsPressed = true;
                startRaceIsPressed = false;
                winner = false;
                for (int i = 0; i < motos.length; i++) {
                    new Moto(motos[i], msg, i + 1).reset();
                }
            }
        }
    }

    /**
     *
     */
    private class Moto extends Thread {

        private final JProgressBar moto;
        private final JLabel msg;
        private final int motoNumber;

        public Moto(JProgressBar moto, JLabel msg, int motoNumber) {
            this.moto = moto;
            this.msg = msg;
            this.motoNumber = motoNumber;
        }

        public void reset() {
            moto.setValue(0);
            moto.repaint();
        }

        public void run() {
            for (int i = 0; i <= 100; i++) {
                moto.setValue(i);
                moto.repaint();

                try {
                    Thread.sleep(Math.abs(UUID.randomUUID().getMostSignificantBits()) % 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (BikeRacePanel.this) {
                    if (winner) {
                        break;
                    }

                    if (i == 100) {
                        winner = true;
                        endRace(100, motoNumber);
                    }
                }
            }
        }
    }
}
