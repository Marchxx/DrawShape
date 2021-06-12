package com.march.main.observe;

import java.util.ArrayList;
import java.util.List;

/**
 * 可观察基类：继承该类的目标对象可注册观察者
 */
public abstract class Observable {

    protected List<Observer> observerList = new ArrayList<>();

    //注册观察者
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    //移除观察者
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    //抽象方法，调用update方法，发送通知
    public abstract void notifyObserver();
}
