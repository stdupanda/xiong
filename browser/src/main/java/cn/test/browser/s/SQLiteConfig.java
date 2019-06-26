package cn.test.browser.s;

import java.io.File;

public class SQLiteConfig {
    public static final String DB_NAME = "browser.db";
    public static final String DB_CLASS = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:" + System.getProperty("user.home") + File.separator + DB_NAME;
}
