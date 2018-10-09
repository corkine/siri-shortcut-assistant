package com.mazhangjing.shortcut.dbkiller;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CommandDAO {

    private QueryRunner runner;

    {
        runner = new QueryRunner();
    }

    public List<Command> queryForList(Connection con, String sql,Object ...args) {

        try {
            return runner.query(con,sql,new BeanListHandler<>(Command.class),args);
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;

    }

    public Command queryForCommand(Connection con, String sql, Object ... args) {

        try {
            return runner.query(con,sql,new BeanHandler<>(Command.class),args);
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    public void update(Connection con, String sql, Object...args) {
        try {
            runner.update(con,sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateList(Connection con, List<Command> list) {
        String sql = "update command set result = ?, status = ? where com_id = ?";
        for (Command command : list) {
            update(con,sql,command.getResult(),command.getStatus(),command.getCommandId());
        }
    }
}
