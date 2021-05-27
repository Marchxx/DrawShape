package com.march.enums;

import java.awt.*;

/**
 * 颜色枚举类
 */
public enum ColorEnum {

    RED(0, Color.RED),
    GREEN(1, Color.GREEN),
    BLUE(2, Color.BLUE),
    BLACK(3, Color.BLACK),
    CYAN(4, Color.CYAN),
    GRAY(5, Color.GRAY),
    MAGENTA(6, Color.MAGENTA),
    ORANGE(7, Color.ORANGE),
    PINK(8, Color.PINK);

    int code;
    Color color;

    ColorEnum(int code, Color color) {
        this.code = code;
        this.color = color;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * 根据Color返回对应枚举项的code
     */
    public static int getCodeByColor(Color color) {
        ColorEnum[] values = ColorEnum.values();
        for (ColorEnum value : values) {
            if (value.color.equals(color)) {
                return value.code;
            }
        }
        return -1;
    }

    /**
     * 根据code返回对应枚举项的Color
     */
    public static Color getColorByCode(int code) {
        ColorEnum[] values = ColorEnum.values();
        for (ColorEnum value : values) {
            if (value.code == code) {
                return value.color;
            }
        }
        return null;
    }
}
