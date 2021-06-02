package com.march.main.eneity;

import com.march.main.eneity.composite.ShapeComposite;
import com.march.main.eneity.decorator.ShapeDecorator;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 * 图形抽象基类
 */
public abstract class ShapeBase {

    private String name;//图形名称

    protected int startX;//起点横坐标
    protected int startY;//起点纵坐标

    private boolean checked = false;//选中标记
    private boolean filled = false;//填充标记

    protected Color lineColor = Color.BLACK;//线条颜色，默认为黑色
    protected Color fillColor = Color.WHITE;//填充颜色，默认为白色

    //组合、装饰对象使用的构造函数
    public ShapeBase(String name) {
        this.name = name;
    }

    //普通对象使用的构造函数
    public ShapeBase(String name, int startX, int startY) {
        this.name = name;
        this.startX = startX;
        this.startY = startY;
    }

    //数据库还原对象使用的构造函数
    public ShapeBase(String name, int startX, int startY, boolean checked, boolean filled, Color lineColor, Color fillColor) {
        this.name = name;
        this.startX = startX;
        this.startY = startY;
        this.checked = checked;
        this.filled = filled;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    //抽象方法：绘图，参数为面板的画笔对象
    public abstract void draw(Graphics2D g2d);


    //抽象方法：移动，根据x、y的偏移量进行移动
    public abstract void move(int distanceX, int distanceY);


    //抽象方法：判断是否被选中，参数为鼠标坐标
    public abstract boolean isSelected(int x, int y);


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
