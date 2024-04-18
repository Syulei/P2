import java.io.*;
import java.sql.*;
import java.util.*;
import oracle.jdbc.driver.*;

public class Student{
    static Connection con;
    static Statement stmt;

    public static void main(String argv[])
    {
        connectToDatabase();
    }

    public static void connectToDatabase()
    {
        String driverPrefixURL="jdbc:oracle:thin:@";
        String jdbc_url="artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

        // IMPORTANT: DO NOT PUT YOUR LOGIN INFORMATION HERE. INSTEAD, PROMPT USER FOR HIS/HER LOGIN/PASSWD
        String username="xsun22";
        String password="oardeezi";

        try{
            //Register Oracle driver
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (Exception e) {
            System.out.println("Failed to load JDBC/ODBC driver.");
            return;
        }

        try{
            System.out.println(driverPrefixURL+jdbc_url);
            con = DriverManager.getConnection(driverPrefixURL+jdbc_url, username, password);
            DatabaseMetaData dbmd = con.getMetaData();
            stmt = con.createStatement();

            testQuery(con);
//            ResultSet res = stmt.executeQuery("Select * from authors");
////            int columnCount = dbmd.getColumnCount();
//            while (res.next()) {
//                System.out.println(res.getString(0));
//            }

            System.out.println("Connected.");

            if(dbmd==null){
                System.out.println("No database meta data");
            }
            else {
                System.out.println("Database Product Name: "+dbmd.getDatabaseProductName());
                System.out.println("Database Product Version: "+dbmd.getDatabaseProductVersion());
                System.out.println("Database Driver Name: "+dbmd.getDriverName());
                System.out.println("Database Driver Version: "+dbmd.getDriverVersion());
            }



        }catch( Exception e) {e.printStackTrace();}
    }// End of connectToDatabase()

    public static void testQuery(Connection conn) throws SQLException {
//        ViewTable viewTable = new ViewTable();
//        viewTable.tableSelection("PUBLICATIONS", conn);
        SearchByFields searchFields = new SearchByFields();
        searchFields.searchFields(null, null,"2016", null,"type",
                true, true, true, false, false, false, conn);
    }
}// End of class
