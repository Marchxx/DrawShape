package com.march.main.eneity.impl;

import com.march.main.eneity.ShapeBase;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 矩形类
 */
public class Rectangle extends ShapeBase {

    private int width;//矩形的宽
    private int height;//矩形的高

    public Rectangle(String name, int startX, int startY, int width, int height) {
        super(name, startX, startY);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void move(int deltaX, int deltaY) {
        this.startX += deltaX;
        this.startY += deltaY;
    }

    public void draw(Graphics2D g2d) {
        if (isFilled()) {
            g2d.setColor(this.fillColor);
            g2d.fillRect(this.startX, this.startY, width, height);
        }
        g2d = this.adjustBrush(g2d);
        g2d.drawRect(this.startX, this.startY, width, height);
    }

    @Override
    public ShapeBase clone() {
        return new Rectangle(this.getName(),
                this.startX + 50,
                this.startY,
                this.width,
                this.height);
    }

    @Override
    public boolean isSelected(int x, int y, MouseEvent e) {
        int boundLeftX = this.startX - 10;
        int boundRightX = this.startX + width + 10;
        int boundUpY = this.startY - 10;
        int boundDownY = this.startY + height + 10;
        return x >= boundLeftX && x <= boundRightX && y >= boundUpY && y <= boundDownY;
    }


}
