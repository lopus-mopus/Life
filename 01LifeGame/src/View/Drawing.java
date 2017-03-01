package View;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Stack;


public class Drawing
{
    public static Color border = Color.BLACK;

    private void lineBresenham(Graphics2D g, int x0, int y0, int x1, int y1)
    {
        int xx = Math.abs(x1 - x0);
        int yy = Math.abs(y1 - y0);

        if((xx == yy) && (xx == 0))
        {
            g.drawLine(x0, y0, x0, y0);
        }
        else
        {
            int error = 0;
            int x = x0;
            int y = y0;
            boolean change = false;

            if(yy > xx)
            {
                int temp = yy;
                yy = xx;
                xx = temp;

                change = true;
            }

            error = 2 * yy - xx;

            int signX = ((x1 - x0) > 0) ? 1 : ((x1 - x0) < 0) ? -1 : 0;
            int signY = ((y1 - y0) > 0) ? 1 : ((y1 - y0) < 0) ? -1 : 0;

            for(int i = 0; i < xx; ++i)
            {
                g.drawLine(x, y, x, y);

                while(error >= 0)
                {
                    if(change)
                    {
                        x = x + signX;
                    }
                    else
                    {
                        y = y + signY;
                    }

                    error = error - 2 * xx;
                }

                if(change)
                {
                    y = y + signY;
                }
                else
                {
                    x = x + signX;
                }

                error = error + 2 * yy;
            }
        }
    }

    public void DrawSix(Graphics2D g, double length, double startX, double startY)
    {
        double sin30 = 1.0 / 2.0;
        double cos30 = Math.sqrt(3) / 2.0;

        double endX = startX + length * cos30;
        double endY = startY - length * sin30;

        lineBresenham(g, (int) startX, (int) startY, (int) endX, (int) endY);

        startX = endX;
        startY = endY;

        endX = startX + length * cos30;
        endY = startY + length * sin30;

        lineBresenham(g, (int) startX, (int) startY, (int) endX, (int) endY);

        startX = endX;
        startY = endY;

        endY = startY + length;

        lineBresenham(g, (int) startX, (int) startY, (int) endX, (int) endY);

        startX = endX;
        startY = endY;

        endX = startX - length * cos30;
        endY = startY + length * sin30;

        lineBresenham(g, (int) startX, (int) startY, (int) endX, (int) endY);

        startX = endX;
        startY = endY;

        endX = startX - length * cos30;
        endY = startY - length * sin30;

        lineBresenham(g, (int) startX, (int) startY, (int) endX, (int) endY);

        startX = endX;
        startY = endY;

        endY = startY - length;

        lineBresenham(g, (int) startX, (int) startY, (int) endX, (int) endY);
    }

    public void drawArea(Graphics2D g, float dep, int hight, int weight, double length)
    {
        double sin30 = 0.5;
        double cos30 = Math.sqrt(3) / 2;

        double startX = (dep) / 2;
        double startY = (dep) / 2 + length * sin30;
        
        System.out.println("!= " + startX + " & " + startY);

        double x0 = startX;
        double y0 = startY;

        int endLine = weight;

        for(int i = 0; i < hight; ++i)
        {
            for(int j = 0; j < endLine; ++j)
            {
                DrawSix(g, length, startX, startY);

                startX += 2 * length * cos30;
               // System.out.println("!!= " + startX + " & " + startY);
            }

            if(i % 2 == 0)
            {
                endLine = weight - 1;
                startX = x0 + length * cos30;
            }
            else
            {
                endLine = weight;
                startX = x0 - length * cos30;
            }

            startY = y0 + length + length * sin30;

            x0 = startX;
            y0 = startY;
           // System.out.println("!!!= " + startX + " & " + startY);
        }
    }

    public void Zaliv(BufferedImage img, int hexY, int hexX, int length)
    {
        Stack<Coordinate> spans = new Stack<Coordinate>();

        int left;
        int right;
        double sin30 = 0.5;
        double cos30 = Math.sqrt(3) / 2;

        //Перевод координат в пиксели
        int x, y;
        if(hexY % 2 == 0) x = (int)(hexX * length * 2 * cos30 + length * cos30);
        else x = (int)(hexX * length * 2 * cos30 + 2 * length * cos30);
        y = (int)(hexY * length * (1 + sin30) + length);

        int tempX = x, tempY = y;
        
        // получаем стартовую координату
        Coordinate start = new Coordinate(x, y);
        // получаем цвет, подлежащий замене
        Color seed = new Color(img.getRGB(start.x, start.y));
        // вставка спана в стек
        spans.push(start);

        // старт алгоритма
        while(!spans.isEmpty())
        {
        	// берем спан из стека
            Coordinate coordinate = spans.pop();
            
            // поиск правой границы
            for(right = coordinate.x;img.getRGB(right, coordinate.y) == seed.getRGB();++right);
            --right;
            
            // идем влево до конца с заливкой и определяем левую границу
            for(left = coordinate.x - 1;img.getRGB(left, coordinate.y) == seed.getRGB();--left){
                Coordinate c = new Coordinate(left, coordinate.y);
                c.setColor(img, Color.YELLOW);
            }
            ++left;

            // Вниз
            tempY = coordinate.y + 1;
            tempX = left;

            boolean flag;
            int tempRight;

            while(tempX < right) {
            	// сдвиг вправо до конца заливки пределяем есть ли справа на спане уже залитые пиксели
                flag = false;
                for(;(img.getRGB(tempX, tempY) == seed.getRGB()) && (tempX < right);tempX++)
                    if(!flag) flag = true;
                
                // вставка если смещались (спан залит не 1 пикселем)
                if(flag) {	// если не двигалиь вправо
                	// вставка нового спана в стек
                    if((img.getRGB(tempX, tempY) == seed.getRGB()) && (tempX == right))
                    	spans.push(new Coordinate(tempX, tempY)); // если стоим на конце спана       	            
                    else spans.push(new Coordinate(tempX - 1, tempY));    // если стоим за спаном то вставка спана выше над тем где был спан            
                    //flag = false;
                }

                // определяем правую координату для следующей вставки спана
                for(tempRight = tempX;(img.getRGB(tempX, tempY) != seed.getRGB()) && (tempX < right);) tempX++;
                if(tempX == tempRight) tempX++;
            }

            // Вверх
            tempY = coordinate.y - 1;
            tempX = left;

            while(tempX < right)
            {
                flag = false;
                for(;(img.getRGB(tempX, tempY) == seed.getRGB()) && (tempX < right);tempX++)
                    if(!flag) flag = true;                

                if(flag){
                    if((img.getRGB(tempX, tempY) == seed.getRGB()) && (tempX == right))
                        spans.push(new Coordinate(tempX, tempY));
                    else  spans.push(new Coordinate(tempX - 1, tempY));
                }
                // определяем правую координату для следующей вставки спана
                for(tempRight = tempX;(img.getRGB(tempX, tempY) != seed.getRGB()) && (tempX < right);) tempX++;              
                if(tempX == tempRight) tempX++;                
            }
        }
    }
}