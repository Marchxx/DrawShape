package com.march.eneity;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 图形组合对象
 */
public class ShapeComposite extends ShapeBase {

    //保存该组合对象包含的叶子节点
    private List<ShapeBase> shapeBaseList = null;

    public ShapeComposite(String name) {
        super(name);
        shapeBaseList = new ArrayList<>();
    }

    //组合对象的特有方法
    public void add(ShapeBase shapeBase) {
        shapeBaseList.add(shapeBase);
    }

    public void remove(ShapeBase shapeBase) {
        shapeBaseList.remove(shapeBase);
    }

    public void getChildren() {
        for (ShapeBase shapeBase : shapeBaseList) {
            System.out.println(shapeBase);
        }
    }


    //组合对象继承的通用方法：分发给列表对象递归调用
    @Override
    public void draw(Graphics2D g2d) {
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.draw(g2d);
        }
    }

    @Override
    public void move(String operation) {
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.move(operation);
        }
    }

    @Override
    public boolean isSelected(int x, int y, MouseEvent e) {
        boolean flag = false;
        for (ShapeBase shapeBase : shapeBaseList) {
            if (shapeBase.isSelected(x, y, e)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public ShapeBase clone() {
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.clone();
        }
        return null;
    }

    @Override
    public void setChecked(boolean checked) {
        //1.组合对象自身设置选中，调用基类的方法设置
        super.setChecked(checked);
        for (ShapeBase shapeBase : shapeBaseList) {
            //2.设置所有叶子结点都选中
            shapeBase.setChecked(checked);
        }
    }
}
