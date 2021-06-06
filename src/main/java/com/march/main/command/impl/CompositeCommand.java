package com.march.main.command.impl;

import com.march.main.command.Command;
import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;
import com.march.main.eneity.composite.ShapeComposite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 执行组合命令
 */
public class CompositeCommand implements Command {

    //定义Receiver
    private DrawPanel drawPanel;//画图面板的引用
    private ShapeComposite shapeComposite;//组合图形

    //定义State，保存第一次选中执行组合的图形
    private List<ShapeBase> redoList = new ArrayList<>();

    public CompositeCommand(DrawPanel drawPanel, ShapeComposite shapeComposite) {
        this.drawPanel = drawPanel;
        this.shapeComposite = shapeComposite;
    }

    @Override
    public void execute() {
        List<ShapeBase> shapeBaseList = drawPanel.getShapeBaseList();
        Iterator<ShapeBase> iterator = shapeBaseList.iterator();
        while (iterator.hasNext()) {
            ShapeBase shapeBase = iterator.next();
            if (shapeBase.isChecked()) {
                //选中图形保存进列表，redo时调用
                redoList.add(shapeBase);
                //Note:使用ArrayList的remove会报并发修改异常
                shapeComposite.add(shapeBase);
                iterator.remove();
            }
        }
        shapeBaseList.add(shapeComposite);
    }

    @Override
    public void unExecute() {
        List<ShapeBase> shapeBaseList = drawPanel.getShapeBaseList();//图形列表
        List<ShapeBase> children = shapeComposite.getChildren();//组合列表
        //1.将组合包含的图形加入列表
        shapeBaseList.addAll(children);
        //2.删除组合图形的引用
        shapeBaseList.remove(shapeComposite);
    }

    @Override
    public void reExecute() {
        List<ShapeBase> shapeBaseList = drawPanel.getShapeBaseList();
        //1.遍历第一次保存的图形，在列表中删除
        for (ShapeBase shapeBase : redoList) {
            if (shapeBaseList.contains(shapeBase)) {
                shapeBaseList.remove(shapeBase);
            }
        }
        //2.将组合图形重新加入
        shapeBaseList.add(shapeComposite);
    }
}
