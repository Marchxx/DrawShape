package com.march.persist.dao.impl;

import com.march.common.utils.DruidUtil;
import com.march.persist.dao.ShapeDao;
import com.march.persist.po.ShapePo;
import org.springframework.jdbc.core.JdbcTemplate;

public class ShapeDaoImpl implements ShapeDao {

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
    public void saveShape(ShapePo shapePo) {
        String sql = "INSERT INTO t_shape VALUES(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, shapePo.getId(), shapePo.getParentid(),
                shapePo.getShapetype(), shapePo.getRadius(),
                shapePo.getEndx(), shapePo.getEndy(),
                shapePo.getWidth(), shapePo.getHeight(),
                shapePo.getGraphid()
        );
    }
}
