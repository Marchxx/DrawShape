package com.march.main.strategy.impl;

import com.march.main.eneity.ShapeBase;
import com.march.main.strategy.AlignStrategy;


public class LeftAlignStrategy implements AlignStrategy {

    @Override
    public void adjustAlign(ShapeBase shapeBase) {
        shapeBase.adjustLeftAlign();
    }

    @Override
    public void restore(ShapeBase shapeBase) {
        shapeBase.restoreLeft();
    }
}
