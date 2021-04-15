package com.march.listener;

import com.march.drawframe.DrawPanel;
import com.march.eneity.ShapeBase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 监听器：响应移动按钮，重绘图形
 */
public class MoveListener implements ActionListener {

    private Graphics2D g2d;//画笔对象，主窗体传入
    private DrawPanel drawPanel;//面板对象，用来计算宽度、高度
    private List<ShapeBase> shapeBaseList = null;//保存主面板图形列表的引用

    public static final MoveListener singletonMoveListener = new MoveListener();

    private MoveListener() {
    }

    public static void setProperties(DrawPanel drawPanel, Graphics2D g2d) {
        singletonMoveListener.drawPanel = drawPanel;
        singletonMoveListener.g2d = g2d;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        shapeBaseList = drawPanel.getShapeBaseList();
        if (shapeBaseList == null)
            return;
        for (ShapeBase shapeBase : shapeBaseList) {
            if (shapeBase.isChecked()) {
                move(shapeBase, e.getActionCommand());
                //break;
            }
        }
        drawPanel.repaint();
    }

    public void move(ShapeBase shapeBase, String operation) {
        //1.根据按钮点击，执行对象
        switch (operation) {
            case "左移":
                shapeBase.move("left");
                break;
            case "右移":
                shapeBase.move("right");
                break;
            case "上移":
                shapeBase.move("top");
                break;
            case "下移":
                shapeBase.move("down");
                break;
        }
    }
}
