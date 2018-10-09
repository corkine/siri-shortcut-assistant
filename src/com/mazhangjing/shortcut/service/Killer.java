package com.mazhangjing.shortcut.service;
import com.mazhangjing.shortcut.strategy.Strategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Properties;

public class Killer extends UnicastRemoteObject implements WebService {

    public Strategy strategy;

    private static final long servialVersionUID = 9633L;

    public static String info = "Siri Shortcut Server.";

    public Killer() throws RemoteException {}

    @Override
    public String runCommand(String command) {
        try {
            System.out.printf("[%s] Exec command: %s\n",new Date(),command);
            String result = strategy.runCommand(command);
            return String.format("[1] Execute successful\n%s",result);
        } catch (Exception e) {
            e.printStackTrace();
            return String.format("[0] Execute failed\n%s",e);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Init server properties...");

        Killer killer = new Killer();

        //InputStream stream = killer.getClass().getClassLoader().getResourceAsStream("server.properties");
        InputStream stream = new FileInputStream(System.getProperty("user.dir") + File.separator + "server.properties");
        Properties properties = new Properties(); properties.load(stream);
        String rmi = properties.getProperty("rmi_url");
        Integer port = Integer.parseInt(properties.getProperty("port"));
        Integer socket = Integer.parseInt(properties.getProperty("socket"));

        Strategy strategy = (Strategy) Class.forName(properties.getProperty("strategy")).newInstance();
        killer.strategy = strategy;
        stream.close();

        System.out.println("Service start. Server information:");
        System.out.println("----------------------------------------");
        System.out.printf("SERVER: %s\nSERVER ID: %s\nRMI: %s\nPORT: %s\nSOCKET: %s\nSTRATEGY: %s\n",info,servialVersionUID,rmi,port,socket,strategy.getClass().getName());
        System.out.println("----------------------------------------");


        RMISocketFactory.setSocketFactory(new CustomSocket());
        LocateRegistry.createRegistry(port);
        Naming.bind(rmi,killer);

        System.out.println("Waiting for invocations from clients....");
    }
}
