import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DisplayWindow {
    private JFrame win;
    private JPanel colPanel;
    public DisplayWindow(JFrame newFrame, JPanel newPanel, boolean addWinListener){
        win=newFrame;
        colPanel=newPanel;
        win.getContentPane().add("Center", colPanel);

        win.pack();

        /* Close program when this window closes */
        if(addWinListener)
        {
            win.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
            });
        }

        // Center Window on Screen
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(
                ((screenSize.width) / 2) - ((win.getSize().width) / 2),
                ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.show();
    }
}
