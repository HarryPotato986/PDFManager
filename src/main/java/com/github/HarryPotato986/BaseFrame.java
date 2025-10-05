package com.github.HarryPotato986;

import org.openpdf.text.Document;
import org.openpdf.text.DocumentException;
import org.openpdf.text.Rectangle;
import org.openpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BaseFrame extends JFrame implements ActionListener {
    private JPanel panel;

    private FileSelectPane imagePicker;
    private FileListPane list;
    private FileSelectPane outputPicker;
    private JButton makeButton;
    private JLabel resultMessage;
    private JTextField fileName;

    BaseFrame(String title) {
        super(title);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.err.println("Could not set native look and feel: " + e.getMessage());
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280,720);

        panel = new JPanel();

        panel.setLayout(new BorderLayout());

        imagePicker = new FileSelectPane(this, "Choose image(s) to add to PDF", "Browse",
                BorderFactory.createEmptyBorder(10, 10, 0, 10));
        panel.add(imagePicker, BorderLayout.NORTH);

        list = new FileListPane();
        panel.add(list, BorderLayout.CENTER);

        JPanel southWrapper = new JPanel(new GridBagLayout());
        southWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints c = new GridBagConstraints();

        outputPicker = new FileSelectPane(this, "Choose output folder", "Browse",
                BorderFactory.createEmptyBorder(0, 0, 10, 0));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = 1;
        southWrapper.add(outputPicker, c);

        makeButton = new JButton("Make PDF");
        makeButton.addActionListener(this);
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = 1;
        southWrapper.add(makeButton, c);

        fileName = new JTextField("Write file name here");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = 1;
        southWrapper.add(fileName, c);

        resultMessage = new JLabel("");
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = 1;
        southWrapper.add(resultMessage, c);

        panel.add(southWrapper, BorderLayout.SOUTH);

        this.add(panel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resultMessage.setText("");
        panel.revalidate();
        panel.repaint();
        if (e.getSource() == imagePicker.button) {
            SwingUtilities.invokeLater(() -> {
                FilePicker picker = new FilePicker();
                File[] files = picker.pickFile(this);
                if (files.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (File file : files) {
                        sb.append("\"");
                        sb.append(file.getName());
                        sb.append("\", ");

                        list.addFile(file);
                    }
                    sb.setLength(sb.length() - 2);
                    String newText = sb.toString();
                    imagePicker.setTextFieldText(newText);
                }
            });
        } else if (e.getSource() == outputPicker.button) {
            SwingUtilities.invokeLater(() -> {
                FolderPicker picker = new FolderPicker();
                File folder = picker.pickFolder(this);
                if (folder != null && folder.isDirectory()) {
                    outputPicker.setTextFieldText(folder.getPath());
                }
            });
        } else if (e.getSource() == makeButton){
            File[] files = list.getFileList();
            if (files.length == 0) {
                return;
            }
            Document d = new Document();

            try {
                String name = fileName.getText();
                if (!name.toLowerCase().endsWith(".pdf")) {
                    name += ".pdf";
                }
                PdfWriter.getInstance(d, new FileOutputStream(outputPicker.getTextFieldText() + "/" + name));

                d.open();

                for (File f : files) {
                    System.out.println(f.getName());
                    addNewImagePage(d, f.getPath());
                }

                d.close();

                resultMessage.setText("Done");
                panel.revalidate();
                panel.repaint();
            } catch (DocumentException | IOException de) {
                System.err.println(de.getMessage());
            }
        }
    }

    private static void addNewImagePage(Document d, org.openpdf.text.Image image) {
        image.setAbsolutePosition(0,0);

        d.setPageSize(getPageSize(image));
        d.newPage();

        d.add(image);
    }

    private static void addNewImagePage(Document d, String filepath) {
        try {
            org.openpdf.text.Image image = org.openpdf.text.Image.getInstance(filepath);
            addNewImagePage(d,image);
        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }
    }

    private static org.openpdf.text.Rectangle getPageSize(org.openpdf.text.Image image) {
        return new Rectangle(image.getScaledWidth(), image.getScaledHeight());
    }
}
