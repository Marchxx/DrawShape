package com.march.listener;

import com.march.drawframe.DrawPanel;
import com.march.eneity.ShapeBase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ListIterator;

/**
 * 监听器：响应复制按钮，绘制clone图形
 */
public class CopyListener implements ActionListener {

    private Graphics2D g2d;//画笔对象，主窗体传入
    private DrawPanel drawPanel;//面板对象，用来计算宽度、高度
    private List<ShapeBase> shapeBaseList = null;//保存主面板图形列表的引用

    public static final CopyListener singletonCopyListener = new CopyListener();

    private CopyListener() {
    }

    public static void setProperties(DrawPanel drawPanel, Graphics2D g2d) {
        singletonCopyListener.drawPanel = drawPanel;
        singletonCopyListener.g2d = g2d;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        shapeBaseList = drawPanel.getShapeBaseList();
        if (shapeBaseList == null)
            return;
        //Note：使用迭代器和增强for遍历列表元素时动态删除和修改会报java.util.ConcurrentModificationException
        for (int i = 0; i < shapeBaseList.size(); i++) {
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
