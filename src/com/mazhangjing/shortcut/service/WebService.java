package com.mazhangjing.shortcut.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WebService extends Remote {
    String runCommand(String command) throws Exception;
}
