//http://students.njay.ru/article/Rabota-s-izobrazheniiami-v-Java-chtenie-zapis-izmenenie

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*  Первоначально в конструктор приходит картинка 16х16, содержащая эталонный символ
 * */
public class MySymbol {
    private String path;
    private Integer minX;
    private Integer maxX;
    private Integer minY;
    private Integer maxY;
    private Integer sourceWidth;
    private Integer sourceHeight;
    private BufferedImage source;
    private ArrayList<MyPoint> good;// массив контуров символа
    private ArrayList<MyPoint> bad;// фон,

    //minx, miny    maxx,miny
    //minx, maxy    maxx,maxy

    // constructor
    public MySymbol(String path) {
        this.path = path;
        minX = 1024;
        maxX = 0;
        minY = 1024;
        maxY = 0;
        // получение ширины и высоты файла
        try {
            File file = new File(path);
            source = ImageIO.read(file);
            sourceWidth = source.getWidth();// ширина
            sourceHeight = source.getHeight();// высота
        } catch (IOException k) {
            System.out.println(k);
        }
        good = new ArrayList<>();
        bad = new ArrayList<>();
    }

    public static void main(String[] args) {
        MySymbol mySymbol = new MySymbol("a.png");
        System.out.println("Путь:" + mySymbol.getPath());
        System.out.println("Ширина: " + mySymbol.getSourceWidth());
        System.out.println("Высота: " + mySymbol.getSourceHeight());
        // сверху вниз, слева направо - (0,0) - левый верхний угол
        for (int y = 0; y < mySymbol.getSourceHeight(); y++) {
            for (int x = 0; x < mySymbol.getSourceWidth(); x++) {
                Color color = new Color(mySymbol.getSource().getRGB(x, y));
                // Получаем каналы этого цвета
                int blue = color.getBlue();
                int red = color.getRed();
                int green = color.getGreen();
                //System.out.println("x: " + x + " y: " + y);
                if (blue != 255 && red != 255 && green != 255) {
                    // получение размеров символа (левая,правая, нижняя, верхнии границы)
                    //  необходимо для дальнейшей записи точек символа в массивы good и bad (символ, фон)
                    //  без лишних фоновых точек, чисто прямоугольник с символом внутри, граница включает точки символа
                    if (x > mySymbol.getMaxX()) {
                        mySymbol.setMaxX(x);
                    }
                    if (x < mySymbol.getMinX()) {
                        mySymbol.setMinX(x);
                    }
                    if (y > mySymbol.getMaxY()) {
                        mySymbol.setMaxY(y);
                    }
                    if (y < mySymbol.getMinY()) {
                        mySymbol.setMinY(y);
                    }
                    //System.out.println("x: " + x + " y: " + y);
                }
            }
        }
        // +1 к максимуму, так правильно
        mySymbol.setMaxX(mySymbol.getMaxX() + 1);
        mySymbol.setMaxY(mySymbol.getMaxY() + 1);
        System.out.println("Символ в ширину: " + (mySymbol.getMaxX() - mySymbol.getMinX()));
        System.out.println("Символ в высоту: " + (mySymbol.getMaxY() - mySymbol.getMinY()));

        // заполнение массивов good и bad
        for (int y = mySymbol.getMinY(); y < mySymbol.getMaxY(); y++) {
            for (int x = mySymbol.getMinX(); x < mySymbol.getMaxX(); x++) {
                Color color = new Color(mySymbol.getSource().getRGB(x, y));
                // Получаем каналы этого цвета
                int blue = color.getBlue();
                int red = color.getRed();
                int green = color.getGreen();
                // если цвет не белый
                if (blue != 255 && red != 255 && green != 255) {
                    mySymbol.getGood().add(new MyPoint(x-mySymbol.getMinX(), y-mySymbol.getMinY()));
                    System.out.println(new MyPoint(x-mySymbol.getMinX(), y-mySymbol.getMinY()));
                } else {
                    mySymbol.getBad().add(new MyPoint(x-mySymbol.getMinX(), y-mySymbol.getMinY()));
                    //System.out.println(new MyPoint(x-mySymbol.getMinX(), y-mySymbol.getMinY()));
                }
            }
        }
        // конец заполнения объекта MySymbol - в нем есть массив точек символа без привязки к остальной картинке и фон
        //  в символе - если совместить оба массива, получим прямоугольник с символом внутри
        // осталось написать код для разбора слова на символы и их сравнение с эталонами
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getMinX() {
        return minX;
    }

    public void setMinX(Integer minX) {
        this.minX = minX;
    }

    public Integer getMaxX() {
        return maxX;
    }

    public void setMaxX(Integer maxX) {
        this.maxX = maxX;
    }

    public Integer getMinY() {
        return minY;
    }

    public void setMinY(Integer minY) {
        this.minY = minY;
    }

    public Integer getMaxY() {
        return maxY;
    }

    public void setMaxY(Integer maxY) {
        this.maxY = maxY;
    }

    public Integer getSourceWidth() {
        return sourceWidth;
    }

    public void setSourceWidth(Integer sourceWidth) {
        this.sourceWidth = sourceWidth;
    }

    public Integer getSourceHeight() {
        return sourceHeight;
    }

    public void setSourceHeight(Integer sourceHeight) {
        this.sourceHeight = sourceHeight;
    }

    public BufferedImage getSource() {
        return source;
    }

    public void setSource(BufferedImage source) {
        this.source = source;
    }

    public ArrayList<MyPoint> getGood() {
        return good;
    }

    public void setGood(ArrayList<MyPoint> good) {
        this.good = good;
    }

    public ArrayList<MyPoint> getBad() {
        return bad;
    }

    public void setBad(ArrayList<MyPoint> bad) {
        this.bad = bad;
    }
}
