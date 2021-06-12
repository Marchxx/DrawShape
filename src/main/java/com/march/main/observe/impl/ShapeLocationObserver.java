package com.march.main.observe.impl;

import com.march.main.eneity.ShapeBase;
import com.march.main.observe.Observer;

import javax.swing.*;
import java.awt.*;

/**
 * 图形位置观察者
 */
public class ShapeLocationObserver implements Observer {

    private JLabel jlabel;

    public ShapeLocationObserver(JLabel jlabel) {
        this.jlabel = jlabel;
    }

    @Override
    public void update(ShapeBase shapeBase) {
        String shapeName = shapeBase.getName();
        Point startPoint = new Point(shapeBase.getStartX(), shapeBase.getStartY());
        jlabel.setText("<html>图元：" + shapeName + "<br/>位置：x=" + startPoint.x + "，y=" + startPoint.y + "</html>");
    }
}
