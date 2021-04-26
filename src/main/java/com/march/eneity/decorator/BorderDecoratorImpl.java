package com.march.eneity.decorator;

import com.march.eneity.ShapeBase;

import java.awt.*;

/**
 * 边框装饰
 */
public class BorderDecoratorImpl extends ShapeDecorator {


    public BorderDecoratorImpl(ShapeBase shapeBase, Color lineColor) {
        super(shapeBase);
        target.setLineColor(lineColor);
    }

    @Override
    public void draw(Graphics2D g2d) {
        target.draw(g2d);
    }
}
