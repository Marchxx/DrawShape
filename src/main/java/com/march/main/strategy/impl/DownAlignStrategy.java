package com.march.main.strategy.impl;

import com.march.main.eneity.ShapeBase;
import com.march.main.strategy.AlignStrategy;


public class DownAlignStrategy implements AlignStrategy {

    @Override
    public void adjustAlign(ShapeBase shapeBase) {
        shapeBase.adjustDownAlign();
    }

    @Override
    public void restore(ShapeBase shapeBase) {
        shapeBase.restoreDown();
    }
}
