package com.march.main.eneity.composite;


import com.march.common.enums.AlignEnum;
import com.march.main.eneity.ShapeBase;
import com.march.main.factory.impl.StandardShapeFactory;

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
    public void doMove(int distanceX, int distanceY) {
        //1.应该调用shape的doMove方法，若调用move每个具体类都会发出通知
        //2.重写notifyObserver，由模板方法完成只通知列表的第一个具体对象，若还是组合会递归调用
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.doMove(distanceX, distanceY);
        }
    }

    @Override
    public boolean isSelected(int x, int y) {
        boolean flag = false;
        for (ShapeBase shapeBase : shapeBaseList) {
            if (shapeBase.isSelected(x, y)) {
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
    public void setFilled(boolean filled) {
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.setFilled(filled);
        }
    }

    @Override
    public void setLineColor(Color lineColor) {
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.setLineColor(lineColor);
        }
    }

    @Override
    public void setFillColor(Color fillColor) {
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.setFillColor(fillColor);
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

    //重写对齐、恢复方法
    //调整对齐
    public void adjustUpAlign() {//上对齐
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.adjustUpAlign();
        }
    }

    public void restoreUp() {//撤销上对齐
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.restoreUp();
        }
    }

    public void adjustDownAlign() {//下对齐
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.adjustDownAlign();
        }
    }

    public void restoreDown() {//撤销下对齐
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.restoreDown();
        }
    }

    public void adjustLeftAlign() {//左对齐
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.adjustLeftAlign();
        }
    }

    public void restoreLeft() {//撤销左对齐
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.restoreLeft();
        }
    }

    public void adjustRightAlign() {//右对齐
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.adjustRightAlign();
        }
    }

    public void restoreRight() {//撤销右对齐
        for (ShapeBase shapeBase : shapeBaseList) {
            shapeBase.restoreRight();
        }
    }

    /**
     * 重写通知方法
     * 组合对象，只通知给列表第一个具体对象
     */
    @Override
    public void notifyObserver() {
        shapeBaseList.get(0).notifyObserver();
    }
}
