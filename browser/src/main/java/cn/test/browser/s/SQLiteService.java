package cn.test.browser.s;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * this is a non-thread-safe sqlite tool, please notice this before use.
 */
public class SQLiteService {
    private static Logger logger = LoggerFactory.getLogger(SQLiteService.class);
    private static SQLiteService instance = new SQLiteService();
    private Connection conn;

    public Connection getConn() throws SQLException {
        logger.debug("try to get connection.");
        if (null == conn || conn.isClosed()) {
            conn = DriverManager.getConnection(SQLiteConfig.DB_URL);
        }
        logger.debug("got connection.");
        return conn;
    }

    private SQLiteService() {
        //
    }

    public static SQLiteService getInstance() {
        return instance;
    }

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            logger.debug("db location:" + SQLiteConfig.DB_URL);
        } catch (Exception e) {
            logger.error("加载 sqlite 驱动异常！", e);
            System.exit(0);
        }
    }

    public void postConstruct() {
        logger.debug("开始db初始化");
        String sql = "CREATE TABLE IF NOT EXISTS user ( \n" +
                "    id        INTEGER         PRIMARY KEY AUTOINCREMENT,\n" +
                "    name      VARCHAR( 128 )  NOT NULL,\n" +
                "    password  VARCHAR( 128 )  NOT NULL,\n" +
                "    gmtCreate VARCHAR( 32 )   NOT NULL \n" +
                ") ";
        exec(sql);
        String sql2 = "CREATE TABLE IF NOT EXISTS record ( \n" +
                "    id        INTEGER          PRIMARY KEY AUTOINCREMENT,\n" +
                "    userId    INTEGER          NOT NULL,\n" +
                "    title     VARCHAR( 1024 )  NOT NULL,\n" +
                "    url       VARCHAR( 2048 )  NOT NULL,\n" +
                "    gmtCreate VARCHAR( 32 )    NOT NULL \n" +
                ") ";
        exec(sql2);
        logger.debug("已完成db初始化");
    }

    /**
     * 执行一个 sql 语句
     *
     * @param sql
     * @throws SQLException
     */
    public void exec(String sql) {
        Connection connection;
        PreparedStatement ps = null;
        try {
            connection = getConn();
            ps = connection.prepareStatement(sql);
            int i = ps.executeUpdate();
            logger.debug("affected rows: " + i);
        } catch (Exception e) {
            logger.error("exec error", e);
        } finally {
            if (null != ps) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    logger.error("prepareStatement close error", e);
                }
            }
        }
    }

    /**
     * 查询返回结果集
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public <T> List<T> selectList(String sql, Class<T> clazz) {
        Connection connection;
        try {
            connection = getConn();
            BeanListHandler<T> Handler = new BeanListHandler<>(clazz);
            List<T> list = new QueryRunner().query(connection, sql, Handler);
            logger.debug("result size:" + list.size());
            return list;
        } catch (Exception e) {
            logger.error("selectList error", e);
            return Collections.emptyList();
        }
    }

    /**
     * 查询返回结果集
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public <T> T selectOne(String sql, Class<T> clazz) {
        Connection connection;
        try {
            connection = getConn();
            BeanHandler<T> Handler = new BeanHandler<>(clazz);
            T t = new QueryRunner().query(connection, sql, Handler);
            logger.debug("result:" + t);
            return t;
        } catch (Exception e) {
            logger.error("selectOne error", e);
            return null;
        }
    }

    public Integer count(String sql) {
        Connection connection;
        try {
            connection = getConn();
            Integer t = (Integer) new QueryRunner().query(connection, sql, new ScalarHandler<>());
            logger.debug("result:" + t);
            return t;
        } catch (Exception e) {
            logger.error("selectOne error", e);
            return null;
        }
    }
}
