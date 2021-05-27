package com.march.main.listener;

import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;
import com.march.persist.service.GraphService;
import com.march.persist.service.impl.GraphServiceImpl;

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

    private GraphService graphService = new GraphServiceImpl();//注入操作图纸的Service

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
        if (inputContent.length() > 0) {
            try {
                graphService.saveGraph(inputContent, drawPanel.getShapeBaseList());
                JOptionPane.showMessageDialog(drawPanel, "保存成功！");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(drawPanel, "保存失败！" + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(drawPanel, "请正确输入！");
        }

    }

}
