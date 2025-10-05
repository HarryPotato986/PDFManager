package com.github.HarryPotato986;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FilePicker {

    public File[] pickFile(JFrame f) {

        JFileChooser dialog = new JFileChooser();
        dialog.setDialogTitle("Choose file(s) to open");
        dialog.setMultiSelectionEnabled(true);
        dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int userSelection = dialog.showOpenDialog(f);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            return dialog.getSelectedFiles();
        } else {
            return new File[0];
        }
    }
}
