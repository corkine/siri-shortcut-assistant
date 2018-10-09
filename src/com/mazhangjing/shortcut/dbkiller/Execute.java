package com.mazhangjing.shortcut.dbkiller;

import com.mazhangjing.shortcut.strategy.Strategy;
import com.mazhangjing.shortcut.service.WebService;

import java.util.List;

public class Execute implements WebService {

    private Strategy stratgy;

    public Execute(Strategy stratgy) {
        this.stratgy = stratgy;
    }

    @Override
    public String runCommand(String command){
        try {
            return stratgy.runCommand(command);
        } catch (Exception e) {
            e.printStackTrace();
        } return null;
    }

    public Command runCommand(Command command){
        String result = null;
        try {
            result = stratgy.runCommand(command.getCommand());
            command.setResult(result);
            command.setStatus(1);
        } catch (Exception e) {
            e.printStackTrace();
            command.setResult(e.getMessage());
            command.setStatus(-1);
        }
        return command;
    }

    public List<Command> runCommands(List<Command> commands) {
        for (Command command : commands) {
            runCommand(command);
        }
        return commands;
    }
}
