package com.mazhangjing.shortcut.dbkiller;

/**
 * 基本的JAVA 命令执行 POJO
 * */
public class Command {

    private int commandId;
    private int pcId;
    private int serverId;
    private String command;
    private String result;
    private int status;

    public int getCommandId() {
        return commandId;
    }

    public void setCommandId(int commandId) {
        this.commandId = commandId;
    }

    public int getPcId() {
        return pcId;
    }

    public void setPcId(int pcId) {
        this.pcId = pcId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Command(int commandId, int pcId, int serverId, String command, String result, int status) {
        this.commandId = commandId;
        this.pcId = pcId;
        this.serverId = serverId;
        this.command = command;
        this.result = result;
        this.status = status;
    }

    public Command() {
    }

    @Override
    public String toString() {
        return "Command{" +
                "commandId=" + commandId +
                ", pcId=" + pcId +
                ", serverId=" + serverId +
                ", command='" + command + '\'' +
                ", result='" + result + '\'' +
                ", status=" + status +
                '}';
    }
}
