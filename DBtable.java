import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

public class DBtable extends JFrame {
	private JList<String> listTables = new JList<>();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JButton viewButton = new JButton("View Selected Tables");
    private Connection connection;

    public DBtable(Connection conn) {
        this.connection = conn;
        setTitle("Database Tables");
        setSize(808, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        listTables.setModel(listModel);
        setLayout(new BorderLayout());
        add(new JScrollPane(listTables), BorderLayout.CENTER);
        add(viewButton, BorderLayout.SOUTH);

        loadTables();

        viewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                for (String tableName : listTables.getSelectedValuesList()) {
                    new DBData(tableName, connection).setVisible(true);
                }
            }
        });
    }

    private void loadTables() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            String schemaPattern = "XSUN22";
            ResultSet rs = metaData.getTables(null, schemaPattern, "%", new String[]{"TABLE"});
            while (rs.next()) {
                listModel.addElement(rs.getString("TABLE_NAME"));
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading tables: " + ex.getMessage());
        }
    }
}
