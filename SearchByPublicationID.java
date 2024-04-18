import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchByPublicationID {

    public void searchPublicationID (String ID, Connection conn) throws SQLException {
        String getByID = "Select * from Publications p join Authors a on p.? = a.? where p.publicationID = ?";
        PreparedStatement queryID = conn.prepareStatement(getByID);
        queryID.setString(1, ID);
        queryID.setString(2, ID);
        queryID.setString(3, ID);

        ResultSet res = queryID.executeQuery();
        while(res.next()) {
            System.out.println(res.next());
        }
        queryID.close();
    }
}
