package com.github.HarryPotato986;

import javax.swing.*;
import java.io.File;

public class FolderPicker {

    public File pickFolder(JFrame f) {

        JFileChooser dialog = new JFileChooser();
        dialog.setDialogTitle("Choose file(s) to open");
        dialog.setMultiSelectionEnabled(false);
        dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = dialog.showOpenDialog(f);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            return dialog.getSelectedFile();
        } else {
            return null;
        }
    }
}
