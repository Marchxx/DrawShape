package com.march.drawframe;

import com.march.eneity.ShapeBase;
import com.march.eneity.impl.MyButton;
import com.march.listener.CopyListener;
import com.march.listener.CreateListener;
import com.march.listener.MoveListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 设置窗体顶部选项卡、按钮
 */
public class DrawJToolBar {

    //创建监听器的单例对象
    private CreateListener createListener = CreateListener.singletonCreateListener;
    private MoveListener moveListener = MoveListener.singletonMoveListener;
    private CopyListener copyListener = CopyListener.singletonCopyListener;

    private DrawPanel jPanelCenter;//中心画图面板
    private JToolBar toolBar = null;//用来返回

    public void setjPanelCenter(DrawPanel jPanelCenter) {
        this.jPanelCenter = jPanelCenter;
    }

    /**
     * 统一调整菜单条样式
     */
    public void getNewInstance() {
        toolBar = new JToolBar();
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        toolBar.setPreferredSize(new Dimension(1900, 50));
    }


    public JToolBar getFileJToolBar() {
        //设置菜单1
        getNewInstance();
        JButton fileClose = new JButton("关闭");
        fileClose.setFont(new Font("黑体", Font.PLAIN, 20));
        fileClose.setPreferredSize(new Dimension(100, 30));
        fileClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //退出按钮：关闭
            }
        });
        toolBar.add(fileClose);
        return toolBar;
    }


    public JToolBar getDrawJToolBar() {
        //设置菜单2
        getNewInstance();
        String[] typeArray = {"线段", "矩形", "圆", "按钮"};
        for (int i = 0; i < typeArray.length; i++) {
            JButton button = new JButton(typeArray[i]);
            button.setFont(new Font("黑体", Font.PLAIN, 20));
            button.setPreferredSize(new Dimension(100, 30));
            //按钮添加创建监听器
            button.addActionListener(createListener);
            toolBar.add(button);
        }
        return toolBar;
    }


    public JToolBar getEditJToolBar() {
        //设置菜单3
        getNewInstance();
        String[] typeArray1 = {"左移", "右移", "上移", "下移"};
        for (int i = 0; i < typeArray1.length; i++) {
            JButton button = new JButton(typeArray1[i]);
            button.setFont(new Font("黑体", Font.PLAIN, 20));
            button.setPreferredSize(new Dimension(100, 30));
            //按钮添加移动监听器
            button.addActionListener(moveListener);
            toolBar.add(button);
        }
        //放置复制按钮
        JButton buttonCopy = new JButton("复制");
        buttonCopy.setFont(new Font("黑体", Font.PLAIN, 20));
        buttonCopy.setPreferredSize(new Dimension(100, 30));
        buttonCopy.addActionListener(copyListener);
        toolBar.add(buttonCopy);
        //放置清空按钮
        JButton buttonCls = new JButton("清空");
        buttonCls.setFont(new Font("黑体", Font.PLAIN, 20));
        buttonCls.setPreferredSize(new Dimension(100, 30));
        buttonCls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //点击清空：遍历列表，若为自定义组件在容器中remove
                List<ShapeBase> shapeBaseList = jPanelCenter.getShapeBaseList();
                for (ShapeBase shapeBase : shapeBaseList) {
                    if (shapeBase instanceof MyButton) {
                        jPanelCenter.remove(((MyButton) shapeBase).getButton());
                    }
                }
                shapeBaseList.clear();
                jPanelCenter.repaint();
            }
        });
        toolBar.add(buttonCls);
        return toolBar;
    }


}
