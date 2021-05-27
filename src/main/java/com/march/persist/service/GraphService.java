package com.march.persist.service;

import com.march.main.eneity.ShapeBase;
import com.march.persist.po.GraphPo;

import java.util.List;


public interface GraphService {

    List<GraphPo> findAllGraph();

    void saveGraph(String graphName, List<ShapeBase> shapeBaseList);

}
