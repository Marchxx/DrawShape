package com.march.common.enums;

import java.awt.*;

/**
 * 图形对齐枚举类，x、y代表设定的坐标
 */
public enum AlignEnum {

    ALIGN_UP(50),
    ALIGN_DOWN(700),
    ALIGN_LEFT(50),
    ALIGN_RIGHT(1400);

    private int delta;

    AlignEnum(int delta) {
        this.delta = delta;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }
}
