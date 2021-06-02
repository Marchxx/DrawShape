package com.march.main.eneity.impl;

import com.march.main.eneity.ShapeBase;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 圆形类
 */
public class Circle extends ShapeBase {

    private int radius;//半径

    public Circle(String name, int startX, int startY, int radius) {
        super(name, startX, startY);
        this.radius = radius;
    }

    public Circle(String name, int startX, int startY, boolean checked, boolean filled, Color lineColor, Color fillColor, int radius) {
        super(name, startX, startY, checked, filled, lineColor, fillColor);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void draw(Graphics2D g2d) {
        if (isFilled()) {
            g2d.setColor(this.fillColor);
            g2d.fillOval(this.startX, this.startY, radius * 2, radius * 2);
        }
        g2d = this.adjustBrush(g2d);
        g2d.drawOval(this.startX, this.startY, radius * 2, radius * 2);
    }

    @Override
    public ShapeBase clone() {
        return new Circle(this.getName(),
                this.startX + 50,
                this.startY,
                this.radius);
    }

    @Override
    public void move(int deltaX, int deltaY) {
        this.startX += deltaX;
        this.startY += deltaY;
    }

    @Override
    public boolean isSelected(int x, int y) {
        //计算(x,y)到圆心的距离
        double X = Math.pow(x - (this.startX + radius), 2);
        double Y = Math.pow(y - (this.startY + radius), 2);
        double distance = Math.sqrt(X + Y);
        return distance <= radius + 10;
    }
}
