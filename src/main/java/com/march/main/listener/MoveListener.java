package com.march.main.listener;

import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;

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
        //1.根据按钮点击进行移动，默认移动距离为10
        int distance = 10;
        switch (operation) {
            case "左移":
                shapeBase.move(-1 * distance, 0);
                break;
            case "右移":
                shapeBase.move(distance, 0);
                break;
            case "上移":
                shapeBase.move(0, -1 * distance);
                break;
            case "下移":
                shapeBase.move(0, distance);
                break;
        }
    }
}
