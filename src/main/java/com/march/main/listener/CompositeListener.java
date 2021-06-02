package com.march.main.listener;

import com.march.main.command.CommandInvoker;
import com.march.main.command.impl.CompositeCommand;
import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;
import com.march.main.eneity.composite.ShapeComposite;
import com.march.main.eneity.decorator.ShapeDecorator;
import com.march.main.factory.ShapeFactory;
import com.march.main.factory.impl.StandardShapeFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

/**
 * 监听器：响应组合与解除组合按钮
 */
public class CompositeListener implements ActionListener {

    private DrawPanel drawPanel;//画图面板的引用，用来获取shapeBaseList和计算宽高
    private List<ShapeBase> shapeBaseList = null; //通过drawPanel获取


    public static final CompositeListener singletonCompositeListener = new CompositeListener();

    private CompositeListener() {
    }

    public static void setProperties(DrawPanel drawPanel) {
        singletonCompositeListener.drawPanel = drawPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "组合":
                composite();
                break;
            case "解除组合":
                unComposite();
                break;
        }
    }

    //组合
    public void composite() {
        shapeBaseList = drawPanel.getShapeBaseList();
        //1.图形工厂对象创建组合图形，获取组合对象
        ShapeFactory shapeFactory = StandardShapeFactory.shapeFactory;
        ShapeBase shapeComposite = shapeFactory.createComposite("组合对象");
        ShapeComposite composite = shapeComposite.getComposite();
        //2.调用执行组合命令
        CompositeCommand compositeCommand = new CompositeCommand(drawPanel, composite);
        CommandInvoker.singletonCommandInvoker.execute(compositeCommand);
    }

    //解除组合
    public void unComposite() {
        shapeBaseList = drawPanel.getShapeBaseList();
        for (int i = 0; i < shapeBaseList.size(); i++) {
            ShapeBase shapeBase = shapeBaseList.get(i);
            if (shapeBase.isChecked()) {
                boolean flag = false;//标记是否为装饰对象
                ShapeBase oldDecorator = shapeBase;//保存列表中原装饰对象的引用
                //1.判断是否为装饰对象，取出装饰对象的最外层target
                if (shapeBase.getDecorator() != null) {
                    flag = true;
                }
                while (shapeBase.getDecorator() != null) {
                    ShapeDecorator shapeDecorator = shapeBase.getDecorator();
                    shapeBase = shapeDecorator.getTarget();
                }
                //2.判断是否为组合对象，解除组合
                if (shapeBase.getComposite() != null) {
                    ShapeComposite composite = shapeBase.getComposite();
                    if (flag)//若为装饰后的组合对象，删除装饰的引用
                        shapeBaseList.remove(oldDecorator);
                    else
                        shapeBaseList.remove(composite);
                    shapeBaseList.addAll(composite.getChildren());
                    break;
                }
            }
        }
    }
}
