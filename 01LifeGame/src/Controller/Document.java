package Controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Model.LMod;

public class Document
{
    JFrame newGameFrame;
    LMod model = LCon.model;
    JTextField widthField, heightField;
    JButton ok, cancel;
    Set<JTextField> okPress = new LinkedHashSet<JTextField>();
    JFrame frame;

    public Document(JFrame frame)
    {
        this.frame = frame;
    }

    public void changeSize()
    {
        newGameFrame = new JFrame();

        newGameFrame.setLayout(new BorderLayout());

        JDialog newGameDialog = new JDialog(newGameFrame, "Field Size", true);
        newGameDialog.setPreferredSize(new Dimension(250, 120));
        JPanel sizePanel = new JPanel();
        JLabel widthLabel = new JLabel("Weight ");
        widthField = new JTextField(4);
        widthField.setText(String.valueOf(model.getWidth()));
        widthLabel.setLabelFor(widthField);
        widthField.addKeyListener(new KeyListener()
        {
            public void keyReleased(KeyEvent arg0)
            {
                int len = widthField.getText().length();

                if(len == 0)
                {
                    ok.setEnabled(false);
                    okPress.add(widthField);
                }
                if(len > 0)
                {
                    okPress.remove(widthField);
                    if(okPress.size() == 0)
                    {
                        ok.setEnabled(true);
                    }
                }
            }

            public void keyPressed(KeyEvent arg0)
            {
            }

            public void keyTyped(KeyEvent e)
            {
                char a = e.getKeyChar();
                int len = widthField.getText().length();

                if((a == '0') && (len == 0))
                {
                    e.consume();
                }
                else if((!Character.isDigit(a)) || (len > 2))
                {
                    e.consume();
                }
            }
        });

        JLabel heightLabel = new JLabel("Height   ");
        heightField = new JTextField(4);
        heightField.setText(String.valueOf(model.getHeight()));
        heightLabel.setLabelFor(heightField);
        heightField.addKeyListener(new KeyListener()
        {
            public void keyReleased(KeyEvent arg0)
            {
                int len = heightField.getText().length();

                if(len == 0)
                {
                    ok.setEnabled(false);
                    okPress.add(heightField);
                }
                if(len > 0)
                {
                    okPress.remove(heightField);
                    if(okPress.size() == 0)
                    {
                        ok.setEnabled(true);
                    }
                }
            }

            public void keyPressed(KeyEvent arg0)
            {
            }

            public void keyTyped(KeyEvent e)
            {
                char a = e.getKeyChar();
                int len = heightField.getText().length();

                if((a == '0') && (len == 0))
                {
                    e.consume();
                }
                else if((!Character.isDigit(a)) || (len > 2))
                {
                    e.consume();
                }
            }
        });

        Box widthBox = Box.createHorizontalBox();
        widthBox.add(widthLabel);
        widthBox.add(Box.createHorizontalStrut(6));
        widthBox.add(widthField);

        Box heightBox = Box.createHorizontalBox();
        heightBox.add(heightLabel);
        heightBox.add(Box.createHorizontalStrut(6));
        heightBox.add(heightField);

        Box sizeBox = Box.createVerticalBox();

        sizeBox.add(widthBox);
        sizeBox.add(Box.createVerticalStrut(18));
        sizeBox.add(heightBox);

        sizePanel.add(sizeBox);
        sizePanel.setBorder(new EmptyBorder(12, 12, 12, 12));

        GridLayout gl = new GridLayout(2, 1);
        gl.setVgap(6);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(gl);

        ok = new JButton("OK");

        cancel = new JButton("Cancel");
        ok.setPreferredSize(cancel.getPreferredSize());
        buttonsPanel.add(ok);

        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                int w = Integer.parseInt(widthField.getText());
                int h = Integer.parseInt(heightField.getText());

                if(h > 100 || w > 100)
                {
                    model.setMessage("The maximum field size: 100 x 100.");
                }
                else
                {
                    model.newSettings(w, h, 1, 20,model.LIVE_BEGIN, model.LIVE_END, model.BIRTH_BEGIN, model.BIRTH_END, model.FST_IMPACT, model.SND_IMPACT);

                    newGameFrame.setVisible(false);
                }
            }
        });

        buttonsPanel.add(cancel);

        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                newGameFrame.setVisible(false);
            }
        });

        buttonsPanel.setBorder(new EmptyBorder(12, 12, 12, 12));

        Box mainBox = Box.createHorizontalBox();
        mainBox.add(sizePanel);
        mainBox.add(Box.createHorizontalStrut(6));
        mainBox.add(buttonsPanel);

        newGameDialog.add(mainBox);
        newGameDialog.setResizable(false);
        newGameDialog.pack();
        newGameDialog.setLocationRelativeTo(frame);
        newGameDialog.setVisible(true);
    }
}
