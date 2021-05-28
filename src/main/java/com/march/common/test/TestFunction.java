package com.march.common.test;

import com.march.common.enums.ColorEnum;
import com.march.common.utils.UuidGenerator;
import org.junit.Test;

import java.awt.*;

public class TestFunction {

    @org.junit.Test
    public void testUuid() {
        for (int i = 0; i < 10; i++) {
            System.out.println(UuidGenerator.getUuid());
        }
    }

    @Test
    public void testEnum() {
        int code = ColorEnum.getCodeByColor(Color.RED);
        System.out.println(code);

        Color color = ColorEnum.getColorByCode(0);
        System.out.println(color);
    }

    @Test
    public void testSubstring() {
        String str = "图纸名称：测试, 创建时间：2021-05-28 10:28, 最后更新时间：2021-05-28 10:28";
        System.out.println(str);
        String substringTemp = str.substring(0, str.lastIndexOf(","));
        String result = substringTemp.substring(5, substringTemp.lastIndexOf(","));
        System.out.println(result);
    }
}
