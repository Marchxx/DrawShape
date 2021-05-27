package com.march.persist.dao;

import com.march.persist.po.ShapeBasePo;

/**
 * 图形基类映射接口：封装操作ShapeBase的方法
 */
public interface ShapeBaseDao {

    void saveShapeBase(ShapeBasePo shapeBasePo);

}
