package com.march.persist.po;


/**
 * 图形基类对象
 */
public class ShapeBasePo {

    private String id;//图形id，针对数据库存储
    private String name;//图形名称
    private int startX;//起点横坐标
    private int startY;//起点纵坐标
    private boolean checked;//选中标记
    private boolean filled;//填充标记
    private int lineColor;//线条颜色
    private int fillColor;//填充颜色

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    @Override
    public String toString() {
        return "ShapeBasePo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startX=" + startX +
                ", startY=" + startY +
                ", checked=" + checked +
                ", filled=" + filled +
                ", lineColor=" + lineColor +
                ", fillColor=" + fillColor +
                '}';
    }
}
