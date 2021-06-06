package com.march.main.listener;

import com.march.common.utils.ComponentUtil;
import com.march.main.command.CommandInvoker;
import com.march.main.command.impl.AlignCommand;
import com.march.main.command.impl.DeleteCommand;
import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;
import com.march.main.strategy.AlignContext;
import com.march.main.strategy.impl.DownAlignStrategy;
import com.march.main.strategy.impl.LeftAlignStrategy;
import com.march.main.strategy.impl.RightAlignStrategy;
import com.march.main.strategy.impl.UpAlignStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 监听器：响应鼠标右键单击，打开菜单
 */
public class RightMenuListener implements MouseListener {

    private DrawPanel drawPanel;//画图面板的引用，用来获取shapeBaseList和计算宽高
    private Graphics2D g2d;//通过drawPanel获取
    private List<ShapeBase> shapeBaseList = null; //通过drawPanel获取
    private Set<ShapeBase> alignShapeSet = new HashSet<>();//记录要对齐的图形集合，每次对齐时重置

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

        //添加撤销按钮
        JMenuItem mUndo = ComponentUtil.getNewJMenuItem("撤销(Undo)");
        mUndo.addActionListener((e) -> {
            CommandInvoker.singletonCommandInvoker.undo();
        });
        menu.add(mUndo);

        //添加重做按钮
        JMenuItem mRedo = ComponentUtil.getNewJMenuItem("重做(Redo)");
        mRedo.addActionListener((e) -> {
            CommandInvoker.singletonCommandInvoker.redo();
        });
        menu.add(mRedo);

        menu.addSeparator();// 添加一条分隔符

        //添加清空面板
        JMenuItem mClear = ComponentUtil.getNewJMenuItem("清空画板(C)");
        mClear.addActionListener(e -> {
            //点击清空：遍历列表
            shapeBaseList = drawPanel.getShapeBaseList();
            for (ShapeBase shapeBase : shapeBaseList) {
                shapeBase.clearShape();
            }
            shapeBaseList.clear();
            drawPanel.repaint();
        });
        menu.add(mClear);

        return menu;
    }

    //获取包含图形选中的右键菜单
    private JPopupMenu getCheckedMenu() {
        JPopupMenu menu = ComponentUtil.getNewJPopupMenu();
        //上对齐
        JMenuItem mUp = ComponentUtil.getNewJMenuItem("上对齐");
        mUp.addActionListener(e -> {
            alignShapeSet.clear();
            shapeBaseList = drawPanel.getShapeBaseList();
            for (ShapeBase shapeBase : shapeBaseList) {
                if (shapeBase.isChecked()) {
                    alignShapeSet.add(shapeBase);//加入移动图形Set
                }
            }
            AlignContext alignContext = new AlignContext(new UpAlignStrategy());//创建上对齐策略
            AlignCommand alignCommand = new AlignCommand(drawPanel, alignShapeSet, alignContext);//创建对齐命令
            CommandInvoker.singletonCommandInvoker.execute(alignCommand);//执行命令
        });
        menu.add(mUp);

        //下对齐
        JMenuItem mBottom = ComponentUtil.getNewJMenuItem("下对齐");
        mBottom.addActionListener(e -> {
            alignShapeSet.clear();
            shapeBaseList = drawPanel.getShapeBaseList();
            for (ShapeBase shapeBase : shapeBaseList) {
                if (shapeBase.isChecked()) {
                    alignShapeSet.add(shapeBase);//加入移动图形Set
                }
            }
            AlignContext alignContext = new AlignContext(new DownAlignStrategy());//创建下对齐策略
            AlignCommand alignCommand = new AlignCommand(drawPanel, alignShapeSet, alignContext);//创建对齐命令
            CommandInvoker.singletonCommandInvoker.execute(alignCommand);//执行命令
        });
        menu.add(mBottom);

        //左对齐
        JMenuItem mLeft = ComponentUtil.getNewJMenuItem("左对齐");
        mLeft.addActionListener(e -> {
            alignShapeSet.clear();
            shapeBaseList = drawPanel.getShapeBaseList();
            for (ShapeBase shapeBase : shapeBaseList) {
                if (shapeBase.isChecked()) {
                    alignShapeSet.add(shapeBase);//加入移动图形Set
                }
            }
            AlignContext alignContext = new AlignContext();//创建左对齐策略
            AlignCommand alignCommand = new AlignCommand(drawPanel, alignShapeSet, alignContext);//创建对齐命令
            CommandInvoker.singletonCommandInvoker.execute(alignCommand);//执行命令
        });
        menu.add(mLeft);

        //右对齐
        JMenuItem mRight = ComponentUtil.getNewJMenuItem("右对齐");
        mRight.addActionListener(e -> {
            alignShapeSet.clear();
            shapeBaseList = drawPanel.getShapeBaseList();
            for (ShapeBase shapeBase : shapeBaseList) {
                if (shapeBase.isChecked()) {
                    alignShapeSet.add(shapeBase);//加入移动图形Set
                }
            }
            AlignContext alignContext = new AlignContext(new RightAlignStrategy());//创建右对齐策略
            AlignCommand alignCommand = new AlignCommand(drawPanel, alignShapeSet, alignContext);//创建对齐命令
            CommandInvoker.singletonCommandInvoker.execute(alignCommand);//执行命令
        });
        menu.add(mRight);

        menu.addSeparator();// 添加一条分隔符

        //删除选中图形
        JMenuItem mDel = ComponentUtil.getNewJMenuItem("删除(D)");
        mDel.addActionListener(e -> {
            DeleteCommand deleteCommand = new DeleteCommand(drawPanel);
            CommandInvoker.singletonCommandInvoker.execute(deleteCommand);
        });
        menu.add(mDel);
        return menu;
    }
}
