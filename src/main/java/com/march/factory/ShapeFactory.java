package com.march.factory;

import com.march.eneity.ShapeBase;

import javax.swing.*;

/**
 * 用于创建图形的工厂
 */
public interface ShapeFactory {

    ShapeBase createCircle(String name, int startX, int startY, int radius);

    ShapeBase createLine(String name, int startX, int startY, int endX, int endY);

    ShapeBase createRectangle(String name, int startX, int startY, int width, int height);

    ShapeBase createButton(String name, int startX, int startY, JPanel drawPanel);

    ShapeBase createComposite(String name);

}
