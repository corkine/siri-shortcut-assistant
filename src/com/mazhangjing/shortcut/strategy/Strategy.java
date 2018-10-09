package com.mazhangjing.shortcut.strategy;

public abstract class Strategy {
    public Strategy() { System.out.printf("Calling strategy %s....\n",getClass().getSimpleName()); }
    public abstract String runCommand(String command) throws Exception;
}
