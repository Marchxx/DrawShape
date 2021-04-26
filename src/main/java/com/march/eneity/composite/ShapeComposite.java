package com.march.eneity.composite;



import com.march.eneity.ShapeBase;
import com.march.factory.impl.StandardShapeFactory;

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

    public List<ShapeBase> getChildren() {
        return shapeBaseList;
    }

    public String printAll(StringBuilder sb) {
        sb.append("{ \n" + "    组合节点：" + this);
        sb.append("    叶子数量=" + this.shapeBaseList.size() + "\n");
        for (ShapeBase child : getChildren()) {
            if (child instanceof ShapeComposite) {
                ShapeComposite composite = (ShapeComposite) child;
                //递归调用，获取当前根结点的字符串表示
                String printAll = composite.printAll(new StringBuilder());
                sb.append(printAll);
            } else {
                sb.append("    叶子节点：" + child + "\n");
            }
        }
        sb.append("}\n");
        return sb.toString();
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
        //1.创建一个组合对象
        ShapeComposite cloneComposite = (ShapeComposite) StandardShapeFactory.shapeFactory.createComposite(this.getName());
        //2.遍历图形集合，分别调用叶子的克隆方法
        for (ShapeBase shapeBase : shapeBaseList) {
            ShapeBase clone = shapeBase.clone();
            cloneComposite.add(clone);
        }
        //3.返回组合对象
        return cloneComposite;
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

    @Override
    public void setLineColor(Color lineColor) {
        super.setLineColor(lineColor);
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.setLineColor(lineColor);
        }
    }

    @Override
    public ShapeComposite getComposite() {
        return this;
    }

    @Override
    public void clearShape() {
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.clearShape();
        }
    }
}
