package com.march.main.command.impl;

import com.march.main.command.Command;
import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;

import java.awt.*;
import java.util.List;

/**
 * 执行复制命令
 */
public class CopyCommand implements Command {

    //定义Receiver
    private DrawPanel drawPanel;//画图面板的引用
    private ShapeBase shapeBase;//克隆原图形

    //定义state
    private ShapeBase clone;//保存clone的引用，用于undo删除

    public CopyCommand(DrawPanel drawPanel, ShapeBase shapeBase) {
        this.drawPanel = drawPanel;
        this.shapeBase = shapeBase;
    }

    @Override
    public void execute() {
        //1.获取克隆的对象，添加到图形集合
        clone = shapeBase.clone();
        List<ShapeBase> shapeBaseList = drawPanel.getShapeBaseList();
        shapeBaseList.add(clone);
        //2.画出该对象
        clone.draw((Graphics2D) (drawPanel).getGraphics());
    }

    @Override
    public void unExecute() {
        //删除clone对象
        List<ShapeBase> shapeBaseList = drawPanel.getShapeBaseList();
        shapeBaseList.remove(clone);
        drawPanel.repaint();
    }

    @Override
    public void reExecute() {
        //直接调用execute
        execute();
    }
}
