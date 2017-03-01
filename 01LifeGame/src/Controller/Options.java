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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Model.LMod;

public class Options
{
    JTextField borderField, lengthField, widthField, heightField, PLIVE_BEGIN, PLIVE_END, PBIRTH_BEGIN, PBIRTH_END, PFST_IMPACT, PSND_IMPACT ;
    JSlider borderSlider, lengthSlider;
    JFrame optionFrame;

    JRadioButton replaceRadioButton, xorRadioButton;
    JButton ok, cancel;
    int mode;
    LMod model = LCon.model;
    JFrame frame;

    Set<JTextField> okPress = new LinkedHashSet<JTextField>();

    public Options(int mode, JFrame frame)
    {
        this.mode = mode;
        this.frame = frame;
    }

    public void checkOptions()
    {
        optionFrame = new JFrame();
        optionFrame.setPreferredSize(new Dimension(900, 100));
        optionFrame.setLayout(new BorderLayout());

        JDialog optionsDialog = new JDialog(optionFrame, "Options", true);

        ButtonGroup group = new ButtonGroup();

        JPanel sizePanel = new JPanel();
        JLabel widthLabel = new JLabel("Ширина ");
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

                if((a == '0') && (len == 0)) e.consume();
                else if((!Character.isDigit(a)) || (len > 2)) e.consume();
            }
        });

        JLabel heightLabel = new JLabel("Высота  ");
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
        sizeBox.add(Box.createVerticalStrut(6));
        sizeBox.add(heightBox);

        sizePanel.add(sizeBox);
        sizePanel.setBorder(BorderFactory.createTitledBorder("параметры поя"));

        /////////////////////////////////////////////////
        
        
        
        
        
        
        
        
        
        
        
        
        JPanel sizePar = new JPanel();
        JLabel LIVE_BEGIN = new JLabel("LIVE_BEGIN ");
        PLIVE_BEGIN = new JTextField(4);
        PLIVE_BEGIN.setText(String.valueOf((double) model.getLIVE_BEGIN()));
        LIVE_BEGIN.setLabelFor(PLIVE_BEGIN);
        PLIVE_BEGIN.addKeyListener(new KeyListener()
        {
            public void keyReleased(KeyEvent arg0)
            {
                int len = PLIVE_BEGIN.getText().length();

                if(len == 0)
                {
                    ok.setEnabled(false);
                    okPress.add(PLIVE_BEGIN);
                }
                if(len > 0)
                {
                    okPress.remove(PLIVE_BEGIN);
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
                int len = PLIVE_BEGIN.getText().length();

                if((a == '0') && (len == 0)) e.consume();
                else if((!Character.isDigit(a)) || (len > 2)) e.consume();
            }
        });

        
        
        
        JLabel BIRTH_BEGIN = new JLabel("BIRTH_BEGIN ");
        PBIRTH_BEGIN = new JTextField(4);
        PBIRTH_BEGIN.setText(String.valueOf((double) model.getBIRTH_BEGIN()));
        BIRTH_BEGIN.setLabelFor(BIRTH_BEGIN);
        PBIRTH_BEGIN.addKeyListener(new KeyListener()
        {
            public void keyReleased(KeyEvent arg0)
            {
                int len = PBIRTH_BEGIN.getText().length();

                if(len == 0)
                {
                    ok.setEnabled(false);
                    okPress.add(PBIRTH_BEGIN);
                }
                if(len > 0)
                {
                    okPress.remove(PBIRTH_BEGIN);
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
                int len = PBIRTH_BEGIN.getText().length();

                if((a == '0') && (len == 0)) e.consume();
                else if((!Character.isDigit(a)) || (len > 2)) e.consume();
            }
        });
        
        
        
        
        
        
        JLabel BIRTH_END = new JLabel("BIRTH_END ");
        PBIRTH_END = new JTextField(4);
        PBIRTH_END.setText(String.valueOf((double) model.getBIRTH_END()));
        BIRTH_END.setLabelFor(PBIRTH_END);
        PBIRTH_END.addKeyListener(new KeyListener()
        {
            public void keyReleased(KeyEvent arg0)
            {
                int len = PBIRTH_END.getText().length();

                if(len == 0)
                {
                    ok.setEnabled(false);
                    okPress.add(PBIRTH_END);
                }
                if(len > 0)
                {
                    okPress.remove(PBIRTH_END);
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
                int len = PBIRTH_END.getText().length();

                if((a == '0') && (len == 0)) e.consume();
                else if((!Character.isDigit(a)) || (len > 2)) e.consume();
            }
        });
        
        
        
        
        
        
        
        JLabel FST_IMPACT = new JLabel("FST_IMPACT ");
        PFST_IMPACT = new JTextField(4);
        PFST_IMPACT.setText(String.valueOf((double) model.getFST_IMPACT()));
        FST_IMPACT.setLabelFor(PFST_IMPACT);
        PFST_IMPACT.addKeyListener(new KeyListener()
        {
            public void keyReleased(KeyEvent arg0)
            {
                int len = PFST_IMPACT.getText().length();

                if(len == 0)
                {
                    ok.setEnabled(false);
                    okPress.add(PFST_IMPACT);
                }
                if(len > 0)
                {
                    okPress.remove(PFST_IMPACT);
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
                int len = PFST_IMPACT.getText().length();

                if((a == '0') && (len == 0)) e.consume();
                else if((!Character.isDigit(a)) || (len > 2)) e.consume();
            }
        });
        
        
        
        
        
        
        
        
        JLabel SND_IMPACT = new JLabel("SND_IMPACT ");
        PSND_IMPACT = new JTextField(4);
        PSND_IMPACT.setText(String.valueOf((double) model.getSND_IMPACT()));
        SND_IMPACT.setLabelFor(PSND_IMPACT);
        PSND_IMPACT.addKeyListener(new KeyListener()
        {
            public void keyReleased(KeyEvent arg0)
            {
                int len = PSND_IMPACT.getText().length();

                if(len == 0)
                {
                    ok.setEnabled(false);
                    okPress.add(PSND_IMPACT);
                }
                if(len > 0)
                {
                    okPress.remove(PSND_IMPACT);
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
                int len = PSND_IMPACT.getText().length();

                if((a == '0') && (len == 0)) e.consume();
                else if((!Character.isDigit(a)) || (len > 2)) e.consume();
            }
        });
        
        
        
        
        
        
        
        
        
        JLabel LIVE_END = new JLabel("LIVE_END  ");
        PLIVE_END = new JTextField(4);
        PLIVE_END.setText(String.valueOf((double) model.getLIVE_END()));
        LIVE_END.setLabelFor(PLIVE_END);
        PLIVE_END.addKeyListener(new KeyListener()
        {
            public void keyReleased(KeyEvent arg0)
            {
                int len = PLIVE_END.getText().length();

                if(len == 0)
                {
                    ok.setEnabled(false);
                    okPress.add(PLIVE_END);
                }
                if(len > 0)
                {
                    okPress.remove(PLIVE_END);
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
                int len = PLIVE_END.getText().length();

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

        Box libe = Box.createHorizontalBox();
        widthBox.add(LIVE_BEGIN);
        widthBox.add(Box.createHorizontalStrut(6));
        widthBox.add(PLIVE_BEGIN);

        Box lien = Box.createHorizontalBox();
        heightBox.add(LIVE_END);
        heightBox.add(Box.createHorizontalStrut(6));
        heightBox.add(PLIVE_END);

        Box lienn = Box.createHorizontalBox();
        heightBox.add(BIRTH_BEGIN);
        heightBox.add(Box.createHorizontalStrut(6));
        heightBox.add(PBIRTH_BEGIN);

        Box liennn = Box.createHorizontalBox();
        heightBox.add(BIRTH_END);
        heightBox.add(Box.createHorizontalStrut(6));
        heightBox.add(PBIRTH_END);
        
        Box l = Box.createHorizontalBox();
        heightBox.add(FST_IMPACT);
        heightBox.add(Box.createHorizontalStrut(6));
        heightBox.add(PFST_IMPACT);
        
        Box ll = Box.createHorizontalBox();
        heightBox.add(SND_IMPACT);
        heightBox.add(Box.createHorizontalStrut(6));
        heightBox.add(PSND_IMPACT);
        Box Boxx = Box.createVerticalBox();
        
        sizeBox.add(libe);
        sizeBox.add(Box.createVerticalStrut(6));
        sizeBox.add(lien);
        sizeBox.add(Box.createVerticalStrut(6));
        sizeBox.add(lienn);
        sizeBox.add(Box.createVerticalStrut(6));
        sizeBox.add(liennn);
        sizeBox.add(Box.createVerticalStrut(6));
        sizeBox.add(l);
        sizeBox.add(Box.createVerticalStrut(6));
        sizeBox.add(ll);

        sizePar.add(Boxx);

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        /////////////////////////////////////////////////
        
        JPanel cellPanel = new JPanel();
        cellPanel.setBorder(BorderFactory.createTitledBorder("шестиугольник"));

        JLabel depthLabel = new JLabel("Граница                                   ");
        borderField = new JTextField(4);
        borderField.setText(String.valueOf((int) model.getBorder()));
        depthLabel.setLabelFor(borderField);
        borderSlider = new JSlider(1, 5, (int)model.getBorder());
        borderSlider.setMajorTickSpacing(1);
        borderSlider.setPaintTicks(true);
        borderSlider.setSnapToTicks(true);
        borderSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                borderField.setText(String.valueOf(borderSlider.getValue()));

                if (okPress.contains(lengthField)) {
                    okPress.remove(borderField);
                }
                if (okPress.size() == 0) {
                    ok.setEnabled(true);
                }
            }
        });

        borderField.addKeyListener(new KeyListener() {
            public void keyReleased(KeyEvent arg0) {
                int len = borderField.getText().length();

                if (len == 0) {
                    ok.setEnabled(false);
                    okPress.add(borderField);
                }
                if (len > 0) {
                    okPress.remove(borderField);
                    if (okPress.size() == 0) {
                        ok.setEnabled(true);
                    }
                }
            }

            public void keyPressed(KeyEvent arg0) {
            }

            public void keyTyped(KeyEvent e) {
                char a = e.getKeyChar();
                int len = borderField.getText().length();

                if ((a == '0') && (len == 0)) {
                    e.consume();
                } else if ((!Character.isDigit(a)) || (len > 0)) {
                    e.consume();
                }
            }
        });

        JLabel lengthLabel = new JLabel("размер шестиугольников  ");
        lengthField = new JTextField(4);
        lengthField.setText(String.valueOf(model.getLength()));
        depthLabel.setLabelFor(lengthField);
        lengthSlider = new JSlider(3, 100, model.getLength());
        lengthSlider.setMajorTickSpacing(10);
        lengthSlider.setPaintTicks(true);
        lengthSlider.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent event)
            {
                lengthField.setText(String.valueOf(lengthSlider.getValue()));

                if(okPress.contains(lengthField))
                {
                    okPress.remove(lengthField);
                }
                if(okPress.size() == 0)
                {
                    ok.setEnabled(true);
                }
            }
        });

        lengthField.addKeyListener(new KeyListener()
        {
            public void keyReleased(KeyEvent arg0)
            {
                int len = lengthField.getText().length();

                if((len == 0) || (lengthField.getText().equals("1")) || (lengthField.getText().equals("2")))
                {
                    ok.setEnabled(false);
                    okPress.add(lengthField);
                }
                else if(len > 0)
                {
                    okPress.remove(lengthField);
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
                int len = lengthField.getText().length();

                if((a == '0') && (len == 0))
                {
                    e.consume();
                }
                else if((!Character.isDigit(a)) || (len > 1))
                {
                    e.consume();
                }
            }
        });

        GridLayout gl = new GridLayout(2, 1);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(gl);

        ok = new JButton("OK");

        cancel = new JButton("Cancel");
        ok.setPreferredSize(cancel.getPreferredSize());
        buttonsPanel.add(ok);

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int w = Integer.parseInt(widthField.getText());
                int h = Integer.parseInt(heightField.getText());
                int d = Integer.parseInt(borderField.getText());
                int l = Integer.parseInt(lengthField.getText());
//PLIVE_BEGIN, PLIVE_END, PBIRTH_BEGIN, PBIRTH_END, PFST_IMPACT, PSND_IMPACT
                double lb = Double.parseDouble(PLIVE_BEGIN.getText());
                double le = Double.parseDouble(PLIVE_END.getText());
                double bb = Double.parseDouble(PBIRTH_BEGIN.getText());
                double be = Double.parseDouble(PBIRTH_END.getText());
                double fi = Double.parseDouble(PFST_IMPACT.getText());
                double si = Double.parseDouble(PSND_IMPACT.getText());
                
                if(h > 100 || w > 100) {
                    model.setMessage("The maximum field size: 100 х 100.");
                } else {
                    model.newSettings(w, h, d, l, lb, le, bb, be, fi, si);

                    optionFrame.setVisible(false);
                }
            }
        });

        buttonsPanel.add(cancel);

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                optionFrame.setVisible(false);
            }
        });

        buttonsPanel.setBorder(new EmptyBorder(12, 12, 12, 12));

        Box depthBox = Box.createHorizontalBox();
        depthBox.add(depthLabel);
        depthBox.add(Box.createHorizontalStrut(6));
        depthBox.add(borderField);
        depthBox.add(Box.createHorizontalStrut(6));
        depthBox.add(borderSlider);

        Box lengthBox = Box.createHorizontalBox();
        lengthBox.add(lengthLabel);
        lengthBox.add(Box.createHorizontalStrut(6));
        lengthBox.add(lengthField);
        lengthBox.add(Box.createHorizontalStrut(6));
        lengthBox.add(lengthSlider);

        Box cellBox = Box.createVerticalBox();

        cellBox.add(depthBox);
        cellBox.add(Box.createVerticalStrut(6));
        cellBox.add(lengthBox);

        cellPanel.add(cellBox);

        Box upBox = Box.createHorizontalBox();
        upBox.add(sizePanel);
        upBox.add(Box.createHorizontalStrut(12));
        upBox.add(cellPanel);
        upBox.add(buttonsPanel);

        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(12, 12, 12, 12));
        mainBox.add(upBox);

        optionsDialog.add(mainBox);
        optionsDialog.setResizable(false);
        optionsDialog.pack();
        optionsDialog.setLocationRelativeTo(frame);
        optionsDialog.setVisible(true);
    }
}
