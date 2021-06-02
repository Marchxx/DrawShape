package com.march.main.eneity.impl;

import com.march.main.eneity.ShapeBase;
import com.march.main.listener.MouseListener;
import com.march.main.listener.RightMenuListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 设计模式：基于对象的适配器。在MyButton类中引用原生JButton，将其整合到图形对象中
 */
public class MyButton extends ShapeBase {

    private JButton button;
    private JPanel drawPanel;


    public MyButton(String name, int startX, int startY, JPanel drawPanel) {
        super(name, startX, startY);
        //1.1创建原生的JButton
        button = new JButton(name);
        button.setFont(new Font("黑体", Font.PLAIN, 20));
        button.setBounds(startX, startY, 150, 30);
        //1.2按钮添加单例的监听器
        button.addMouseListener(MouseListener.singletonMouseListener);
        button.addMouseMotionListener(MouseListener.singletonMouseListener);
        button.addMouseListener(RightMenuListener.singletonRightMenuListener);
        //1.3将按钮添加到面板
        drawPanel.add(button);
        this.drawPanel = drawPanel;
    }

    public MyButton(String name, int startX, int startY, boolean checked, boolean filled, Color lineColor, Color fillColor, JPanel drawPanel) {
        super(name, startX, startY, checked, filled, lineColor, fillColor);
        //1.1 创建原生的JButton
        button = new JButton(name);
        button.setFont(new Font("黑体", Font.PLAIN, 20));
        button.setBounds(startX, startY, 150, 30);
        //1.2 按钮添加单例的监听器
        button.addMouseListener(MouseListener.singletonMouseListener);
        button.addMouseMotionListener(MouseListener.singletonMouseListener);
        button.addMouseListener(RightMenuListener.singletonRightMenuListener);
        //1.3 将按钮添加到面板
        drawPanel.add(button);
        this.drawPanel = drawPanel;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isChecked()) {
            button.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        } else {
            //button.setBorder(BorderFactory.createEtchedBorder());//默认样式
            button.setBorder(BorderFactory.createLineBorder(this.lineColor, 3));
        }
    }

    @Override
    public void move(int deltaX, int deltaY) {
        button.setLocation(button.getX() + deltaX, button.getY() + deltaY);
    }

    @Override
    public boolean isSelected(int x, int y) {
        int boundLeftX = button.getX() - 10;
        int boundRightX = button.getX() + button.getWidth() + 10;
        int boundUpY = button.getY() - 10;
        int boundDownY = button.getY() + button.getHeight() + 10;
        return x >= boundLeftX && x <= boundRightX && y >= boundUpY && y <= boundDownY;
    }

    @Override
    public ShapeBase clone() {
        //创建MyButton的克隆对象，在构造函数中直接画出button
        return new MyButton(this.getName(),
                this.button.getX(), this.button.getY() + 50, drawPanel);
    }

    @Override
    public void clearShape() {
        //在画板上清除该组件
        drawPanel.remove(button);
    }
}
