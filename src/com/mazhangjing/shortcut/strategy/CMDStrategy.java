package com.mazhangjing.shortcut.strategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class CMDStrategy extends Strategy {

    @Override
    public String runCommand(String command) throws IOException {
        System.out.printf("[%s] %s Running command: %s\n",new Date(),getClass().getSimpleName(),command);
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("cmd /c " + command);
        InputStreamReader reader = new InputStreamReader(process.getInputStream());
        BufferedReader breader = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder(); String message;
        while ((message = breader.readLine()) != null) sb.append(message);
        System.out.print(sb.toString());
        breader.close(); reader.close();
        return "Success";
    }
}
