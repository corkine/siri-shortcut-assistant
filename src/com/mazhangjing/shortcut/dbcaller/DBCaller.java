package com.mazhangjing.shortcut.dbcaller;

import com.mazhangjing.shortcut.dbkiller.CommandDAO;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBCaller {

    private final String version = "0.1.2";

    private final String productName = "Siri Shortcut SQL version " + version;

    private DataSource source;

    private CommandDAO dao = new CommandDAO();

    private Connection con = null;

    public void run(String command) {

        try (InputStream stream = new FileInputStream(System.getProperty("user.dir") + File.separator + "server.properties")) {


            Properties info = new Properties();
            info.load(stream);
            String server = info.getProperty("server_id");
            String pc = info.getProperty("pc_id");
            String sql = "insert into command (pc_id, server_id, command, status) values (?,?,?,?)";
            System.setProperty("com.mchange.v2.c3p0.cfg.xml",System.getProperty("user.dir") + File.separator + "c3p0-config.xml");
            source = new ComboPooledDataSource(info.getProperty("server_config_name"));
            con = source.getConnection();
            dao.update(con,sql,pc,server,command,0);
            System.out.println("\nDone!!");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error!!");
        } finally {
            releaseDB(null,null,con);
        }
    }

    public  void call(String[] args) throws Exception {
        System.out.println(productName + "\nYour args:");
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i]);
        }
        if (args.length != 1) { throw new RuntimeException("arg must is a command."); }
        run(args[0]);
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

    public static void main(String[] args) throws Exception {

        String cmd = "C:\\Launcher.lnk";
        //new DBCaller().call(new String[]{cmd});
        new DBCaller().call(args);
    }
}