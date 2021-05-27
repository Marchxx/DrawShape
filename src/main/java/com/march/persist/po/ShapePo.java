package com.march.persist.po;

/**
 * 图形对象：封装了Shape全部实现类的属性字段，针对数据库扩展出来
 */
public class ShapePo {

    private String id;
    private String parentid;
    private int shapetype;
    private int radius;
    private int endx;
    private int endy;
    private int width;
    private int height;
    private String graphid;//外键

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public int getShapetype() {
        return shapetype;
    }

    public void setShapetype(int shapetype) {
        this.shapetype = shapetype;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
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

    public String getGraphid() {
        return graphid;
    }

    public void setGraphid(String graphid) {
        this.graphid = graphid;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "id='" + id + '\'' +
                ", parentid='" + parentid + '\'' +
                ", shapetype=" + shapetype +
                ", radius=" + radius +
                ", endx=" + endx +
                ", endy=" + endy +
                ", width=" + width +
                ", height=" + height +
                ", graphid='" + graphid + '\'' +
                '}';
    }
}
