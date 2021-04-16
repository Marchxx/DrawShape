package com.march.eneity;


import java.awt.*;
import java.util.List;

/**
 * 图形组合对象
 */
public class ShapeComposite extends ShapeBase {

    //保存该组合对象包含的叶子节点
    private List<ShapeBase> shapeBaseList = null;

    public ShapeComposite(String name) {
        super(name);
    }

    @Override
    public void draw(Graphics2D g2d) {

    }

    @Override
    public void move(String operation) {

    }

    @Override
    public boolean isSelected(int x, int y) {
        return false;
    }

    @Override
    public ShapeBase clone() {
        return null;
    }
}
