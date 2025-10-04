package com.github.HarryPotato986;

import org.openpdf.text.Document;
import org.openpdf.text.DocumentException;
import org.openpdf.text.Image;
import org.openpdf.text.Rectangle;
import org.openpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("PDF Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280,720);

        //frame.setVisible(true);

        Document d = new Document();


        try {
            PdfWriter.getInstance(d, new FileOutputStream("outputs/test.pdf"));

            d.open();

            Image testImage = Image.getInstance("src/main/resources/images/wertt wyatt.png");
            //testImage.scalePercent(50);

            addNewImagePage(d, testImage);

            testImage.scalePercent(50);

            addNewImagePage(d, testImage);

            d.close();

        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }
    }

    private static void addNewImagePage(Document d, Image image) {
        image.setAbsolutePosition(0,0);

        d.setPageSize(getPageSize(image));
        d.newPage();

        d.add(image);
    }

    private static void addNewImagePage(Document d, String filepath) {
        try {
            Image image = Image.getInstance(filepath);
            addNewImagePage(d,image);
        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }
    }

    private static Rectangle getPageSize(Image image) {
        return new Rectangle(image.getScaledWidth(), image.getScaledHeight());
    }
}