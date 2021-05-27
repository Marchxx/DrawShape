package com.march.common.test;

import com.march.common.enums.ColorEnum;
import org.junit.Test;

import java.awt.*;

public class TestEnum {

    @Test
    public void test01() {
        int code = ColorEnum.getCodeByColor(Color.RED);
        System.out.println(code);

        Color color = ColorEnum.getColorByCode(0);
        System.out.println(color);
    }

}
