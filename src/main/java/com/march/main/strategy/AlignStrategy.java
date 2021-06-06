package com.march.main.strategy;

import com.march.main.eneity.ShapeBase;


/**
 * 图形对齐策略接口：实现类调用图形内部对齐、撤销方法（组合装饰图形便于重写）
 */
public interface AlignStrategy {

    void adjustAlign(ShapeBase shapeBase);

    void restore(ShapeBase shapeBase);

}
