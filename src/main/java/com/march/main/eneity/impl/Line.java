package com.march.main.eneity.impl;

import com.march.main.eneity.ShapeBase;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 线段类
 */
public class Line extends ShapeBase {

    private int endX;//终点横坐标
    private int endY;//终点纵坐标

    public Line(String name, int startX, int startY, int endX, int endY) {
        super(name, startX, startY);
        this.endX = endX;
        this.endY = endY;
    }

    public Line(String name, int startX, int startY, boolean checked, boolean filled, Color lineColor, Color fillColor, int endX, int endY) {
        super(name, startX, startY, checked, filled, lineColor, fillColor);
        this.endX = endX;
        this.endY = endY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d = this.adjustBrush(g2d);
        g2d.drawLine(this.startX, this.startY, endX, endY);
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
    public void doMove(int deltaX, int deltaY) {
        this.startX += deltaX;
        this.startY += deltaY;
        endX += deltaX;
        endY += deltaY;
    }

    @Override
    public boolean isSelected(int x, int y) {
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
