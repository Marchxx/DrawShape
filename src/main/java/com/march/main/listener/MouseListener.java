package com.march.main.listener;

import com.march.common.utils.ComponentUtil;
import com.march.main.command.CommandInvoker;
import com.march.main.command.impl.MoveCommand;
import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;
import com.march.main.eneity.composite.ShapeComposite;
import com.march.main.eneity.decorator.ShapeDecorator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 监听器：响应鼠标点击单击选中图形、框选多个图形、点击移动图形
 */
public class MouseListener implements java.awt.event.MouseListener, MouseMotionListener {

    private DrawPanel drawPanel;//画图面板的引用，用来获取shapeBaseList和计算宽高
    private Graphics2D g2d;//通过drawPanel获取
    private List<ShapeBase> shapeBaseList = null; //通过drawPanel获取

    private Point startPoint = new Point();//鼠标选框绘制起点坐标in，拖拽过程变化
    private Point endPoint = new Point();//鼠标选框绘制终点坐标，拖拽过程变化
    private Point drawStartPoint = new Point();//选框绘制左上角坐标

    private Point pressStartPoint = new Point();//记录按下时的起点，每次按下才改变
    private Set<ShapeBase> moveShapeSet = new HashSet<>();//记录要移动的图形集合，防止重复添加，每次按下清空

    private boolean downChooseFlag = false;//按下并选中图形标识：满足为true
    private boolean isMouseLeft = false;//判断是否为鼠标左键

    public static final MouseListener singletonMouseListener = new MouseListener();

    private MouseListener() {
    }

    public static void setProperties(DrawPanel drawPanel) {
        singletonMouseListener.drawPanel = drawPanel;
        //获取面板的画笔并设置
        Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10.0f, new float[]{10, 10, 10}, 0));
        g2d.setColor(Color.BLUE);
        singletonMouseListener.g2d = g2d;
    }

    /**
     * 鼠标左键点击：实现图形单选
     * 如果选中则直接break，无需判定列表后续
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) {
            return;
        }
        shapeBaseList = drawPanel.getShapeBaseList();
        if (shapeBaseList == null) return;
        cancelSelected();
        singleSelect(e);
        drawPanel.repaint();
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) {
            return;
        }
        isMouseLeft = true;//鼠标左键按下
        Point realLocation = ComponentUtil.getMouseRealLocation(e);
        shapeBaseList = drawPanel.getShapeBaseList();
        for (ShapeBase shapeBase : shapeBaseList) {
            // 判断按下时，鼠标是否处于某个图形面积内并且该图形已被选中
            if (shapeBase.isSelected(realLocation.x, realLocation.y) && shapeBase.isChecked()) {
                downChooseFlag = true;//设置chooseFlag为true
                moveShapeSet.clear();//清空移动图形列表
            }
        }
        // 记录按下时的开始坐标
        startPoint.x = realLocation.x;
        startPoint.y = realLocation.y;
        pressStartPoint.x = realLocation.x;
        pressStartPoint.y = realLocation.y;
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) {
            return;
        }
        isMouseLeft = false;//鼠标左键释放
        Point realLocation = ComponentUtil.getMouseRealLocation(e);
        endPoint.x = realLocation.x;
        endPoint.y = realLocation.y;
        if (downChooseFlag) {
            //1.拖拽时进行了移动，发送移动命令，记录按下与松开的偏移量
            MoveCommand moveCommand = new MoveCommand(drawPanel, moveShapeSet,
                    endPoint.x - pressStartPoint.x, endPoint.y - pressStartPoint.y);
            CommandInvoker.singletonCommandInvoker.execute(moveCommand);
            //2.恢复按下选中标识，取消拖拽
            downChooseFlag = false;
        } else {
            //2.反之判断鼠标框选多个图形
            if (startPoint.equals(endPoint)) {
                //起点坐标与终点一致，与单选进行区分
                return;
            }
            if (drawPanel.getShapeBaseList() == null) {
                return;
            }
            //System.out.println("当前列表：" +drawPanel.getShapeBaseList());
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
    public void mouseDragged(MouseEvent e) {//拖拽：左键按下并移动
        Point realLocation = ComponentUtil.getMouseRealLocation(e);
        //1.根据判断结果，完成不同的鼠标动作
        if (downChooseFlag && isMouseLeft) {
            //2.1 鼠标左键按下并且存在图形被选中，则进行拖拽移动
            endPoint.x = realLocation.x;
            endPoint.y = realLocation.y;
            int deltaX = endPoint.x - startPoint.x;
            int deltaY = endPoint.y - startPoint.y;
            startPoint.x = endPoint.x;
            startPoint.y = endPoint.y;
            for (ShapeBase shapeBase : shapeBaseList) {
                if (shapeBase.isChecked()) {
                    shapeBase.move(deltaX, deltaY);
                    moveShapeSet.add(shapeBase);//加入移动图形Set
                }
            }
            drawPanel.repaint();
        } else if (isMouseLeft) {
            //2.2 鼠标左键按下，但无图形选中执行框选
            //防止绘制的矩形重叠，repaint()不会立即执行，调用它后会有一个等待处理的过程
            drawPanel.paintImmediately(0, 0, 1920, 1080);
            endPoint.x = realLocation.x;
            endPoint.y = realLocation.y;
            //计算绘制矩形的起点坐标
            drawStartPoint.x = Math.min(startPoint.x, endPoint.x);
            drawStartPoint.y = Math.min(startPoint.y, endPoint.y);
            g2d.drawRect(drawStartPoint.x, drawStartPoint.y, Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * 所有图形取消选中
     */
    private void cancelSelected() {
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
            Point realLocation = ComponentUtil.getMouseRealLocation(e);
            if (shapeBase.isSelected(realLocation.x, realLocation.y)) {
                shapeBase.setChecked(true);

                //若选中装饰对象，取出最外层target
                while (shapeBase.getDecorator() != null) {
                    ShapeDecorator shapeDecorator = shapeBase.getDecorator();
                    shapeBase = shapeDecorator.getTarget();
                }
                //若为 组合对象、装饰最外层target为组合对象，显示消息
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
            //2.遍历所有坐标点，判断成功直接跳出内层循环
            for (int x = drawStartPoint.x; x <= drawStartPoint.x + width; x++) {
                boolean flag = false;
                for (int y = drawStartPoint.y; y <= drawStartPoint.y + height; y++) {
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
