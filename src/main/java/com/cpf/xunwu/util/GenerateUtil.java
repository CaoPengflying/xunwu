package com.cpf.xunwu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GenerateUtil {
    private static List<String> colnames = new ArrayList<>();
    private static List<String> colTypes = new ArrayList<>();
    private static List<String> colComment = new ArrayList<>();
    private static Boolean importDate = false;


    public static void main(String[] args) throws Exception {

        generateEntity("rm-bp1lx35997eliful6o.mysql.rds.aliyuncs.com", "3306", "mkl_order", "mkl_test", "4BeLdVeRBY",
//                "house",
//                "id",
//                "House", "com.cpf.xunwu.entity",
//                                "house_detail",
//                "id",
//                "HouseDetail", "com.cpf.xunwu.entity",
//                                "house_picture",
//                "id",
//                "HousePicture", "com.cpf.xunwu.entity",
//                                "house_subscribe",
//                "id",
//                "HouseSubscribe", "com.cpf.xunwu.entity",
//                                "house_tag",
//                "id",
//                "HouseTag", "com.cpf.xunwu.entity",
//                                "role",
//                "id",
//                "Role", "com.cpf.xunwu.entity",
//                                "support_address",
//                "id",
//                "SupportAddress", "com.cpf.xunwu.entity",
                                "pos_order",
                "orderId",
                "PosOrder", "com.cpf.xunwu.entity",
                "E:\\IdeaWorkSpace\\xunwu/src/main/java/com\\cpf\\xunwu\\entity/");
        GenerateMapper.generate("PosOrder","E:\\IdeaWorkSpace\\xunwu/src/main/java/com\\cpf\\xunwu\\mclon\\mapper/");
        GenerateService.generate("PosOrder","E:\\IdeaWorkSpace\\xunwu/src/main/java/com\\cpf\\xunwu\\mclon\\service/");

    }

    public static void generateEntity(String host, String port, String database, String username, String password,
                                      String tableName, String keyId,  String entityName, String basePackage, String
                                              basePath) throws Exception {
        //读取表结构
        initTable(host, port, database, username, password, tableName);
        //生成实体类
        GenerateEntity.generate(entityName, basePackage, basePath, database, tableName, keyId, colnames, colTypes,
                colComment, importDate);

    }

    private static void initTable(String host, String port, String database, String username, String password, String tableName) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = String.format("jdbc:mysql://%s:%s/%s", host, port, database);
        Connection conn = DriverManager.getConnection(url, username, password);
        String strsql = "SELECT COLUMN_NAME, DATA_TYPE, COLUMN_KEY, COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '" + database + "' AND TABLE_NAME = '" + tableName + "'"; //读一行记录;
        PreparedStatement pstmt = conn.prepareStatement(strsql);
        ResultSet result = pstmt.executeQuery();
        while (result.next()) {
            colnames.add(result.getString(1));
            colTypes.add(result.getString(2));
            colComment.add(result.getString(4));
            if ("date".equals(result.getString(2)) || "datetime".equals(result.getString(2)) || "timestamp".equals(result.getString(2))) {
                importDate = true;
            }
        }
        if (conn != null) {
            conn.close();
        }
    }

}
