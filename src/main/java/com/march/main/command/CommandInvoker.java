package com.march.main.command;


import java.util.ArrayList;
import java.util.List;

/**
 * 命令执行器：客户端调用，可执行execute、undo、redo操作
 */
public class CommandInvoker {

    public static final CommandInvoker singletonCommandInvoker = new CommandInvoker();

    //保存：撤销列表、重做列表
    private List<Command> undoList = new ArrayList<>();
    private List<Command> redoList = new ArrayList<>();

    private int maxCount = 50;//最大 记录撤销的命令数

    private CommandInvoker() {
    }

    //执行传入的command的execute方法
    public void execute(Command command) {
        //1.执行命令
        command.execute();
        //2.加入撤销列表
        undoList.add(command);
        if (undoList.size() > maxCount) {
            undoList.remove(0);
        }
        //3.清空重做列表
        redoList.clear();
    }

    //撤销
    public void undo() {
        if (undoList.size() <= 0) {
            return;
        }
        //1.取出撤销列表中最后一条command，执行unExecute
        Command command = undoList.get(undoList.size() - 1);
        command.unExecute();
        undoList.remove(command);
        //2.加入重做列表
        redoList.add(command);
    }

    //重做
    public void redo() {
        if (redoList.size() <= 0) {
            return;
        }
        //1.取出重做列表中最后一条command，执行reExecute
        Command command = redoList.get(redoList.size() - 1);
        command.reExecute();
        redoList.remove(command);
        //2.加入撤销列表
        undoList.add(command);
    }

}
