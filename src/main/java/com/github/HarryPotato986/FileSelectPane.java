package com.github.HarryPotato986;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class FileSelectPane extends JPanel {
    public JTextField textField;
    public JButton button;

    FileSelectPane(ActionListener l, String textFieldText, String buttonText) {
        this(l, textFieldText, buttonText, BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    FileSelectPane(ActionListener l, String textFieldText, String buttonText, Border b) {
        super();

        this.setLayout(new BorderLayout());
        this.setBorder(b);

        textField = new JTextField(textFieldText);

        button = new JButton(buttonText);
        button.addActionListener(l);

        JPanel buttonWrapper = new JPanel(new BorderLayout());
        buttonWrapper.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        buttonWrapper.add(button, BorderLayout.CENTER);

        this.add(textField, BorderLayout.CENTER);
        this.add(buttonWrapper, BorderLayout.EAST);
    }

    public void setTextFieldText(String newText) {
        textField.setText(newText);
    }

    public String getTextFieldText() {
        return textField.getText();
    }

}
