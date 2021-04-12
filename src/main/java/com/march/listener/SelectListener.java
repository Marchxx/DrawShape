package com.march.listener;

import com.march.drawframe.DrawPanel;
import com.march.eneity.ShapeBase;
import com.march.eneity.impl.MyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * 监听器：响应鼠标点击选中图形
 */
public class SelectListener implements MouseListener {

    private DrawPanel drawPanel;//面板对象，用来计算宽度、高度
    private Graphics2D g2d;//画笔对象，主窗体传入
    private List<ShapeBase> shapeBaseList = null; //保存主面板图形列表的引用

    public static final SelectListener singletonSelectListener = new SelectListener();

    private SelectListener() {
    }

    public static void setProperties(DrawPanel drawPanel, Graphics2D g2d) {
        singletonSelectListener.drawPanel = drawPanel;
        singletonSelectListener.g2d = g2d;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        shapeBaseList = drawPanel.getShapeBaseList();
        if (shapeBaseList == null)
            return;
        //1.图形全不选
        cancelSelected();
        //2.遍历图形列表
        for (ShapeBase shapeBase : shapeBaseList) {
            //3.判断点击处是否为控件，获取坐标
            int x, y;
            if (shapeBase instanceof MyButton && e.getSource() instanceof JButton) {
                JButton jButton = (JButton) e.getSource();
                x = jButton.getX();
                y = jButton.getY();
            } else {
                x = e.getX();
                y = e.getY();
            }
            if (shapeBase.isSelected(x, y)) {
                shapeBase.setChecked(true);
                break;
            }
        }
        drawPanel.repaint();//重绘
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //全部取消选中
    public void cancelSelected() {
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.setChecked(false);
        }
        drawPanel.repaint();
    }
}
