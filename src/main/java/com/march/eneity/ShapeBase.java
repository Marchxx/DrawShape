package com.march.eneity;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 图形抽象基类
 */
public abstract class ShapeBase {

    private String name;//图形名称
    private boolean checked = false;//选中标记

    //设置为protected，不同包的子类能够访问
    protected int deltaX = 0;//x轴偏移
    protected int deltaY = 0;//y轴偏移

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

    public ShapeBase(String name) {
        this.name = name;
    }


    //抽象方法：绘图，参数为面板的画笔对象
    public abstract void draw(Graphics2D g2d);


    //抽象方法：移动，根据指令进行移动
    public abstract void move(String operation);


    //抽象方法：判断是否被选中，参数为鼠标坐标、e用来判定当前鼠标是否为自定义组件
    public abstract boolean isSelected(int x, int y, MouseEvent e);


    //抽象方法：对象克隆
    public abstract ShapeBase clone();


    /**
     * 根据选中状态调整画笔属性
     *
     * @param g2d
     * @return
     */
    public Graphics2D adjustBrush(Graphics2D g2d) {
        if (isChecked()) {
            g2d.setStroke(new BasicStroke(3.5f));
            g2d.setColor(Color.red);
        } else {
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.setColor(Color.BLACK);
        }
        return g2d;
    }


    /**
     * 根据operation计算偏移值
     * this：实现类调用的对象
     *
     * @param operation
     */
    public void calculateDelta(String operation) {
        switch (operation) {
            case "left":
                this.deltaX -= 10;
                break;
            case "right":
                this.deltaX += 10;
                break;
            case "top":
                this.deltaY -= 10;
                break;
            case "down":
                this.deltaY += 10;
                break;
        }
    }
}
