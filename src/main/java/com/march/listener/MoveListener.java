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

    private DrawPanel drawPanel;//画图面板的引用，用来获取shapeBaseList和计算宽高
    private List<ShapeBase> shapeBaseList = null; //通过drawPanel获取

    public static final MoveListener singletonMoveListener = new MoveListener();

    private MoveListener() {
    }

    public static void setProperties(DrawPanel drawPanel) {
        singletonMoveListener.drawPanel = drawPanel;
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
