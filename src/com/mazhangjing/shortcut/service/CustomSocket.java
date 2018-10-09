package com.mazhangjing.shortcut.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;
import java.util.Properties;

public class CustomSocket extends RMISocketFactory {

    public static void main(String[] args) { }

    @Override public ServerSocket createServerSocket(int port) throws IOException {
        System.out.println("port now is " + port);
        if (port == 0) {
            InputStream stream = new FileInputStream(System.getProperty("user.dir") + File.separator + "server.properties");
            Properties properties = new Properties(); properties.load(stream);
            Integer socket = Integer.parseInt(properties.getProperty("socket"));
            port = socket;
            stream.close();
        }
        System.out.println("[end]port now is " + port);
        return new ServerSocket(port);
    }

    @Override public Socket createSocket(String host, int port) throws IOException {

        System.out.println("host:"+host+" port:"+port);
        return new Socket(host,port);
    }
}
