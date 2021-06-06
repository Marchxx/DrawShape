package com.march.main.strategy;

import com.march.main.eneity.ShapeBase;
import com.march.main.strategy.impl.LeftAlignStrategy;

import java.awt.*;

/**
 * 执行对齐策略的上下文环境，与客户端进行交互
 */
public class AlignContext {

    //无参构造创建默认策略，左对齐
    private AlignStrategy alignStrategy = new LeftAlignStrategy();

    public AlignContext() {
    }

    public AlignContext(AlignStrategy alignStrategy) {
        this.alignStrategy = alignStrategy;
    }

    //执行策略方法
    public void executeStrategy(ShapeBase shapeBase) {
        alignStrategy.adjustAlign(shapeBase);
    }

    //撤销策略方法
    public void restore(ShapeBase shapeBase) {
        alignStrategy.restore(shapeBase);
    }
}
