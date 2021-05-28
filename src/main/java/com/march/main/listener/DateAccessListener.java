package com.march.main.listener;

import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;
import com.march.persist.po.GraphPo;
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
                loadGraphList();
                break;
            case "保存":
                saveGraph();
                break;
        }
    }

    /**
     * 加载图纸列表
     */
    private void loadGraphList() {
        try {
            //1.查询图纸列表
            List<GraphPo> graphPoList = graphService.findAllGraph();
            if (graphPoList.size() == 0) {
                JOptionPane.showMessageDialog(drawPanel, "系统没有可以打开的图纸，请先保存！");
                return;
            }
            Object[] selectionValues = graphPoList.toArray();
            Object inputContent = JOptionPane.showInputDialog(
                    null,
                    "请选择要打开的图纸: ",
                    "图纸列表",
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    selectionValues,
                    selectionValues[0]
            );
            //2.截取出图纸名称
            if (inputContent == null) return;
            String str = inputContent.toString();
            String substringTemp = str.substring(0, str.lastIndexOf(","));
            String graphName = substringTemp.substring(5, substringTemp.lastIndexOf(","));
            GraphPo graphPo = graphService.findOneByName(graphName);
            //3.打开图纸
            openGraph(graphPo.getId());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(drawPanel, "打开图纸列表失败！" + e.getMessage());
        }
    }

    /**
     * 打开图纸
     *
     * @param graphId
     */
    private void openGraph(String graphId) {
        try {
            //清空画板
            List<ShapeBase> shapeBaseList = drawPanel.getShapeBaseList();
            for (ShapeBase shapeBase : shapeBaseList) {
                shapeBase.clearShape();
            }
            shapeBaseList.clear();
            //调用Service的方法返回图纸列表，设置给drawPanel并重绘界面
            graphService.setDrawPanel(drawPanel);//设置面板，创建自定义按钮使用
            drawPanel.setShapeBaseList(graphService.openGraph(graphId));
            drawPanel.repaint();
            JOptionPane.showMessageDialog(drawPanel, "打开图纸成功！");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(drawPanel, "打开图纸失败！" + e.getMessage());
        }
    }

    /**
     * 保存图纸
     */
    private void saveGraph() {
        // 1. 显示输入对话框, 返回输入的内容
        String inputContent = JOptionPane.showInputDialog(
                null, "输入要保存的图纸名称:", "图纸保存", JOptionPane.INFORMATION_MESSAGE
        );
        if (inputContent.length() == 0) {
            JOptionPane.showMessageDialog(drawPanel, "请正确输入图纸名称！");
            return;
        }
        GraphPo one = graphService.findOneByName(inputContent);
        if (one != null) {
            JOptionPane.showMessageDialog(drawPanel, "图纸名称已存在！");
            return;
        }
        try {
            graphService.saveGraph(inputContent, drawPanel.getShapeBaseList());
            JOptionPane.showMessageDialog(drawPanel, "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(drawPanel, "保存失败！" + e.getMessage());
        }
    }

}
