package com.march.main.command.impl;

import com.march.main.command.Command;
import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;

import java.util.Set;

/**
 * 执行移动指令：记录图形列表，以及移动的X、Y偏移量
 */
public class MoveCommand implements Command {

    //定义Receiver
    private DrawPanel drawPanel;//画图面板的引用
    private Set<ShapeBase> moveShapeSet;//要移动的图形列表

    //保存移动的x、y偏移
    private int deltaX;
    private int deltaY;

    public MoveCommand(DrawPanel drawPanel, Set<ShapeBase> moveShapeSet, int deltaX, int deltaY) {
        this.drawPanel = drawPanel;
        this.moveShapeSet = moveShapeSet;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    @Override
    public void execute() {
    }

    @Override
    public void unExecute() {
        for (ShapeBase shapeBase : moveShapeSet) {
            shapeBase.move(-deltaX, -deltaY);
        }
        drawPanel.repaint();
    }

    @Override
    public void reExecute() {
        for (ShapeBase shapeBase : moveShapeSet) {
            shapeBase.move(deltaX, deltaY);
        }
        drawPanel.repaint();
    }
}
