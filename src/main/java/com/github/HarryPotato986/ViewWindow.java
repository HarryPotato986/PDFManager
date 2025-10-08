package com.github.HarryPotato986;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ViewWindow extends JFrame {

    ViewWindow(String imagePath) {
        super("Image Viewer");
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //ImageIcon image = new ImageIcon(imagePath);
        ImageIcon scaledImage = makeScaledImage(imagePath);
        JLabel imageLabel = new JLabel(scaledImage);


        this.setSize(scaledImage.getIconWidth(), scaledImage.getIconHeight());
        this.add(imageLabel);

        this.setVisible(true);
    }

    private ImageIcon makeScaledImage(String path) {
        Dimension zeroDim = new Dimension(0, 0);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        Dimension testDim = new Dimension(50, 50);

        ImageIcon ogImage = new ImageIcon(path);

        int imageWidth = ogImage.getIconWidth();
        int imageHeight = ogImage.getIconHeight();
        Dimension imageSize = new Dimension(imageWidth, imageHeight);
        int newImageWidth;
        int newImageHeight;

        if (compareDimensions(imageSize, zeroDim, screenSize, new Dimension(-25, -25))) {
            newImageWidth = imageWidth;
            newImageHeight = imageHeight;
        } else if (imageWidth > screenWidth - 25){
            newImageWidth = (screenWidth / 2);
            float scalingFactor = ((float) newImageWidth / imageWidth);
            newImageHeight = (int) (imageHeight * scalingFactor);
        } else {
            newImageHeight = (screenHeight / 2);
            float scalingFactor = ((float) newImageHeight / imageHeight);
            newImageWidth = (int) (imageWidth * scalingFactor);
        }

        return new ImageIcon(ogImage.getImage().getScaledInstance(newImageWidth, newImageHeight, Image.SCALE_FAST));
    }

    /***
     * Checks if dim1 fits inside dim2. padding1 is added to dim1 and padding2 is added to dim2
     *
     * @param dim1 First Dimension
     * @param padding1 Padding for dim1
     * @param dim2 Second Dimension
     * @param padding2 Padding for dim2
     * @return true if dim1 fits inside dim2, and false if not. If dim1 = dim2, true is returned.
     */
    private boolean compareDimensions(Dimension dim1, Dimension padding1, Dimension dim2, Dimension padding2) {
        return (dim1.width + padding1.width) <= (dim2.width + padding2.width) && (dim1.height + padding1.height) <= (dim2.height + padding2.height);
    }

    private boolean compareDimensions(Dimension dim1, Dimension dim2) {
        Dimension zeroDim = new Dimension(0, 0);
        return compareDimensions(dim1, zeroDim, dim2, zeroDim);
    }
}
