package com.march.persist.service.impl;

import com.march.common.enums.ColorEnum;
import com.march.common.enums.ShapeEnum;
import com.march.common.utils.UuidGenerator;
import com.march.main.eneity.ShapeBase;
import com.march.main.eneity.composite.ShapeComposite;
import com.march.main.eneity.decorator.ShapeDecorator;
import com.march.main.eneity.impl.Circle;
import com.march.main.eneity.impl.Line;
import com.march.main.eneity.impl.MyButton;
import com.march.main.eneity.impl.Rectangle;
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

import java.util.ArrayList;
import java.util.List;

public class GraphServiceImpl implements GraphService {

    //注入实现数据库操作的Dao对象
    private GraphDao graphDao = new GraphDaoImpl();
    private ShapeBaseDao shapeBaseDao = new ShapeBaseDaoImpl();
    private ShapeDao shapeDao = new ShapeDaoImpl();

    @Override
    public List<GraphPo> findAllGraph() {
        return null;
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

    //递归存储组合对象
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

    //根据shapeBase以及参数构造ShapeBasePo对象
    public ShapeBasePo getShapeBasePo(ShapeBase shapeBase, ShapeEnum shapeEnum) {
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

    //根据ShapeBasePo生成的id构造ShapePo对象
    public ShapePo getShapePo(String shapeBaseId, ShapeBase shapeBase) {
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
