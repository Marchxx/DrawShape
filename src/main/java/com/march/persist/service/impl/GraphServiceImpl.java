package com.march.persist.service.impl;

import com.march.common.enums.ColorEnum;
import com.march.common.enums.ShapeEnum;
import com.march.common.utils.UuidGenerator;
import com.march.main.drawframe.DrawPanel;
import com.march.main.eneity.ShapeBase;
import com.march.main.eneity.composite.ShapeComposite;
import com.march.main.eneity.decorator.ShapeDecorator;
import com.march.main.eneity.impl.Circle;
import com.march.main.eneity.impl.Line;
import com.march.main.eneity.impl.MyButton;
import com.march.main.eneity.impl.Rectangle;
import com.march.main.factory.ShapeFactory;
import com.march.main.factory.impl.StandardShapeFactory;
import com.march.persist.dao.GraphDao;
import com.march.persist.dao.ShapeBaseDao;
import com.march.persist.dao.ShapeDao;
import com.march.persist.dao.impl.GraphDaoImpl;
import com.march.persist.dao.impl.ShapeBaseDaoImpl;
import com.march.persist.dao.impl.ShapeDaoImpl;
import com.march.persist.po.GraphPo;
import com.march.persist.po.ShapeBasePo;
import com.march.persist.po.ShapePo;
import com.march.persist.service.GraphService;
import com.march.persist.vo.ShapeInfoVo;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GraphServiceImpl implements GraphService {

    //注入实现数据库操作的Dao对象
    private GraphDao graphDao = new GraphDaoImpl();
    private ShapeBaseDao shapeBaseDao = new ShapeBaseDaoImpl();
    private ShapeDao shapeDao = new ShapeDaoImpl();

    private DrawPanel drawPanel;//监听器类中设置画图面板的引用，方便创建MyButton按钮

    public void setDrawPanel(DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
    }

    @Override
    public GraphPo findOneByName(String name) {
        return graphDao.findOneByName(name);
    }

    @Override
    public List<GraphPo> findAllGraph() {
        return graphDao.findAllGraph();
    }


    //根据图纸名称打开图纸，返回图形列表
    @Override
    public List<ShapeBase> openGraph(String graphId) {
        List<ShapeBase> shapeBaseList = new ArrayList<>();
        HashMap<String, ShapeInfoVo> shapeVoMap = new HashMap<>();//存放组合节点的数据库vo字段
        HashMap<String, ShapeBase> shapeMap = new HashMap<>();//存放实际的对象
        //1.查询全部图形信息
        List<ShapeInfoVo> infoVoList = shapeDao.findAllShapeInfo(graphId);

        //2.遍历查询结果集，将组合节点扫描进shapeVoMap
        for (ShapeInfoVo shapeInfoVo : infoVoList) {
            if (shapeInfoVo.getShapetype() == 4) {
                shapeVoMap.put(shapeInfoVo.getId(), shapeInfoVo);
            }
        }

        //3.再次遍历扫描 处理非组合节点
        for (ShapeInfoVo shapeInfoVo : infoVoList) {
            //3.1 无父节点、不为组合的图形，单独创建对象并保存
            if (shapeInfoVo.getShapetype() != 4 && shapeInfoVo.getParentid() == null) {
                ShapeBase shapeBase = createShape(shapeInfoVo);
                shapeBaseList.add(shapeBase);
            } else if (shapeInfoVo.getShapetype() != 4 && shapeInfoVo.getParentid() != null) {
                //3.2 有父节点、不为组合的图形 创建该图形、父节点并添加组合 一起加入shapeMap
                ShapeBase shapeBase = createShape(shapeInfoVo);
                ShapeInfoVo shapeInfoParent = shapeVoMap.get(shapeInfoVo.getParentid());
                ShapeBase shapeParent = shapeMap.get(shapeInfoParent.getId());
                if (shapeParent == null) {
                    shapeParent = createShape(shapeInfoParent);
                }
                //3.2.1 将创建的子图形，加入到Parent中
                ShapeComposite shapeComposite = shapeParent.getComposite();
                shapeComposite.add(shapeBase);
                //3.2.2 加入到shapeMap中
                shapeMap.put(shapeInfoParent.getId(), shapeComposite);
            }
        }

        //4.依次遍历ShapeVoMap，处理纯组合节点
        Set<String> keySet = shapeVoMap.keySet();
        Map<Integer, List<ShapeInfoVo>> listMap = new TreeMap<>();
        for (String s : keySet) {
            ShapeInfoVo shapeInfoVo = shapeVoMap.get(s);
            //按照组合深度进行排序，将组合的叶子节点放在最前面
            Integer depth = calcDepth(shapeVoMap, shapeInfoVo);
            List<ShapeInfoVo> list = listMap.get(depth);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(shapeInfoVo);
            listMap.put(depth * -1, list);//深度的负数，从treeMap中正序遍历则从小到大
        }

        //5.依次遍历第三步处理好的Map，同时修改shapeMap的结构
        for (Integer integer : listMap.keySet()) {
            //只处理不为0的节点
            if (integer != 0) {
                List<ShapeInfoVo> shapeInfoVos = listMap.get(integer);
                for (ShapeInfoVo shapeInfoVo : shapeInfoVos) {
                    //取出shapeMap中的父节点，将该结点加入其中并删除本身，重新put回map
                    ShapeBase shapeBase = shapeMap.get(shapeInfoVo.getId());
                    ShapeBase shapeParent = shapeMap.get(shapeInfoVo.getParentid());
                    ShapeComposite composite = shapeParent.getComposite();
                    composite.add(shapeBase);
                    shapeMap.remove(shapeInfoVo.getId());
                    shapeMap.put(shapeInfoVo.getParentid(), composite);
                }
            }
        }
        //6. 将shapeMap中的图形，加入shapeList
        shapeBaseList.addAll(shapeMap.values());
        return shapeBaseList;
    }

    //计算当前组合图形的深度
    private Integer calcDepth(HashMap<String, ShapeInfoVo> shapeVoMap, ShapeInfoVo shapeInfoVo) {
        int count = 0;
        while (shapeInfoVo.getParentid() != null) {
            count++;
            shapeInfoVo = shapeVoMap.get(shapeInfoVo.getParentid());
        }
        return count;
    }

    //私有方法：恢复图形
    private ShapeBase createShape(ShapeInfoVo shapeInfoVo) {
        //1.获取创建恢复图形的工厂
        ShapeFactory shapeFactory = StandardShapeFactory.shapeFactory;
        ShapeBase result = null;
        //2.根据图形typeId创建图形
        String name = shapeInfoVo.getName();
        int startX = shapeInfoVo.getStartX();
        int startY = shapeInfoVo.getStartY();
        boolean checked = shapeInfoVo.isChecked();
        boolean filled = shapeInfoVo.isFilled();
        Color lineColor = ColorEnum.getColorByCode(shapeInfoVo.getLineColor());
        Color fillColor = ColorEnum.getColorByCode(shapeInfoVo.getFillColor());
        switch (shapeInfoVo.getShapetype()) {
            case 0:
                result = shapeFactory.createRecoverCircle(name, startX, startY, checked, filled, lineColor, fillColor, shapeInfoVo.getRadius());
                break;
            case 1:
                result = shapeFactory.createRecoverLine(name, startX, startY, checked, filled, lineColor, fillColor, shapeInfoVo.getEndx(), shapeInfoVo.getEndy());
                break;
            case 2:
                result = shapeFactory.createRecoverButton(name, startX, startY, checked, filled, lineColor, fillColor, drawPanel);
                break;
            case 3:
                result = shapeFactory.createRecoverRectangle(name, startX, startY, checked, filled, lineColor, fillColor, shapeInfoVo.getWidth(), shapeInfoVo.getHeight());
                break;
            case 4:
                result = shapeFactory.createComposite(name);
                break;
        }
        return result;
    }


    //根据图形列表，保存当前图纸对象
    @Override
    public void saveGraph(String graphName, List<ShapeBase> shapeBaseList) {
        //1.创建图纸对象，生成32位的Uuid作为主键，进行保存
        GraphPo graphPo = new GraphPo();
        graphPo.setId(UuidGenerator.getUuid());
        graphPo.setName(graphName);
        graphPo.setCreateTime(null);
        graphPo.setUpdateTime(null);
        graphDao.saveGraph(graphPo);
        //2.遍历图形列表，构造存储列表用于批量插入数据库
        List<ShapeBasePo> shapeBasePoList = new ArrayList<>();
        List<ShapePo> shapePoList = new ArrayList<>();
        for (ShapeBase shapeBase : shapeBaseList) {

            //2.0 将装饰全部解除，统一处理组合和普通对象
            while (shapeBase.getDecorator() != null) {
                ShapeDecorator shapeDecorator = shapeBase.getDecorator();
                shapeBase = shapeDecorator.getTarget();
            }
            //2.1 判断非组合对象，分别构造ShapeBasePo与ShapePo对象
            if (shapeBase.getComposite() == null) {
                ShapeBasePo shapeBasePo = getShapeBasePo(shapeBase, null);
                shapeBasePoList.add(shapeBasePo);//加入图形基类持久化对象列表
                //根据ShapeBase生成的id，构造ShapePo对象
                ShapePo shapePo = getShapePo(shapeBasePo.getId(), shapeBase);
                shapePoList.add(shapePo);//加入图形实现类持久化对象列表
            }
            //2.2 判断组合对象
            else {
                //递归调用，将组合对象以及包含的子对象加入列表
                saveComposite(shapeBasePoList, shapePoList, shapeBase.getComposite(), null);
            }
        }
        //3.分别将List中的数据批量插入
        for (ShapeBasePo shapeBasePo : shapeBasePoList) {
            shapeBaseDao.saveShapeBase(shapeBasePo);
        }
        for (ShapePo shapePo : shapePoList) {
            //统一设置图纸id
            shapePo.setGraphid(graphPo.getId());
            shapeDao.saveShape(shapePo);
        }
    }

    //私有方法：递归存储组合对象
    private void saveComposite(List<ShapeBasePo> shapeBasePoList, List<ShapePo> shapePoList, ShapeComposite composite, String parentId) {
        //1.创建Composite对象，并将创建出的id递归传递
        ShapeBasePo shapeBaseParentPo = getShapeBasePo(composite, ShapeEnum.COMPOSITE);
        ShapePo shapeParentPo = getShapePo(shapeBaseParentPo.getId(), composite);
        shapeParentPo.setParentid(parentId);
        shapeBasePoList.add(shapeBaseParentPo);
        shapePoList.add(shapeParentPo);
        //2.遍历组合对象的children
        List<ShapeBase> children = composite.getChildren();
        for (ShapeBase child : children) {

            //2.0 将装饰全部解除，统一处理组合和普通对象
            while (child.getDecorator() != null) {
                ShapeDecorator shapeDecorator = child.getDecorator();
                child = shapeDecorator.getTarget();
            }
            if (child.getComposite() != null) {
                saveComposite(shapeBasePoList, shapePoList, child.getComposite(), shapeParentPo.getId());
            } else {
                //3.创建普通子节点
                ShapeBasePo shapeBasePo = getShapeBasePo(child, null);
                ShapePo shapePo = getShapePo(shapeBasePo.getId(), child);
                shapePo.setParentid(shapeParentPo.getId());//设置子节点id指向父节点
                shapeBasePoList.add(shapeBasePo);
                shapePoList.add(shapePo);
            }
        }
    }

    //私有方法：根据shapeBase以及参数构造ShapeBasePo对象
    private ShapeBasePo getShapeBasePo(ShapeBase shapeBase, ShapeEnum shapeEnum) {
        ShapeBasePo shapeBasePo = new ShapeBasePo();
        shapeBasePo.setId(UuidGenerator.getUuid());
        shapeBasePo.setName(shapeBase.getName());
        if (shapeEnum == null) {
            //非组合以及装饰图形，则设置起点x、y坐标
            shapeBasePo.setStartX(shapeBase.getStartX());
            shapeBasePo.setStartY(shapeBase.getStartY());
        }
        shapeBasePo.setChecked(shapeBase.isChecked());
        shapeBasePo.setFilled(shapeBase.isFilled());
        shapeBasePo.setLineColor(ColorEnum.getCodeByColor(shapeBase.getLineColor()));//根据枚举类进行映射
        shapeBasePo.setFillColor(ColorEnum.getCodeByColor(shapeBase.getFillColor()));//根据枚举类进行映射
        return shapeBasePo;
    }

    //私有方法：根据ShapeBasePo生成的id构造ShapePo对象
    private ShapePo getShapePo(String shapeBaseId, ShapeBase shapeBase) {
        ShapePo shapePo = new ShapePo();
        shapePo.setId(shapeBaseId);
        //shapePo.setParentid();//无父节点，无需设置
        //判断图形类型，分别赋值
        ShapeEnum shapeEnum = null;
        if (shapeBase instanceof Circle) {
            shapeEnum = ShapeEnum.CIRCLE;
            Circle shapeImpl = (Circle) shapeBase;
            shapePo.setRadius(shapeImpl.getRadius());
        }
        if (shapeBase instanceof Line) {
            shapeEnum = ShapeEnum.LINE;
            Line shapeImpl = (Line) shapeBase;
            shapePo.setEndx(shapeImpl.getEndX());
            shapePo.setEndy(shapeImpl.getEndY());
        }
        if (shapeBase instanceof Rectangle) {
            shapeEnum = ShapeEnum.RECTANGLE;
            Rectangle shapeImpl = (Rectangle) shapeBase;
            shapePo.setWidth(shapeImpl.getWidth());
            shapePo.setHeight(shapeImpl.getHeight());
        }
        if (shapeBase instanceof MyButton) {
            shapeEnum = ShapeEnum.MYBUTTON;
        }
        if (shapeBase instanceof ShapeComposite) {
            shapeEnum = ShapeEnum.COMPOSITE;
        }
        shapePo.setShapetype(shapeEnum.getCode());//设置图形枚举对应数字
        return shapePo;
    }
}
