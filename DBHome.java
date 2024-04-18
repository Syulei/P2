import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class DBHome extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public DBHome(Connection conn) {
		setTitle("Home page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 808, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton viewTableBtn = new JButton("View Table");
		viewTableBtn.setBounds(47, 191, 97, 23);
		contentPane.add(viewTableBtn);
		viewTableBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 new DBtable(conn).setVisible(true);
            }
        });
		
		JButton searchBtn = new JButton("Search");
		searchBtn.setBounds(47, 263, 97, 23);
		contentPane.add(searchBtn);
		searchBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 new DBSearch(conn).setVisible(true);
            }
        });

		JButton fieldsSearch = new JButton("AdvancedSearch");
		fieldsSearch.setBounds(47, 350, 150, 23);
		contentPane.add(fieldsSearch);
		fieldsSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				new DBquerybycondition(conn).setVisible(true);
			}
		});
	}
}
