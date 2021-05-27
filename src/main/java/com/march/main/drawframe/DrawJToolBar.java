package com.march.main.drawframe;

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
    private MoveListener moveListener = MoveListener.singletonMoveListener;
    private CopyListener copyListener = CopyListener.singletonCopyListener;
    private CompositeListener compositeListener = CompositeListener.singletonCompositeListener;

    private DrawPanel jPanelCenter;//中心画图面板

    public void setjPanelCenter(DrawPanel jPanelCenter) {
        this.jPanelCenter = jPanelCenter;
    }

    /**
     * 统一调整菜单条样式
     */
    public JToolBar getNewToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        toolBar.setPreferredSize(new Dimension(1900, 50));
        return toolBar;
    }

    /**
     * 统一调整按钮样式
     */
    public JButton getNewButton(String label) {
        JButton jButton = new JButton(label);
        jButton.setFont(new Font("黑体", Font.PLAIN, 20));
        jButton.setPreferredSize(new Dimension(100, 30));
        return jButton;
    }

    /**
     * 统一调整标签样式
     */
    public JLabel getNewLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("黑体", Font.PLAIN, 20));
        label.setPreferredSize(new Dimension(100, 30));
        return label;
    }


    public JToolBar getFileJToolBar() {
        //设置菜单1
        JToolBar toolBar = getNewToolBar();
        //放置打开文件按钮
        JButton fileOpen = getNewButton("打开");
        fileOpen.addActionListener(DateAccessListener.singletonDateAccessListener);
        toolBar.add(fileOpen);
        //放置保存文件按钮
        JButton fileSave = getNewButton("保存");
        fileSave.addActionListener(DateAccessListener.singletonDateAccessListener);
        toolBar.add(fileSave);
        //放置关闭按钮
        JButton fileClose = getNewButton("关闭");
        fileClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //退出按钮：关闭
            }
        });
        toolBar.add(fileClose);
        //放置清空按钮
        JButton buttonCls = getNewButton("清空画板");
        buttonCls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //点击清空：遍历列表
                List<ShapeBase> shapeBaseList = jPanelCenter.getShapeBaseList();
                for (ShapeBase shapeBase : shapeBaseList) {
                    shapeBase.clearShape();
                }
                shapeBaseList.clear();
                jPanelCenter.repaint();
            }
        });
        toolBar.add(buttonCls);
        return toolBar;
    }


    public JToolBar getDrawJToolBar() {
        //设置菜单2
        JToolBar toolBar = getNewToolBar();
        String[] typeArray = {"线段", "矩形", "圆", "按钮"};
        for (int i = 0; i < typeArray.length; i++) {
            JButton button = getNewButton(typeArray[i]);
            //按钮添加创建监听器
            button.addActionListener(createListener);
            toolBar.add(button);
        }
        return toolBar;
    }


    public JToolBar getMoveJToolBar() {
        //设置菜单3
        JToolBar toolBar = getNewToolBar();
        String[] typeArray = {"左移", "右移", "上移", "下移"};
        for (int i = 0; i < typeArray.length; i++) {
            JButton button = getNewButton(typeArray[i]);
            //按钮添加移动监听器
            button.addActionListener(moveListener);
            toolBar.add(button);
        }
        return toolBar;
    }


    public JToolBar getEditJToolBar() {
        //设置菜单4
        JToolBar toolBar = getNewToolBar();
        //放置复制按钮
        JButton buttonCopy = getNewButton("复制");
        buttonCopy.addActionListener(copyListener);
        toolBar.add(buttonCopy);
        //放置组合按钮
        JButton buttonComposite = getNewButton("组合");
        buttonComposite.addActionListener(compositeListener);
        toolBar.add(buttonComposite);
        //放置解除组合按钮
        JButton buttonUnComposite = getNewButton("解除组合");
        buttonUnComposite.addActionListener(compositeListener);
        toolBar.add(buttonUnComposite);
        //放置解除组合按钮
        JButton buttonClickClear = getNewButton("清除选中");
        buttonClickClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //点击清空：遍历列表
                List<ShapeBase> shapeBaseList = jPanelCenter.getShapeBaseList();
                //通过迭代器遍历，能够在遍历list动态删除元素
                Iterator<ShapeBase> iterator = shapeBaseList.iterator();
                while (iterator.hasNext()) {
                    ShapeBase shapeBase = iterator.next();
                    if (shapeBase.isChecked()) {
                        shapeBase.clearShape();
                        iterator.remove();
                    }
                }
                jPanelCenter.repaint();
            }
        });
        toolBar.add(buttonClickClear);
        return toolBar;
    }

    public JToolBar getDecorateJToolBar() {
        //设置菜单5
        //或者颜色枚举数组
        ColorEnum[] colorEnums = ColorEnum.values();

        //设置边框颜色
        JToolBar toolBar = getNewToolBar();
        JLabel jLabel = getNewLabel("边框颜色：");
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
                        //2.将选中对象的引用指向装饰对象
                        shapeBaseList.set(j, borderDecorator);
                    }
                }
                jPanelCenter.repaint();
            });
            toolBar.add(borderDecorate);
        }

        //填充颜色
        JLabel jLabel1 = getNewLabel("填充颜色：");
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
                        //2.将选中对象的引用指向装饰对象
                        shapeBaseList.set(j, fillDecorator);
                    }
                }
                jPanelCenter.repaint();
            });
            toolBar.add(centerFill);
        }

        //取消填充
        JButton clearFill = getNewButton("取消填充");
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