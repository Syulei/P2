import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchByFields {

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
    }



}
