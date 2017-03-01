package Controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import Model.LMod;
import View.Coordinate;
import View.LVie;
import View.Drawing;


public class LCon implements MouseListener, MouseMotionListener
{
    static LMod model;
    JFrame mainFrame;
    JMenuItem newGameMenuItem, openFileMenuItem, saveGameMenuItem, exitMenuItem, runMenuItem, nextStepMenuItem, xorModeMenuItem, replaceModeMenuItem,
            clearMenuItem, optionsMenuItem, viewValuesMenuItem, aboutMenuItem;

    JButton newGameButton, openFileButton, saveGameButton, exitButton, runButton, nextStepButton, xorModeButton, replaceModeButton, clearButton,
            optionsButton, viewValuesButton, aboutButton;

    boolean needSave = false;

    Coordinate oldCoordinate = new Coordinate(-1, -1);

    static int mode = 1; 
    int lastMode = 0;

    public LCon(LMod gameLifeModel)
    {
        model = gameLifeModel;
    }

    public JMenuBar createMenuBar()
    {
        JMenuBar menu = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menu.add(fileMenu);

        newGameMenuItem = new JMenuItem("новая игра");
        newGameMenuItem.addActionListener(gameAL);
        fileMenu.add(newGameMenuItem);

        fileMenu.addSeparator();

        openFileMenuItem = new JMenuItem("открыть игру");
        openFileMenuItem.addActionListener(fileAL);
        fileMenu.add(openFileMenuItem);

        saveGameMenuItem = new JMenuItem("сохранить игру");
        saveGameMenuItem.addActionListener(saveAL);
        fileMenu.add(saveGameMenuItem);

        fileMenu.addSeparator();

        exitMenuItem = new JMenuItem("Выход");
        exitMenuItem.addActionListener(exitAL);
        fileMenu.add(exitMenuItem);

        JMenu actionMenu = new JMenu("Game");
        menu.add(actionMenu);

        runMenuItem = new JMenuItem("Run");
        runMenuItem.addActionListener(runAL);
        actionMenu.add(runMenuItem);

        nextStepMenuItem = new JMenuItem("следующий шаг");
        nextStepMenuItem.addActionListener(nextAL);
        actionMenu.add(nextStepMenuItem);

        actionMenu.addSeparator();

        replaceModeMenuItem = new JMenuItem("Replace режим");
        replaceModeMenuItem.addActionListener(replaceAL);
        actionMenu.add(replaceModeMenuItem);

        xorModeMenuItem = new JMenuItem("XOR режим");
        xorModeMenuItem.addActionListener(xorAL);
        actionMenu.add(xorModeMenuItem);

        actionMenu.addSeparator();

        clearMenuItem = new JMenuItem("протокол: чистый лист");
        clearMenuItem.addActionListener(clearAL);
        actionMenu.add(clearMenuItem);

        JMenu viewMenu = new JMenu("Оptions");
        menu.add(viewMenu);

        optionsMenuItem = new JMenuItem("настройки");  
        optionsMenuItem.addActionListener(optionsAL);
        viewMenu.add(optionsMenuItem);

        viewValuesMenuItem = new JMenuItem("значения");
        viewValuesMenuItem.addActionListener(valuesAL);
        viewMenu.add(viewValuesMenuItem);

        JMenu helpMenu = new JMenu("Help");
        menu.add(helpMenu);

        aboutMenuItem = new JMenuItem("?");
        aboutMenuItem.addActionListener(aboutAL);
        helpMenu.add(aboutMenuItem);

        return menu;
    }

    public JToolBar createToolBar()
    {
        JToolBar tbar = new JToolBar();

        tbar.setFloatable(false);

        newGameButton = (new Button(new ImageIcon("resources/window_new_9492.png"), "новая игра", "новая игра", gameAL)).newButton();
        tbar.add(newGameButton);

        openFileButton = (new Button(new ImageIcon("resources/folder_open_2366.png"), "открыть игру", "Открыть игру из файла", fileAL)).newButton();
        tbar.add(openFileButton);

        saveGameButton = (new Button(new ImageIcon("resources/filesaveas_7940.png"), "сохранить", "сохранить игру", saveAL)).newButton();
        tbar.add(saveGameButton);

        tbar.addSeparator();

        runButton = (new Button(new ImageIcon("resources/amor_4166.png"), "Run", "игра", runAL)).newButton();
        tbar.add(runButton);

        nextStepButton = (new Button(new ImageIcon("resources/package_favorite_7309.png"), "следующий шаг", "следующий шаг", nextAL)).newButton();
        tbar.add(nextStepButton);

        tbar.addSeparator();

        replaceModeButton = (new Button(new ImageIcon("resources/gnome-lockscreen_9803.png"), "replace режим", "режим replase", replaceAL)).newButton();
        tbar.add(replaceModeButton);

        xorModeButton = (new Button(new ImageIcon("resources/kwalletmanager_3117.png"), "XOR режим", "режим xor", xorAL)).newButton();
        tbar.add(xorModeButton);

        tbar.addSeparator();

        clearButton = (new Button(new ImageIcon("resources/checkbox_no_4006.png"), "отчистить", "отчистить", clearAL)).newButton();
        tbar.add(clearButton);

        tbar.addSeparator();

        optionsButton = (new Button(new ImageIcon("resources/configure_4366.png"), "настройки", "настройки", optionsAL)).newButton();
        tbar.add(optionsButton);

        viewValuesButton = (new Button(new ImageIcon("resources/document-properties_7488.png"), "значения", "показывает состояние ячеек", valuesAL)).newButton();
        tbar.add(viewValuesButton);

        tbar.addSeparator();

        aboutButton = (new Button(new ImageIcon("resources/emblem-people_4859.png"), "?", "тыкни", aboutAL)).newButton();
        tbar.add(aboutButton);

        tbar.addSeparator();

        exitButton = (new Button(new ImageIcon("resources/gtk-quit_9845.png"), "выход", "выход", exitAL)).newButton();
        tbar.add(exitButton);

        return tbar;
    }

    private ActionListener exitAL = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            int answer = 0;

            if(needSave)
            {
                answer = setSaveQ();

                if(answer != 2)
                {
                    System.exit(0);
                }
            }
            else System.exit(0);
        }
    };

    private ActionListener xorAL = new ActionListener()
    {
        public void actionPerformed(ActionEvent event)
        {
            mode = 2;
        }
    };

    private ActionListener runAL = new ActionListener()
    {
        public void actionPerformed(ActionEvent event)
        {
            Component[] buttons = {newGameButton, openFileButton, saveGameButton, nextStepButton, optionsButton, clearButton, replaceModeButton, xorModeButton,
                    newGameMenuItem, openFileMenuItem, saveGameMenuItem, nextStepMenuItem, optionsMenuItem, clearMenuItem, replaceModeMenuItem, xorModeMenuItem};

            needSave = true;

            if(model.isSimulating())
            {
                model.stopSimulation();
                runMenuItem.setText("Run");

                for(int i = 0; i < buttons.length; ++i) buttons[i].setEnabled(true);

                mode = lastMode;
            }
            else
            {
                model.startSimulation();
                runMenuItem.setText("Stop");

                for(int i = 0; i < buttons.length; ++i) buttons[i].setEnabled(false);

                lastMode = mode;
                mode = 0;
            }
        }
    };

    private ActionListener fileAL = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            int answerAboutSave = 0;

            if(needSave) answerAboutSave = setSaveQ();


            if(answerAboutSave != 2)
            {
                JFileChooser fileOpen = new JFileChooser();
                fileOpen.setCurrentDirectory(new File("C:\\Users\\437\\Desktop\\war\\01LifeGame\\src\\Life\\Data"));
                setCenter();
                int answer = fileOpen.showDialog(mainFrame, "Open file");

                if (answer == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        File file = fileOpen.getSelectedFile();
                        model.parseFile(file);
                    }
                    catch(FileNotFoundException fileExc)
                    {
                        model.setMessage("File not found.");
                    }
                    catch (IOException parseExc)
                    {
                        parseExc.printStackTrace();
                    }
                }
            }
        }
    };

    private ActionListener gameAL = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            int answer = 0;

            if(needSave) answer = setSaveQ();


            if(answer != 2)
            {
                setCenter();

                Document doc = new Document(mainFrame);
                doc.changeSize();
            }
        }
    };

    private ActionListener saveAL = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            saveWorld();
        }
    };

    private ActionListener nextAL = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                model.nextStep();
                needSave = true;
            }
            catch (InterruptedException exception)
            {
                exception.printStackTrace();
            }
        }
    };

    private ActionListener valuesAL = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            if(model.showValues) model.stopShowValues();
            else model.showValues();
        }
    };

    private ActionListener optionsAL = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            setCenter();
            Options options = new Options(mode, mainFrame);
            options.checkOptions();
            needSave = true;
        }
    };

    private ActionListener aboutAL = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            model.about();
        }
    };

    private ActionListener clearAL = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            model.clear();
            needSave = true;
        }
    };

    private ActionListener replaceAL = new ActionListener()
    {
        public void actionPerformed(ActionEvent event)
        {
            mode = 1;
        }
    };

    public void mouseClicked(MouseEvent e)
    {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
        Coordinate coordinate = new Coordinate(e.getX(), e.getY());

        Color color = new Color(LVie.bufferedImage.getRGB(coordinate.x, coordinate.y));

        if(color.getRGB() != Drawing.border.getRGB())
        {
            Six hex = new Six();

            if(hex.getHexagon(coordinate, LVie.len, LVie.BORDER).x != -1)
            {
                needSave = true;
                if(mode == 1) model.replaceMode(hex.getHexagon(coordinate, LVie.len, LVie.BORDER));
                else if(mode == 2) model.xorMode(hex.getHexagon(coordinate, LVie.len, LVie.BORDER));
            }
        }
    }

    public void mouseReleased(MouseEvent e)
    {
    }

    public void mouseDragged(MouseEvent arg)
    {
        Coordinate coordinate = new Coordinate(arg.getX(), arg.getY());

        if(!((coordinate.x <= 0) || (coordinate.x >= LVie.drawPanel.getWidth()) || (coordinate.y <= 0) || (coordinate.y >= LVie.drawPanel.getHeight())))
        {
            Color color = new Color(LVie.bufferedImage.getRGB(coordinate.x, coordinate.y));

            if(color.getRGB() != Drawing.border.getRGB())
            {
                Six hex = new Six();

                if((hex.getHexagon(coordinate, LVie.len, LVie.BORDER).x != -1) && (!hex.getHexagon(coordinate, LVie.len, LVie.BORDER).equals(oldCoordinate)))
                {
                    if(mode == 1) model.replaceMode(hex.getHexagon(coordinate, LVie.len, LVie.BORDER));
                    else if(mode == 2) model.xorMode(hex.getHexagon(coordinate, LVie.len, LVie.BORDER));
                    oldCoordinate = hex.getHexagon(coordinate, LVie.len, LVie.BORDER);
                }
            }
        }
    }

    public void mouseMoved(MouseEvent arg)
    {
    }

    public int setSaveQ()
    {
        setCenter();
        int answer = JOptionPane.showConfirmDialog(mainFrame, "Do you want to save this game?", "Save", JOptionPane.YES_NO_CANCEL_OPTION);

        switch(answer)
        {
            case(JOptionPane.YES_OPTION):
            {
                saveWorld();
                break;
            }
            case(JOptionPane.NO_OPTION):
            {
                break;
            }
            case(JOptionPane.CANCEL_OPTION):
            {
                break;
            }
        }

        return answer;
    }

    public void saveWorld()
    {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT","txt");

        JFileChooser fileSave = new JFileChooser();
        fileSave.setFileFilter(filter);
        fileSave.setCurrentDirectory(new File("C:\\Users\\437\\Desktop\\war\\01LifeGame\\src\\Life\\Data"));

        setCenter();
        int answer = fileSave.showSaveDialog(mainFrame);

        if(answer == JFileChooser.APPROVE_OPTION)
        {
            File file = fileSave.getSelectedFile();
            try
            {
                model.Save(file);
                needSave = false;
            }
            catch (IOException exc)
            {
                exc.printStackTrace();
            }
        }
    }

    private void setCenter()
    {
        mainFrame = LVie.frame;
    }
}