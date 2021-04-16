package com.march.eneity.impl;

import com.march.eneity.ShapeBase;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 线段类
 */
public class Line extends ShapeBase {

    private int startX;//起点横坐标
    private int startY;//起点纵坐标
    private int endX;//终点横坐标
    private int endY;//终点纵坐标

    public Line(String name, int startX, int startY, int endX, int endY) {
        super(name);
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d = this.adjustBrush(g2d);
        g2d.drawLine(startX, startY, endX, endY);
    }

    @Override
    public ShapeBase clone() {
        return new Line(this.getName(),
                this.startX + 100,
                this.startY,
                this.endX + 100,
                this.endY);
    }

    @Override
    public void move(String operation) {
        this.deltaX = 0;
        this.deltaY = 0;
        calculateDelta(operation);
        startX += deltaX;
        startY += deltaY;
        endX += deltaX;
        endY += deltaY;
    }

    @Override
    public boolean isSelected(int x, int y, MouseEvent e) {
        //点到直线的距离公式
        double A = (endY - startY) * 1.0 / (endX - startX);
        double B = -1;
        double C = (startY - (endY - startY) * startX * 1.0 / (endX - startX));
        double distance = Math.abs(A * x + B * y + C) / Math.sqrt(A * A + B * B);
        int leftX = Math.min(startX, endX);
        int rightX = Math.max(startX, endX);
        return distance <= 10 && x >= leftX && x <= rightX;
    }


}
