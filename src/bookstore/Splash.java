package bookstore;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;

public class Splash extends JFrame {

	private JPanel contentPane;
	private static JProgressBar progressBar;
	private static JLabel lblPercentage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
				
		Splash splashFrame = new Splash();
		splashFrame.setVisible(true);
		
		try {	
				for(int x=0;x<=100;x++)
				{
					Splash.progressBar.setValue(x);
					Thread.sleep(50);
					Splash.lblPercentage.setText(Integer.toString(x)+"%");
				}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Login().setVisible(true);
		splashFrame.dispose();
	}

	/**
	 * Create the frame.
	 */
	public Splash() {
		setUndecorated(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Splash.class.getResource("/images/magazine.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 500, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(220, 20, 60));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblheading = new JLabel("Book Store Management system");
		lblheading.setForeground(new Color(255, 255, 255));
		lblheading.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblheading.setHorizontalAlignment(SwingConstants.CENTER);
		lblheading.setBounds(100, 23, 300, 32);
		contentPane.add(lblheading);
		
		JLabel lblImg = new JLabel("");
		lblImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblImg.setIcon(new ImageIcon(Splash.class.getResource("/images/book_logo.png")));
		lblImg.setBounds(125, 88, 250, 194);
		contentPane.add(lblImg);
		
		JLabel lblstart = new JLabel("starting system");
		lblstart.setForeground(new Color(255, 255, 255));
		lblstart.setFont(new Font("Calibri Light", Font.BOLD, 16));
		lblstart.setBounds(25, 338, 113, 32);
		contentPane.add(lblstart);
		
		lblPercentage = new JLabel("");
		lblPercentage.setFont(new Font("Calibri Light", Font.BOLD, 16));
		lblPercentage.setForeground(new Color(255, 255, 255));
		lblPercentage.setBounds(137, 338, 91, 32);
		contentPane.add(lblPercentage);
		
		progressBar = new JProgressBar();
		progressBar.setForeground(new Color(50, 205, 50));
		progressBar.setBounds(0, 385, 500, 15);
		contentPane.add(progressBar);
	}

}
