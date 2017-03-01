package View;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Coordinate
{
    public int x;
    public int y;

    public Coordinate(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void setColor(BufferedImage img, Color newColor)
    {
        img.setRGB(this.x, this.y, newColor.getRGB());
    }

    public boolean equals(Coordinate c)
    {
        return ((this.x == c.x) && (this.y == c.y));
    }
}