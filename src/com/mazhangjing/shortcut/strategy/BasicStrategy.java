package com.mazhangjing.shortcut.strategy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

public class BasicStrategy extends Strategy {
    @Override
    public String runCommand(String command) throws Exception {
        System.out.printf("[%s] Running command: %s\n",new Date(),command);
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(command);
        InputStreamReader reader = new InputStreamReader(process.getInputStream());
        BufferedReader breader = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder(); String message;
        while ((message = breader.readLine()) != null) sb.append(message);
        System.out.print(sb.toString());
        breader.close(); reader.close();
        return "Success";
    }
}
