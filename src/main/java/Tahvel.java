import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.AdjustmentListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Tahvel {
    private static String pass = null;

    public static void main(String[] args) throws InterruptedException {
        createFrame();
    }

    public static void createFrame() {
        JFrame frame = new JFrame("Tahvel Test v-0.0.1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 400);

        //Создание навигационного меню и его компонентов
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("FILE");
        JMenu help = new JMenu("Help");
        menuBar.add(file);
        menuBar.add(help);
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save as");
        file.add(open);
        file.add(save);

        //Создание панели в низу графического интерфейса
        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel label = new JLabel("Enter Personal Code");
        JTextField tf = new JTextField(10); // accepts upto 10 characters
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(send);
        panel.add(reset);

        DefaultListModel<String> dlm = new DefaultListModel<String>();
        JList<String> list = new JList(dlm);

        //Добавление компонентов в навигационное меню
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, list);
        frame.getRootPane().setDefaultButton(send);
        frame.add(new JScrollPane(list));

        send.addActionListener(e -> {
            String text = tf.getText();
            dlm.addElement(text);
            tf.setText(null);
        });

        reset.addActionListener(e -> {
            dlm.removeAllElements();
        });
        list.addListSelectionListener((ListSelectionListener) e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedValue = list.getSelectedValue();
                pass = selectedValue;
                try {
                    doShit();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        open.addActionListener(e -> {
            //Действие
            String absolutePath = FileChooser.choose(null);
            if (absolutePath != null) {
                dlm.removeAllElements();
            }
            try {
                try (var fileReader = new BufferedReader(new FileReader(absolutePath))) {
                    fileReader.lines().forEach(s -> dlm.addElement(s));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        save.addActionListener(e -> {
            String absolutePath = FileChooser.chooseDirectory();
            ArrayList<String> objects = new ArrayList<>();
            for (int i = 0; i < list.getModel().getSize(); i++) {
                objects.add(list.getModel().getElementAt(i));
            }
            save(objects, absolutePath);
        });
        //Разместим программу по центру
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void save(ArrayList<String> list, String path) {
        try {
            Files.write(Path.of(path), list, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        var file = new File(path);
//        try (var bufferedWriter = new BufferedWriter(new FileWriter(file));
//             var stream = list.stream()) {
//            stream.forEach(e -> {
//                try {
//                    bufferedWriter.write(e);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static void doShit() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/jekaterinakulikova/Desktop/chromedriver");
        ChromeDriver DRIVER = new ChromeDriver();

        DRIVER.get
                (
                        "https://tahvel.edu.ee/"
                );

        DRIVER.manage().window().maximize();

        JavascriptExecutor js = DRIVER;


        // Открывает логин через SMART-ID
        DRIVER.findElement
                (
                        By.className
                                (
                                        "user-section"
                                )
                ).click();
        Thread.sleep
                (
                        1000
                );
        DRIVER.findElement
                (
                        By.xpath
                                (
                                        "/html/body/div[3]/md-dialog/md-dialog-content/div/div/div/div[1]/button[2]"
                                )
                ).click();
        Thread.sleep
                (
                        1000
                );
        DRIVER.findElement
                (
                        By.xpath
                                (
                                        "/html/body/div[3]/md-dialog/md-dialog-content/div/md-content[4]/a"
                                )
                ).click();
        Thread.sleep
                (
                        1000
                );
        DRIVER.findElement(By.xpath("/html/body/div/div/div[3]/div[2]/nav/ul/li[3]/a/span")).click();
        Thread.sleep
                (
                        1000
                );
        DRIVER.findElement
                (
                        By.xpath
                                (
                                        "/html/body/div/div/div[3]/div[2]/main/div[3]/div/div[1]/div[2]/form/table/tbody/tr[1]/td[2]/div[1]/input"
                                )
                ).click();
        Thread.sleep
                (
                        1000
                );
        // Вводит личный код и используая скрипт для шифврования его от лишних глаз
        WebElement password = DRIVER.findElement
                (
                        By.xpath
                                (
                                        "/html/body/div/div/div[3]/div[2]/main/div[3]/div/div[1]/div[2]/form/table/tbody/tr[1]/td[2]/div[1]/input"
                                )
                );

        js.executeScript("document.getElementById('sid-personal-code').type='password'; console.log('check')");
        password.sendKeys(pass);
        DRIVER.findElement
                (
                        By.xpath
                                (
                                        "/html/body/div/div/div[3]/div[2]/main/div[3]/div/div[1]/div[2]/form/table/tbody/tr[2]/td[2]/button"
                                )
                ).click();


    }

}
