package bookstore;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Users extends JFrame {

	private JPanel contentPane;
	
	private JPanel headingPanel;
	private JLabel lblHead;
	private JButton btnLogout;
	private JLabel lblAdminProfile;
	private JLabel lblAdminIcon;
	
	private JPanel optionPanel;
	private JLabel lblUserIcon;
	private JLabel lblUsers;
	private JLabel lblBookIcon;
	private JLabel lblBooks;
	private JPanel switchPanel;
	
	private JPanel mainPanel;
	private JLabel lblUid;
	private JLabel lblUName;
	private JLabel lblMob;
	private JLabel lblAddress;
	private JLabel lblMail;
	private JLabel lblPassword;
	private JTextField tfUid;
	private JTextField tfUName;
	private JTextField tfMob;
	private JTextField tfAddress;
	private JTextField tfMail;
	private JTextField tfPassword;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnReset;
	private JLabel lblUsersList;
	private JTable userstable;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	private JButton btnPrint;

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
					new Users().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Users() {
		setTitle(" Users Information");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Users.class.getResource("/images/users.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 1350, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		headingPanel = new JPanel();
		headingPanel.setBackground(new Color(220, 20, 60));
		headingPanel.setBounds(0, 0, 1336, 40);
		contentPane.add(headingPanel);
		headingPanel.setLayout(null);
		
		lblHead = new JLabel("Book Store Software");
		lblHead.setBounds(563, 8, 210, 25);
		headingPanel.add(lblHead);
		lblHead.setHorizontalAlignment(SwingConstants.CENTER);
		lblHead.setForeground(new Color(255, 255, 255));
		lblHead.setFont(new Font("Times New Roman", Font.BOLD, 22));
		
		btnLogout = new JButton("Logout");
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logout();
			}
		});
		btnLogout.setForeground(new Color(0, 0, 51));
		btnLogout.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnLogout.setBorder(new LineBorder(new Color(250, 235, 215), 1, true));
		btnLogout.setBackground(new Color(245, 222, 179));
		btnLogout.setBounds(1226, 5, 100, 30);
		headingPanel.add(btnLogout);
		
		lblAdminProfile = new JLabel("Admin");
		lblAdminProfile.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdminProfile.setForeground(new Color(255, 250, 250));
		lblAdminProfile.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblAdminProfile.setBounds(39, 10, 60, 20);
		headingPanel.add(lblAdminProfile);
		
		lblAdminIcon = new JLabel("");
		lblAdminIcon.setIcon(new ImageIcon(Books.class.getResource("/images/admin.png")));
		lblAdminIcon.setBounds(10, 5, 30, 30);
		headingPanel.add(lblAdminIcon);
		
		optionPanel = new JPanel();
		optionPanel.setBackground(new Color(255, 255, 224));
		optionPanel.setBounds(0, 40, 1336, 40);
		contentPane.add(optionPanel);
		optionPanel.setLayout(null);
		
		lblUserIcon = new JLabel("");
		lblUserIcon.setBounds(430, 5, 30, 30);
		optionPanel.add(lblUserIcon);
		lblUserIcon.setIcon(new ImageIcon(Books.class.getResource("/images/person.png")));
		
		lblUsers = new JLabel("Users");
		lblUsers.setBounds(456, 10, 60, 20);
		optionPanel.add(lblUsers);
		lblUsers.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsers.setForeground(new Color(139, 0, 0));
		lblUsers.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		lblBookIcon = new JLabel("");
		lblBookIcon.setIcon(new ImageIcon(Books.class.getResource("/images/book_icon.png")));
		lblBookIcon.setBounds(795, 5, 30, 30);
		optionPanel.add(lblBookIcon);
		
		lblBooks = new JLabel("Books");
		lblBooks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switchToBooks();
			}
		});
		lblBooks.setBounds(820, 10, 60, 20);
		optionPanel.add(lblBooks);
		lblBooks.setHorizontalAlignment(SwingConstants.CENTER);
		lblBooks.setForeground(new Color(139, 0, 0));
		lblBooks.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		switchPanel = new JPanel();
		switchPanel.setLayout(null);
		switchPanel.setBackground(new Color(0, 255, 0));
		switchPanel.setBounds(465, 28, 50, 3);
		optionPanel.add(switchPanel);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(192, 192, 192));
		mainPanel.setBounds(0, 80, 1336, 633);
		contentPane.add(mainPanel);
		mainPanel.setLayout(null);
		
		lblUid = new JLabel("Id");
		lblUid.setHorizontalAlignment(SwingConstants.CENTER);
		lblUid.setForeground(new Color(220, 20, 60));
		lblUid.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblUid.setBounds(20, 10, 40, 20);
		mainPanel.add(lblUid);
		
		lblUName = new JLabel("Name");
		lblUName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUName.setForeground(new Color(220, 20, 60));
		lblUName.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblUName.setBounds(180, 10, 70, 20);
		mainPanel.add(lblUName);
		
		lblMob = new JLabel("Mobile No.");
		lblMob.setHorizontalAlignment(SwingConstants.CENTER);
		lblMob.setForeground(new Color(220, 20, 60));
		lblMob.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblMob.setBounds(430, 10, 110, 20);
		mainPanel.add(lblMob);
		
		lblAddress = new JLabel("Address");
		lblAddress.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddress.setForeground(new Color(220, 20, 60));
		lblAddress.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblAddress.setBounds(670, 10, 110, 20);
		mainPanel.add(lblAddress);
		
		lblMail = new JLabel("E-mail");
		lblMail.setHorizontalAlignment(SwingConstants.CENTER);
		lblMail.setForeground(new Color(220, 20, 60));
		lblMail.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblMail.setBounds(920, 10, 100, 20);
		mainPanel.add(lblMail);
		
		lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setForeground(new Color(220, 20, 60));
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblPassword.setBounds(1155, 10, 112, 20);
		mainPanel.add(lblPassword);
		
		tfUid = new JTextField();
		tfUid.setBorder(UIManager.getBorder("TextField.border"));
		tfUid.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfUid.setColumns(10);
		tfUid.setBounds(30, 40, 110, 25);
		mainPanel.add(tfUid);
		
		tfUName = new JTextField();
		tfUName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfUName.setColumns(10);
		tfUName.setBorder(UIManager.getBorder("TextField.border"));
		tfUName.setBounds(190, 40, 200, 25);
		mainPanel.add(tfUName);
		
		tfMob = new JTextField();
		tfMob.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfMob.setColumns(10);
		tfMob.setBorder(UIManager.getBorder("TextField.border"));
		tfMob.setBounds(440, 40, 200, 25);
		mainPanel.add(tfMob);
		
		tfAddress = new JTextField();
		tfAddress.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfAddress.setColumns(10);
		tfAddress.setBorder(UIManager.getBorder("TextField.border"));
		tfAddress.setBounds(690, 40, 200, 25);
		mainPanel.add(tfAddress);
		
		tfMail = new JTextField();
		tfMail.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfMail.setColumns(10);
		tfMail.setBorder(UIManager.getBorder("TextField.border"));
		tfMail.setBounds(941, 40, 160, 25);
		mainPanel.add(tfMail);
		
		tfPassword = new JTextField();
		tfPassword.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfPassword.setColumns(10);
		tfPassword.setBorder(UIManager.getBorder("TextField.border"));
		tfPassword.setBounds(1170, 40, 139, 25);
		mainPanel.add(tfPassword);
		
		btnAdd = new JButton("Add");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addUsers();
			}
		});
		btnAdd.setForeground(new Color(0, 0, 51));
		btnAdd.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnAdd.setBorder(new LineBorder(new Color(250, 235, 215), 1, true));
		btnAdd.setBackground(new Color(245, 222, 179));
		btnAdd.setBounds(330, 100, 100, 30);
		mainPanel.add(btnAdd);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateUsers();
			}
		});
		btnUpdate.setForeground(new Color(0, 0, 51));
		btnUpdate.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnUpdate.setBorder(new LineBorder(new Color(250, 235, 215), 1, true));
		btnUpdate.setBackground(new Color(245, 222, 179));
		btnUpdate.setBounds(499, 100, 100, 30);
		mainPanel.add(btnUpdate);
		
		btnDelete = new JButton("Delete");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteUsers();
			}
		});
		btnDelete.setForeground(new Color(0, 0, 51));
		btnDelete.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnDelete.setBorder(new LineBorder(new Color(250, 235, 215), 1, true));
		btnDelete.setBackground(new Color(245, 222, 179));
		btnDelete.setBounds(670, 100, 100, 30);
		mainPanel.add(btnDelete);
		
		btnReset = new JButton("Reset");
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				reset();
			}
		});
		btnReset.setForeground(new Color(0, 0, 51));
		btnReset.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnReset.setBorder(new LineBorder(new Color(250, 235, 215), 1, true));
		btnReset.setBackground(new Color(245, 222, 179));
		btnReset.setBounds(833, 100, 100, 30);
		mainPanel.add(btnReset);
		
		lblUsersList = new JLabel("Users List");
		lblUsersList.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsersList.setForeground(new Color(165, 42, 42));
		lblUsersList.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblUsersList.setBounds(613, 150, 110, 20);
		mainPanel.add(lblUsersList);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(73, 180, 1189, 390);
		mainPanel.add(scrollPane);
		
		userstable = new JTable();
		userstable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				model = (DefaultTableModel)userstable.getModel();
				int myIndex = userstable.getSelectedRow();
				tfUid.setText(model.getValueAt(myIndex, 0).toString());
				tfUName.setText(model.getValueAt(myIndex, 1).toString());
				tfMob.setText(model.getValueAt(myIndex, 2).toString());
				tfAddress.setText(model.getValueAt(myIndex, 3).toString());
				tfMail.setText(model.getValueAt(myIndex, 4).toString());
				tfPassword.setText(model.getValueAt(myIndex, 5).toString());
			}
		});
		userstable.setRowMargin(2);
		userstable.setSelectionForeground(new Color(255, 255, 255));
		userstable.setSelectionBackground(new Color(139, 0, 0));
		userstable.setRowHeight(25);
		userstable.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		model = new DefaultTableModel();
		Object[] column = {"Id","Name","Mobile No.","Address","E-mail","Password"};
		Object[] row = new Object[0];
		model.setColumnIdentifiers(column);
		userstable.setModel(model);
		JTableHeader tableHeader = userstable.getTableHeader();
		tableHeader.setBackground(Color.DARK_GRAY);
	    tableHeader.setForeground(Color.white);
	    tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 18));
		scrollPane.setViewportView(userstable);
		
		btnPrint = new JButton("Print");
		btnPrint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					userstable.print();
				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnPrint.setForeground(new Color(0, 0, 51));
		btnPrint.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnPrint.setBorder(new LineBorder(new Color(250, 235, 215), 1, true));
		btnPrint.setBackground(new Color(245, 222, 179));
		btnPrint.setBounds(618, 585, 100, 30);
		mainPanel.add(btnPrint);
		
		displayUsers();
	}
	
	private void addUsers() {
		if(tfUid.getText().isEmpty() || tfUName.getText().isEmpty() || tfMob.getText().isEmpty() || tfAddress.getText().isEmpty() || tfMail.getText().isEmpty() || tfPassword.getText().isEmpty() ) {
			JOptionPane.showMessageDialog(this,"Missing Information");
		} else {
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstoredb","root","Pass@123");
				PreparedStatement add = con.prepareStatement("insert into usertbl values(?,?,?,?,?,?)");
				add.setInt(1, Integer.valueOf(tfUid.getText()));
				add.setString(2, tfUName.getText());
				add.setString(3, tfMob.getText());
				add.setString(4, tfAddress.getText());
				add.setString(5, tfMail.getText());
				add.setString(6, tfPassword.getText()); 
				add.executeUpdate();
				JOptionPane.showMessageDialog(this,"User Added");
				displayUsers();
				reset();
			} catch (Exception e2) {
				e2.printStackTrace();					
			}
		}
	}
	
	private void displayUsers() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstoredb","root","Pass@123");
			st = con.createStatement();
			rs = st.executeQuery("select * from usertbl");
			userstable.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateUsers() {
		if(tfUid.getText().isEmpty() || tfUName.getText().isEmpty() || tfMob.getText().isEmpty() || tfAddress.getText().isEmpty() || tfMail.getText().isEmpty() || tfPassword.getText().isEmpty() ) {
			JOptionPane.showMessageDialog(this,"Missing Information");
		} else {
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstoredb","root","Pass@123");
				String uid = tfUid.getText();
				String query = "update usertbl set UName='"+tfUName.getText()+"',UMob='"+tfMob.getText()+"',UAddress='"+tfAddress.getText()+"',UMail='"+tfMail.getText()+"',UPassword='"+tfPassword.getText()+"' where UId="+uid;
				Statement update = con.createStatement();
				update.executeUpdate(query);
				JOptionPane.showMessageDialog(this,"User Updated");
				displayUsers();
				reset();
			} catch (Exception e2) {
				e2.printStackTrace();					
			}
		}
	}
	
	private void deleteUsers() {
		if(tfUid.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this,"Missing Information");
		} else {
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstoredb","root","Pass@123");
				String uid = tfUid.getText();
				String query = "delete from usertbl where UId = "+uid;
				Statement delete = con.createStatement();
				delete.executeUpdate(query);
				JOptionPane.showMessageDialog(this,"User deleted");
				displayUsers();
				reset();
			} catch (Exception e2) {
				e2.printStackTrace();					
			}
		}
	}
	
	private void reset() {
		tfUid.setText("");
		tfUName.setText("");
		tfMob.setText("");
		tfAddress.setText("");
		tfMail.setText("");
		tfPassword.setText("");
	}
	
	private void logout() {
		new Login().setVisible(true);
		this.dispose();
	}
	
	private void switchToBooks() {
		new Books().setVisible(true);
		this.dispose();
	}
}
