package com.march.drawframe;

import com.march.listener.CopyListener;
import com.march.listener.CreateListener;
import com.march.listener.MoveListener;
import com.march.listener.SelectListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class DrawFrame extends JFrame {

    //创建监听器的单例对象
    private CreateListener createListener = CreateListener.singletonCreateListener;
    private MoveListener moveListener = MoveListener.singletonMoveListener;
    private CopyListener copyListener = CopyListener.singletonCopyListener;
    private SelectListener selectListener = SelectListener.singletonSelectListener;
    //中心画图面板
    private DrawPanel jPanelCenter = null;


    public DrawFrame() {
        //设置顶层窗体Frame属性值：标题、大小、显示位置、关闭操作、可见。
        setTitle("Java绘图板");
        setSize(1920, 1080);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //创建并设置主面板
        JPanel mainPanel = createMainPanel();
        setContentPane(mainPanel);
        setVisible(true);

        // 获取右侧面板的画笔：必须在setVisible(true)后调用
        Graphics g = jPanelCenter.getGraphics();
        //设置 画图面板和画笔
        CreateListener.setProperties(jPanelCenter, (Graphics2D) g);
        CopyListener.setProperties(jPanelCenter, (Graphics2D) g);
        MoveListener.setProperties(jPanelCenter, (Graphics2D) g);
        SelectListener.setProperties(jPanelCenter, (Graphics2D) g);
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
        //放置菜单棒
        jPanelUp.add(new DrawMenuBar());
        //放置标签
        JLabel jLabel = new JLabel("请选择要创建的图形：");
        jLabel.setFont(new Font("黑体", Font.PLAIN, 20));
        jPanelUp.add(jLabel);
        //放置按钮
        String[] typeArray = {"线段", "矩形", "圆", "按钮"};
        for (int i = 0; i < typeArray.length; i++) {
            JButton button = new JButton(typeArray[i]);
            button.setFont(new Font("黑体", Font.PLAIN, 20));
            button.setPreferredSize(new Dimension(100, 30));
            //按钮添加创建监听器
            button.addActionListener(createListener);
            jPanelUp.add(button);
        }
        //放置标签
        JLabel jLabe2 = new JLabel("请选择：");
        jLabe2.setFont(new Font("黑体", Font.PLAIN, 20));
        jPanelUp.add(jLabe2);
        //放置按钮
        String[] typeArray1 = {"左移", "右移", "上移", "下移"};
        for (int i = 0; i < typeArray1.length; i++) {
            JButton button = new JButton(typeArray1[i]);
            button.setFont(new Font("黑体", Font.PLAIN, 20));
            button.setPreferredSize(new Dimension(100, 30));
            //按钮添加移动监听器
            button.addActionListener(moveListener);
            jPanelUp.add(button);
        }
        //放置复制按钮
        JButton button = new JButton("复制");
        button.setFont(new Font("黑体", Font.PLAIN, 20));
        button.setPreferredSize(new Dimension(100, 30));
        //按钮添加拷贝监听器
        button.addActionListener(copyListener);
        jPanelUp.add(button);

        //2.创建左侧面板
        JPanel jPanelLeft = new JPanel();
        jPanelLeft.setBorder(BorderFactory.createEtchedBorder());
        jPanelLeft.setBackground(Color.white);
        jPanelLeft.setPreferredSize(new Dimension(200, 100));

        //3.创建右侧画图面板
        jPanelCenter = new DrawPanel();
        //设置绝对布局，在窗口显示后创建控件默认为绝对布局，需要手动指定坐标和宽高
        jPanelCenter.setLayout(null);
        jPanelCenter.setBackground(Color.white);
        jPanelCenter.setPreferredSize(new Dimension(200, 100));
        jPanelCenter.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        //右侧画图面板添加判断选中监听器
        jPanelCenter.addMouseListener(selectListener);

        //4.将以上加入到主面板对应位置
        mainPanel.add(jPanelUp, BorderLayout.NORTH);
        mainPanel.add(jPanelLeft, BorderLayout.WEST);
        mainPanel.add(jPanelCenter, BorderLayout.CENTER);
        return mainPanel;
    }

    /**
     * 主函数运行
     *
     * @param args
     */
    public static void main(String[] args) {
        new DrawFrame();
    }

}
