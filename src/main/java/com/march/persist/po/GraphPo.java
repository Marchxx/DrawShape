package com.march.persist.po;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 图纸对象
 */
public class GraphPo {

    private String id;
    private String name;
    private Date createTime;
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return "图纸名称：" + name +
                ", 创建时间：" + sdf.format(createTime) +
                ", 最后更新时间：" + sdf.format(updateTime)
                ;
    }
}
