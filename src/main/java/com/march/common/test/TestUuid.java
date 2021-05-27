package com.march.common.test;

import com.march.common.utils.UuidGenerator;
import org.junit.Test;

public class TestUuid {

    @Test
    public void test01() {
        for (int i = 0; i < 10; i++) {
            System.out.println(UuidGenerator.getUuid());
        }
    }

}
