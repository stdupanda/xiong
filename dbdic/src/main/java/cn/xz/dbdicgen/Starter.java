package cn.xz.dbdicgen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Starter {
    public static void main(String[] args) throws SQLException, IOException {
        if (4 > args.length) {
            System.out.println("输入格式: <ip> <port> <rootPassword> <databaseName>");
            return;
        }
        // 读取命令行参数
        String ip = args[0];
        String port = args[1];
        String rootPassword = args[2];
        String databaseName = args[3];
        System.out.println("ip:           " + ip);
        System.out.println("port:         " + port);
        System.out.println("rootPassword: " + rootPassword);
        System.out.println("databaseName: " + databaseName);

        // String ip = "192.168.1.233";
        // String port = "3306";
        // String rootPassword = "123456";
        // String databaseName = "hce_develop";

        // 生成 db conn
        System.out.println("尝试连接数据库.");
        String url = "jdbc:mysql://" + ip + ":" + port + "/information_schema?useUnicode=true&characterEncoding=utf-8";

        Connection connection = DriverManager.getConnection(url, "root", rootPassword);
        System.out.println("连接数据库成功.");

        StringBuffer sb = new StringBuffer("");
        sb.append(genHead(databaseName));

        List<Map<String, String>> dbTables = Utils.getDbTables(connection, databaseName);
        for (Map<String, String> map : dbTables) {
            sb.append(genTable(connection, databaseName, map));
            System.out.println("处理 " + map.get(EMapKey.TABLE_NAME.toString()) + " 已完成.");
        }
        sb.append(genTail());
        String html = sb.toString();
        String userDir = System.getProperty("user.dir");
        writeFile(userDir + File.separator + "dic_" + databaseName + ".html", html);
    }

    static void printRS(ResultSet rs) throws SQLException {
        while (rs.next()) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String str = "";
            for (int i = 0; i < columnCount; i++) {
                str += rs.getString(i + 1) + ",";
            }
            System.out.println(str);
        }
    }

    static String genHead(String dbName) {
        String title = dbName + " 数据字典";
        return "<!doctype html><html><head><meta charset=\"UTF-8\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><title>"
                + title
                + "</title><link href=\"http://cdn.bootcss.com/twitter-bootstrap/3.0.3/css/bootstrap.min.css\" rel=\"stylesheet\"></head><body><div class=\"container\"><h1 style=\"text-align:center;\">"
                + title + "</h1>";
    }

    static String genTable(Connection connection, String dbName, Map<String, String> tableMap) throws SQLException {
        String tableName = tableMap.get(EMapKey.TABLE_NAME.toString());
        String tableEngine = tableMap.get(EMapKey.TABLE_ENGINE.toString());
        String tableComment = tableMap.get(EMapKey.TABLE_COMMENT.toString());

        StringBuffer sb = new StringBuffer("");
        String tableDesc = tableName + " (";
        if (!"".equals(tableComment) && !"".equals(tableEngine)) {
            tableDesc += tableComment + ",";
        }
        tableDesc += tableEngine + ")";
        sb.append("<h3>").append(tableDesc).append("</h3>");
        sb.append("<table class=\"table table-hover table-bordered table-condensed\">");
        sb.append("<thead>");
        sb.append("<tr><th>字段名</th><th>数据类型</th><th>键</th><th>默认值</th><th>是否允许空</th><th>备注</th></tr>");
        sb.append("</thead>");
        sb.append("<tbody>");
        List<Map<String, String>> columns = Utils.getTableColumns(connection, dbName, tableName);
        for (Map<String, String> map : columns) {
            sb.append("<tr>");
            sb.append("<td>" + map.get(EMapKey.COLUMN_NAME.toString()) + "</td>");
            sb.append("<td>" + map.get(EMapKey.COLUMN_TYPE.toString()) + "</td>");
            sb.append("<td>" + map.get(EMapKey.COLUMN_KEY.toString()) + "</td>");
            sb.append("<td>" + map.get(EMapKey.COLUMN_DEFAULT.toString()) + "</td>");
            sb.append("<td>" + map.get(EMapKey.COLUMN_IS_NULLABLE.toString()) + "</td>");
            sb.append("<td>" + map.get(EMapKey.COLUMN_COMMENT.toString()) + "</td>");
            sb.append("</tr>");
        }
        sb.append("</tbody>");
        sb.append("</table>");

        return sb.toString();
    }

    static String genTail() {
        String str = "</div></body></html>";
        return str;
    }

    static void writeFile(String path, String content) throws IOException {
        File file = new File(path);
        OutputStream out = new FileOutputStream(file, false);
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(out, Charset.forName("UTF-8")));
        br.write(content);
        br.close();
        out.close();
        System.out.println("文件写入到:" + file.getAbsolutePath());
    }
}
