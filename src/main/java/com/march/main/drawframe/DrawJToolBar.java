package com.march.main.drawframe;

import com.march.common.utils.ComponentUtil;
import com.march.main.command.CommandInvoker;
import com.march.main.command.impl.DecorateCommand;
import com.march.main.eneity.ShapeBase;
import com.march.main.eneity.decorator.BorderDecoratorImpl;
import com.march.main.eneity.decorator.FillDecoratorImpl;
import com.march.common.enums.ColorEnum;
import com.march.main.listener.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

/**
 * 设置窗体顶部选项卡、按钮
 */
public class DrawJToolBar {

    //创建监听器的单例对象
    private CreateListener createListener = CreateListener.singletonCreateListener;
    private CopyListener copyListener = CopyListener.singletonCopyListener;
    private CompositeListener compositeListener = CompositeListener.singletonCompositeListener;

    private DrawPanel jPanelCenter;//中心画图面板

    public void setjPanelCenter(DrawPanel jPanelCenter) {
        this.jPanelCenter = jPanelCenter;
    }


    public JToolBar getFileJToolBar() {
        //设置菜单1
        JToolBar toolBar = ComponentUtil.getNewToolBar();
        //放置打开文件按钮
        JButton fileOpen = ComponentUtil.getNewButton("打开");
        fileOpen.addActionListener(DateAccessListener.singletonDateAccessListener);
        toolBar.add(fileOpen);
        //放置保存文件按钮
        JButton fileSave = ComponentUtil.getNewButton("保存");
        fileSave.addActionListener(DateAccessListener.singletonDateAccessListener);
        toolBar.add(fileSave);
        //放置关闭按钮
        JButton fileClose = ComponentUtil.getNewButton("关闭");
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
        JToolBar toolBar = ComponentUtil.getNewToolBar();
        String[] typeArray = {"线段", "矩形", "圆", "按钮"};
        for (int i = 0; i < typeArray.length; i++) {
            JButton button = ComponentUtil.getNewButton(typeArray[i]);
            //按钮添加创建监听器
            button.addActionListener(createListener);
            toolBar.add(button);
        }
        return toolBar;
    }


    public JToolBar getEditJToolBar() {
        //设置菜单3
        JToolBar toolBar = ComponentUtil.getNewToolBar();
        //放置复制按钮
        JButton buttonCopy = ComponentUtil.getNewButton("复制");
        buttonCopy.addActionListener(copyListener);
        toolBar.add(buttonCopy);
        //放置组合按钮
        JButton buttonComposite = ComponentUtil.getNewButton("组合");
        buttonComposite.addActionListener(compositeListener);
        toolBar.add(buttonComposite);
        //放置解除组合按钮
        JButton buttonUnComposite = ComponentUtil.getNewButton("解除组合");
        buttonUnComposite.addActionListener(compositeListener);
        toolBar.add(buttonUnComposite);
        return toolBar;
    }

    public JToolBar getDecorateJToolBar() {
        //设置菜单4
        //或者颜色枚举数组
        ColorEnum[] colorEnums = ColorEnum.values();

        //设置边框颜色
        JToolBar toolBar = ComponentUtil.getNewToolBar();
        JLabel jLabel = ComponentUtil.getNewLabel("边框颜色：");
        toolBar.add(jLabel);
        for (ColorEnum colorEnum : colorEnums) {
            JButton borderDecorate = new JButton();
            borderDecorate.setPreferredSize(new Dimension(30, 30));
            borderDecorate.setBackground(colorEnum.getColor());
            borderDecorate.addActionListener((e) -> {
                List<ShapeBase> shapeBaseList = jPanelCenter.getShapeBaseList();
                for (int j = 0; j < shapeBaseList.size(); j++) {
                    if (shapeBaseList.get(j).isChecked()) {
                        //1.创建新的装饰对象，并设置选中为true可以连续点击增加装饰
                        BorderDecoratorImpl borderDecorator = new BorderDecoratorImpl(shapeBaseList.get(j), colorEnum.getColor());
                        borderDecorator.setChecked(true);
                        //2.执行装饰命令
                        DecorateCommand decorateCommand = new DecorateCommand(jPanelCenter, borderDecorator);
                        CommandInvoker.singletonCommandInvoker.execute(decorateCommand);
                    }
                }
                jPanelCenter.repaint();
            });
            toolBar.add(borderDecorate);
        }

        //填充颜色
        JLabel jLabel1 = ComponentUtil.getNewLabel("填充颜色：");
        toolBar.add(jLabel1);
        for (ColorEnum colorEnum : colorEnums) {
            JButton centerFill = new JButton();
            centerFill.setPreferredSize(new Dimension(30, 30));
            centerFill.setBackground(colorEnum.getColor());
            centerFill.addActionListener((e) -> {
                List<ShapeBase> shapeBaseList = jPanelCenter.getShapeBaseList();
                for (int j = 0; j < shapeBaseList.size(); j++) {
                    if (shapeBaseList.get(j).isChecked()) {
                        //1.创建新的装饰对象，设置选中为true可以连续点击增加装饰
                        FillDecoratorImpl fillDecorator = new FillDecoratorImpl(shapeBaseList.get(j), colorEnum.getColor());
                        fillDecorator.setChecked(true);
                        //2.执行装饰命令
                        DecorateCommand decorateCommand = new DecorateCommand(jPanelCenter, fillDecorator);
                        CommandInvoker.singletonCommandInvoker.execute(decorateCommand);
                    }
                }
                jPanelCenter.repaint();
            });
            toolBar.add(centerFill);
        }

        //取消填充
        JButton clearFill = ComponentUtil.getNewButton("取消填充");
        clearFill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (ShapeBase shapeBase : jPanelCenter.getShapeBaseList()) {
                    if (shapeBase.isChecked()) {
                        shapeBase.setFilled(false);
                    }
                }
                jPanelCenter.repaint();
            }
        });
        toolBar.add(clearFill);

        return toolBar;
    }

}
