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
import java.sql.*;
import javax.swing.JComboBox;

public class Books extends JFrame {

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
	private JLabel lblid;
	private JLabel lblName;
	private JLabel lblAuthor;
	private JLabel lblCategory;
	private JLabel lblQuantity;
	private JLabel lblPrice;
	private JTextField tfid;
	private JTextField tfName;
	private JTextField tfAuthor;
	private JComboBox<Object> comboBoxCategory;
	private JTextField tfQuantity;
	private JTextField tfPrice;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnReset;
	private JLabel lblBooksList;
	private JTable bookstable;
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
					new Books().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Books() {
		setTitle(" Books Information");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Books.class.getResource("/images/magazine.png")));
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
		lblUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switchToUsers();
			}
		});
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
		lblBooks.setBounds(820, 10, 60, 20);
		optionPanel.add(lblBooks);
		lblBooks.setHorizontalAlignment(SwingConstants.CENTER);
		lblBooks.setForeground(new Color(139, 0, 0));
		lblBooks.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		switchPanel = new JPanel();
		switchPanel.setBounds(825, 28, 55, 3);
		optionPanel.add(switchPanel);
		switchPanel.setBackground(new Color(0, 255, 0));
		switchPanel.setLayout(null);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(192, 192, 192));
		mainPanel.setBounds(0, 80, 1336, 633);
		contentPane.add(mainPanel);
		mainPanel.setLayout(null);
		
		lblid = new JLabel("Id");
		lblid.setHorizontalAlignment(SwingConstants.CENTER);
		lblid.setForeground(new Color(220, 20, 60));
		lblid.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblid.setBounds(20, 10, 40, 20);
		mainPanel.add(lblid);
		
		lblName = new JLabel("Name");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setForeground(new Color(220, 20, 60));
		lblName.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblName.setBounds(180, 10, 70, 20);
		mainPanel.add(lblName);
		
		lblAuthor = new JLabel("Author");
		lblAuthor.setHorizontalAlignment(SwingConstants.CENTER);
		lblAuthor.setForeground(new Color(220, 20, 60));
		lblAuthor.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblAuthor.setBounds(430, 10, 80, 20);
		mainPanel.add(lblAuthor);
		
		lblCategory = new JLabel("Category");
		lblCategory.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategory.setForeground(new Color(220, 20, 60));
		lblCategory.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblCategory.setBounds(670, 10, 110, 20);
		mainPanel.add(lblCategory);
		
		lblQuantity = new JLabel("Quantity");
		lblQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuantity.setForeground(new Color(220, 20, 60));
		lblQuantity.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblQuantity.setBounds(920, 10, 110, 20);
		mainPanel.add(lblQuantity);
		
		lblPrice = new JLabel("Price");
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice.setForeground(new Color(220, 20, 60));
		lblPrice.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblPrice.setBounds(1150, 10, 80, 20);
		mainPanel.add(lblPrice);
		
		tfid = new JTextField();
		tfid.setBorder(UIManager.getBorder("TextField.border"));
		tfid.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfid.setColumns(10);
		tfid.setBounds(30, 40, 110, 25);
		mainPanel.add(tfid);
		
		tfName = new JTextField();
		tfName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfName.setColumns(10);
		tfName.setBorder(UIManager.getBorder("TextField.border"));
		tfName.setBounds(190, 40, 200, 25);
		mainPanel.add(tfName);
		
		tfAuthor = new JTextField();
		tfAuthor.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfAuthor.setColumns(10);
		tfAuthor.setBorder(UIManager.getBorder("TextField.border"));
		tfAuthor.setBounds(440, 40, 200, 25);
		mainPanel.add(tfAuthor);
		
		comboBoxCategory = new JComboBox<Object>(new Object[]{"General","Programming","Technology","Autobiography","Story","Science", "Commerce", "Arts","History","Action and Adventure","Comic","Other"});
		comboBoxCategory.setForeground(new Color(219, 112, 147));
		comboBoxCategory.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		comboBoxCategory.setEditable(true);
		comboBoxCategory.setBackground(new Color(255, 255, 240));
		comboBoxCategory.setBounds(690, 40, 169, 25);
		mainPanel.add(comboBoxCategory);
		
		tfQuantity = new JTextField();
		tfQuantity.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfQuantity.setColumns(10);
		tfQuantity.setBorder(UIManager.getBorder("TextField.border"));
		tfQuantity.setBounds(941, 40, 160, 25);
		mainPanel.add(tfQuantity);
		
		tfPrice = new JTextField();
		tfPrice.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfPrice.setColumns(10);
		tfPrice.setBorder(UIManager.getBorder("TextField.border"));
		tfPrice.setBounds(1170, 40, 139, 25);
		mainPanel.add(tfPrice);
		
		btnAdd = new JButton("Add");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addBooks();
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
				updateBooks();
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
				deleteBooks();
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
		
		lblBooksList = new JLabel("Books List");
		lblBooksList.setHorizontalAlignment(SwingConstants.CENTER);
		lblBooksList.setForeground(new Color(165, 42, 42));
		lblBooksList.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblBooksList.setBounds(613, 150, 110, 20);
		mainPanel.add(lblBooksList);
		
		scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		scrollPane.setBounds(73, 180, 1189, 390);
		mainPanel.add(scrollPane);
		
		bookstable = new JTable();
		bookstable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				model = (DefaultTableModel)bookstable.getModel();
				int myIndex = bookstable.getSelectedRow();
				tfid.setText(model.getValueAt(myIndex, 0).toString());
				tfName.setText(model.getValueAt(myIndex, 1).toString());
				tfAuthor.setText(model.getValueAt(myIndex, 2).toString());
				comboBoxCategory.setSelectedItem(model.getValueAt(myIndex, 3).toString());
				tfQuantity.setText(model.getValueAt(myIndex, 4).toString());
				tfPrice.setText(model.getValueAt(myIndex, 5).toString());
			}
		});
		bookstable.setRowMargin(2);
		bookstable.setSelectionForeground(new Color(255, 255, 255));
		bookstable.setSelectionBackground(new Color(139, 0, 0));
		bookstable.setRowHeight(25);
		bookstable.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		model = new DefaultTableModel();
		Object[] column = {"Id","Name","Author","Category","Quantity","Price"};
		Object[] row = new Object[0];
		model.setColumnIdentifiers(column);
		bookstable.setModel(model);
		JTableHeader tableHeader = bookstable.getTableHeader();
		tableHeader.setBackground(Color.DARK_GRAY);
	    tableHeader.setForeground(Color.white);
	    tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 18));
		scrollPane.setViewportView(bookstable);
		
		btnPrint = new JButton("Print");
		btnPrint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					bookstable.print();
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
		
		displayBooks();
	}
	
	private void addBooks() {
		if(tfid.getText().isEmpty() || tfName.getText().isEmpty() || tfAuthor.getText().isEmpty() || comboBoxCategory.getSelectedIndex() == -1 || tfQuantity.getText().isEmpty() || tfPrice.getText().isEmpty() ) {
			JOptionPane.showMessageDialog(this,"Missing Information");
		} else {
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstoredb","root","Pass@123");
				PreparedStatement add = con.prepareStatement("insert into booktbl values(?,?,?,?,?,?)");
				add.setInt(1, Integer.valueOf(tfid.getText()));
				add.setString(2, tfName.getText());
				add.setString(3, tfAuthor.getText());
				add.setString(4, comboBoxCategory.getSelectedItem().toString());
				add.setString(5, tfQuantity.getText());
				add.setString(6, tfPrice.getText()); 
				add.executeUpdate();
				JOptionPane.showMessageDialog(this,"Book Added");
				displayBooks();
				reset();
			} catch (Exception e2) {
				e2.printStackTrace();					
			}
		}
	}
	
	private void displayBooks() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstoredb","root","Pass@123");
			st = con.createStatement();
			rs = st.executeQuery("select * from booktbl");
			bookstable.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateBooks() {
		if(tfid.getText().isEmpty() || tfName.getText().isEmpty() || tfAuthor.getText().isEmpty() || comboBoxCategory.getSelectedIndex() == -1 || tfQuantity.getText().isEmpty() || tfPrice.getText().isEmpty() ) {
			JOptionPane.showMessageDialog(this,"Missing Information");
		} else {
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstoredb","root","Pass@123");
				String bid = tfid.getText();
				String query = "update booktbl set Title='"+tfName.getText()+"',Author='"+tfAuthor.getText()+"',Category='"+comboBoxCategory.getSelectedItem() +"',Quantity='"+tfQuantity.getText()+"',Price='"+tfPrice.getText()+"' where BId="+bid;
				Statement update = con.createStatement();
				update.executeUpdate(query);
				JOptionPane.showMessageDialog(this,"Book Updated");
				displayBooks();
				reset();
			} catch (Exception e2) {
				e2.printStackTrace();					
			}
		}
	}
	
	private void deleteBooks() {
		if(tfid.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this,"Missing Information");
		} else {
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstoredb","root","Pass@123");
				String bid = tfid.getText();
				String query = "delete from booktbl where BId = "+bid;
				Statement delete = con.createStatement();
				delete.executeUpdate(query);
				JOptionPane.showMessageDialog(this,"Book deleted");
				displayBooks();
				reset();
			} catch (Exception e2) {
				e2.printStackTrace();					
			}
		}
	}
	
	private void reset() {
		tfid.setText("");
		tfName.setText("");
		tfAuthor.setText("");
		comboBoxCategory.setSelectedIndex(0);
		tfQuantity.setText("");
		tfPrice.setText("");
	}
	
	private void logout() {
		new Login().setVisible(true);
		this.dispose();
	}
	
	private void switchToUsers() {
		new Users().setVisible(true);
		this.dispose();
	}
}
