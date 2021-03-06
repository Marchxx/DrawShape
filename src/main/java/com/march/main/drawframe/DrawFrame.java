package com.march.main.drawframe;

import com.march.common.utils.ComponentUtil;
import com.march.main.factory.impl.StandardShapeFactory;
import com.march.main.listener.*;

import javax.swing.*;
import java.awt.*;

public class DrawFrame extends JFrame {

    //鼠标监听器的单例对象,传递给画图面板
    private MouseListener mouseListener = MouseListener.singletonMouseListener;
    //右键菜单监听的单例对象,传递给画图面板
    private RightMenuListener rightMenuListener = RightMenuListener.singletonRightMenuListener;
    //中心画图面板
    private DrawPanel jPanelCenter = null;


    public DrawFrame() {
        //修改界面风格为默认windows风格
        String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);

        //设置顶层窗体Frame属性值：标题、大小、显示位置、关闭操作、可见。
        setTitle("Java绘图板");
        setSize(1920, 1080);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //创建并设置主面板
        JPanel mainPanel = createMainPanel();
        setContentPane(mainPanel);
        setVisible(true);
        //设置画图面板，用来给监听器获取右侧面板的画笔：必须在setVisible(true)后调用
        CompositeListener.setProperties(jPanelCenter);
        CopyListener.setProperties(jPanelCenter);
        CreateListener.setProperties(jPanelCenter);
        DateAccessListener.setProperties(jPanelCenter);
        MouseListener.setProperties(jPanelCenter);
        RightMenuListener.setProperties(jPanelCenter);
    }

    /**
     * 创建主面板：
     * 最外层面板mainPanel
     * 包括三个面板 jPanelUp对应north、jPanelLeft对应WEST、jPanelCenter对应center
     */
    public JPanel createMainPanel() {

        //0.创建底层面板，使用边界布局
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));


        //1.创建顶部面板
        JPanel jPanelUp = new JPanel();
        jPanelUp.setLayout(new FlowLayout(FlowLayout.LEFT));
        // 创建选项卡并设置菜单
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("黑体", Font.PLAIN, 20));
        DrawJToolBar drawJToolBar = new DrawJToolBar();
        tabbedPane.addTab("文件", drawJToolBar.getFileJToolBar());//设置菜单1
        tabbedPane.addTab("绘制图形", drawJToolBar.getDrawJToolBar()); //设置菜单2
        tabbedPane.addTab("基本操作", drawJToolBar.getEditJToolBar()); //设置菜单3
        tabbedPane.addTab("调整样式", drawJToolBar.getDecorateJToolBar()); //设置菜单4
        jPanelUp.add(tabbedPane, FlowLayout.LEFT);
        //设置默认选中的选项卡
        tabbedPane.setSelectedIndex(1);


        //2.创建左侧面板
        JPanel jPanelLeft = new JPanel(null);//使用绝对布局
        jPanelLeft.setBorder(BorderFactory.createEtchedBorder());
        jPanelLeft.setBackground(Color.white);
        //2.1设置Jlabel，显示图元位置信息
        JLabel jLabel = ComponentUtil.getShapeMsgLabel("<html>图元：&nbsp;<br/>位置：&nbsp;</html>");
        jPanelLeft.add(jLabel);
        //2.2抽象工厂传入JLabel
        StandardShapeFactory.shapeFactory.createObserver(jLabel);
        jPanelLeft.setPreferredSize(new Dimension(200, 100));


        //3.创建右侧画图面板
        jPanelCenter = new DrawPanel();
        //设置绝对布局，在窗口显示后创建控件默认为绝对布局，需要手动指定坐标和宽高
        jPanelCenter.setLayout(null);
        jPanelCenter.setBackground(Color.white);
        jPanelCenter.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        //右侧画图面板添加判断选中监听器
        jPanelCenter.addMouseListener(mouseListener);
        jPanelCenter.addMouseMotionListener(mouseListener);
        //右侧画图面板添加 右键菜单监听器
        jPanelCenter.addMouseListener(rightMenuListener);

        //3.5菜单选项卡中传递画图面板的引用，用来操作页面
        drawJToolBar.setjPanelCenter(jPanelCenter);

        //4.将以上加入到主面板对应位置
        mainPanel.add(jPanelUp, BorderLayout.NORTH);
        mainPanel.add(jPanelLeft, BorderLayout.WEST);
        mainPanel.add(jPanelCenter, BorderLayout.CENTER);
        return mainPanel;
    }

}
