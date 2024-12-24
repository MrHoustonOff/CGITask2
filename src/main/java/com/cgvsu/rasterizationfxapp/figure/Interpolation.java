package com.cgvsu.rasterizationfxapp.figure;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Interpolation {

    public static Color interpolateColor(Color startColor, Color endColor, double factor) {
        double red = startColor.getRed() + (endColor.getRed() - startColor.getRed()) * factor;
        double green = startColor.getGreen() + (endColor.getGreen() - startColor.getGreen()) * factor;
        double blue = startColor.getBlue() + (endColor.getBlue() - startColor.getBlue()) * factor;
        double alpha = startColor.getOpacity() + (endColor.getOpacity() - startColor.getOpacity()) * factor;
        return new Color(red, green, blue, alpha);
    }

    public static void drawFilledOvalWithInterpolation(
            GraphicsContext graphicsContext,
            int x, int y,
            int width, int height,
            Color startColor,
            Color endColor) {

        PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        int focusA = width / 2;
        int focusB = height / 2;
        Point center = new Point(x + focusA, y + focusB);

        for (int row = y; row <= y + height; row++) {
            for (int col = x; col <= x + width; col++) {
                double dx = (col - center.getX()) / (double) focusA;
                double dy = (row - center.getY()) / (double) focusB;
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance <= 1) {
                    Color interpolatedColor = interpolateColor(startColor, endColor, distance);
                    pixelWriter.setColor(col, row, interpolatedColor);
                }
            }
        }
    }
}
