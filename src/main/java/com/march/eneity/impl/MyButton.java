package com.march.eneity.impl;

import com.march.eneity.ShapeBase;
import com.march.listener.MouseListener;

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
        //1.2按钮添加单例的选中监听器
        button.addMouseListener(MouseListener.singletonMouseListener);
        this.drawPanel = drawPanel;
    }

    public JButton getButton() {
        return button;
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
    public boolean isSelected(int x, int y, MouseEvent e) {
        //判断点击处是否为控件，获取坐标
        if (e.getSource() instanceof JButton) {
            JButton jButton = (JButton) e.getSource();
            x = jButton.getX();
            y = jButton.getY();
        }
        int boundLeftX = button.getX() - 10;
        int boundRightX = button.getX() + button.getWidth() + 10;
        int boundUpY = button.getY() - 10;
        int boundDownY = button.getY() + button.getHeight() + 10;
        return x >= boundLeftX && x <= boundRightX && y >= boundUpY && y <= boundDownY;
    }

    @Override
    public ShapeBase clone() {
        //创建MyButton的克隆对象，并添加到面板
        MyButton clone = new MyButton(this.getName(),
                this.button.getX(), this.button.getY() + 50, drawPanel);
        drawPanel.add(clone.getButton());
        return clone;
    }

    @Override
    public void clearShape() {
        //在画板上清除该组件
        drawPanel.remove(button);
    }
}
