package Controller;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import Model.LMod;

public class Button extends JButton
{
    private ImageIcon imageIcon;
    private String status;
    private String hint;
    private ActionListener actionListener;
    private LMod model = LCon.model;

    public Button(ImageIcon imageIcon, String hint, String status, ActionListener actionListener)
    {
        this.imageIcon = imageIcon;
        this.hint = hint;
        this.status = status;
        this.actionListener = actionListener;
    }

    public JButton newButton()
    {
        JButton button = new JButton(imageIcon);
        button.setToolTipText(hint);
        button.addActionListener(actionListener);
        button.addMouseListener(mouseListener);

        return button;
    }

    private MouseListener mouseListener = new MouseListener()
    {
        public void mouseReleased(MouseEvent e)
        {
        }

        public void mousePressed(MouseEvent e)
        { 
        }

        public void mouseExited(MouseEvent e)
        {
            setStatusBar(")");
        }

        public void mouseEntered(MouseEvent e)
        {
            setStatusBar(status);
        }

        public void mouseClicked(MouseEvent e)
        {
        }
    };

    private void setStatusBar(String string)
    {
        model.setStatus(string);
    }
}
