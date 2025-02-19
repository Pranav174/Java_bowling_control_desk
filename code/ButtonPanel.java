import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonPanel {
    private JPanel newPanel;
    private  JButton newButton;
    
    public ButtonPanel(String text, ControlDeskView cd){
        newPanel=new JPanel();
        newButton=new JButton(text);
        newPanel.setLayout(new FlowLayout());
        newButton.addActionListener((ActionListener) cd);
        newPanel.add(newButton);
    }

    public JButton getButton(){
        return  newButton;
    }

    public JPanel getPanel(){
        return newPanel;
    }
}
