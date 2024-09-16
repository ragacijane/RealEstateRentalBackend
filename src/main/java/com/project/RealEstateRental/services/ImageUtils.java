package com.project.RealEstateRental.services;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.AlphaComposite;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static BufferedImage addWatermark(BufferedImage sourceImage, BufferedImage watermarkImage) {
        Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

        int sourceWidth = sourceImage.getWidth();
        int sourceHeight = sourceImage.getHeight();

        // Calculate the shorter side to determine the scaling factor for the watermark
        int shorterSide = Math.min(sourceWidth, sourceHeight);

        // Set both watermark width and height to 25% of the shorter side (since watermark is square)
        int watermarkSize = (int) (shorterSide * 0.25);

        // Scale the watermark image
        Image scaledWatermark = watermarkImage.getScaledInstance(watermarkSize, watermarkSize, Image.SCALE_SMOOTH);

        // Calculate the position to center the watermark
        int watermarkX = (sourceWidth - watermarkSize) / 2;
        int watermarkY = (sourceHeight - watermarkSize) / 2;

        // Set the opacity to 50%
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

        // Draw the watermark at the center
        g2d.drawImage(scaledWatermark, watermarkX, watermarkY, null);

        // Clean up
        g2d.dispose();

        return sourceImage;
    }

    public static BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(new File(path));
    }

    public static void saveImage(BufferedImage image, String path) throws IOException {
        ImageIO.write(image, "png", new File(path));
    }
}
