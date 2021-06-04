package com.march.main.command.impl;

import com.march.main.command.Command;
import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;
import com.march.main.eneity.decorator.BorderDecoratorImpl;
import com.march.main.eneity.decorator.FillDecoratorImpl;
import com.march.main.eneity.decorator.ShapeDecorator;

import java.util.List;

/**
 * 执行装饰图形命令
 */
public class DecorateCommand implements Command {

    //定义Receiver
    private DrawPanel drawPanel;//画图面板的引用
    private ShapeDecorator shapeDecorator;//装饰图形
    private Integer index;//替换原图纸的下标位置

    public DecorateCommand(DrawPanel drawPanel, ShapeDecorator shapeDecorator, Integer index) {
        this.drawPanel = drawPanel;
        this.shapeDecorator = shapeDecorator;
        this.index = index;
    }

    @Override
    public void execute() {
        List<ShapeBase> shapeBaseList = drawPanel.getShapeBaseList();
        //将target替换为装饰对象
        shapeBaseList.set(index, shapeDecorator);
    }

    @Override
    public void unExecute() {
        List<ShapeBase> shapeBaseList = drawPanel.getShapeBaseList();
        //还原target的属性
        ShapeBase target = shapeDecorator.getTarget();
        if (shapeDecorator instanceof FillDecoratorImpl) {
            FillDecoratorImpl fillDecorator = (FillDecoratorImpl) shapeDecorator;
            target.setFillColor(fillDecorator.getTargetFillColor());
        }
        if (shapeDecorator instanceof BorderDecoratorImpl) {
            BorderDecoratorImpl borderDecorator = (BorderDecoratorImpl) shapeDecorator;
            target.setLineColor(borderDecorator.getTargetLineColor());
        }
        //将装饰图形引用替换为target
        shapeBaseList.set(index, target);
        drawPanel.repaint();
    }

    @Override
    public void reExecute() {
        List<ShapeBase> shapeBaseList = drawPanel.getShapeBaseList();
        ShapeBase target = shapeDecorator.getTarget();
        //遍历图形列表，将target属性修改为装饰对象
        if (shapeDecorator instanceof FillDecoratorImpl) {
            FillDecoratorImpl fillDecorator = (FillDecoratorImpl) shapeDecorator;
            target.setFillColor(fillDecorator.getFillColor());
        }
        if (shapeDecorator instanceof BorderDecoratorImpl) {
            BorderDecoratorImpl borderDecorator = (BorderDecoratorImpl) shapeDecorator;
            target.setLineColor(borderDecorator.getLineColor());
        }
        shapeBaseList.set(index, shapeDecorator);
        drawPanel.repaint();
    }
}
