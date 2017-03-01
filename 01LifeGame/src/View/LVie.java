package View;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import Controller.LCon;
import Model.LMod;

public class LVie extends JPanel implements Observer
{
    public static int hexagonWIDTH;
    public static int hexagonHEIGHT;
    public static float BORDER;
    private static int LENGTH;
    private static int XWINDOW = 900;
    private static int YWINDOW = 600;
    public static BufferedImage bufferedImage;
    LCon controller = null;
    LMod model = null;

    private boolean about = false;

    Graphics2D g2;
    Drawing drawing;
    public static int len;
    JScrollPane sc;
    public static JPanel drawPanel;
    private static JLabel statusBarText;
    public static JFrame frame;
    int bufWidth, bufHeight;
    JMenuBar menu;
    JToolBar toolBar;
    JPanel statusPanel;


    public LVie(LMod model, LCon controller)
    {
        this.model = model;
        this.controller = controller;

        hexagonWIDTH = model.getWidth();
        hexagonHEIGHT = model.getHeight();
        BORDER = model.getBorder();
        LENGTH = model.getLength();

        len = (int)(LENGTH + BORDER / Math.sqrt(3));

        XWINDOW = (int)((hexagonWIDTH +1) * len * Math.sqrt(3) + BORDER);
        YWINDOW = (int)((hexagonHEIGHT + 1) * (len + len / 2) + len / 2 + BORDER);

        frame = new JFrame("Игра Жизнь");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawing = new Drawing();
        menu = controller.createMenuBar();
        frame.setJMenuBar(menu);

        drawPanel = new JPanel();
        drawPanel.setLayout(new BoxLayout(drawPanel, BoxLayout.LINE_AXIS));
        drawPanel.add(this);

        toolBar = controller.createToolBar();
        sc = new JScrollPane(drawPanel);
        statusPanel = new JPanel();
        statusBarText = new JLabel(")");
        statusPanel.add(statusBarText);

        toolBar.setRollover(true);
        frame.add(toolBar, BorderLayout.PAGE_START);

        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        frame.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 20));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));

        frame.add(sc);
        drawPanel.addMouseListener(controller);
        drawPanel.addMouseMotionListener(controller);
        drawPanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));

        frame.addComponentListener(new ComponentListener()
        {
            public void componentShown(ComponentEvent e)
            {
            }

            public void componentResized(ComponentEvent e)
            {
                SizeNew();
            }

            public void componentMoved(ComponentEvent e)
            {
            }

            public void componentHidden(ComponentEvent e)
            {
            }
        });

        frame.setPreferredSize(new Dimension(850, 625));

        frame.pack();
        frame.setVisible(true);

        bufferedImage = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);

        bufWidth = frame.getWidth();
        bufHeight = frame.getHeight();
    }

    public void update (Observable obs, Object obj)
    {
        repaint();

        if(model.msg != null)
        {
            JFrame msgFrame = new JFrame();
            JOptionPane optionPane = new JOptionPane(model.msg, JOptionPane.PLAIN_MESSAGE, JOptionPane.CLOSED_OPTION);

            JDialog dialog = optionPane.createDialog(msgFrame, "Error Message");
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
            model.setMessage(null);
        }

        if(model.aboutFlag && !about)
        {
            JFrame aboutFrame = new JFrame();
            about = true;

            Box mainBox = Box.createHorizontalBox();
            final ImageIcon icon = new ImageIcon("resources/i.jpg");
            JLabel aboutIcon = new JLabel(icon);
            mainBox.add(aboutIcon);
            mainBox.add(Box.createHorizontalStrut(12));
            JDialog dialog = new JDialog(aboutFrame, "?", true);
            dialog.setSize(550, 470);

            Box textBox = Box.createVerticalBox();
            textBox.add(new JLabel("<html>Это Игра<br><br><br>Елисеев Иван<br>группа 14204 ФИТ НГУ<br><br>2017</html>"));
            mainBox.setBorder(new EmptyBorder(12,12,0,12));

            mainBox.add(textBox);
            dialog.add(mainBox);

            JPanel buttonsPanel = new JPanel();


            JButton okButton = new JButton("OK");
            buttonsPanel.add(okButton);
            okButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    dialog.setVisible(false);
                }
            });
            dialog.add(buttonsPanel, BorderLayout.PAGE_END);
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
            about = false;
            model.aboutFlag = false;
        }

        if(model.status != null) statusBarText.setText(model.status);
    }

    public void paintComponent(Graphics g)
    {
        if(model.newField)
        {
            SizeNew();

            model.newField = false;
        }

        g2 = bufferedImage.createGraphics();

        BasicStroke depth = new BasicStroke(BORDER, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

        g2.setStroke(depth);
        g2.setColor(Color.orange);
        g2.fillRect(0, 0, bufWidth, bufHeight);

        g2.setColor(Color.BLACK);
        drawing.drawArea(g2, BORDER, hexagonHEIGHT, hexagonWIDTH, len);

        IzmenColor();

        if ((model.showValues) && (LENGTH >= 15))  PokazZnach();

        g.drawImage(bufferedImage, 0, 0, this);
    }

    private void IzmenColor()
    {
        boolean[][] states = model.getStates();

        for(int i = 0; i < states.length; ++i)
        	for(int j = 0; j < states[i].length; ++j)          
                if(states[i][j])  drawing.Zaliv(bufferedImage, i, j, len);
        
                
    }

    private void PokazZnach()
    {
        g2.setColor(new Color(6, 6, 4));
        Font fontOfDigit = new Font("Arial", Font.PLAIN, 18);
        g2.setFont(fontOfDigit);

        double[][] values = model.getValues();

        int x, y, hexagonWidth;

        for(int i = 0; i < values.length; ++i)
        {
            if(i % 2 == 0)  hexagonWidth =  values[i].length;
            else hexagonWidth =  values[i].length - 1;

            for(int j = 0; j < hexagonWidth; ++j)
            {
                String value = String.valueOf(values[i][j]);


                if(value.indexOf(".0") != -1) value = value.substring(0, value.length() - 2);

                int rest = value.indexOf(".");
                value = value.substring(0, rest + 2);

                y = (int)(i * (len + len / 2) + len + fontOfDigit.getSize() / 2);

                if(i % 2 == 0) x = (int)(j * 2 * len * Math.sqrt(3) / 2 + len * Math.sqrt(3) / 2 - value.length() * fontOfDigit.getSize() / 4);
                
                else  x = (int)(j * 2 * len * Math.sqrt(3) / 2 + 2 * len * Math.sqrt(3) / 2 - value.length() * fontOfDigit.getSize() / 4);
                

                g2.drawString(value, x, y);
            }
        }
    }

    public void SizeNew()
    {
        hexagonWIDTH = model.getWidth();
        hexagonHEIGHT = model.getHeight();
        BORDER = model.getBorder();
        LENGTH = model.getLength();

        len = (int)(LENGTH + BORDER / Math.sqrt(3));

        XWINDOW = (int)((hexagonWIDTH +1) * len * Math.sqrt(3) + BORDER);
        YWINDOW = (int)((hexagonHEIGHT + 1) * (len + len / 2) + len / 2 + BORDER);

        int frameX = frame.getWidth() - 18;
        int frameY = frame.getHeight() - menu.getHeight() - 2 * statusPanel.getHeight();

        if(XWINDOW <= frameX) bufWidth = frameX;
        else bufWidth = XWINDOW;

        if(YWINDOW <= frameY) bufHeight = frameY;
        else bufHeight = YWINDOW;

        drawPanel.setPreferredSize(new Dimension(bufWidth, bufHeight));
        bufferedImage = new BufferedImage(bufWidth, bufHeight, BufferedImage.TYPE_INT_RGB);

        sc.setViewportView(drawPanel);
    }
}