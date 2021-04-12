package com.march.eneity.impl;

import com.march.eneity.ShapeBase;
import com.march.listener.SelectListener;

import javax.swing.*;
import java.awt.*;

/**
 * 设计模式：基于对象的适配器。在MyButton类中引用原生JButton，将其整合到图形对象中
 */
public class MyButton extends ShapeBase {

    private JButton button;
    private JPanel drawPanel;

    public MyButton(String name, int leftUpX, int leftUpY, JPanel drawPanel) {
        super(name);
        //1.1创建原生的JButton
        button = new JButton("自定义按钮");
        button.setFont(new Font("黑体", Font.PLAIN, 20));
        button.setBounds(leftUpX, leftUpY, 150, 30);
        //1.2按钮添加单例的选中监听器
        button.addMouseListener(SelectListener.singletonSelectListener);
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
            button.setBorder(BorderFactory.createEtchedBorder());
        }
    }

    @Override
    public void move(String operation) {
        this.deltaX = 0;
        this.deltaY = 0;
        calculateDelta(operation);
        button.setLocation(button.getX() + deltaX, button.getY() + deltaY);
    }

    @Override
    public boolean isSelected(int x, int y) {
        return x == button.getX() && y == button.getY();
    }

    @Override
    public ShapeBase clone() {
        //创建MyButton的克隆对象，并添加到面板
        MyButton clone = new MyButton(this.getName(),
                this.button.getX(), this.button.getY() + 50, drawPanel);
        drawPanel.add(clone.getButton());
        return clone;
    }
}
