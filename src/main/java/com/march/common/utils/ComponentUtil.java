package com.march.common.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 组件工具类
 */
public class ComponentUtil {

    /**
     * 菜单条样式
     */
    public static JToolBar getNewToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        toolBar.setPreferredSize(new Dimension(1900, 50));
        return toolBar;
    }

    /**
     * 按钮样式
     */
    public static JButton getNewButton(String label) {
        JButton jButton = new JButton(label);
        jButton.setFont(new Font("黑体", Font.PLAIN, 20));
        jButton.setPreferredSize(new Dimension(100, 30));
        return jButton;
    }

    /**
     * 标签样式
     */
    public static JLabel getNewLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("黑体", Font.PLAIN, 20));
        label.setPreferredSize(new Dimension(100, 30));
        return label;
    }

    /**
     * 弹出菜单条样式
     */
    public static JPopupMenu getNewJPopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        //menu.setPreferredSize(new Dimension(120, 80));
        return menu;
    }

    /**
     * 弹出菜单样式
     */
    public static JMenuItem getNewJMenuItem(String text) {
        JMenuItem jMenuItem = new JMenuItem(text);
        jMenuItem.setFont(new Font("黑体", Font.PLAIN, 18));
        return jMenuItem;
    }

    /**
     * 返回鼠标位置，处理自定义按钮组件
     * @param e
     * @return
     */
    public static Point getMouseRealLocation(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        if (e.getSource() instanceof JButton) {
            JComponent jComponent = (JComponent) e.getSource();
            x += jComponent.getX();
            y += jComponent.getY();
        }
        return new Point(x, y);
    }
}
