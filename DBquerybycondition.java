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


    // 将 JTextField 改为 JComboBox<String>
    private JComboBox<String> publicationID;
    private JComboBox<String> outAuthor;
    private JComboBox<String> outTitle;
    private JComboBox<String> outYear;
    private JComboBox<String> outType;
    private JComboBox<String> summary;

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


        // 从JComboBox获取选项，并检查是否选择了"Yes"
        Boolean outP = publicationID.getSelectedItem().equals("Yes");
        Boolean outA = outAuthor.getSelectedItem().equals("Yes");
        Boolean outT = outTitle.getSelectedItem().equals("Yes");
        Boolean outY = outYear.getSelectedItem().equals("Yes");
        Boolean outTy = outType.getSelectedItem().equals("Yes");
        Boolean outSu = summary.getSelectedItem().equals("Yes");

        SearchByFields searchFields = new SearchByFields();
        ResultSet rs = searchFields.searchFields(author, title, year, type, sort, outP, outA, outT, outY, outTy, outSu,
        conn);
        System.out.println(rs.getMetaData());
        System.out.println("After return the resultset");

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

        // 将 JTextField 替换为 JComboBox

        publicationID = new JComboBox<>(new String[]{"Yes", "No"});
        outAuthor = new JComboBox<>(new String[]{"Yes", "No"});
        outTitle = new JComboBox<>(new String[]{"Yes", "No"});
        outYear = new JComboBox<>(new String[]{"Yes", "No"});
        outType = new JComboBox<>(new String[]{"Yes", "No"});
        summary = new JComboBox<>(new String[]{"Yes", "No"});

        // 设置默认选择为 "No"
        publicationID.setSelectedIndex(0);
        outAuthor.setSelectedIndex(1);
        outTitle.setSelectedIndex(1);
        outYear.setSelectedIndex(1);
        outType.setSelectedIndex(1);
        summary.setSelectedIndex(1);

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
