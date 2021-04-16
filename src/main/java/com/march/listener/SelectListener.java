package com.march.listener;

import com.march.drawframe.DrawPanel;
import com.march.eneity.ShapeBase;
import com.march.eneity.impl.MyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

/**
 * 监听器：响应鼠标点击选中图形
 */
public class SelectListener implements MouseListener, MouseMotionListener {

    private DrawPanel drawPanel;//面板对象，用来计算宽度、高度
    private Graphics2D g2d;//画笔对象，主窗体传入
    private List<ShapeBase> shapeBaseList = null; //保存主面板图形列表的引用
    private Point startPoint = new Point();//鼠标选框左上角坐标
    private Point endPoint = new Point();//鼠标选框右下角坐标

    public static final SelectListener singletonSelectListener = new SelectListener();

    private SelectListener() {
    }

    public static void setProperties(DrawPanel drawPanel, Graphics2D g2d) {
        singletonSelectListener.drawPanel = drawPanel;
        singletonSelectListener.g2d = g2d;
    }

    /**
     * 鼠标点击：实现图形单选
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        shapeBaseList = drawPanel.getShapeBaseList();
        if (shapeBaseList == null)
            return;
        cancelSelected();
        singleSelect(e);
        drawPanel.repaint();
    }


    @Override
    public void mousePressed(MouseEvent e) {
        startPoint.x = e.getX();
        startPoint.y = e.getY();
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10.0f, new float[]{10, 10, 10}, 0));
        g2d.setColor(Color.blue);
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        endPoint.x = e.getX();
        endPoint.y = e.getY();
        g2d.drawRect(startPoint.x, startPoint.y, Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
        shapeBaseList = drawPanel.getShapeBaseList();
        if (shapeBaseList == null)
            return;
        //鼠标画框实现多选
        cancelSelected();
        multiSelect(startPoint, endPoint);
        drawPanel.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //重置画板，防止绘制的矩形重叠
        drawPanel.repaint();
        endPoint.x = e.getX();
        endPoint.y = e.getY();
        g2d.drawRect(startPoint.x, startPoint.y, Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    /**
     * 所有图形取消选中
     */
    public void cancelSelected() {
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.setChecked(false);
        }
        drawPanel.repaint();
    }

    /**
     * 根据鼠标点击坐标，判断单个图形选中
     */
    private void singleSelect(MouseEvent e) {
        for (ShapeBase shapeBase : shapeBaseList) {
            //判断点击处是否为控件，获取坐标
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
    }

    /**
     * 根据鼠标画出矩形的起点、终点坐标，判断多个图形选中
     *
     * @param startPoint
     * @param endPoint
     */
    private void multiSelect(Point startPoint, Point endPoint) {
        //1.遍历图形集合
        for (ShapeBase shapeBase : shapeBaseList) {
            //2.遍历所有坐标点
            for (int x = startPoint.x; x <= endPoint.x; x++) {
                boolean flag = false;
                for (int y = startPoint.y; y <= endPoint.y; y++) {
                    if (shapeBase.isSelected(x, y)) {
                        shapeBase.setChecked(true);
                        flag = true;
                        break;
                    }
                }
                if (flag)
                    break;
            }
        }
    }

}