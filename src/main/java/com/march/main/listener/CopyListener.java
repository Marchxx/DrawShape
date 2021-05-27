package com.march.main.listener;

import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 监听器：响应复制按钮，绘制clone图形
 */
public class CopyListener implements ActionListener {

    private Graphics2D g2d;//通过drawPanel获取
    private DrawPanel drawPanel;//画图面板的引用，用来获取shapeBaseList和计算宽高
    private List<ShapeBase> shapeBaseList = null;//通过drawPanel获取

    public static final CopyListener singletonCopyListener = new CopyListener();

    private CopyListener() {
    }

    public static void setProperties(DrawPanel drawPanel) {
        singletonCopyListener.drawPanel = drawPanel;
        singletonCopyListener.g2d = (Graphics2D) drawPanel.getGraphics();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        shapeBaseList = drawPanel.getShapeBaseList();
        if (shapeBaseList == null)
            return;
        //Note：使用迭代器和增强for遍历列表元素时动态删除和修改会报java.util.ConcurrentModificationException
        //只涉及动态增加，使用listSize为定值遍历集合
        int listSize = shapeBaseList.size();
        for (int i = 0; i < listSize; i++) {
            ShapeBase shapeBase = shapeBaseList.get(i);
            if (shapeBase.isChecked()) {
                clone(shapeBase);
                //break;
            }
        }
    }

    private void clone(ShapeBase shapeBase) {
        //1.获取克隆出的对象，添加到图形集合
        ShapeBase clone = shapeBase.clone();
        shapeBaseList.add(clone);
        //2.画出该对象
        clone.draw(g2d);
    }
}
