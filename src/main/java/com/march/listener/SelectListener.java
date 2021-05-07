package com.march.listener;

import com.march.drawframe.DrawPanel;
import com.march.eneity.ShapeBase;
import com.march.eneity.composite.ShapeComposite;
import com.march.eneity.decorator.ShapeDecorator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 监听器：响应鼠标点击选中图形
 */
public class SelectListener implements MouseListener, MouseMotionListener {

    private DrawPanel drawPanel;//画图面板的引用，用来获取shapeBaseList和计算宽高
    private Graphics2D g2d;//通过drawPanel获取
    private List<ShapeBase> shapeBaseList = null; //通过drawPanel获取

    //绘制选框矩形
    private Point startPoint = new Point();//鼠标选框绘制起点坐标
    private Point endPoint = new Point();//鼠标选框绘制终点坐标
    private Point drawStartPoint = new Point();//绘图起点坐标
    private boolean mouseDownFlag = false;//鼠标按下标识：按下为true

    public static final SelectListener singletonSelectListener = new SelectListener();

    private SelectListener() {
    }

    public static void setProperties(DrawPanel drawPanel) {
        singletonSelectListener.drawPanel = drawPanel;
        //获取面板的画笔并设置
        Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10.0f, new float[]{10, 10, 10}, 0));
        g2d.setColor(Color.blue);
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
        mouseDownFlag = true;
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        endPoint.x = e.getX();
        endPoint.y = e.getY();
        //与单选进行区分
        if (!startPoint.equals(endPoint)) {
            //g2d.drawRect(drawStartPoint.x, drawStartPoint.y, Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
            shapeBaseList = drawPanel.getShapeBaseList();
            //System.out.println("当前列表：" + shapeBaseList);
            if (shapeBaseList == null)
                return;
            //鼠标画框实现多选
            cancelSelected();
            multiSelect(drawStartPoint, Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y), e);
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
        //防止绘制的矩形重叠，repaint()不会立即执行，调用它后会有一个等待处理的过程
        drawPanel.paintImmediately(0, 0, 1920, 1080);
        endPoint.x = e.getX();
        endPoint.y = e.getY();
        //计算绘制矩形的起点坐标
        drawStartPoint.x = Math.min(startPoint.x, endPoint.x);
        drawStartPoint.y = Math.min(startPoint.y, endPoint.y);
        g2d.drawRect(drawStartPoint.x, drawStartPoint.y, Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
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

                //若选中装饰对象，取出最外层target
                while (shapeBase.getDecorator() != null) {
                    ShapeDecorator shapeDecorator = shapeBase.getDecorator();
                    shapeBase = shapeDecorator.getTarget();
                }
                //若选中组合对象，显示消息
                if (shapeBase.getComposite() != null) {
                    ShapeComposite composite = shapeBase.getComposite();
                    String printAll = composite.printAll(new StringBuilder());
                    JOptionPane.showMessageDialog(drawPanel, printAll);
                }

                //判定成功直接break
                break;
            }
        }
    }

    /**
     * 判断多个图形选中
     */
    private void multiSelect(Point drawStartPoint, Integer width, Integer height, MouseEvent e) {
        //1.遍历图形集合
        for (ShapeBase shapeBase : shapeBaseList) {
            //2.遍历所有坐标点
            for (int x = drawStartPoint.x; x <= drawStartPoint.x + width; x++) {
                boolean flag = false;
                for (int y = drawStartPoint.y; y <= drawStartPoint.y + height; y++) {
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
