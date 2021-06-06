package com.march.main.strategy.impl;

import com.march.main.eneity.ShapeBase;
import com.march.main.strategy.AlignStrategy;


public class UpAlignStrategy implements AlignStrategy {

    @Override
    public void adjustAlign(ShapeBase shapeBase) {
        shapeBase.adjustUpAlign();
    }

    @Override
    public void restore(ShapeBase shapeBase) {
        shapeBase.restoreUp();
    }
}
