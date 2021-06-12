package com.march.main.observe;

import com.march.main.eneity.ShapeBase;

/**
 * 观察者接口：包含update方法
 */
public interface Observer {

    void update(ShapeBase shapeBase);

}
