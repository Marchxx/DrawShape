package com.march.listener;

import com.march.drawframe.DrawPanel;
import com.march.eneity.ShapeBase;
import com.march.eneity.ShapeComposite;
import com.march.factory.ShapeFactory;
import com.march.factory.impl.StandardShapeFactory;

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
        //1.图形工厂对象创建组合图形
        ShapeFactory shapeFactory = StandardShapeFactory.shapeFactory;
        ShapeComposite composite = (ShapeComposite) shapeFactory.createComposite("组合对象");
        //2.通过迭代器遍历，能够在遍历list动态删除元素
        Iterator<ShapeBase> iterator = shapeBaseList.iterator();
        while (iterator.hasNext()) {
            ShapeBase shapeBase = iterator.next();
            if (shapeBase.isChecked()) {
                //Note:使用ArrayList的remove会报并发修改异常
                composite.add(shapeBase);
                iterator.remove();
            }
        }
        shapeBaseList.add(composite);
    }

    //解除组合
    public void unComposite() {
        shapeBaseList = drawPanel.getShapeBaseList();
        for (int i = 0; i < shapeBaseList.size(); i++) {
            ShapeBase shapeBase = shapeBaseList.get(i);
            if (shapeBase.getComposite() != null) {
                ShapeComposite composite = shapeBase.getComposite();
                //1.判断组合对象是否被选中
                if (shapeBase.isChecked()) {
                    //2.在列表中删除组合，并将其叶子加入列表
                    shapeBaseList.remove(composite);
                    shapeBaseList.addAll(composite.getChildren());
                    break;
                }
            }
        }
    }
}
