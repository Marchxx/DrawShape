package com.march.eneity.impl;

import com.march.eneity.ShapeBase;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 圆形类
 */
public class Circle extends ShapeBase {

    private int centerX;//圆心x轴坐标
    private int centerY;//圆心y轴坐标
    private int radius;//半径

    public Circle(String name, int centerX, int centerY, int radius) {
        super(name);
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public void draw(Graphics2D g2d) {
        if (isFilled()) {
            g2d.setColor(this.fillColor);
            g2d.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        }
        g2d = this.adjustBrush(g2d);
        g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    @Override
    public ShapeBase clone() {
        return new Circle(this.getName(),
                this.centerX + 50,
                this.centerY,
                this.radius);
    }

    @Override
    public void move(String operation) {
        this.deltaX = 0;
        this.deltaY = 0;
        calculateDelta(operation);
        centerX += deltaX;
        centerY += deltaY;
    }

    @Override
    public boolean isSelected(int x, int y, MouseEvent e) {
        //计算(x,y)到圆心的距离
        double X = Math.pow(x - centerX, 2);
        double Y = Math.pow(y - centerY, 2);
        double distance = Math.sqrt(X + Y);
        return distance <= radius + 10;
    }
}
