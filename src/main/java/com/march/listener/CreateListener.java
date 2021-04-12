package com.march.listener;

import com.march.drawframe.DrawPanel;
import com.march.eneity.ShapeBase;
import com.march.eneity.impl.MyButton;
import com.march.factory.ShapeFactory;
import com.march.factory.impl.StandardShapeFactory;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Random;

/**
 * 监听器：响应创建图形按钮绘制图形
 */
public class CreateListener implements ActionListener {


    private DrawPanel drawPanel;//面板对象，用来计算宽度、高度
    private Graphics2D g2d;//画笔对象，主窗体传入
    private List<ShapeBase> shapeBaseList = null; //保存主面板图形列表的引用

    public static final CreateListener singletonCreateListener = new CreateListener();

    private CreateListener() {
    }

    public static void setProperties(DrawPanel drawPanel, Graphics2D g2d) {
        singletonCreateListener.drawPanel = drawPanel;
        singletonCreateListener.g2d = g2d;
    }

    public void actionPerformed(ActionEvent e) {
        //1.创建抽象基类ShapeBase的引用
        ShapeBase shapeBase = null;
        //2.获取创建图形的工厂单例对象
        ShapeFactory shapeFactory = StandardShapeFactory.shapeFactory;
        //3.获取当前窗体的大小，设置坐标的范围为0.9
        int height = (int) (drawPanel.getSize().height * 0.8);
        int width = (int) (drawPanel.getSize().width * 0.8);
        Random random = new Random();
        //4.根据按钮点击，创建对象
        switch (e.getActionCommand()) {
            case "线段":
                shapeBase = shapeFactory.createLine("线段", random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
                break;
            case "矩形":
                shapeBase = shapeFactory.createRectangle("矩形", random.nextInt(width), random.nextInt(height), random.nextInt(500) + 50, random.nextInt(500) + 50);
                break;
            case "圆":
                shapeBase = shapeFactory.createCircle("圆", random.nextInt(width), random.nextInt(height), random.nextInt(100) + 50);
                break;
            case "按钮":
                //1.通过抽象工厂,创建适配JButton的MyButton对象
                shapeBase = shapeFactory.createButton("按钮", random.nextInt(width), random.nextInt(height), drawPanel);
                //2.shapeBase向下转型为MyButton获取JButton对象
                MyButton myButton = (MyButton) shapeBase;
                //3.立刻添加到面板
                drawPanel.add(myButton.getButton());
                break;
        }
        //5.从画图面板对象中取出shapeBaseList的引用，增加对应的图形
        shapeBaseList = drawPanel.getShapeBaseList();
        shapeBaseList.add(shapeBase);
        //6.调用draw，实现子类重写的不同方法
        shapeBase.draw(g2d);
    }
}
