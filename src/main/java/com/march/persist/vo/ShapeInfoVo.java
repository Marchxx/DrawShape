package com.march.persist.vo;

/**
 * 查询数据库全部图形的返回封装
 */
public class ShapeInfoVo {

    private String id;//图形id，针对数据库存储
    private String name;//图形名称
    private int startX;//起点横坐标
    private int startY;//起点纵坐标
    private boolean checked;//选中标记
    private boolean filled;//填充标记
    private int lineColor;//线条颜色
    private int fillColor;//填充颜色
    private int shapetype;//图形类别
    private int radius;
    private int endx;
    private int endy;
    private int width;
    private int height;
    private String parentid;

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

    public int getShapetype() {
        return shapetype;
    }

    public void setShapetype(int shapetype) {
        this.shapetype = shapetype;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getEndx() {
        return endx;
    }

    public void setEndx(int endx) {
        this.endx = endx;
    }

    public int getEndy() {
        return endy;
    }

    public void setEndy(int endy) {
        this.endy = endy;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    @Override
    public String toString() {
        return "ShapeInfoVo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", startX=" + startX +
                ", startY=" + startY +
                ", checked=" + checked +
                ", filled=" + filled +
                ", lineColor=" + lineColor +
                ", fillColor=" + fillColor +
                ", shapetype=" + shapetype +
                ", radius=" + radius +
                ", endx=" + endx +
                ", endy=" + endy +
                ", width=" + width +
                ", height=" + height +
                ", parentid='" + parentid + '\'' +
                '}';
    }
}
