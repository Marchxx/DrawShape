package com.march.common.enums;

/**
 * 图形枚举类
 */
public enum ShapeEnum {

    CIRCLE(0),
    LINE(1),
    MYBUTTON(2),
    RECTANGLE(3),
    COMPOSITE(4),
    DECORATOR(5);

    int code;

    ShapeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
