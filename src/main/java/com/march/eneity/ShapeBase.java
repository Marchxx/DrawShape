package com.march.eneity;

import com.march.eneity.composite.ShapeComposite;
import com.march.eneity.decorator.ShapeDecorator;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 图形抽象基类
 */
public abstract class ShapeBase {

    private String name;//图形名称
    private boolean checked = false;//选中标记
    private boolean isFilled = false;//填充标记

    protected Color lineColor = Color.BLACK;//线条颜色，默认为黑色
    protected Color fillColor = Color.WHITE;//填充颜色，默认为白色

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public ShapeBase(String name) {
        this.name = name;
    }


    //抽象方法：绘图，参数为面板的画笔对象
    public abstract void draw(Graphics2D g2d);


    //抽象方法：移动，根据x、y的偏移量进行移动
    public abstract void move(int distanceX, int distanceY);


    //抽象方法：判断是否被选中，参数为鼠标坐标、e用来判定当前鼠标是否为自定义组件
    public abstract boolean isSelected(int x, int y, MouseEvent e);


    //抽象方法：对象克隆
    public abstract ShapeBase clone();


    /**
     * 清空图形：默认图形无实现，第三方组件类如MyButton进行重写
     */
    public void clearShape() {
    }


    /**
     * 解耦：基类默认返回null，组合对象返回this。避免程序运行时动态判断基类引用对应的实现类
     */
    public ShapeComposite getComposite() {
        return null;
    }

    /**
     * 解耦：基类默认返回null，装饰对象返回this。避免程序运行时动态判断基类引用对应的实现类
     */
    public ShapeDecorator getDecorator() {
        return null;
    }

    /**
     * 根据选中状态调整画笔属性，设置边框颜色
     */
    public Graphics2D adjustBrush(Graphics2D g2d) {
        if (isChecked()) {
            //若选中则画笔设为红色
            g2d.setStroke(new BasicStroke(4.0f));
            g2d.setColor(Color.RED);
        } else {
            g2d.setStroke(new BasicStroke(3.5f));
            g2d.setColor(this.lineColor);
        }
        return g2d;
    }
}
