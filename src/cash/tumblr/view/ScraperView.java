package cash.tumblr.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

import cash.tumblr.controller.ScrapeController;
import cash.utils.OpenBrowser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ScraperView extends JFrame {
	public final static ScraperView INSTANCE = new ScraperView(ScrapeController.INSTANCE);

	private JPanel contentPane;
	private JTextField urlField;
	private final Action action = new SwingAction();
	private ScrapeController controller;
	private JLabel lblNewLabel;
	private JButton addToList;
	private JButton btnStart;
	/**
	 * Launch the application.
	 */
	private static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScraperView frame = INSTANCE;
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
	private ScraperView(ScrapeController controller) {
		this.controller = controller;
		setTitle("Tumblr Scraper by Matessim@BHW\r\n");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ScraperView.class.getResource("/resources/scraper.jpg")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 357, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		urlField = new JTextField();
		urlField.setBounds(106, 25, 119, 20);
		contentPane.add(urlField);
		urlField.setColumns(10);
		
		final JSpinner pageCount = new JSpinner();
		pageCount.setModel(new SpinnerNumberModel(1, 1, 5000, 1));
		pageCount.setBounds(10, 25, 86, 20);
		contentPane.add(pageCount);
		
		final JSpinner threadsSpinner = new JSpinner();
		threadsSpinner.setModel(new SpinnerNumberModel(10, 1, 100, 1));
		threadsSpinner.setBounds(13, 81, 53, 20);
		contentPane.add(threadsSpinner);
		
		JLabel lblPages = new JLabel("Pages");
		lblPages.setBounds(10, 11, 46, 14);
		contentPane.add(lblPages);
		
		JLabel lblThreads = new JLabel("Threads");
		lblThreads.setBounds(10, 58, 56, 24);
		contentPane.add(lblThreads);
		
		JLabel lblUrl = new JLabel("URL");
		lblUrl.setBounds(106, 11, 46, 14);
		contentPane.add(lblUrl);
		
		addToList = new JButton("Add");
		
		addToList.setBounds(235, 24, 89, 23);
		contentPane.add(addToList);
		
		JButton btnDonate = new JButton("");
		btnDonate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				OpenBrowser.openURL("http://www.blackhatworld.com/blackhat-seo/private.php?do=newpm&u=23738");
			}
		});
		btnDonate.setIcon(new ImageIcon(ScraperView.class.getResource("/resources/donate.gif")));
		btnDonate.setBounds(224, 347, 127, 58);
		contentPane.add(btnDonate);
		
		JLabel lblPmMeTo = new JLabel("PM to Donate,i can accept: PayPal,\r\n\r\n");
		lblPmMeTo.setFont(new Font("Arial", Font.BOLD, 10));
		lblPmMeTo.setBounds(10, 347, 206, 20);
		contentPane.add(lblPmMeTo);
		
		btnStart = new JButton("Start");
	
		btnStart.setBounds(73, 300, 153, 47);
		contentPane.add(btnStart);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(73, 62, 251, 221);
		contentPane.add(scrollPane);
		
		final JTextPane listTargets = new JTextPane();
		scrollPane.setViewportView(listTargets);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setBounds(10, 264, 46, 14);
		contentPane.add(lblStatus);
		
		lblNewLabel = new JLabel("Idle");
		lblNewLabel.setBounds(10, 281, 331, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblTargets = new JLabel("Targets");
		lblTargets.setBounds(116, 38, 79, 34);
		contentPane.add(lblTargets);
		
		JLabel lblPayoneerskrillWuAlertpay = new JLabel("Payoneer,Skrill, W/U, AlertPay and Bitcoins.");
		lblPayoneerskrillWuAlertpay.setFont(new Font("Arial", Font.BOLD, 10));
		lblPayoneerskrillWuAlertpay.setBounds(10, 360, 215, 21);
		contentPane.add(lblPayoneerskrillWuAlertpay);
		
		JLabel lblThankYouFor = new JLabel("Thank you for your support BHW!");
		lblThankYouFor.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblThankYouFor.setBounds(10, 380, 202, 25);
		contentPane.add(lblThankYouFor);
		
		JLabel lblPmButton = new JLabel("PM Button");
		lblPmButton.setBounds(248, 324, 76, 20);
		contentPane.add(lblPmButton);
	
		addToList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String target = urlField.getText().replace("http://", "")
						.replace("https://", "").replace(":", "")+ ":" + Integer.toString((int)pageCount.getValue());
				listTargets.setText(listTargets.getText()  + target+ "\n");
			}
		});
		
	
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnStart.setEnabled(false);
				Scanner sc = new Scanner(listTargets.getText());
				List<String> strings = new ArrayList<>();
				while(sc.hasNextLine()){
					strings.add(sc.nextLine());
				}
				Executor e = Executors.newSingleThreadExecutor();
				e.execute(new UpdaterRunnable(strings, (int)threadsSpinner.getValue()));
				btnStart.setEnabled(true);
			}
		});
	}
	
	public void update(){
		this.lblNewLabel.setText(controller.status());
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	public void setStatus(String status){
		this.lblNewLabel.setText(status);
	}
	
	public JButton getAddToList() {
		return addToList;
	}
	public JButton getBtnStart() {
		return btnStart;
	}
}
