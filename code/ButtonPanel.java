import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

<<<<<<< HEAD
public abstract class ButtonPanel implements ActionListener {
    private JPanel newPanel;
    private  JButton newButton;
    public ButtonPanel(String text){
        newPanel=new JPanel();
        newButton=new JButton(text);
        newPanel.setLayout(new FlowLayout());
        newButton.addActionListener(this);
=======
public class ButtonPanel {
    private JPanel newPanel;
    private  JButton newButton;
    
    public ButtonPanel(String text, ControlDeskView cd){
        newPanel=new JPanel();
        newButton=new JButton(text);
        newPanel.setLayout(new FlowLayout());
        newButton.addActionListener((ActionListener) cd);
>>>>>>> newFeatures
        newPanel.add(newButton);
    }

    public JButton getButton(){
        return  newButton;
    }

    public JPanel getPanel(){
        return newPanel;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> newFeatures
