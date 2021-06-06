package com.march.main.strategy.impl;

import com.march.common.enums.AlignEnum;
import com.march.main.eneity.ShapeBase;
import com.march.main.strategy.AlignStrategy;

import java.awt.*;

public class RightAlignStrategy implements AlignStrategy {

    @Override
    public void adjustAlign(ShapeBase shapeBase) {
        shapeBase.adjustRightAlign();
    }

    @Override
    public void restore(ShapeBase shapeBase) {
        shapeBase.restoreRight();
    }
}
