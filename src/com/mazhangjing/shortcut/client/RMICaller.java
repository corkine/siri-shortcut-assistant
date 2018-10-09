package com.mazhangjing.shortcut.client;

import com.mazhangjing.shortcut.service.WebService;
import sun.awt.windows.WEmbeddedFrame;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.Naming;
import java.util.Properties;

public class RMICaller {

    public static void run(String command) {

        try (InputStream stream = new FileInputStream(System.getProperty("user.dir") + File.separator + "server.properties")) {

            Properties info = new Properties();
            info.load(stream);
            String url = info.getProperty("rmi_url");
            WebService service = (WebService) Naming.lookup(url);
            String result = service.runCommand(command);
            System.out.printf("Service execute result: \n%s",result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void call(String[] args) throws Exception {
        System.out.print("Your args: \n");
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i]);
        }
        if (args.length != 1) { throw new RuntimeException("arg must is a command."); }
        run(args[0]);
    }

    public static void main(String[] args) throws Exception {

        String cmd = "C:\\Launcher.lnk";
        //run(String.format("cmd /c %s",cmd));
        call(args);
    }
}
