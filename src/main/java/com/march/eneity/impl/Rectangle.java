package com.march.eneity.impl;

import com.march.eneity.ShapeBase;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 矩形类
 */
public class Rectangle extends ShapeBase {

    private int leftUpX;//左上角x坐标
    private int leftUpY;//左上角y坐标
    private int width;//矩形的宽
    private int height;//矩形的高

    public Rectangle(String name, int leftUpX, int leftUpY, int width, int height) {
        super(name);
        this.leftUpX = leftUpX;
        this.leftUpY = leftUpY;
        this.width = width;
        this.height = height;
    }

    @Override
    public void move(String operation) {
        this.deltaX = 0;
        this.deltaY = 0;
        calculateDelta(operation);
        leftUpX += deltaX;
        leftUpY += deltaY;
    }

    public void draw(Graphics2D g2d) {
        g2d = this.adjustBrush(g2d);
        if (isFilled()) {
            g2d.fillRect(leftUpX, leftUpY, width, height);
        } else {
            g2d.drawRect(leftUpX, leftUpY, width, height);
        }
    }

    @Override
    public ShapeBase clone() {
        return new Rectangle(this.getName(),
                this.leftUpX + 50,
                this.leftUpY,
                this.width,
                this.height);
    }

    @Override
    public boolean isSelected(int x, int y, MouseEvent e) {
        int boundLeftX = leftUpX - 10;
        int boundRightX = leftUpX + width + 10;
        int boundUpY = leftUpY - 10;
        int boundDownY = leftUpY + height + 10;
        return x >= boundLeftX && x <= boundRightX && y >= boundUpY && y <= boundDownY;
    }


}
