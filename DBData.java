import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class DBData extends JFrame {

	private JTable tableData = new JTable();
    private DefaultTableModel tableModel = new DefaultTableModel();
    
    public DBData(String tableName, Connection conn) {
        setTitle("View Table: " + tableName);
        setSize(808, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        loadTablesData(conn, tableName);
    }
    
    private void loadTablesData(Connection conn, String tableName) {
        try {
        	Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            DefaultTableModel model = new DefaultTableModel();

            // Add column names
            for (int column = 1; column <= columnCount; column++) {
                model.addColumn(metaData.getColumnName(column));
            }

            // Add row data
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }
            rs.close();
            stmt.close();
            
            JTable table = new JTable(model);
            add(new JScrollPane(table), BorderLayout.CENTER);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data from table: " + ex.getMessage());
        }
    }

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	

}
