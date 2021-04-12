package com.march.drawframe;

import com.march.eneity.ShapeBase;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 中心画图面板，调用paint方法重绘时遍历封装图形的集合
 */
public class DrawPanel extends JPanel {

    private List<ShapeBase> shapeBaseList = null;//封装创建的图形对象;

    public DrawPanel() {
        shapeBaseList = new ArrayList<>();
    }

    public List<ShapeBase> getShapeBaseList() {
        return shapeBaseList;
    }

    /**
     * 覆盖重写绘图方法：窗口拖拽或最大小化自动调用，也可手动调用repaint重绘
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (ShapeBase shapeBase : this.shapeBaseList) {
            shapeBase.draw((Graphics2D) g);
        }
    }
}
