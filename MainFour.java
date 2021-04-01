////4лаба Входные данные: картинка с текстом.
////        Необходимо разделить картинку на символы и произвести сравнение с эталонами.
////        Алгоритм:
////        1.	загрузка изображения: входное изображение в высоту 16, в длину не известно сколько символов
////        2.	изменение размера изображения до размера эталона: слишком долго, ф топку
////        3.	сравнение с эталоном: разбить входную картинку на картинки высотой в символ, длиной в символ,
////          то есть из картинки вычленить символ и прогнать его по выборке, причем из символов выборки также вычленить
////          символ - прямоугольник с минимальной высотой и длиной
////        Итог: в виде строки выдать распознанные данные.

import java.awt.*;
import java.beans.IntrospectionException;
import java.util.ArrayList;

public class MainFour {
    private StorageMySymbol storage;
    private ArrayList<Integer> notEmptyRows;
    private ArrayList<MyBorder> borders;
    private String s;// выходная строка

    public MainFour() {
        this.storage = new StorageMySymbol();
        this.borders = new ArrayList<>();
        this.notEmptyRows = new ArrayList<>();// массив с координатами пустых столбцов
        this.s = "";
    }

    // метод добавления объекта MySymbol в storage
    public void add(String pathname) {
        MySymbol mySymbol = new MySymbol(pathname);
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
        for (int x = mySymbol.getMinX(); x < mySymbol.getMaxX(); x++) {
            for (int y = mySymbol.getMinY(); y < mySymbol.getMaxY(); y++) {
                Color color = new Color(mySymbol.getSource().getRGB(x, y));
                // Получаем каналы этого цвета
                int blue = color.getBlue();
                int red = color.getRed();
                int green = color.getGreen();
                // если цвет не белый
                if (blue != 255 && red != 255 && green != 255) {
                    mySymbol.getGood().add(new MyPoint(x - mySymbol.getMinX(), y - mySymbol.getMinY()));
                    System.out.println(new MyPoint(x - mySymbol.getMinX(), y - mySymbol.getMinY()));
                } else {
                    mySymbol.getBad().add(new MyPoint(x - mySymbol.getMinX(), y - mySymbol.getMinY()));
                    //System.out.println(new MyPoint(x-mySymbol.getMinX(), y-mySymbol.getMinY()));
                }
            }
        }
        storage.add(mySymbol);
        // конец заполнения объекта MySymbol - в нем есть массив точек символа без привязки к остальной картинке и фон
        //  в символе - если совместить оба массива, получим прямоугольник с символом внутри
        // осталось написать код для разбора слова на символы и их сравнение с эталонами
    }

    // main
    public static void main(String[] args) {
        MainFour mainFour = new MainFour();
        mainFour.add("a.png");
        mainFour.add("b.png");
        mainFour.add("c.png");
        mainFour.add("d.png");
        mainFour.add("e.png");
        //mainFour.add("x.png");
        System.out.println();
        mainFour.parse("abcdex.png");
        //mainFour.parse("abcdeedcba.png");
        System.out.println(mainFour.getS());
    }

    public void parse(String pathname) {
        MySymbol mySymbol = new MySymbol(pathname);
        System.out.println("Путь:" + mySymbol.getPath());
        System.out.println("Ширина: " + mySymbol.getSourceWidth());
        System.out.println("Высота: " + mySymbol.getSourceHeight());
        // сверху вниз, слева направо - (0,0) - левый верхний угол
        // примем допущение, что между символами 1 пустой пиксель
        // тогда найдем все не пустые столбцы - в них есть символ
        Integer y = 0;
        for (Integer x = 0; x < mySymbol.getSourceWidth(); x++) {
            y = 0;
            while (y < mySymbol.getSourceHeight()) {
                Color color = new Color(mySymbol.getSource().getRGB(x, y));
                // Получаем каналы этого цвета
                int blue = color.getBlue();
                int red = color.getRed();
                int green = color.getGreen();
                //System.out.println("x: " + x + " y: " + y);
                // если пиксель не пустой
                if (blue != 255 && red != 255 && green != 255) {
                    notEmptyRows.add(x);
                    y = mySymbol.getSourceHeight();
                    //System.out.println(x);
                }
                y++;
            }
        }
        y = 0;
        System.out.println();
        // теперь в этих не пустых столбцах найдем верхнюю и нижнюю границу символа
        // пройдем по всем столбцам
        Integer minX = 1024;
        Integer maxX = 0;
        Integer minY = 1024;
        Integer maxY = 0;
        for (int x = 0; x < mySymbol.getSourceWidth(); x++) {
            // если столбец содержится в массиве не пустых столбцов
            if (notEmptyRows.contains(x)) {
                // тогда ищем: длину символа (minX, maxX)
                //  и высоту: (minY, maxY)
                // если x==0 или предыдущий столбец пустой - данный x=minX
                if (x == 0) {
                    minX = x;
                } else if (!(notEmptyRows.contains(x - 1))) {
                    minX = x;
                }
                // если x==mySymbol.getSourceWidth()-1 (последний) или
                //  в таблице пустых столбцов нет следующего x - данный x=maxX
                if (x == mySymbol.getSourceWidth() - 1) {
                    maxX = x;
                } else if (!(notEmptyRows.contains(x + 1))) {
                    maxX = x;
                }
                // теперь надо найти minY, maxY
                y = 0;
                while (y < mySymbol.getSourceHeight()) {
                    Color color = new Color(mySymbol.getSource().getRGB(x, y));
                    // Получаем каналы этого цвета
                    int blue = color.getBlue();
                    int red = color.getRed();
                    int green = color.getGreen();
                    //System.out.println("x: " + x + " y: " + y);
                    // если пиксель не пустой
                    if (blue != 255 && red != 255 && green != 255) {
                        // minY
                        if (y < minY) {
                            minY = y;
                        }
                        // maxY
                        if (y > maxY) {
                            maxY = y;
                        }
                    }
                    y++;
                }
            } else if (minX != 1024 && maxX != 0) {
                // теперь имеем minX, maxX и minY,maxY
                System.out.println("minX: " + minX);
                System.out.println("maxX: " + maxX);
                System.out.println("minY: " + minY);
                System.out.println("maxY: " + maxY);
                System.out.println();
                // добавим объект класса MyBorder в массив рамок, чтоб сохранить
                //  значения расположения найденного символа на картинке
                ArrayList<Integer> mas = new ArrayList<>();
                for (int i = minX; i < maxX; i++) {
                    mas.add(i);
                }
                borders.add(new MyBorder(minX, maxX, minY, maxY, mas));
                // дальше идет сравнение с эталонами
                Integer k = 0;
                while (k < storage.getLength()) {
                    System.out.println(k);
                    MySymbol symbol = storage.getSymbolById(k);
                    Integer goodLength = symbol.getGood().size();
                    System.out.println("goodLength: " + goodLength);
                    Integer bufGoodLength = 0;
                    for (int i = minX; i <= maxX; i++) {
                        for (int j = minY; j <= maxY; j++) {
                            Color color = new Color(mySymbol.getSource().getRGB(i, j));// берется цыет из ПРОВЕРЯЕМОЙ картинки
                            // Получаем каналы этого цвета
                            int blue = color.getBlue();
                            int red = color.getRed();
                            int green = color.getGreen();
                            //System.out.println("i: " + i + " j: " + j);
                            // если пиксель не пустой
                            if (blue != 255 && red != 255 && green != 255) {
                                System.out.println("i: " + (i - minX) + " j: " + (j - minY));
                                // сравнение его позиции с позицией закраш. пикселя ы эталоне
                                //if (symbol.getGood().contains(new MyPoint(i - minX, j - minY))) {}
                                int xGood = symbol.getGood().get(bufGoodLength).getX();
                                int yGood = symbol.getGood().get(bufGoodLength).getY();
                                if (xGood == (i - minX) && yGood == (j - minY)) {
                                    //System.out.println("ку");
                                    bufGoodLength++;
                                } else if (xGood == (i - minX) && yGood == (j - minY)) {
                                    // в веденной есть, а в эталоне нет
                                    bufGoodLength = 0;
                                    //System.out.println("end");
                                    k++;
                                }

                            }
                        }
                    }// end for (int i = minX; i <= maxX; i++){}

                    // проверка, если все закрашенные точки входной картинки совпали с эталоном, то пишем в выходную строку название картинки (a.png -> s+=a;)
                    if (goodLength == bufGoodLength) {
                        s += symbol.getPath().replace(".png","");
                        k = storage.getLength();
                    } else {
                        s += "-";
                    }
                    bufGoodLength = 0;
                    k++;
                }
                k = 0;
                minX = 1024;
                maxX = 0;
                minY = 1024;
                maxY = 0;
            }
        }
        s=s.replace("-","");
        y = 0;
    }

    public StorageMySymbol getStorage() {
        return storage;
    }

    public void setStorage(StorageMySymbol storage) {
        this.storage = storage;
    }

    public ArrayList<Integer> getNotEmptyRows() {
        return notEmptyRows;
    }

    public void setNotEmptyRows(ArrayList<Integer> notEmptyRows) {
        this.notEmptyRows = notEmptyRows;
    }

    public ArrayList<MyBorder> getBorders() {
        return borders;
    }

    public void setBorders(ArrayList<MyBorder> borders) {
        this.borders = borders;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
