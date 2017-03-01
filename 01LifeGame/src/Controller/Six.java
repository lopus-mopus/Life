package Controller;

import View.Coordinate;
import View.LVie;

public class Six
{

    public Coordinate getHexagon1(Coordinate coordinate, int lenght, float border){
        int x = coordinate.x - (int)border / 2;
        int y = coordinate.y - (int)border / 2;

        Coordinate finalCoordinate = new Coordinate(x,y);
        return finalCoordinate;
    }

    //��������� ������������� ���������� �� ������������� �������
    public Coordinate getHexagon(Coordinate coordinate, int length, float border)
    {
        int x = coordinate.x - (int)border / 2;
        int y = coordinate.y - (int)border / 2;

        int width = LVie.hexagonWIDTH;
        int height = LVie.hexagonHEIGHT;

        double sin30 = 1.0 / 2.0;
        double cos30 = Math.sqrt(3) / 2.0;
        double tg30 = 1.0 / Math.sqrt(3);

        int hexCoordinateX = (int)(x / (2 * length * cos30));
        int hexCoordinateY = (int)(y / (length + length * sin30));

        if((x < (width * 2 * length * cos30)) && (y < (height * (length + 2 * length * sin30)))) {
            if ((y % (length + length * sin30)) >= (length * sin30)) {

                //���� ����� �������� � �������������
                if (hexCoordinateY % 2 == 0) {
                    hexCoordinateX = (int) (x / (2 * length * cos30));
                } else {
                    if (x > length * cos30) {
                        x -= (int) (length * cos30);
                        hexCoordinateX = (int) (x / (2 * length * cos30));
                    } else {
                        hexCoordinateX = hexCoordinateY = -1;
                    }
                    if (hexCoordinateX >= width - 1) {
                        hexCoordinateX = hexCoordinateY = -1;
                    }
                }
            } else {

                //���� ����� �������� � ������������
                int XTriangle = (int) (x / (length * cos30));
                int y0, x0;
                y0 = (int) (y - (hexCoordinateY * (length + length * sin30)));

                if (hexCoordinateY % 2 == 0) {
                    if (XTriangle % 2 == 0) {

                        ///����� ������������ (����� � ����)
                        x0 = (int) (x - (hexCoordinateX * (2 * length * cos30)));

                        if (y0 <= (length * sin30 - x0 * tg30)) {
                            //Left-Up  triangle//����� ������� �����������

                            hexCoordinateY--;
                            hexCoordinateX--;

                            if (hexCoordinateX < 0) {
                                hexCoordinateX = hexCoordinateY = -1;
                            }
                        } else {
                            //����� ������ �����������
                        }
                    } else {

                        
                        x0 = (int) (x - (hexCoordinateX * (2 * length * cos30)) - length * cos30);

                        if (y0 <= (x0 * tg30)) {

                            
                            hexCoordinateY--;
                            if (hexCoordinateX >= (width - 1)) {
                                hexCoordinateX = hexCoordinateY = -1;
                            }
                        } else {

                            //������ ������ �����������
                            if (hexCoordinateX < 0) {
                                hexCoordinateX = hexCoordinateY = -1;
                            }
                        }
                    }
                } else {
                    if (XTriangle % 2 == 0) {

                        
                        x0 = (int) (x - (hexCoordinateX * (2 * length * cos30)));

                        if (y0 <= (x0 * tg30))  hexCoordinateY--; 
                        else {

                            hexCoordinateX--;

                            if (hexCoordinateX < 0) hexCoordinateX = hexCoordinateY = -1;
                        }
                    } else {

                        x0 = (int) (x - (hexCoordinateX * (2 * length * cos30)) - length * cos30);

                        if (y0 <= (length * sin30 - x0 * tg30)) {

                            hexCoordinateY--;
                        } else {

                            if (hexCoordinateX >= (width - 1)) {
                                hexCoordinateX = hexCoordinateY = -1;
                            }
                        }
                    }
                }
            }

            //�������� ����, ��� ����� �������� � �������������
            if ((hexCoordinateY <= -1) || (hexCoordinateX >= width) || (hexCoordinateY >= height)) {
                hexCoordinateX = hexCoordinateY = -1;
            }
        }
        else
        {
            hexCoordinateX = hexCoordinateY = -1;
        }

        Coordinate finalCoordinate = new Coordinate(hexCoordinateX, hexCoordinateY);

        return finalCoordinate;
    }
}
