package com.march.main.factory.impl;

import com.march.main.eneity.ShapeBase;
import com.march.main.eneity.composite.ShapeComposite;
import com.march.main.eneity.impl.Circle;
import com.march.main.eneity.impl.Line;
import com.march.main.eneity.impl.MyButton;
import com.march.main.eneity.impl.Rectangle;
import com.march.main.factory.ShapeFactory;
import com.march.main.observe.Observer;
import com.march.main.observe.impl.ShapeLocationObserver;

import javax.swing.*;
import java.awt.*;

/**
 * 具体类：生产标准的图形工厂
 */
public class StandardShapeFactory implements ShapeFactory {

    /**
     * 单例对象
     * 1.将工厂默认构造方法设置为private 2.通过静态变量保证唯一
     */
    public static final StandardShapeFactory shapeFactory = new StandardShapeFactory();

    private Observer observer;//观察者对象，调用createObserver时加入

    //构造一个观察者，外部调用
    public void createObserver(JLabel jLabel) {
        observer = new ShapeLocationObserver(jLabel);
    }

    // private构造方法保证外部无法实例化:
    private StandardShapeFactory() {
    }

    @Override
    public ShapeBase createCircle(String name, int startX, int startY, int radius) {
        ShapeBase shapeBase = new Circle(name, startX, startY, radius);
        shapeBase.addObserver(observer);
        return shapeBase;
    }

    @Override
    public ShapeBase createLine(String name, int startX, int startY, int endX, int endY) {
        ShapeBase shapeBase = new Line(name, startX, startY, endX, endY);
        shapeBase.addObserver(observer);
        return shapeBase;
    }

    @Override
    public ShapeBase createRectangle(String name, int startX, int startY, int width, int height) {
        ShapeBase shapeBase = new Rectangle(name, startX, startY, width, height);
        shapeBase.addObserver(observer);
        return shapeBase;
    }

    @Override
    public ShapeBase createButton(String name, int startX, int startY, JPanel drawPanel) {
        ShapeBase shapeBase = new MyButton(name, startX, startY, drawPanel);
        shapeBase.addObserver(observer);
        return shapeBase;
    }

    @Override
    public ShapeBase createComposite(String name) {
        ShapeBase shapeBase = new ShapeComposite(name);
        shapeBase.addObserver(observer);
        return shapeBase;
    }

    @Override
    public ShapeBase createRecoverCircle(String name, int startX, int startY, boolean checked, boolean filled, Color lineColor, Color fillColor, int radius) {
        ShapeBase shapeBase = new Circle(name, startX, startY, checked, filled, lineColor, fillColor, radius);
        shapeBase.addObserver(observer);
        return shapeBase;
    }

    @Override
    public ShapeBase createRecoverLine(String name, int startX, int startY, boolean checked, boolean filled, Color lineColor, Color fillColor, int endX, int endY) {
        ShapeBase shapeBase = new Line(name, startX, startY, checked, filled, lineColor, fillColor, endX, endY);
        shapeBase.addObserver(observer);
        return shapeBase;
    }

    @Override
    public ShapeBase createRecoverRectangle(String name, int startX, int startY, boolean checked, boolean filled, Color lineColor, Color fillColor, int width, int height) {
        ShapeBase shapeBase = new Rectangle(name, startX, startY, checked, filled, lineColor, fillColor, width, height);
        shapeBase.addObserver(observer);
        return shapeBase;
    }

    @Override
    public ShapeBase createRecoverButton(String name, int startX, int startY, boolean checked, boolean filled, Color lineColor, Color fillColor, JPanel drawPanel) {
        ShapeBase shapeBase = new MyButton(name, startX, startY, checked, filled, lineColor, fillColor, drawPanel);
        shapeBase.addObserver(observer);
        return shapeBase;
    }
}
