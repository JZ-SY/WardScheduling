package ui;
import java.awt.*;
import hospital.HospitalInformation;
import services.Scheduler;
import javax.swing.*;

public class WardFrame extends JFrame {
    private final Scheduler scheduler;
    // 8 个 bay
    private final JLabel[] labels = new JLabel[HospitalInformation.bays.length];

    public WardFrame(Scheduler scheduler) {
        super("ICU Ward Services");
        this.scheduler = scheduler;
        // 条件:题目说 2×4
        setLayout(new GridLayout(2, 4, 5, 5));

        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel("", SwingConstants.CENTER);
            // JLabel 要能变色必须 opaque
            labels[i].setOpaque(true);
            labels[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
            add(labels[i]);
        }

        setSize(900, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        refresh();
    }

    // Encapsulation: 外部只调这个
    public void refresh(){
        for (int i = 0; i < labels.length; i++) {
            String bay = HospitalInformation.bays[i];
            labels[i].setText(scheduler.describeBay(bay));
            labels[i].setBackground(scheduler.bayBusy(bay) ? new Color(255, 225, 190) : Color.WHITE);
        }
        repaint();
    }
}

