package com.march.persist.dao;

import com.march.persist.po.ShapePo;
import com.march.persist.vo.ShapeInfoVo;

import java.util.List;

/**
 * 图形对象映射接口：封装操作Shape的方法
 */
public interface ShapeDao {

    void saveShape(ShapePo shapePo);

    List<ShapeInfoVo> findAllShapeInfo(String graphId);
}
