package bookstore;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;

public class Billing extends JFrame {

	private JPanel contentPane;
	
	private JPanel headingPanel;
	private JLabel lblHead;
	private JButton btnLogout;
	private static JLabel lblUserProfile;
	private JLabel lblUserIcon;
	
	private JPanel mainPanel;
	private JLabel lblBookName;
	private JLabel lblPrice;
	private JLabel lblQuantity;
	private JLabel lblBillNumber;
	private JTextField tfBookName;
	private JTextField tfPrice;
	private JTextField tfQuantity;
	private JTextField tfBillNumber;
	private JLabel lblCustomerName;
	private JLabel lblCustomerMob;
	private JLabel lblCustomerAddress;
	private JTextField tfCustomerName;
	private JTextField tfCustomerMob;
	private JTextField tfCustomerAddress;
	private JButton btnAddToBill;
	private JButton btnReset;
	private JLabel lblBooksList;
	private JTable bookstable;
	private JScrollPane scrollPane_Book;
	private DefaultTableModel model;
	private JLabel lblBooksBill;
	private JScrollPane scrollPane_Bill;
	private JTextArea billText;
	private JLabel lblPayment;
	JButton btnPrintBill;
	
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
					new Billing().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Billing() {
		initialize();
		displayBooks();
		tfBookName.setEditable(false);
		tfPrice.setEditable(false);
		countRow();
		tfBillNumber.setEditable(false);
	}
	
	public Billing(String UN) {
		initialize();
		lblUserProfile.setText(UN);
		displayBooks();
		tfBookName.setEditable(false);
		tfPrice.setEditable(false);
		countRow();
		tfBillNumber.setEditable(false);
	}
	
	private void initialize() {
		setTitle(" Books Payment");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Billing.class.getResource("/images/payment_icon.png")));
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
		
		lblUserProfile = new JLabel("User");
		lblUserProfile.setHorizontalAlignment(SwingConstants.LEFT);
		lblUserProfile.setForeground(new Color(255, 250, 250));
		lblUserProfile.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblUserProfile.setBounds(40, 10, 136, 20);
		headingPanel.add(lblUserProfile);
		
		lblUserIcon = new JLabel("");
		lblUserIcon.setIcon(new ImageIcon(Billing.class.getResource("/images/person.png")));
		lblUserIcon.setBounds(10, 5, 30, 30);
		headingPanel.add(lblUserIcon);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(192, 192, 192));
		mainPanel.setBounds(0, 40, 1336, 663);
		contentPane.add(mainPanel);
		mainPanel.setLayout(null);
		
		lblBookName = new JLabel("Book Name");
		lblBookName.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookName.setForeground(new Color(220, 20, 60));
		lblBookName.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblBookName.setBounds(10, 22, 121, 20);
		mainPanel.add(lblBookName);
		
		lblPrice = new JLabel("Price");
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice.setForeground(new Color(220, 20, 60));
		lblPrice.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblPrice.setBounds(244, 22, 60, 20);
		mainPanel.add(lblPrice);
		
		lblQuantity = new JLabel("Quantity");
		lblQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuantity.setForeground(new Color(220, 20, 60));
		lblQuantity.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblQuantity.setBounds(410, 22, 93, 20);
		mainPanel.add(lblQuantity);
		
		lblBillNumber = new JLabel("Bill Number");
		lblBillNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblBillNumber.setForeground(new Color(220, 20, 60));
		lblBillNumber.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblBillNumber.setBounds(582, 22, 100, 20);
		mainPanel.add(lblBillNumber);
		
		tfBookName = new JTextField();
		tfBookName.setBackground(Color.WHITE);
		tfBookName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfBookName.setColumns(10);
		tfBookName.setBorder(UIManager.getBorder("TextField.border"));
		tfBookName.setBounds(20, 52, 200, 25);
		mainPanel.add(tfBookName);
		
		tfPrice = new JTextField();
		tfPrice.setBackground(Color.WHITE);
		tfPrice.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfPrice.setColumns(10);
		tfPrice.setBorder(UIManager.getBorder("TextField.border"));
		tfPrice.setBounds(250, 52, 140, 25);
		mainPanel.add(tfPrice);
		
		tfQuantity = new JTextField();
		tfQuantity.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfQuantity.setColumns(10);
		tfQuantity.setBorder(UIManager.getBorder("TextField.border"));
		tfQuantity.setBounds(422, 52, 130, 25);
		mainPanel.add(tfQuantity);
		
		tfBillNumber = new JTextField();
		tfBillNumber.setBorder(UIManager.getBorder("TextField.border"));
		tfBillNumber.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfBillNumber.setColumns(10);
		tfBillNumber.setBounds(582, 52, 121, 25);
		mainPanel.add(tfBillNumber);
		
		lblCustomerName = new JLabel("Customer Name");
		lblCustomerName.setHorizontalAlignment(SwingConstants.CENTER);
		lblCustomerName.setForeground(new Color(220, 20, 60));
		lblCustomerName.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblCustomerName.setBounds(10, 101, 153, 20);
		mainPanel.add(lblCustomerName);
		
		lblCustomerMob = new JLabel("Customer Mobile No.");
		lblCustomerMob.setHorizontalAlignment(SwingConstants.CENTER);
		lblCustomerMob.setForeground(new Color(220, 20, 60));
		lblCustomerMob.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblCustomerMob.setBounds(244, 101, 192, 20);
		mainPanel.add(lblCustomerMob);
		
		lblCustomerAddress = new JLabel("Customer Address");
		lblCustomerAddress.setHorizontalAlignment(SwingConstants.CENTER);
		lblCustomerAddress.setForeground(new Color(220, 20, 60));
		lblCustomerAddress.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblCustomerAddress.setBounds(472, 101, 170, 20);
		mainPanel.add(lblCustomerAddress);
		
		tfCustomerName = new JTextField();
		tfCustomerName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfCustomerName.setColumns(10);
		tfCustomerName.setBorder(UIManager.getBorder("TextField.border"));
		tfCustomerName.setBounds(20, 131, 200, 25);
		mainPanel.add(tfCustomerName);
		
		tfCustomerMob = new JTextField();
		tfCustomerMob.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfCustomerMob.setColumns(10);
		tfCustomerMob.setBorder(UIManager.getBorder("TextField.border"));
		tfCustomerMob.setBounds(254, 131, 200, 25);
		mainPanel.add(tfCustomerMob);
		
		tfCustomerAddress = new JTextField();
		tfCustomerAddress.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tfCustomerAddress.setColumns(10);
		tfCustomerAddress.setBorder(UIManager.getBorder("TextField.border"));
		tfCustomerAddress.setBounds(485, 131, 218, 25);
		mainPanel.add(tfCustomerAddress);
		
		btnAddToBill = new JButton("Add to Bill");
		btnAddToBill.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addToBillClicked();
			}
		});
		btnAddToBill.setForeground(new Color(0, 0, 51));
		btnAddToBill.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnAddToBill.setBorder(new LineBorder(new Color(250, 235, 215), 1, true));
		btnAddToBill.setBackground(new Color(245, 222, 179));
		btnAddToBill.setBounds(173, 185, 140, 30);
		mainPanel.add(btnAddToBill);
		
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
		btnReset.setBounds(422, 185, 100, 30);
		mainPanel.add(btnReset);
		
		lblBooksList = new JLabel("Books List");
		lblBooksList.setHorizontalAlignment(SwingConstants.CENTER);
		lblBooksList.setForeground(new Color(165, 42, 42));
		lblBooksList.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblBooksList.setBounds(310, 230, 110, 20);
		mainPanel.add(lblBooksList);
		
		scrollPane_Book = new JScrollPane();
		scrollPane_Book.setBounds(30, 260, 700, 390);
		mainPanel.add(scrollPane_Book);
		
		bookstable = new JTable();
		bookstable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bookstableMouseClicked();
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
		scrollPane_Book.setViewportView(bookstable);
		
		lblBooksBill = new JLabel("Books Bill");
		lblBooksBill.setHorizontalAlignment(SwingConstants.CENTER);
		lblBooksBill.setForeground(new Color(165, 42, 42));
		lblBooksBill.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblBooksBill.setBounds(995, 10, 110, 20);
		mainPanel.add(lblBooksBill);
		
		scrollPane_Bill = new JScrollPane();
		scrollPane_Bill.setBounds(775, 40, 500, 500);
		mainPanel.add(scrollPane_Bill);
		
		billText = new JTextArea();
		billText.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		scrollPane_Bill.setViewportView(billText);
		
		lblPayment = new JLabel("Rs. ");
		lblPayment.setHorizontalAlignment(SwingConstants.CENTER);
		lblPayment.setForeground(new Color(165, 42, 42));
		lblPayment.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblPayment.setBounds(955, 550, 150, 20);
		mainPanel.add(lblPayment);
		
		btnPrintBill = new JButton("Print Bill");
		btnPrintBill.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				printBill();
			}
		});
		btnPrintBill.setForeground(new Color(0, 0, 51));
		btnPrintBill.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnPrintBill.setBorder(new LineBorder(new Color(250, 235, 215), 1, true));
		btnPrintBill.setBackground(new Color(245, 222, 179));
		btnPrintBill.setBounds(960, 590, 140, 30);
		mainPanel.add(btnPrintBill);
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
	
	int bid;
	private void updateBook() {
		int newQuantity = stock - Integer.valueOf(tfQuantity.getText());
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstoredb","root","Pass@123");
			String query = "update booktbl set Quantity="+newQuantity+" where BId="+bid;
			Statement update = con.createStatement();
			update.executeUpdate(query);
			JOptionPane.showMessageDialog(this,"Book Updated");
			displayBooks();
		} catch (Exception e2) {
			e2.printStackTrace();					
		}
	}
	
	int stock = 0;
	private void bookstableMouseClicked() {
		model = (DefaultTableModel)bookstable.getModel();
		int myIndex = bookstable.getSelectedRow();
		bid = Integer.valueOf(model.getValueAt(myIndex, 0).toString());
		tfBookName.setText(model.getValueAt(myIndex, 1).toString());
		stock = Integer.valueOf(model.getValueAt(myIndex, 4).toString());
		tfPrice.setText(model.getValueAt(myIndex, 5).toString());
	}
	
	int i=0,total=0,grandTotal=0;
	private void addToBillClicked() {
		if(tfQuantity.getText().isEmpty() || tfPrice.getText().isEmpty() || tfBookName.getText().isEmpty() || tfCustomerName.getText().isEmpty() || tfCustomerMob.getText().isEmpty() || tfCustomerAddress.getText().isEmpty()) 
		{
			JOptionPane.showMessageDialog(this,"Missing Information");
		}else if(Integer.valueOf(tfQuantity.getText())>stock) 
		{
			JOptionPane.showMessageDialog(this,"No Enough Books In Stock");
		}else {
			i++;
			total = Integer.valueOf(tfPrice.getText())*Integer.valueOf(tfQuantity.getText());
			if(i==1)
			{
				billText.setText(billText.getText()+"\n	==================== BOOK STORE ====================\n\n"
						+ "----------------------------------------------------------------------------------------------------------------------------\n"
						+" Customer Name:  "+tfCustomerName.getText()+"\n Customer Mob.:  "+tfCustomerMob.getText()+"\n Customer Address:  "+tfCustomerAddress.getText()
						+ "\n----------------------------------------------------------------------------------------------------------------------------\n"
						+"  NUM                BOOK                                    PRICE*QUANTITY                    TOTAL \n"
						+ "----------------------------------------------------------------------------------------------------------------------------\n"
						+"   "+i+"          "+tfBookName.getText()+"                            "+tfPrice.getText()+"*"+tfQuantity.getText()+"                                "+total+"\n");
			}else {
				billText.setText(billText.getText()+"   "+i+"          "+tfBookName.getText()+"                            "+tfPrice.getText()+"*"+tfQuantity.getText()+"                                "+total+"\n");
			}
			grandTotal = grandTotal + total;
			lblPayment.setText("Rs. "+grandTotal);
			updateBook();
		}
	}
	
	boolean printStatus;
	private void printBill() {
		try {
			billText.setText(billText.getText()
					+ "\n\n----------------------------------------------------------------------------------------------------------------------------\n"
					+ "\t Date and Time:  " + getDateTime()
					+"\t Total Payment = "+grandTotal+"\n"
					+ "----------------------------------------------------------------------------------------------------------------------------\n"
					+ "\n\n\n\n\n----------------------------------------------------------------------------------------------------------------------------\n"
					+"                                                THANK YOU FOR YOUR ORDER.........!\n                                                                VISIT AGAIN..!"
					+ "\n----------------------------------------------------------------------------------------------------------------------------\n");
			
			printStatus = billText.print();
		} catch (PrinterException e) {
			e.printStackTrace();
		}
		if(printStatus) {
			saveBill();
			countRow();
		}
		reset();
	}
	
	private String getDateTime()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy    HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
	
	private void saveBill() {
		if(tfBillNumber.getText().isEmpty() || tfCustomerName.getText().isEmpty() ) {
			JOptionPane.showMessageDialog(this,"Missing Information");
		} else {
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstoredb","root","Pass@123");
				PreparedStatement add = con.prepareStatement("insert into billtbl values(?,?,?,?)");
				add.setInt(1, Integer.valueOf(tfBillNumber.getText()));
				add.setString(2, lblUserProfile.getText());
				add.setString(3, tfCustomerName.getText());
				add.setInt(4, grandTotal); 
				add.executeUpdate();
				JOptionPane.showMessageDialog(this,"Bill Saved");
			} catch (Exception e2) {
				e2.printStackTrace();					
			}
		}
	}
	
	private void countRow() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstoredb","root","Pass@123");
			st = con.createStatement();
			rs = st.executeQuery("select * from billtbl order by BNum DESC LIMIT 1");
			if(rs.next()==false)
			{
				tfBillNumber.setText("1");
			}else {
				int ID = rs.getInt(1)+1;
				tfBillNumber.setText(ID+"");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void reset() {
		tfBookName.setText("");
		tfPrice.setText("");
		tfQuantity.setText("");
		tfCustomerName.setText("");
		tfCustomerMob.setText("");
		tfCustomerAddress.setText("");
		billText.setText("");
		i=0;
		lblPayment.setText("Rs.");
	}
	
	private void logout() {
		new Login().setVisible(true);
		this.dispose();
	}
}
