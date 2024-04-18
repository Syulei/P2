import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DBquerybycondition extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField searchAuthor;
    private JTextField searchTitle;
    private JTextField searchYear;
    private JTextField searchType;
    private JTextField sortBy;

    private JTextField publicationID ;
    private JTextField outAuthor ;
    private JTextField outTitle;
    private JTextField outYear ;
    private JTextField outType ;
    private JTextField summary ;

    private JTable table;
    private DefaultTableModel tableModel;

    public DBquerybycondition(Connection conn) {

        System.out.println("In query by condition");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 808, 520);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(5, 5));

//        JPanel panel = new JPanel();
//        panel.setBounds(0, 0, 0, 0);
//        contentPane.add(panel);

        JButton searchBtn = new JButton("Search");
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(searchBtn);
        contentPane.add(topPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        table = new JTable();
        scrollPane.setViewportView(table);
        tableModel = new DefaultTableModel();
        table.setModel(tableModel);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        searchBtn.addActionListener(e -> {
            try {
                performSearch(conn);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        JPanel inputPanel = getInputPanel();
        JPanel outputPanel = getOutputPanel();

        contentPane.add(inputPanel, BorderLayout.WEST);
        contentPane.add(outputPanel, BorderLayout.EAST);



    }

    private void performSearch(Connection conn) throws SQLException {
        String author = searchAuthor.getText().trim();
        String title = searchTitle.getText().trim();
        String year = searchYear.getText().trim();
        String type = searchType.getText().trim();
        String sort = sortBy.getText().trim();

        Boolean outP = publicationID.getText().trim().toLowerCase().equals("yes");
        Boolean outA = outAuthor.getText().trim().toLowerCase().equals("yes");
        Boolean outT = outTitle.getText().trim().toLowerCase().equals("yes");
        Boolean outY = outYear.getText().trim().toLowerCase().equals("yes");
        Boolean outTy = outType.getText().trim().toLowerCase().equals("yes");
        Boolean outSu = summary.getText().trim().toLowerCase().equals("yes");

        SearchByFields searchFields = new SearchByFields();
        ResultSet rs = searchFields.searchFields(author, title, year, type, sort, outP, outA, outT, outY, outTy, outSu,
        conn);
        fillTable(rs);

    }

    public JPanel getInputPanel() {

        JPanel inputFieldsPanel = new JPanel(new GridLayout(5, 2));
        searchAuthor = new JTextField();
        searchTitle = new JTextField();
        searchYear = new JTextField();
        searchType = new JTextField();
        sortBy = new JTextField();

        inputFieldsPanel.add(new JLabel("author:"));
        inputFieldsPanel.add(searchAuthor);
        inputFieldsPanel.add(new JLabel("title:"));
        inputFieldsPanel.add(searchTitle);
        inputFieldsPanel.add(new JLabel("year:"));
        inputFieldsPanel.add(searchYear);
        inputFieldsPanel.add(new JLabel("type:"));
        inputFieldsPanel.add(searchType);
        inputFieldsPanel.add(new JLabel("sort by:"));
        inputFieldsPanel.add(sortBy);

        return inputFieldsPanel;

    }

    public JPanel getOutputPanel() {
        JPanel outputFieldsPanel = new JPanel(new GridLayout(6, 2));
        publicationID = new JTextField();
        outAuthor = new JTextField();
        outTitle = new JTextField();
        outYear = new JTextField();
        outType = new JTextField();
        summary = new JTextField();

        outputFieldsPanel.add(new JLabel("PublicationID(Yes/No):"));
        outputFieldsPanel.add(publicationID);
        outputFieldsPanel.add(new JLabel("Author(Yes/No):"));
        outputFieldsPanel.add(outAuthor);
        outputFieldsPanel.add(new JLabel("title(Yes/No):"));
        outputFieldsPanel.add(outTitle);
        outputFieldsPanel.add(new JLabel("Year(Yes/No):"));
        outputFieldsPanel.add(outYear);
        outputFieldsPanel.add(new JLabel("Type(Yes/No):"));
        outputFieldsPanel.add(outType);
        outputFieldsPanel.add(new JLabel("Summary(Yes/No):"));
        outputFieldsPanel.add(summary);
        return outputFieldsPanel;

    }

    private void fillTable(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = metaData.getColumnName(i + 1);
        }

        tableModel.setColumnIdentifiers(columnNames);
        tableModel.setRowCount(0);

        while (rs.next()) {
            String[] rowData = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                rowData[i] = rs.getString(i + 1);
            }
            tableModel.addRow(rowData);
        }

    }


}
