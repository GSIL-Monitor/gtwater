package com.gt.manager.user.utils.wx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateOldToken {
    private static Logger logger = LoggerFactory.getLogger(UpdateOldToken.class);
    public static void update(String accessToken){
        String url = "jdbc:mysql://rds4dst4pdqfq2xpt24u9.mysql.rds.aliyuncs.com:3306/gt_water_manger?useUnicode=true&characterEncoding=UTF8";
        String user = "gt_water";
        String password = "GT_water_web888";
        try {
            Connection conn = JDBCUtil.getConnection4Mysql(url,user, password);
            int i = 0;
            String sql = "update gt_water_sys set gt_wechat_access_token='" + accessToken + "' where gt_water_sys_id=1";
            logger.info("数据库url={}", url);
            logger.info("UpdateOldToken,spl语句={}", sql);
            PreparedStatement pstmt;
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            logger.info("resutl: {}",i);
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改旧系统token异常", e);
        }
    }
}
