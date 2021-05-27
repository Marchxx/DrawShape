package com.march.main.eneity.decorator;

import com.march.main.eneity.ShapeBase;

import java.awt.*;

/**
 * 填充装饰
 */
public class FillDecoratorImpl extends ShapeDecorator {

    private Color fillColor;

    public FillDecoratorImpl(ShapeBase shapeBase, Color fillColor) {
        super(shapeBase);
        this.fillColor = fillColor;
        target.setFilled(true);
        target.setFillColor(fillColor);
    }

    @Override
    public void draw(Graphics2D g2d) {
        target.draw(g2d);
    }

    @Override
    public ShapeBase clone() {
        //创建一个装饰目标对象的克隆
        ShapeBase shapeBaseClone = target.clone();
        return new FillDecoratorImpl(shapeBaseClone, target.getFillColor());
    }
}
