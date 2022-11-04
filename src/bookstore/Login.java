package bookstore;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends JFrame {

	private JPanel contentPane;
	private JComboBox<Object> comboBoxRole;
	private JTextField tfUsername;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private JButton btnReset;
	
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Login().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle(" Login");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/images/person.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel sidepanel = new JPanel();
		sidepanel.setBackground(new Color(220, 20, 60));
		sidepanel.setBounds(0, 0, 190, 400);
		sidepanel.setLayout(null);
		contentPane.add(sidepanel);
		
		JLabel lblLoginImg = new JLabel("");
		lblLoginImg.setIcon(new ImageIcon(Login.class.getResource("/images/login.png")));
		lblLoginImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginImg.setBounds(65, 127, 64, 64);
		sidepanel.add(lblLoginImg);
		
		JPanel loginpanel = new JPanel();
		loginpanel.setBackground(new Color(255, 255, 204));
		loginpanel.setBounds(188, 0, 310, 400);
		loginpanel.setLayout(null);
		contentPane.add(loginpanel);
		
		JLabel lblhead = new JLabel("Book Store Software");
		lblhead.setForeground(new Color(220, 20, 60));
		lblhead.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblhead.setHorizontalAlignment(SwingConstants.CENTER);
		lblhead.setBounds(40, 20, 229, 20);
		loginpanel.add(lblhead);
		
		JLabel lblBookImg = new JLabel("");
		lblBookImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookImg.setIcon(new ImageIcon(Login.class.getResource("/images/book_logo.png")));
		lblBookImg.setBounds(81, 60, 147, 105);
		loginpanel.add(lblBookImg);
		
		String[] items = {"Admin","User"};
		comboBoxRole = new JComboBox<Object>(items);
		comboBoxRole.setFont(new Font("Times New Roman", Font.BOLD, 14));
		comboBoxRole.setEditable(true);
		comboBoxRole.setBackground(new Color(255, 248, 220));
		comboBoxRole.setForeground(new Color(220, 20, 60));
		comboBoxRole.setBounds(110, 185, 90, 30);
		loginpanel.add(comboBoxRole);
		
		JLabel lblUserName = new JLabel("Username");
		lblUserName.setForeground(new Color(220, 20, 60));
		lblUserName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblUserName.setBounds(40, 230, 77, 30);
		loginpanel.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(new Color(220, 20, 60));
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblPassword.setBounds(40, 270, 77, 30);
		loginpanel.add(lblPassword);
		
		tfUsername = new JTextField();
		tfUsername.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tfUsername.setBounds(120, 235, 150, 23);
		loginpanel.add(tfUsername);
		tfUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		passwordField.setBounds(120, 275, 150, 23);
		loginpanel.add(passwordField);
		
		btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				login();
			}
		});
		btnLogin.setBorder(UIManager.getBorder("Button.border"));
		btnLogin.setForeground(new Color(220, 20, 60));
		btnLogin.setBackground(new Color(144, 238, 144));
		btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnLogin.setBounds(165, 320, 100, 30);
		loginpanel.add(btnLogin);
		
		btnReset = new JButton("Reset");
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				reset();
			}
		});
		btnReset.setBorder(UIManager.getBorder("Button.border"));
		btnReset.setForeground(new Color(220, 20, 60));
		btnReset.setBackground(new Color(144, 238, 144));
		btnReset.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnReset.setBounds(40, 320, 100, 30);
		loginpanel.add(btnReset);
	}
	
	private void login() {
		if(tfUsername.getText().isEmpty() || passwordField.getText().isEmpty() ) 
		{
			JOptionPane.showMessageDialog(this,"Please Enter the Username and Password");
		}else if(comboBoxRole.getSelectedIndex()==1) 
		{
			String query = "select * from usertbl where UName='"+tfUsername.getText()+"' and UPassword='"+passwordField.getText()+"' ";
			String UserName = tfUsername.getText();
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstoredb","root","Pass@123");
				st = con.createStatement();
				rs = st.executeQuery(query);
				if(rs.next())
				{
					new Billing(UserName).setVisible(true);
					this.dispose();
				}else {
					JOptionPane.showMessageDialog(this,"Wrong Username or Password");
					reset();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			if(tfUsername.getText().equals("Admin") && passwordField.getText().equals("Password")) {
				new Books().setVisible(true);
				this.dispose();
			}else {
				JOptionPane.showMessageDialog(this,"Contact the Admin");
				reset();
			}
		}
	}
	
	private void reset() {
		tfUsername.setText("");
		passwordField.setText("");
	}

}
