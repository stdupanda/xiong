package cn.xz.dbdicgen;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static ResultSet doQuery(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql).executeQuery();
    }

    public static List<String> getDbs(Connection connection) throws SQLException {
        connection.prepareStatement("use gzt").execute();
        ResultSet rs = connection.prepareStatement("show databases").executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        return list;
    }

    public static List<Map<String, String>> getDbTables(Connection connection, String dbName) throws SQLException {
        List<Map<String, String>> list = new ArrayList<>();
        // select * from information_schema.tables where table_schema='hce_develop' and
        // table_type='base table';
        String sql = "select * from tables where table_schema='" + dbName + "' and table_type='base table' ";
        ResultSet rs = doQuery(connection, sql);
        boolean ifHasSuchDb = false;
        while (rs.next()) {
            ifHasSuchDb = true;
            Map<String, String> map = new HashMap<>();
            for (EMapKey key : EMapKey.values()) {
                if (key.name().startsWith("TABLE")) {
                    map.put(key.toString(), rs.getString(key.toString()));
                }
            }
            list.add(map);
        }
        if (!ifHasSuchDb) {
            throw new SQLException("无此数据库：" + dbName + "，请检查！");
        }
        return list;
    }

    public static List<Map<String, String>> getTableColumns(Connection connection, String dbName, String tableName)
            throws SQLException {
        List<Map<String, String>> list = new ArrayList<>();
        // select * from information_schema.tables where table_schema='hce_develop' and
        // table_type='base table';
        String sql = "select * from information_schema.COLUMNS where table_name = '" + tableName
                + "' and table_schema = '" + dbName + "' ORDER BY ORDINAL_POSITION ";
        ResultSet rs = doQuery(connection, sql);
        while (rs.next()) {
            Map<String, String> map = new HashMap<>();
            for (EMapKey key : EMapKey.values()) {
                if (key.name().startsWith("COLUMN")) {
                    map.put(key.toString(), rs.getString(key.toString()));
                }
            }
            list.add(map);
        }
        return list;
    }
}
