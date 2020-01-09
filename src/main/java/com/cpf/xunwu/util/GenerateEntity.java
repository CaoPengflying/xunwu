package com.cpf.xunwu.util;

import org.apache.commons.lang3.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by emmet on 17-3-29.
 * desc:
 */
public class GenerateEntity {

    //生成实体类
    public static void generate(String entityName, String entityPackage, String entityPath, String databaseName, String tableName, String primaryKey, List<String> colnames, List<String> colTypes, List<String> colComment, boolean importDate) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("package ").append(entityPackage).append(";\r\n").append("\r\n");
        sb.append("import javax.persistence.Id;\r\n");
        sb.append("import javax.persistence.Column;\r\n");
        sb.append("import javax.persistence.Table;\r\n");
        sb.append("import java.math.BigDecimal;\r\n");
        if(StringUtils.isNotEmpty(primaryKey)){
            sb.append("import javax.persistence.*;\r\n");
        }
        if (importDate) {
            sb.append("import java.util.Date;\r\n").append("\r\n");
        }
        sb.append("@Table(schema = \"`").append(databaseName).append("`\", name = \"").append(tableName).append("\")").append("\r\n");
        sb.append("public class ").append(entityName).append(" {").append("\r\n");
        sb.append("\t@Id\r\n");
        for (int i = 0; i < colnames.size(); i++) {
            sb.append("\t@Column(name = \"" + colnames.get(i) + "\")\r\n");
            String columnName = underline2Camel(colnames.get(i));
            if(primaryKey.equals(columnName)){
                sb.append("\t@GeneratedValue(strategy = GenerationType.IDENTITY)\r\n");
            }
            sb.append("\tprivate ").append(getType(colTypes.get(i))).append(" ").append(columnName)
                    .append(";//").append(colComment.get(i)).append("\r\n\r\n");
        }
        sb.append("\r\n");
        for (int i = 0; i < colnames.size(); i++) {
            String columnName = underline2Camel(colnames.get(i));
            sb.append("\tpublic void set").append(initcap(columnName)).append("(").append(getType(colTypes.get(i))).append(" ")
                    .append(columnName).append(") {\r\n").append("\t\tthis.").append(columnName).append(" = ").append(columnName)
                    .append(";\r\n").append("\t}\r\n");
            sb.append("\tpublic ").append(getType(colTypes.get(i))).append(" get").append(initcap(columnName)).append("() {\r\n")
                    .append("\t\treturn ").append(columnName).append(";\r\n").append("\t}\r\n");
        }
        sb.append("}\r\n");
        String content = sb.toString();
        System.out.println(content);
        FileWriter fw = new FileWriter(entityPath + entityName + ".java");
        PrintWriter pw = new PrintWriter(fw);
        pw.println(content);
        pw.flush();
    }

    //根据SQL类型获取java类型
    private static String getType(String sqlType) {
        switch (sqlType) {
            case "tinyint":
                return "Boolean";
            case "smallint":
            case "int":
            case "integer":
            case "char":
                return "Integer";
            case "bigint":
                return "Long";
            case "float":
            case "double":
            case "decimal":
            case "numeric":
                return "BigDecimal";
            case "varchar":
            case "text":
            case "mediumtext":
                return "String";
            case "date":
            case "time":
            case "datetime":
            case "timestamp":
                return "Date";
            default:
                System.out.println("ERROR DATA TYPE : " + sqlType);
                return null;
        }
    }

    //把输入字符串的首字母改成大写
    private static String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') ch[0] = (char) (ch[0] - 32);
        return new String(ch);
    }

    //下划线转首字母大写
    public static String underline2Camel(String line) {
        if (line == null || "".equals(line)) return "";
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
}
