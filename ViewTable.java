import java.sql.*;
import java.util.Objects;

public class ViewTable {

    public void tableSelection(String selection, Connection conn) throws SQLException {
        if (Objects.equals(selection, "PUBLICATIONS")) {
            String getPublication = "Select * from PUBLICATIONS";
            PreparedStatement publication = conn.prepareStatement(getPublication);

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(getPublication);

//            ResultSet res = publication.executeQuery();
            while (res.next()) {
                System.out.println(res.getString(0));
                System.out.println(res.getString(1));
                System.out.println(res.getString(2));
            }
            publication.close();

        } else {

            String getAuthors = "Select * from Authors";
            PreparedStatement author = conn.prepareStatement(getAuthors);

            ResultSet res = author.executeQuery();
            while (res.next()) {
                System.out.println(res.next());
            }
            author.close();
        }

    }

}
