package com.march.common.utils;

import java.util.UUID;

/**
 * 获取32位的id，作为数据库主键
 */
public class UuidGenerator {

    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
