package com.march.main.command;

/**
 * 命令模式接口
 */
public interface Command {

    void execute();

    void unExecute();

    void reExecute();

}
