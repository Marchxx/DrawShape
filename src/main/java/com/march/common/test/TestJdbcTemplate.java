package com.march.common.test;

import com.march.persist.dao.GraphDao;
import com.march.persist.dao.ShapeDao;
import com.march.persist.dao.impl.GraphDaoImpl;
import com.march.persist.dao.impl.ShapeDaoImpl;
import com.march.persist.po.GraphPo;
import com.march.common.utils.DruidUtil;
import com.march.persist.vo.ShapeInfoVo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TestJdbcTemplate {

    public JdbcTemplate jdbcTemplate = null;
    public GraphDao graphDao = null;
    public ShapeDao shapeDao = null;

    @Before
    public void init() {
        jdbcTemplate = new JdbcTemplate(DruidUtil.getDataSource());
        graphDao = new GraphDaoImpl();
        shapeDao = new ShapeDaoImpl();
    }

    @Test
    public void test01() {
        System.out.println(jdbcTemplate);
        List<GraphPo> graphPoList = jdbcTemplate.query("SELECT * FROM t_graph", new BeanPropertyRowMapper<>(GraphPo.class));
        for (GraphPo graphPo : graphPoList) {
            System.out.println(graphPo);
        }
    }

    @Test
    public void test02() {
        List<GraphPo> graphPoList = graphDao.findAllGraph();
        for (GraphPo graphPo : graphPoList) {
            System.out.println(graphPo);
        }
    }

    @Test
    public void test03() {
        GraphPo graphPo = new GraphPo();
        graphPo.setId("e140ca501cd84492b2de91422e49d729");
        graphPo.setName("测试");
        graphDao.saveGraph(graphPo);
    }

    @Test
    public void test04() {
        List<ShapeInfoVo> infoVoList = shapeDao.findAllShapeInfo("7e436af7fa4b4b4a9be62a64d10f9c03");
        for (ShapeInfoVo shapeInfoVo : infoVoList) {
            System.out.println(shapeInfoVo);
        }
    }
}
