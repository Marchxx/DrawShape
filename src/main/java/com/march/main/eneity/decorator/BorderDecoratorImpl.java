package com.march.main.eneity.decorator;

import com.march.main.eneity.ShapeBase;

import java.awt.*;

/**
 * 边框装饰
 */
public class BorderDecoratorImpl extends ShapeDecorator {

    private Color targetLineColor;

    public BorderDecoratorImpl(ShapeBase shapeBase, Color lineColor) {
        super(shapeBase);
        this.lineColor = lineColor;
        this.targetLineColor = target.getLineColor();
        target.setLineColor(lineColor);
    }

    public Color getTargetLineColor() {
        return targetLineColor;
    }

    @Override
    public void draw(Graphics2D g2d) {
        target.draw(g2d);
    }

    @Override
    public ShapeBase clone() {
        //创建一个装饰目标对象的克隆
        ShapeBase shapeBaseClone = target.clone();
        return new BorderDecoratorImpl(shapeBaseClone, this.getLineColor());
    }
}
