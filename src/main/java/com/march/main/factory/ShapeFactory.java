package com.march.main.factory;

import com.march.main.eneity.ShapeBase;

import javax.swing.*;
import java.awt.*;

/**
 * 用于创建图形的工厂
 */
public interface ShapeFactory {

    ShapeBase createCircle(String name, int startX, int startY, int radius);

    ShapeBase createLine(String name, int startX, int startY, int endX, int endY);

    ShapeBase createRectangle(String name, int startX, int startY, int width, int height);

    ShapeBase createButton(String name, int startX, int startY, JPanel drawPanel);

    ShapeBase createRecoverCircle(String name, int startX, int startY, boolean checked, boolean filled, Color lineColor, Color fillColor, int radius);

    ShapeBase createRecoverLine(String name, int startX, int startY, boolean checked, boolean filled, Color lineColor, Color fillColor, int endX, int endY);

    ShapeBase createRecoverRectangle(String name, int startX, int startY, boolean checked, boolean filled, Color lineColor, Color fillColor, int width, int height);

    ShapeBase createRecoverButton(String name, int startX, int startY, boolean checked, boolean filled, Color lineColor, Color fillColor, JPanel drawPanel);

    ShapeBase createComposite(String name);

}
