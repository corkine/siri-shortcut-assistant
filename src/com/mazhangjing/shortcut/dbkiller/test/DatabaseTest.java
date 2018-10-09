package com.mazhangjing.shortcut.dbkiller.test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DatabaseTest {

    @Test
    public void testLink() throws SQLException {

        DataSource source = new ComboPooledDataSource("basic");
        System.out.println(source.getConnection());
    }
}
