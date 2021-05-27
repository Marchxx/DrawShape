package com.march.common.test;

import com.march.persist.dao.GraphDao;
import com.march.persist.dao.impl.GraphDaoImpl;
import com.march.persist.po.GraphPo;
import com.march.common.utils.DruidUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TestJdbcTemplate {

    public JdbcTemplate jdbcTemplate = null;
    public GraphDao graphDao = null;

    @Before
    public void init() {
        jdbcTemplate = new JdbcTemplate(DruidUtil.getDataSource());
        graphDao = new GraphDaoImpl();
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
}
