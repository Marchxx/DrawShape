package com.march.main.factory.impl;

import com.march.main.eneity.ShapeBase;
import com.march.main.eneity.composite.ShapeComposite;
import com.march.main.eneity.impl.Circle;
import com.march.main.eneity.impl.Line;
import com.march.main.eneity.impl.MyButton;
import com.march.main.eneity.impl.Rectangle;
import com.march.main.factory.ShapeFactory;

import javax.swing.*;

/**
 * 具体类：生产标准的图形工厂
 */
public class StandardShapeFactory implements ShapeFactory {

    /**
     * 单例对象
     * 1.将工厂默认构造方法设置为private 2.通过静态变量保证唯一
     */
    public static final ShapeFactory shapeFactory = new StandardShapeFactory();

    // private构造方法保证外部无法实例化:
    private StandardShapeFactory() {

    }

    @Override
    public ShapeBase createCircle(String name, int startX, int startY, int radius) {
        return new Circle(name, startX, startY, radius);
    }

    @Override
    public ShapeBase createLine(String name, int startX, int startY, int endX, int endY) {
        return new Line(name, startX, startY, endX, endY);
    }

    @Override
    public ShapeBase createRectangle(String name, int startX, int startY, int width, int height) {
        return new Rectangle(name, startX, startY, width, height);
    }

    @Override
    public ShapeBase createButton(String name, int startX, int startY, JPanel drawPanel) {
        return new MyButton(name, startX, startY, drawPanel);
    }

    @Override
    public ShapeBase createComposite(String name) {
        return new ShapeComposite(name);
    }
}
