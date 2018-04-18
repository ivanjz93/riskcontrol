package org.dan.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

public class DataSourceUtil {
    private static ComboPooledDataSource dataSource = null;

    public static DataSource getDataSource() throws PropertyVetoException {
        if(dataSource == null) {
            dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://219.141.189.148:3306/riskcontroll");
            dataSource.setUser("bigdata");
            dataSource.setPassword("ctbri@smb");
        }
        return dataSource;
    }

    public static void main(String[] args) throws Exception {
        DataSource ds = DataSourceUtil.getDataSource();
        System.out.println(ds.getConnection());
    }

}
