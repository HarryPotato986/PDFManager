package com.github.HarryPotato986;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class FileListPane extends JPanel implements ActionListener {
    private JPanel panel;
    protected ArrayList<ListElement> elements = new ArrayList<>();

    FileListPane() {
        super(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(panel);

        this.add(scrollPane);

        //File testFile = new File("D:\\Codeing Projects\\PDFManager\\src\\main\\resources\\images\\wertt wyatt.png");
        //ListElement test = new ListElement(this, testFile);
        //panel.add(test);

    }

    public void reloadList() {
        panel.removeAll();

        if(!elements.isEmpty()) {
            for(ListElement e : elements) {
                e.setAlignmentX(Component.CENTER_ALIGNMENT);
                e.setAlignmentY(Component.TOP_ALIGNMENT);
                e.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
                panel.add(e);
            }
        }
        //panel.add(Box.createVerticalGlue());
        this.revalidate();
        this.repaint();
    }

    public void addFile(File file) {
        elements.add(new ListElement(this, file));
        reloadList();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(ListElement element : elements) {
            if (e.getSource() == element.viewButton) {
                viewImage();
                return;
            } else if (e.getSource() == element.removeButton) {
                removeFile(element);
                return;
            } else if (e.getSource() == element.upButton) {
                moveFile(element, -1);
                return;
            } else if (e.getSource() == element.downButton) {
                moveFile(element, 1);
                return;
            }
        }
    }

    private void viewImage() {

    }

    private void removeFile(ListElement e) {
        //e.removeAll();
        elements.remove(e);
        reloadList();
    }

    private void moveFile(ListElement e, int dIndex) {
        int oldIndex = elements.indexOf(e);
        int newIndex = oldIndex + dIndex;
        if (elements.size() > newIndex && newIndex >= 0) {
            ListElement elementToMove = elements.get(newIndex);
            elements.set(newIndex, e);
            elements.set(oldIndex, elementToMove);

            reloadList();
        }
    }

    public File[] getFileList() {

        return elements.stream().map(ListElement::getFile).toArray(File[]::new);
    }



    private class ListElement extends JPanel {
        protected File file;
        protected JLabel label;
        protected JButton viewButton;
        protected JButton upButton;
        protected JButton downButton;
        protected JButton removeButton;

        ListElement(ActionListener l, File file) {
            super(new GridBagLayout());
            //this.setBackground(new Color(200, 200, 150));

            GridBagConstraints c = new GridBagConstraints();
            c.gridy = 0;

            this.file = file;

            label = new JLabel(file.getName());
            label.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.weightx = 1;
            c.weighty = 1;
            this.add(label, c);

            int buttonPadding = 8;

            viewButton = new JButton("View");
            viewButton.addActionListener(l);
            JPanel vbWrapper = new JPanel();
            vbWrapper.setBorder(BorderFactory.createEmptyBorder(0, buttonPadding, 0, 0));
            vbWrapper.add(viewButton);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 1;
            c.weightx = 0;
            c.weighty = 0;
            this.add(vbWrapper, c);

            JPanel upDownWrapper = new JPanel();
            upDownWrapper.setLayout(new BoxLayout(upDownWrapper, BoxLayout.Y_AXIS));
            upDownWrapper.setBorder(BorderFactory.createEmptyBorder(0, buttonPadding, 0, 0));
            ImageIcon upImg = new ImageIcon("src/main/resources/images/upArrow.png");
            ImageIcon upImgScaled = new ImageIcon(upImg.getImage().getScaledInstance(11, 7, Image.SCALE_FAST));
            upButton = new JButton(upImgScaled);
            upButton.addActionListener(l);
            upButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            upButton.setAlignmentY(Component.CENTER_ALIGNMENT);
            ImageIcon downImg = new ImageIcon("src/main/resources/images/downArrow.png");
            ImageIcon downImgScaled = new ImageIcon(downImg.getImage().getScaledInstance(11, 7, Image.SCALE_FAST));
            downButton = new JButton(downImgScaled);
            downButton.addActionListener(l);
            downButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            downButton.setAlignmentY(Component.CENTER_ALIGNMENT);
            upDownWrapper.add(upButton);
            upDownWrapper.add(downButton);
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 2;
            c.weightx = 0;
            c.weighty = 1;
            this.add(upDownWrapper, c);

            removeButton = new JButton("Remove");
            removeButton.addActionListener(l);
            JPanel rbWrapper = new JPanel();
            rbWrapper.setBorder(BorderFactory.createEmptyBorder(0, buttonPadding, 0, buttonPadding));
            rbWrapper.add(removeButton);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 3;
            c.weightx = 0;
            c.weighty = 0;
            this.add(rbWrapper, c);
        }

        public File getFile() {
            return this.file;
        }
    }
}
