import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.BorderLayout;

public class DBSearch extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField searchTextField;
	private JTable table;
	private DefaultTableModel tableModel;

	/**
	 * Create the frame.
	 */
	public DBSearch(Connection conn) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 808, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 0, 0);
		contentPane.add(panel);
		
		JButton searchBtn = new JButton("Search");
		searchBtn.setBounds(450, 35, 97, 23);
		contentPane.add(searchBtn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 68, 794, 410);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        tableModel = new DefaultTableModel();
        table.setModel(tableModel);

        searchBtn.addActionListener(e -> performSearch(conn));
		
		searchTextField = new JTextField();
		searchTextField.setHorizontalAlignment(SwingConstants.LEFT);
		searchTextField.setBounds(93, 36, 335, 21);
		contentPane.add(searchTextField);
		searchTextField.setColumns(10);
		
		/*
		table = new JTable();
		table.setBounds(10, 73, 774, 400);
		contentPane.add(table);
		*/
	}
	
	private void performSearch(Connection conn) {
        String publicationId = searchTextField.getText().trim();
        if (!publicationId.isEmpty()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PUBLICATIONS WHERE publicationid = ?")) {
                stmt.setString(1, publicationId);
                ResultSet rs = stmt.executeQuery();
                fillTable(rs);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            }
        }
    }
	
	private void fillTable(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = metaData.getColumnName(i + 1);
        }
        tableModel.setColumnIdentifiers(columnNames);
        tableModel.setRowCount(0); // Clear existing data

        while (rs.next()) {
            String[] rowData = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                rowData[i] = rs.getString(i + 1);
            }
            tableModel.addRow(rowData);
        }
    }
	
}
