package com.march.listener;

import com.march.drawframe.DrawPanel;
import com.march.eneity.ShapeBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 监听器：响应文件打开、保存按钮
 */
public class DateAccessListener implements ActionListener {

    private DrawPanel drawPanel;//画图面板的引用，用来获取shapeBaseList和计算宽高
    private List<ShapeBase> shapeBaseList = null; //通过drawPanel获取


    public static final DateAccessListener singletonDateAccessListener = new DateAccessListener();

    private DateAccessListener() {
    }

    public static void setProperties(DrawPanel drawPanel) {
        singletonDateAccessListener.drawPanel = drawPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "打开":
                //showInputDialog("请输入:", JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, "Test", 500, 300);
                //showCustomDialog(jFrame);
                break;
            case "保存":
                saveGraph();
                break;
        }
    }


    /**
     * 保存图纸
     */
    public void saveGraph() {
        // 显示输入对话框, 返回输入的内容
        String inputContent = JOptionPane.showInputDialog(
                null, "输入要保存的文件名称:", "文件保存", JOptionPane.INFORMATION_MESSAGE
        );
        if (inputContent != null && inputContent.length() > 0) {
            System.out.println("执行保存..." + inputContent);
        }
    }

}
