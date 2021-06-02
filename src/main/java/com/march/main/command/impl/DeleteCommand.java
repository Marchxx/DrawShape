package com.march.main.command.impl;

import com.march.main.command.Command;
import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;
import com.march.main.eneity.impl.MyButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 执行删除命令
 */
public class DeleteCommand implements Command {

    //定义Receiver
    private DrawPanel drawPanel;//画图面板的引用

    //定义State
    private List<ShapeBase> deleteShapeList = new ArrayList<>();//保存删除图形的列表


    public DeleteCommand(DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
    }

    @Override
    public void execute() {
        List<ShapeBase> shapeBaseList = drawPanel.getShapeBaseList();
        //通过迭代器遍历，能够在遍历list动态删除元素
        Iterator<ShapeBase> iterator = shapeBaseList.iterator();
        while (iterator.hasNext()) {
            ShapeBase shapeBase = iterator.next();
            if (shapeBase.isChecked()) {
                //保存删除的图形，选中设为false
                shapeBase.setChecked(false);
                deleteShapeList.add(shapeBase);
                shapeBase.clearShape();
                iterator.remove();
            }
        }
        drawPanel.repaint();
    }

    @Override
    public void unExecute() {
        List<ShapeBase> shapeBaseList = drawPanel.getShapeBaseList();
        //遍历已删除的列表，重新创建
        for (ShapeBase shapeBase : deleteShapeList) {
            if (shapeBase instanceof MyButton) {
                //此处shapeBase还保存着，但MyButton中构造出的JButton组件已经被清除
                MyButton button = (MyButton) shapeBase;
                button.createJButton(button.getName());
            }
            shapeBaseList.add(shapeBase);
        }
        drawPanel.repaint();
    }

    @Override
    public void reExecute() {
        List<ShapeBase> shapeBaseList = drawPanel.getShapeBaseList();
        for (ShapeBase shapeBase : deleteShapeList) {
            if (shapeBaseList.contains(shapeBase)) {
                shapeBase.clearShape();
                shapeBaseList.remove(shapeBase);
            }
        }
        drawPanel.repaint();
    }
}
