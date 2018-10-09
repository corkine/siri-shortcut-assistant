package com.mazhangjing.shortcut.dbkiller.test;

import com.mazhangjing.shortcut.dbkiller.Command;
import com.mazhangjing.shortcut.dbkiller.CommandDAO;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CommandDAOTest {

    CommandDAO dao = new CommandDAO();
    DataSource source = new ComboPooledDataSource("basic");

    @Test
    public void queryForList() throws SQLException {
        String sql = "select com_id commandId, pc_id pcId, server_id serverId ," +
                "command, result, status from command where status = -1";
        List<Command> list = dao.queryForList(source.getConnection(),sql);
        System.out.println(list);
    }

    @Test
    public void queryForCommand() throws SQLException {
        String sql = "select com_id commandId, pc_id pcId, server_id serverId ," +
                "command, result, status from command where com_id = 1";
        Command command = dao.queryForCommand(source.getConnection(),sql);
        System.out.println(command);
    }

    @Test
    public void update() throws SQLException {
        String sql = "update command set status = 1 where com_id = 1";
        dao.update(source.getConnection(),sql);
    }

    @Test
    public void updateList() throws SQLException {
        Command com = new Command();
        com.setCommandId(1);
        com.setStatus(1);
        com.setResult("success");
        dao.updateList(source.getConnection(),new ArrayList<Command>(Arrays.asList(com)));
    }
}