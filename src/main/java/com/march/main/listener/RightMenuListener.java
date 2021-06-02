package com.march.main.listener;

import com.march.common.utils.ComponentUtil;
import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;

/**
 * 监听器：响应鼠标右键单击，打开菜单
 */
public class RightMenuListener implements MouseListener {

    private DrawPanel drawPanel;//画图面板的引用，用来获取shapeBaseList和计算宽高
    private Graphics2D g2d;//通过drawPanel获取
    private List<ShapeBase> shapeBaseList = null; //通过drawPanel获取

    public static final RightMenuListener singletonRightMenuListener = new RightMenuListener();

    private RightMenuListener() {
    }

    public static void setProperties(DrawPanel drawPanel) {
        singletonRightMenuListener.drawPanel = drawPanel;
        singletonRightMenuListener.g2d = (Graphics2D) drawPanel.getGraphics();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON3) {
            return;
        }
        //1.判断是否有图形被选中，生成不同的菜单
        JPopupMenu menu;
        if (isSelected()) {
            menu = getCheckedMenu();
        } else {
            menu = getUnCheckedMenu();
        }
        //2.根据鼠标在面板上的位置，生成右键菜单
        Point realLocation = ComponentUtil.getMouseRealLocation(e);
        menu.show(drawPanel, realLocation.x, realLocation.y);
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

    //判断是否有图形被选中
    private boolean isSelected() {
        boolean flag = false;
        shapeBaseList = drawPanel.getShapeBaseList();
        for (ShapeBase shapeBase : shapeBaseList) {
            if (shapeBase.isChecked()) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    //获取无图形选中的右键菜单
    private JPopupMenu getUnCheckedMenu() {
        JPopupMenu menu = ComponentUtil.getNewJPopupMenu();
        JMenuItem mUndo = ComponentUtil.getNewJMenuItem("撤销");
        JMenuItem mClear = ComponentUtil.getNewJMenuItem("清空画板");
        menu.add(mUndo);
        menu.add(mClear);
        mClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //点击清空：遍历列表
                shapeBaseList = drawPanel.getShapeBaseList();
                for (ShapeBase shapeBase : shapeBaseList) {
                    shapeBase.clearShape();
                }
                shapeBaseList.clear();
                drawPanel.repaint();
            }
        });
        return menu;
    }

    //获取包含图形选中的右键菜单
    private JPopupMenu getCheckedMenu() {
        JPopupMenu menu = ComponentUtil.getNewJPopupMenu();
        JMenuItem mDel = ComponentUtil.getNewJMenuItem("删除(D)");
        menu.add(mDel);
        mDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //点击清空：遍历列表
                shapeBaseList = drawPanel.getShapeBaseList();
                //通过迭代器遍历，能够在遍历list动态删除元素
                Iterator<ShapeBase> iterator = shapeBaseList.iterator();
                while (iterator.hasNext()) {
                    ShapeBase shapeBase = iterator.next();
                    if (shapeBase.isChecked()) {
                        shapeBase.clearShape();
                        iterator.remove();
                    }
                }
                drawPanel.repaint();
            }
        });
        return menu;
    }
}
