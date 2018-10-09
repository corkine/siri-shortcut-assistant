package com.mazhangjing.shortcut.dbkiller;

import com.mazhangjing.shortcut.strategy.Strategy;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Killer {

    private final long servalID = 9634L;

    private final String version = "0.1.2";

    private final String productName = "Siri Shortcut SQL version " + version;

    {
        System.setProperty("com.mchange.v2.c3p0.cfg.xml",System.getProperty("user.dir") + File.separator + "c3p0-config.xml");
    }

    private DataSource source;

    private Strategy strategy;

    private CommandDAO dao = new CommandDAO();

    private Connection con = null;

    private final String sql =  "select com_id commandId, pc_id pcId, server_id serverId ," +
            "command, result, status from command where status = 0 and pc_id = ?";

    private List<Command> commands = new ArrayList<>();

    private int pcId;

    private void doService()   {


        try {
            con = source.getConnection();
            if (!(commands = dao.queryForList(con,sql,pcId)).isEmpty()) {
                //System.out.println("getCommand ");
                new Execute(strategy).runCommands(commands);
            }
            dao.updateList(con,commands);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            commands.clear();
            releaseDB(null,null,con);
        }


    }

    public void startService() {

        System.out.println("Init server properties...");
        readProperties();

        System.out.println("Service start. Server information:");
        System.out.println("----------------------------------------");
        System.out.printf("SERVER: %s\nSERVER ID: %s\nSTRATEGY: %s\n",productName,servalID,strategy.getClass().getName());
        System.out.println("----------------------------------------");

        while (true) {
            try {
                //System.out.println("doService Now...");
                doService();
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void releaseDB(ResultSet set, Statement statement, Connection connection) {
        if (set != null) {
            try {
                set.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void readProperties() {
        Strategy stratgy = null;
        InputStream stream = null;
        String stratgyClass = null;
        try {
            stream = new FileInputStream(System.getProperty("user.dir") + File.separator + "server.properties");
            Properties properties = new Properties(); properties.load(stream);
            stratgyClass = properties.getProperty("strategy");
            this.strategy = (Strategy) Class.forName(stratgyClass).newInstance();
            this.pcId = Integer.parseInt(properties.getProperty("pc_id"));
            this.source = new ComboPooledDataSource(properties.getProperty("pc_config_name"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Killer().startService();
    }
}
