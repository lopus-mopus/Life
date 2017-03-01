package Model;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import View.Coordinate;


public class FileWorld
{
    private int width;
    private int height;
    private int w;
    private int k;
    private Coordinate[] coordinates;

    private boolean flag = true;
    private String errorMessage = null;

    public void parseFile(File file) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));

        String str;
        int i = 0;

        while((str = reader.readLine()) != null)
        {
            i++;

            str = removeComments(str);

            if(i < 5)
            {
                parseString(str, i);
            }
            else if(i < (coordinates.length + 5))
            {
                parseCoords(str, i);
            }
            else if(!parseNullStr(str, i))
            {
                setError("Error in " + i + " string.");
            }

            if(!flag)
            {
                break;
            }
        }

        reader.close();
    }

    private String removeComments(String str)
    {
        int index = str.indexOf("//");

        if(index != -1)
        {
            str = str.substring(0, index);
        }

        return str;
    }

    private void parseString(String str, int i)
    {
        String parts[] = str.split(" ");

        for(int j = 0; j < parts.length; ++j)
        {
            for (int k = 0; k < parts[j].length(); ++k)
            {
                if(!Character.isDigit(parts[j].charAt(k)))
                {
                    setError("Error in " + i + " string.");
                }
            }
        }

        if(flag)
        {
            switch(i)
            {
                case 1:
                {
                    if(parts.length != 2) setError("Error в " + i + " строке.");
                    else
                    {
                        int h = Integer.parseInt(parts[0]);
                        int w = Integer.parseInt(parts[1]);

                        if(h > 100 || w > 100) setError("максимальный размер поля: 100 х 100.");
                        else
                        {
                            setHeight(h);
                            setWidth(w);
                        }
                    }
                    break;
                }
                case 2:
                {
                    if(parts.length != 1) setError("Error в " + i + " строке.");
                    else
                    {
                        int d = Integer.parseInt(parts[0]);
                        if(d > 5) setError("максимальное значение линии: 5.");
                        else setBorder(d);
                    }
                    break;
                }
                case 3 :
                {
                    if(parts.length != 1) setError("Error в " + i + " строке.");
                    else
                    {
                        int l = Integer.parseInt(parts[0]);
                        if(l <= 100) setLength(l);
                        else setError("максимальный размер шестиугольника: 100.");
                    }
                    break;
                }
                case 4 :
                {
                    if(parts.length != 1) setError("Error в " + i + " строке.");
                    else coordinates = new Coordinate[Integer.parseInt(parts[0])];
                    break;
                }
            }
        }
    }

    private void parseCoords(String str, int i)
    {
        String parts[] = str.split(" ");

        if(parts.length != 2)
        {
            setError("Error в" + i + " строке.");
        }
        else
        {
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[0]);

            int parityWidth;

            if(y % 2 == 0)
            {
                parityWidth = width;
            }
            else
            {
                parityWidth = width - 1;
            }

            if((x >= 0) && (y >= 0) && (x < parityWidth) && (y < height))
            {
                Coordinate newCoordinate = new Coordinate(y, x);
                for(int j = 0; j < i - 5; ++j)
                {
                    if(newCoordinate.equals(coordinates[j]))
                    {
                        setError("Координаты живых клеток не должны повторяться.");
                        break;
                    }
                }
                coordinates[i - 5] = newCoordinate;
            }
            else
            {
                setError("Вы вышли за пределы дозволенного");
            }
        }
    }

    private boolean parseNullStr(String str, int i)
    {
        return ((str.isEmpty()) && (i == (coordinates.length + 5)));
    }

    private void setError(String message)
    {
        flag = false;

        errorMessage = message;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getBorder()
    {
        return w;
    }

    public void setBorder(int border)
    {
        this.w = border;
    }

    public int getLength()
    {
        return k;
    }

    public void setLength(int length)
    {
        this.k = length;
    }

    public Coordinate[] getCoordinates()
    {
        return coordinates;
    }

    public boolean getError()
    {
        return flag;
    }

    public String getMessage()
    {
        return errorMessage;
    }

}