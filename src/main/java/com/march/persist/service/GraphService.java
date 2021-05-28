package com.march.persist.service;

import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;
import com.march.persist.po.GraphPo;

import java.util.List;


public interface GraphService {

    public void setDrawPanel(DrawPanel drawPanel);

    GraphPo findOneByName(String name);

    List<GraphPo> findAllGraph();

    void saveGraph(String graphName, List<ShapeBase> shapeBaseList);

    List<ShapeBase> openGraph(String graphId);
}
