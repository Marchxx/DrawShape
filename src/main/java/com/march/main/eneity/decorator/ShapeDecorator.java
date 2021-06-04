package com.march.main.eneity.decorator;

import com.march.main.eneity.ShapeBase;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 图形装饰器基类对象
 */
public abstract class ShapeDecorator extends ShapeBase {

    //保存装饰的目标对象
    protected ShapeBase target;

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
    public void setFilled(boolean filled) {
        //设置目标是否填充
        target.setFilled(filled);
    }

    @Override
    public void setLineColor(Color lineColor) {
        //设置边框颜色
        target.setLineColor(lineColor);
    }

    @Override
    public void setFillColor(Color fillColor) {
        //设置填充颜色
        target.setFillColor(fillColor);
    }

    @Override
    public void draw(Graphics2D g2d) {
        target.draw(g2d);
    }

    @Override
    public void move(int deltaX, int deltaY) {
        target.move(deltaX, deltaY);
    }

    @Override
    public boolean isSelected(int x, int y) {
        return target.isSelected(x, y);
    }

    @Override
    public ShapeBase clone() {
        return target.clone();
    }

    @Override
    public void clearShape() {
        target.clearShape();
    }

    @Override
    public ShapeDecorator getDecorator() {
        return this;
    }
}
