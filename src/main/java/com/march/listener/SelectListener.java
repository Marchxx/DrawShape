package com.march.listener;

import com.march.drawframe.DrawPanel;
import com.march.eneity.ShapeBase;
import com.march.eneity.ShapeComposite;

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

    private DrawPanel drawPanel;//画图面板的引用，用来获取shapeBaseList和计算宽高
    private Graphics2D g2d;//画笔对象，主窗体传入
    private List<ShapeBase> shapeBaseList = null; //通过drawPanel获取
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
     * 单选操作用于一个组合对象，如果选中则直接break，无需判定列表后续元素
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
        //与单选进行区分
        if (!startPoint.equals(endPoint)) {
            g2d.drawRect(startPoint.x, startPoint.y, Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
            shapeBaseList = drawPanel.getShapeBaseList();
            //System.out.println("当前列表：" + shapeBaseList);
            if (shapeBaseList == null)
                return;
            //鼠标画框实现多选
            cancelSelected();
            multiSelect(startPoint, endPoint, e);
            drawPanel.repaint();
        }
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
            if (shapeBase.isSelected(e.getX(), e.getY(), e)) {
                shapeBase.setChecked(true);

                //若选中组合对象，显示消息
                if (shapeBase instanceof ShapeComposite) {
                    ShapeComposite composite = (ShapeComposite) shapeBase;
                    String printAll = composite.printAll(new StringBuilder());
                    JOptionPane.showMessageDialog(drawPanel, printAll);
                }

                //判定成功直接break，若为组合对象会将其叶子全部设置
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
    private void multiSelect(Point startPoint, Point endPoint, MouseEvent e) {
        //1.遍历图形集合
        for (ShapeBase shapeBase : shapeBaseList) {
            //2.遍历所有坐标点
            for (int x = startPoint.x; x <= endPoint.x; x++) {
                boolean flag = false;
                for (int y = startPoint.y; y <= endPoint.y; y++) {
                    if (shapeBase.isSelected(x, y, e)) {
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
