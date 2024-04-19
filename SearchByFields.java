import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchByFields {

//    public ResultSet searchFields(String author, String title, String year, String type, String sort, boolean out_ID,
//                             boolean out_author, boolean out_title, boolean out_year, boolean out_type, boolean summary, Connection conn) throws SQLException {
//
//        String getByFields = "Select ?, ?, ?, ?, ?, ? from Publications p join Authors a on p.publicationID = a.publicationID " +
//                " where author LIKE ? or title LIKE ? or Year LIKE ? or Type LIKE ? order by ?";
//
//        PreparedStatement queryFields = conn.prepareStatement(getByFields);
//
//        String a = author == null ? "" : author;
//        String t = title == null ? "" : title;
//        String y = year == null ? "" : year;
//        String ty = type == null ? "" : type;
////        String a = "%" + author + "%";
////        String t = "title Like '%" + title + "%' ";
////        String y = "year Like '%" + year + "%' ";
////        String ty = "type Like '%" + type + "%' ";
//        String order = sort == null ? "" : sort;
//
//        StringBuilder sb = new StringBuilder();
//
//        String outId = out_ID == false ? "" : "publicationID, ";
//        String outA = out_author == false ? "" : "author, ";
//        String outT = out_title == false ? "" : "title, ";
//        String outY = out_year == false ? "" : "year, ";
//        String outType = out_type == false ? "" : "type, ";
//        String outS = summary == false ? "" : "summary";
//
//        String[] selectedFields = {outId, outA, outT, outY, outType, outS};
//        for (int i = 0; i < selectedFields.length; i++) {
//            if (selectedFields[i] == "") {
//                continue;
//            }
//            sb.append(selectedFields[i]);
//            if (i < selectedFields.length - 1) {
//                sb.append(", ");
//            }
//        }
//
//
//        queryFields.setString(1, outId);
//        queryFields.setString(2, outA);
//        queryFields.setString(3, outT);
//        queryFields.setString(4, outY);
//        queryFields.setString(5, outType);
//        queryFields.setString(6, outS);
//
//        queryFields.setString(7, "%" + a + "%");
//        System.out.println("%" + author + "%");
//        queryFields.setString(8, "%" + t + "%");
//        queryFields.setString(9, "%" + y + "%");
//        queryFields.setString(10, "%" + ty + "%");
//        queryFields.setString(11, order);
//
//        ResultSet res = queryFields.executeQuery();
//        while (res.next()) {
//            System.out.println(res.getString(2));
//        }
//
//        return res;
//
//    }


//    public ResultSet searchFields(String author, String title, String year, String type, String sort, boolean out_ID,
//                             boolean out_author, boolean out_title, boolean out_year, boolean out_type, boolean summary, Connection conn) throws SQLException {
//        // 动态构建需要选择的列
//        StringBuilder fields = new StringBuilder();
//        if (out_ID) fields.append("publicationID, ");
//        if (out_author) fields.append("a.author, ");
//        if (out_title) fields.append("title, ");
//        if (out_year) fields.append("year, ");
//        if (out_type) fields.append("type, ");
//        if (summary) fields.append("summary, ");
//        if (fields.length() > 0) fields.delete(fields.length() - 2, fields.length());  // 删除最后的逗号和空格
//
//        // 构建查询语句
//        String getByFields = "SELECT " + fields.toString() + " FROM Publications p JOIN Authors a ON p.publicationID = a.publicationID " +
//                "WHERE a.author LIKE ? OR title LIKE ? OR year LIKE ? OR type LIKE ? " +
//                (sort.isEmpty() ? "" : "ORDER BY " + sort);  // 注意: 这可能引入SQL注入风险
//
//        ResultSet res;
//        try (PreparedStatement queryFields = conn.prepareStatement(getByFields)) {
//            queryFields.setString(1, "%" + (author == null ? "" : author) + "%");
//            queryFields.setString(2, "%" + (title == null ? "" : title) + "%");
//            queryFields.setString(3, "%" + (year == null ? "" : year) + "%");
//            queryFields.setString(4, "%" + (type == null ? "" : type) + "%");
//
//            res = queryFields.executeQuery();
////            while (res.next()) {
////                for (int i = 1; i <= res.getMetaData().getColumnCount(); i++) {
////                    System.out.print(res.getString(i) + " ");
////                }
////                System.out.println();  // 打印每行结果
////            }
//
//        }
//        return res;
//    }

    public ResultSet searchFields(String author, String title, String year, String type, String sort,
                             boolean out_ID, boolean out_author, boolean out_title,
                             boolean out_year, boolean out_type, boolean summary, Connection conn) throws SQLException {
        StringBuilder selectClause = new StringBuilder("SELECT ");
        if (out_ID) selectClause.append("p.publicationID, ");
        if (out_author) selectClause.append("a.author, ");
        if (out_title) selectClause.append("p.title, ");
        if (out_year) selectClause.append("p.year, ");
        if (out_type) selectClause.append("p.type, ");
        if (summary) selectClause.append("p.summary, ");

        // 移除末尾的逗号和空格
        if (selectClause.length() > 7) {
            selectClause.setLength(selectClause.length() - 2);
        }

        StringBuilder whereClause = new StringBuilder(" FROM Publications p JOIN Authors a ON p.publicationID = a.publicationID WHERE ");
        boolean first = true;

        // 动态构建 WHERE 子句
        if (author != null) {
            if (!first) whereClause.append(" AND ");
            whereClause.append("a.author LIKE ?");
            first = false;
        }
        if (title != null) {
            if (!first) whereClause.append(" AND ");
            whereClause.append("p.title LIKE ?");
            first = false;
        }
        if (year != null) {
            if (!first) whereClause.append(" AND ");
            whereClause.append("p.year LIKE ?");
            first = false;
        }
        if (type != null) {
            if (!first) whereClause.append(" AND ");
            whereClause.append("p.type LIKE ?");
            first = false;
        }

        String orderByClause = sort.isEmpty() ? "" : " ORDER BY " + sort;
        String sql = selectClause.toString() + whereClause + orderByClause;

        PreparedStatement queryFields = conn.prepareStatement(sql);
        int index = 1;
        if (author != null) queryFields.setString(index++, "%" + author + "%");
        if (title != null) queryFields.setString(index++, "%" + title + "%");
        if (year != null) queryFields.setString(index++, "%" + year + "%");
        if (type != null) queryFields.setString(index++, "%" + type + "%");

        ResultSet res = queryFields.executeQuery();
//            while (res.next()) {
//                for (int i = 1; i <= res.getMetaData().getColumnCount(); i++) {
//                    System.out.print(res.getString(i) + " ");
//                }
//                System.out.println();  // 打印每行结果
//            }

        return res;
//        try (PreparedStatement queryFields = conn.prepareStatement(sql)) {
//            int index = 1;
//            if (author != null) queryFields.setString(index++, "%" + author + "%");
//            if (title != null) queryFields.setString(index++, "%" + title + "%");
//            if (year != null) queryFields.setString(index++, "%" + year + "%");
//            if (type != null) queryFields.setString(index++, "%" + type + "%");
//
//            ResultSet res = queryFields.executeQuery();
////            while (res.next()) {
////                for (int i = 1; i <= res.getMetaData().getColumnCount(); i++) {
////                    System.out.print(res.getString(i) + " ");
////                }
////                System.out.println();  // 打印每行结果
////            }
//            return res;
//        }

    }



}
