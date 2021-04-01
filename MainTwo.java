//// Класс представления различного внешнего вида кнопок JButton
////3лаба Необходимо задать5 черно-белых эталонов (букв алфавита) в формате 16*16 пикселей.
////        Алгоритм:
////        1.	загрузка изображения
////        2.	изменение размера изображения до размера эталона
////        3.	сравнение с эталоном
////        4.	механизм голосования, если эталон, то необходимо показать какой, если не эталон, то записать как новый эталон
////        5.	выполнить в виде всплывающего окна
////        6.	задать кнопки: Сравнить, да, нет.
//
////4лаба Входные данные: картинка с текстом.
////        Необходимо разделить картинку на символы и произвести сравнение с эталонами.
////        Алгоритм:
////        1.	загрузка изображения: входное изображение в высоту 16, в длину не известно сколько символов
////        2.	изменение размера изображения до размера эталона: слишком долго, ф топку
////        3.	сравнение с эталоном: разбить входную картинку на картинки высотой в символ, длиной в символ,
////          то есть из картинки вычленить символ и прогнать его по выборке, причем из символов выборки также вычленить
////          символ - прямоугольник с минимальной высотой и длиной
////        Итог: в виде строки выдать распознанные данные.
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.Scanner;
//
//public class MainTwo extends JFrame {
//    private static final long serialVersionUID = 1L;
//    JButton button1;
//    JButton button2;
//    JButton button3;
//    JButton button4;
//    JButton button5;
//    String firstIconName;// относительный путь к проверяемой картинке
//    String secondIconName;
//    private StorageMySymbol storage = new StorageMySymbol();
//    String etalonName;
//    MySymbol symbolFromImage;
//    Integer scaleX;
//    Integer scaleY;
//    Integer stepX;
//    Integer stepY;
//
//    public MainTwo() {
//        super("лаба4");
//        scaleX = 1;
//        scaleY = 1;
//        stepX = 16;
//        stepY = 16;
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        // Устанавливаем последовательное расположение
//        Container container = getContentPane();
//        //container.setLayout(new FlowLayout( FlowLayout.LEFT, 10, 10));
//        container.setLayout(new GridLayout(2, 3));
//
//        // кнопка с входной картинкой
//        button1 = new JButton();
//        firstIconName = "empty.png";
//        button1.setIcon(new ImageIcon(firstIconName));
//        // Убираем все ненужные рамки и закраску
//        button1.setBorderPainted(false);
//        button1.setFocusPainted(false);
//        button1.setContentAreaFilled(false);
//        container.add(button1);
//
//        // кнопка сравинть
//        button2 = new JButton("Сравнить");
//        // Подключение слушателей событий
//        button2.addActionListener(new ListenerAction());
//        container.add(button2);
//
//        // кнопка со второй картинкой
//        button3 = new JButton();
//        secondIconName = "empty.png";
//        button3.setIcon(new ImageIcon(secondIconName));
//        // Убираем все ненужные рамки и закраску
//        button3.setBorderPainted(false);
//        button3.setFocusPainted(false);
//        button3.setContentAreaFilled(false);
//        container.add(button3);
//
//        // кнопка 'Да'
//        button4 = new JButton("Да");
//        // Подключение слушателей событий
//        button4.addActionListener(new ListenerAction());
//        container.add(button4);
//
//        // кнопка 'Нет'
//        button5 = new JButton("Нет");
//        // Подключение слушателей событий
//        button5.addActionListener(new ListenerAction());
//        container.add(button5);
//
//        // выводим окно на экран
//        setSize(400, 350);
//        setVisible(true);
//    }
//
//    class ListenerAction implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//            System.out.println("Нажатие кнопки! От - " +
//                    e.getActionCommand() + "\n");
//            switch (e.getActionCommand()) {
//                case "Сравнить": {
//                    System.out.println(button2.getText());
//                    // получение матрицы из входной картинки
//                    Integer buf256[][] = new Integer[16][16];
//                    try {
//                        // Открываем изображение
//                        File file = new File(firstIconName);
//                        BufferedImage source = ImageIO.read(file);
//                        Integer sourceWidth = source.getWidth();
//                        Integer sourceHeight = source.getHeight();
//
//                        if (sourceWidth > 16) {
//                            scaleX = sourceWidth / 16;
//                        } else if (sourceWidth < 16) {
//                            stepX = sourceWidth;
//                        }
//                        if (sourceHeight > 16) {
//                            scaleY = sourceHeight / 16;
//                        } else if (sourceHeight < 16) {
//                            stepY = sourceHeight;
//                        }
//                        //буферная переменная размером 16x16
//                        //for (int x = 0; x < stepX; x = x + scaleX) {
//                        //    for (int y = 0; y < stepY; y = y + scaleY) {}}
//                        for (int x = 0; x < 16; x++) {
//                            for (int y = 0; y < 16; y++) {
//                                if(x < stepX && y<stepY){
//                                    // Получаем цвет текущего пикселя
//                                    Color color = new Color(source.getRGB(x*scaleX, y*scaleY));
//                                    // Получаем каналы этого цвета
//                                    int blue = color.getBlue();
//                                    int red = color.getRed();
//                                    int green = color.getGreen();
//                                    //зануляем буферную переменную
//                                    buf256[x][y] = 0;
//                                    // если цвет не белый
//                                    if (blue != 255 && red != 255 && green != 255) {
//                                        buf256[x][y] = 1;
//                                    }
//                                }else{
//                                    buf256[x][y] = 0;
//                                }
//
//                            }
//                        }
//                    } catch (IOException k) {
//                        System.out.println(k);
//                    }
//
//                    symbolFromImage = new MySymbol(firstIconName, buf256);
//
//                    // получение разницы между матрицей картинки и эталоном (по количеству эталонов)(массив значений разницы матриц добавить надо)
//                    Integer buf = 257;// минимальное различие между картинкой и эталоном
//                    Integer bufBuf = buf;
//                    for (Integer i = 0; i < storage.getLength(); i++) {
//                        buf = symbolFromImage.equals(storage.getSymbolById(i));
//                        System.out.println("С " + storage.getSymbolById(i).getPath() + " различий: " + buf);
//                        if (buf < bufBuf) {
//                            etalonName = storage.getSymbolById(i).getPath();
//                            bufBuf = buf;
//                        }
//                    }
//
//                    // установить картинки
//                    button1.setIcon(new ImageIcon(firstIconName));
//                    button3.setIcon(new ImageIcon(etalonName));
//
//
//                    break;
//                }
//                case "Да": {
//                    System.out.println("Да");
//                    // вывод входной картинки
//                    Integer[][] buf256 = symbolFromImage.getMatrix();
//                    System.out.println(symbolFromImage.getPath());
//                    System.out.println("----------------------------------------------------");
//                    for (int y = 0; y < 16; y++) {
//                        for (int x = 0; x < 16; x++) {
//                            if(buf256[x][y]==0){
//                                System.out.print((char)'\u25A1');
//                            }else{
//                                System.out.print((char)'\u25A0');
//                            }
//                        }
//                        System.out.println();
//                    }
//                    System.out.println("----------------------------------------------------");
//                    System.out.println("stepX: "+stepX+"\n"+
//                            "stepY: "+stepY+"\n"+
//                            "scaleX: "+scaleX+"\n"+
//                            "scaleY "+scaleY);
//                    System.out.println();
//                    break;
//                }
//                case "Нет": {
//                    System.out.println("Нет");
//                    storage.add(symbolFromImage);
//                    System.out.println(symbolFromImage.getPath() + " добавлен в хранилище");
//                    break;
//                }
//                default: {
//                    System.out.println("ListenerAction: кнопка с таким текстом не обрабатывается");
//                }
//            }
//        }
//
//    }
//
//    public StorageMySymbol getStorage() {
//        return storage;
//    }
//
//    // добавление эталона в хранилище
//    public void addMySymbol(String pathname) {
//        System.out.println(pathname);
//        try {
//            // Открываем изображение
//            File file = new File(pathname);
//            BufferedImage source = ImageIO.read(file);
//            //буферная переменная размером 16x16
//            Integer buf256[][] = new Integer[16][16];
//            for (int x = 0; x < 16; x++) {
//                for (int y = 0; y < 16; y++) {
//                    // Получаем цвет текущего пикселя
//                    Color color = new Color(source.getRGB(x, y));
//                    // Получаем каналы этого цвета
//                    int blue = color.getBlue();
//                    int red = color.getRed();
//                    int green = color.getGreen();
//                    //зануляем буферную переменную
//                    buf256[x][y] = 0;
//                    // если цвет не белый
//                    if (blue != 255 && red != 255 && green != 255) {
//                        buf256[x][y] = 1;
//                    }
//                }
//            }
//            // добавление картинки в хранилище эталонов
//            this.storage.add(new MySymbol(pathname, buf256));
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }
//
//    // главный метод
//    public static void main(String[] args) {
//        MainTwo mainTwo = new MainTwo();
//        mainTwo.addMySymbol("a.png");
//        mainTwo.addMySymbol("b.png");
//        mainTwo.addMySymbol("c.png");
//        mainTwo.addMySymbol("d.png");
//        mainTwo.addMySymbol("e.png");
//
//        boolean endCycle = true;
//        Scanner scanner = new Scanner(System.in);
//        // создание MySymbol из полученной матрицы
//
//        while (endCycle) {
//            System.out.println("Введите относительный путь до сравниваемой картинки, например: x.png");
//            mainTwo.setFirstIconName(scanner.next());
//            System.out.println(mainTwo.getFirstIconName());
//
//            //endCycle = false;
//        }//eng while
//    }
//
//    public String getFirstIconName() {
//        return firstIconName;
//    }
//
//    public void setFirstIconName(String firstIconName) {
//        this.firstIconName = firstIconName;
//    }
//
//    public String getSecondIconName() {
//        return secondIconName;
//    }
//
//    public void setSecondIconName(String secondIconName) {
//        this.secondIconName = secondIconName;
//    }
//}