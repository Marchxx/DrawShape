package com.march.persist.dao.impl;

import com.march.common.utils.DruidUtil;
import com.march.persist.dao.ShapeBaseDao;
import com.march.persist.po.ShapeBasePo;
import org.springframework.jdbc.core.JdbcTemplate;

public class ShapeBaseDaoImpl implements ShapeBaseDao {

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
    public void saveShapeBase(ShapeBasePo shapeBasePo) {
        String sql = "INSERT INTO t_shapebase VALUES(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, shapeBasePo.getId(), shapeBasePo.getName(),
                shapeBasePo.getStartX(), shapeBasePo.getStartY(),
                shapeBasePo.isChecked(), shapeBasePo.isFilled(),
                shapeBasePo.getLineColor(),
                shapeBasePo.getFillColor()
        );
    }
}
