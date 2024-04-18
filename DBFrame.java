import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Label;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.io.*;
import java.sql.*;
import java.util.*;
import oracle.jdbc.driver.*;

public class DBFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textAccount;
	private JPasswordField txtPwd;
	static Connection con;
    static Statement stmt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBFrame frame = new DBFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DBFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 808, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DBtest");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(238, 10, 317, 89);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 42));
		contentPane.add(lblNewLabel);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(this);
		btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnLogin.setBounds(342, 413, 110, 36);
		contentPane.add(btnLogin);
		
		JLabel lblNewLabel_1 = new JLabel("Account");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1.setBounds(164, 243, 73, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_2.setBounds(164, 334, 73, 15);
		contentPane.add(lblNewLabel_2);
		
		textAccount = new JTextField();
		textAccount.setBounds(264, 234, 266, 36);
		contentPane.add(textAccount);
		textAccount.setColumns(10);
		
		txtPwd = new JPasswordField();
		txtPwd.setBounds(264, 325, 266, 36);
		contentPane.add(txtPwd);
		
	}
	public void actionPerformed(ActionEvent e) {
		String account=textAccount.getText();
		String pwd=new String(txtPwd.getPassword());
		String driverPrefixURL="jdbc:oracle:thin:@";
		String jdbc_url="artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
		
	        // IMPORTANT: DO NOT PUT YOUR LOGIN INFORMATION HERE. INSTEAD, PROMPT USER FOR HIS/HER LOGIN/PASSWD
	        String username=account;
	        String password=pwd;
		
	        try{
		    //Register Oracle driver
	            DriverManager.registerDriver(new OracleDriver());
	        } catch (Exception e1) {
	        	JOptionPane.showMessageDialog(this, "Failed to load JDBC/ODBC driver.");
	            System.out.println("Failed to load JDBC/ODBC driver.");
	            return;
	        }

	       try{
	            System.out.println(driverPrefixURL+jdbc_url);
	            con=DriverManager.getConnection(driverPrefixURL+jdbc_url, username, password);
	            DatabaseMetaData dbmd=con.getMetaData();
	            stmt=con.createStatement();

	            System.out.println("Connected."); 

	            if(dbmd==null){
	            	JOptionPane.showMessageDialog(this, "No database meta data");
	                System.out.println("No database meta data");
	            }
	            else {
	                System.out.println("Database Product Name: "+dbmd.getDatabaseProductName());
	                System.out.println("Database Product Version: "+dbmd.getDatabaseProductVersion());
	                System.out.println("Database Driver Name: "+dbmd.getDriverName());
	                System.out.println("Database Driver Version: "+dbmd.getDriverVersion());
	            }
	            new DBHome(con).setVisible(true);  // Open the next window
	            this.dispose();  // Close login window
	        }catch( Exception e1) {e1.printStackTrace();}
	}
}
