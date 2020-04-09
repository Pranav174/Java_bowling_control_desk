import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class ButtonPanel implements ActionListener {
    private JPanel newPanel;
    private  JButton newButton;
    public ButtonPanel(String text){
        newPanel=new JPanel();
        newButton=new JButton(text);
        newPanel.setLayout(new FlowLayout());
        newButton.addActionListener(this);
        newPanel.add(newButton);
    }

    public JButton getButton(){
        return  newButton;
    }

    public JPanel getPanel(){
        return newPanel;
    }
}
