package com.march.persist.dao.impl;

import com.march.persist.dao.GraphDao;
import com.march.persist.po.GraphPo;
import com.march.common.utils.DruidUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class GraphDaoImpl implements GraphDao {

    //注入JdbcTemplate对象
    public JdbcTemplate jdbcTemplate = null;

    //代码块加载JdbcTemplate
    {
        jdbcTemplate = new JdbcTemplate(DruidUtil.getDataSource());
        if (jdbcTemplate == null) {
            throw new RuntimeException("加载数据库失败...");
        }
    }

    @Override
    public List<GraphPo> findAllGraph() {
        return jdbcTemplate.query("SELECT * FROM t_graph", new BeanPropertyRowMapper<>(GraphPo.class));
    }

    @Override
    public void saveGraph(GraphPo graphPo) {
        String sql = "INSERT INTO t_graph(id, name) VALUES(?,?)";
        jdbcTemplate.update(sql, graphPo.getId(), graphPo.getName());
    }
}
