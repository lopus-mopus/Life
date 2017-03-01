package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import View.Coordinate;

public class LMod extends Observable implements Runnable
{
    private int width, height;
    private double[][] values;
    private boolean[][] states;
    private int border;
    private int length;
    private Thread simulation;
    public double FST_IMPACT = 1.0;
    public double SND_IMPACT = 0.3;
    public double BIRTH_BEGIN = 2.3;
    public double BIRTH_END = 2.9;
    public double LIVE_BEGIN = 2.0;
    public double LIVE_END = 3.3;

    public String msg = null;
    public boolean showValues = false;
    public boolean aboutFlag = false;
    public String status = null;
    public boolean newField = false;

    public LMod(int width, int height, int length, int border)
    {
        this.width = width;
        this.height = height;
        this.border = border;
        this.length = length;

        values = new double[height][width];
        states = new boolean[height][width];

        for (int i = 0; i < height; ++i)
            for (int j = 0; j < width; ++j)
            {
                states[i][j] = false;
                values[i][j] = 0.0;
            }
    }

    public void run()
    {
        while(simulation != null)
        {
            try{
                nextStep();}
            catch (InterruptedException e){
                e.printStackTrace();}
        }

    }
    public void nextStep() throws InterruptedException
    {
        //Массив для хранения промежуточных состояний ячеек
        boolean[][] tempStates = new boolean[height][width];

        for (int i = 0; i < height; ++i)
            for (int j = 0; j < width; ++j)
                tempStates[i][j] = states[i][j];
        
        for (int i = 0; i < height; ++i)
        {
            if(i % 2 == 0)
            	for (int j = 0; j < width; ++j)
                {
                    int FST_COUNT = countFST(i, j);
                    int SND_COUNT = countSND(i, j);

                    double impact = FST_IMPACT * FST_COUNT + SND_IMPACT * SND_COUNT;
                    values[i][j] = impact;

                    if((!states[i][j]) && (BIRTH_BEGIN <= impact) && (impact <= BIRTH_END)) tempStates[i][j] = true;

                    if((states[i][j]) && (LIVE_BEGIN <= impact) && (impact <= LIVE_END)) tempStates[i][j] = true;

                    if(impact < LIVE_BEGIN) tempStates[i][j] = false;

                    if(impact > LIVE_END) tempStates[i][j] = false;
                }
            else
            	for (int j = 0; j < width - 1; ++j)
                {
                    int FST_COUNT = countFST(i, j);
                    int SND_COUNT = countSND(i, j);

                    double impact = FST_IMPACT * FST_COUNT + SND_IMPACT * SND_COUNT;
                    values[i][j] = impact;

                    if((!states[i][j]) && (BIRTH_BEGIN <= impact) && (impact <= BIRTH_END)) tempStates[i][j] = true;

                    if((states[i][j]) && (LIVE_BEGIN <= impact) && (impact <= LIVE_END)) tempStates[i][j] = true;

                    if(impact < LIVE_BEGIN) tempStates[i][j] = false;

                    if(impact > LIVE_END) tempStates[i][j] = false;
                }
            
        }


        for (int i = 0; i < height; ++i)
        	for (int j = 0; j < width; ++j)
            	states[i][j] = tempStates[i][j];
        setChanged();
        notifyObservers();
        Thread.sleep(1000);
    }

    private void countImpact()
    {
        for (int i = 0; i < height; ++i)
        {
            if(i % 2 == 0)
            	for (int j = 0; j < width; ++j)
            	{
            		int fst_count = countFST(i, j);
            		int snd_count = countSND(i, j);
            		double impact = FST_IMPACT * fst_count + SND_IMPACT * snd_count;
            		values[i][j] = impact;
            	}
            else   
            	for (int j = 0; j < width - 1; ++j)
            	{
            		int fst_count = countFST(i, j);
            		int snd_count = countSND(i, j);
            		double impact = FST_IMPACT * fst_count + SND_IMPACT * snd_count;
            		values[i][j] = impact;
            	}
        }
    }

    private int countFST(int i, int j)
    {
        int count = 0;

        if(i % 2 == 0)
        {
            if(checkState(i-1, j-1)) count ++;
            if(checkState(i-1, j)) count ++;
            if(checkState(i, j+1)) count ++;
            if(checkState(i+1, j)) count ++;
            if(checkState(i+1, j-1)) count ++;
            if(checkState(i, j-1)) count ++;
        }
        else
        {
            if(checkState(i-1, j)) count ++;
            if(checkState(i-1, j+1)) count ++;
            if(checkState(i, j+1)) count ++;
            if(checkState(i+1, j+1)) count ++;
            if(checkState(i+1, j)) count ++;
            if(checkState(i, j-1)) count ++;
        }

        return count;
    }

    private int countSND(int i, int j)
    {
        int count = 0;

        if(i % 2 == 0)
        {
            if(checkState(i-2, j)) count ++;
            if(checkState(i-1, j+1)) count ++;
            if(checkState(i+1, j+1)) count ++;
            if(checkState(i+2, j)) count ++;
            if(checkState(i+1, j-2)) count ++;
            if(checkState(i-1, j-2)) count ++;
        }
        else
        {
            if(checkState(i-2, j)) count ++;
            if(checkState(i-1, j+2)) count ++;
            if(checkState(i+1, j+2)) count ++;
            if(checkState(i+2, j)) count ++;
            if(checkState(i+1, j-1)) count ++;
            if(checkState(i-1, j-1)) count ++;
        }

        return count;
    }

    private boolean checkState(int i, int j)
    {
        boolean flag = false;

        if(i % 2 == 0)
        	{if(i >= 0 && j >= 0 && i < height && j < width) flag = states[i][j];}
        else
        	{if(i >= 0 && j >= 0 && i < height && j < width - 1) flag = states[i][j];}
        
        return flag;
    }

    public boolean[][] getStates()
    {
        return states;
    }

    public double[][] getValues()
    {
        return values;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getLength()
    {
        return length;
    }
    
    ///////////////
    
    public double getSND_IMPACT()
    {
        return SND_IMPACT;
    }
    public double getFST_IMPACT(){
        return FST_IMPACT;
    }
    public double getBIRTH_END()
    {
        return BIRTH_END;
    }
    public double getBIRTH_BEGIN()
    {
        return BIRTH_BEGIN;
    }
    public double getLIVE_END()
    {
        return LIVE_END;
    }
    public double getLIVE_BEGIN()
    {
        return LIVE_BEGIN;
    }
    
    
    public void setSND_IMPACT( double i)
    {
        SND_IMPACT=i;
    }
    public void setFST_IMPACT(double i){
        FST_IMPACT=i;
    }
    public void setBIRTH_END(double i)
    {
        BIRTH_END=i;
    }
    public void setBIRTH_BEGIN(double i)
    {
        BIRTH_BEGIN=i;
    }
    public void setLIVE_END(double i)
    {
        LIVE_END=i;
    }
    public void setLIVE_BEGIN(double i)
    {
        LIVE_BEGIN =i;
    }
    
    
    
    
    
    
    ///////////////

    public float getBorder()
    {
        return border;
    }

    public void setWidth(int w)
    {
        width = w;
    }

    public void setHeight(int h)
    {
        height = h;
    }

    public void setLength(int l)
    {
        length = l;
    }

    public void setBorder(int b)
    {
        border = b;
    }

    public void clear()
    {
        for (int i = 0; i < height; ++i)
            for (int j = 0; j < width; ++j)
            {
                states[i][j] = false;
                values[i][j] = 0.0;
            }

        setChanged();// //отметьте, как изменилось значение
        notifyObservers();//метод () уведомляет всех своих наблюдателей , если объект изменился.//триггер уведомление
    }

    public void startSimulation()
    {
        if (simulation == null)
        {
            simulation = new Thread(this);
            System.out.println(simulation);
            simulation.start();
        }
    }

    public void stopSimulation()
    {
        simulation = null;
    }

    public boolean isSimulating()
    {
        return simulation != null;
    }

    public void replaceMode(Coordinate coordinate)
    {
        states[coordinate.y][coordinate.x] = true;
        countImpact();
        setChanged();
        notifyObservers();
    }

    public void xorMode(Coordinate coordinate)
    {
        states[coordinate.y][coordinate.x] = !states[coordinate.y][coordinate.x];
        countImpact();
        setChanged();
        notifyObservers();
    }


    public void showValues()
    {
        showValues = true;

        setChanged();
        notifyObservers();
    }

    public void stopShowValues()
    {
        showValues = false;

        setChanged();
        notifyObservers();
    }

    public void parseFile(File file) throws IOException
    {
        FileWorld fio = new FileWorld();

        fio.parseFile(file);

        if(fio.getError())
        {
            width = fio.getWidth();
            height = fio.getHeight();
            border = fio.getBorder();
            length = fio.getLength();

            values = new double[height][width];
            states = new boolean[height][width];

            for (int i = 0; i < height; ++i)
                for (int j = 0; j < width; ++j)
                {
                    states[i][j] = false;
                    values[i][j] = 0.0;
                }

            Coordinate[] coordinates = fio.getCoordinates();

            for(int i = 0; i < coordinates.length; ++i)
            {
                states[coordinates[i].x][coordinates[i].y] = true;
            }
            countImpact();
            newField = true;

            setChanged();
            notifyObservers();
        }
        else
        {
            String messageError = fio.getMessage();

            setMessage(messageError);
        }
    }

    public void setMessage(String messageError)
    {
        msg = messageError;

        setChanged();
        notifyObservers();
    }

    public void Save(File file) throws IOException
    {
        FileWriter fw = new FileWriter(file.getPath()+".txt");

        fw.write(height + " " + width + "\n");
        fw.write((int)border + "\n");
        fw.write(length + "\n");

        ArrayList<Coordinate> liveCoordinates = new ArrayList<Coordinate>();

        for(int i = 0; i < height; ++i)
        {
            for(int j = 0; j < width; ++j)
            {
                if(states[i][j])
                {
                    liveCoordinates.add(new Coordinate(i, j));
                }
            }
        }

        fw.write(liveCoordinates.size() + "\n");

        for (Coordinate c : liveCoordinates)
        {
            fw.write(c.x + " " + c.y + "\n");
        }
        fw.write("\n");

        fw.close();
    }

    public void about()
    {
        aboutFlag = true;

        setChanged();
        notifyObservers();
    }

    public void newSettings(int w, int h, int b, int l, double lb, double le, double bb, double be, double fi, double si)
    {
        boolean[][] interimStates = new boolean[height][width];

        for (int i = 0; i < height; ++i)
            for (int j = 0; j < width; ++j)
            {
                interimStates[i][j] = states[i][j];
            }

        int interimHeight, interimWidth;

        interimWidth = (width < w) ? width : w;
        interimHeight = (height < h) ? height : h;

        setWidth(w);
        setHeight(h);
        setBorder(b);
        setLength(l);
        setSND_IMPACT(si);
        setFST_IMPACT(fi);
        setBIRTH_END(be);
        setBIRTH_BEGIN(bb);
        setLIVE_END(le);
        setLIVE_BEGIN(lb);

        values = new double[height][width];
        states = new boolean[height][width];

        int parityWidth;

        for (int i = 0; i < interimHeight; ++i)
        {
            if(i % 2 == 0)
                parityWidth = interimWidth;
            else
                parityWidth = interimWidth - 1;
            for (int j = 0; j < parityWidth; ++j)
                states[i][j] = interimStates[i][j];
        }

        newField = true;

        setChanged();
        notifyObservers();
    }

    public void setStatus(String str)
    {
        status = str;

        setChanged();
        notifyObservers();
    }
}