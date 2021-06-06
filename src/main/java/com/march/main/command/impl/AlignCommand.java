package com.march.main.command.impl;

import com.march.main.command.Command;
import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;
import com.march.main.strategy.AlignContext;

import java.util.Set;

/**
 * 图形对齐命令：配合策略模式，使用上下文调用不同策略
 */
public class AlignCommand implements Command {

    //定义Receiver
    private DrawPanel drawPanel;//画图面板的引用
    private Set<ShapeBase> alignShapeSet;//要移动的图形列表
    private AlignContext alignContext;//策略上下文对象

    public AlignCommand(DrawPanel drawPanel, Set<ShapeBase> alignShapeSet, AlignContext alignContext) {
        this.drawPanel = drawPanel;
        this.alignShapeSet = alignShapeSet;
        this.alignContext = alignContext;
    }


    @Override
    public void execute() {
        //1.遍历图形集合，调用上下文对象执行策略
        for (ShapeBase shapeBase : alignShapeSet) {
            alignContext.executeStrategy(shapeBase);
        }
        drawPanel.repaint();
    }

    @Override
    public void unExecute() {
        for (ShapeBase shapeBase : alignShapeSet) {
            alignContext.restore(shapeBase);
        }
        drawPanel.repaint();
    }

    @Override
    public void reExecute() {
        execute();
    }
}
