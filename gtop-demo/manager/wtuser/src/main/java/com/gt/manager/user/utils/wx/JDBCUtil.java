package com.gt.manager.user.utils.wx;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * JDBCUtil
 */
public class JDBCUtil {
    // Empty Constructor
    public JDBCUtil() {
        super();
    }

    /**
     * postgreSQL
     * @param dbIp 数据库IP地址
     * @param dbPort 数据库端口号
     * @param dbSid 数据库名称
     * @param user 用户名
     * @param pswd 密码
     * @return Connection
     */
    public static Connection getConnection(String dbIp, String dbPort, String dbSid, String user, String pswd) {
        try {
            // 加载JDBC驱动
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://" + dbIp + ":" + dbPort + "/" + dbSid;
            Connection connection = DriverManager.getConnection(url, user, pswd);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * MySQl
     * @param user   用户名
     * @param pswd  密码
     * @return
     */
    public static Connection getConnection4Mysql(String url, String user, String pswd) {
        try {
            // 加载JDBC驱动
            Class.forName("org.gjt.mm.mysql.Driver");
//            String url = "jdbc:mysql://"+dbIp+":"+port+"/"+dbname+"?characterEncoding=utf-8";
            Connection connection = DriverManager.getConnection(url, user, pswd);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * oracle
     * @param dbIp    ip
     * @param port    端口
     * @param dbname  数据库名称
     * @param user    用户名
     * @param pswd    密码
     * @return
     */
    public static Connection getConnection4Oracle(String dbIp,String port,String dbname, String user, String pswd) {
        try {
            // 加载JDBC驱动
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@"+dbIp+":"+port+":"+dbname;
            Connection connection = DriverManager.getConnection(url, user, pswd);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
