package com.march.main.command.impl;

import com.march.main.command.Command;
import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;
import com.march.main.eneity.impl.MyButton;

import java.awt.*;
import java.util.List;

/**
 * 执行创建图形命令
 */
public class CreateCommand implements Command {

    //定义Receiver
    private DrawPanel drawPanel;//画图面板的引用
    private ShapeBase shapeBase;//创建图形

    public CreateCommand(DrawPanel drawPanel, ShapeBase shapeBase) {
        this.drawPanel = drawPanel;
        this.shapeBase = shapeBase;
    }

    @Override
    public void execute() {
        shapeBase.draw((Graphics2D) drawPanel.getGraphics());
        List<ShapeBase> shapeBaseList = drawPanel.getShapeBaseList();
        shapeBaseList.add(shapeBase);
    }

    @Override
    public void unExecute() {
        shapeBase.clearShape();//清除图形，针对自定义按钮
        List<ShapeBase> shapeBaseList = drawPanel.getShapeBaseList();
        shapeBaseList.remove(shapeBase);
        drawPanel.repaint();
    }

    @Override
    public void reExecute() {
        //1.判断Shape为按钮，则调用MyButton类创建JButton方法
        if (shapeBase instanceof MyButton) {
            //此处shapeBase还保存着，但MyButton中构造出的JButton组件已经被清除
            MyButton button = (MyButton) shapeBase;
            button.createJButton(button.getName());
        }
        execute();
    }
}
