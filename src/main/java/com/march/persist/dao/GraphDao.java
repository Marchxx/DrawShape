package com.march.persist.dao;

import com.march.persist.po.GraphPo;

import java.util.List;

/**
 * 图形对象映射接口：封装操作Graph的方法
 */
public interface GraphDao {

    List<GraphPo> findAllGraph();

    void saveGraph(GraphPo graphPo);

}
