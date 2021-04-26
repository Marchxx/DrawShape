package com.march.eneity.decorator;

import com.march.eneity.ShapeBase;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 图形装饰器基类对象
 */
public class ShapeDecorator extends ShapeBase {

    //保存装饰的目标对象
    public ShapeBase target;

    //构造函数传入装饰对象
    public ShapeDecorator(ShapeBase shapeBase) {
        super(shapeBase.getName());
        target = shapeBase;
    }

    public ShapeBase getTarget() {
        return target;
    }

    @Override
    public void setChecked(boolean checked) {
        //1.装饰对象自身设置选中
        super.setChecked(checked);
        //2.设置装饰目标选中
        target.setChecked(checked);
    }

    @Override
    public void setLineColor(Color lineColor) {
        super.setLineColor(lineColor);
        target.setLineColor(lineColor);
    }

    @Override
    public void draw(Graphics2D g2d) {
        target.draw(g2d);
    }

    @Override
    public void move(String operation) {
        target.move(operation);
    }

    @Override
    public boolean isSelected(int x, int y, MouseEvent e) {
        return target.isSelected(x, y, e);
    }

    @Override
    public ShapeBase clone() {
        return target.clone();
    }

}
