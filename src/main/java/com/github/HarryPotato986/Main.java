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
        BaseFrame frame = new BaseFrame("PDF Manager");


        /*
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
        */
    }
}