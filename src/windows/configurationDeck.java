package windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import daoImpl.CardDaoImplExist;
import iDao.ICard;
import model.Card;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

public class configurationDeck extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private ICard cardDaoImplExist = new CardDaoImplExist();
	private DefaultListModel cardModel = new DefaultListModel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					configurationDeck frame = new configurationDeck();
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
	public configurationDeck() {
		setResizable(false);
		setTitle("Configurator Deck");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JList listCard = new JList();
		
		JList listDeck = new JList();
		
		JButton btnLeft = new JButton("<--");
		
		JButton btnRight = new JButton("-->");
		
		JButton btnLoadCards = new JButton("Load Cards");
		btnLoadCards.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Card> cards = cardDaoImplExist.getAllCards();
				for (Card card : cards) {
					cardModel.addElement(card.toString());
				}
				
				listCard.setModel(cardModel);
				
			}
		});
		
		JButton btnRdnDeck = new JButton("Random Deck");
		
		JLabel lblCards = new JLabel("Cards");
		
		JLabel lblDecks = new JLabel("Decks");
		
		JButton btnSaveDecl = new JButton("Save Deck");
		
		JLabel lblNameDeck = new JLabel("Name Deck");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JButton btnLoadDeck = new JButton("Load Deck");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(54)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(listCard, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
						.addComponent(lblCards, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
						.addComponent(btnLoadCards, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnLeft, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRight, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnRdnDeck, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
								.addComponent(listDeck, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
							.addGap(45))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblDecks, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnLoadDeck, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(textField)
						.addComponent(btnSaveDecl, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
						.addComponent(lblNameDeck, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(48)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCards, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNameDeck)
								.addComponent(lblDecks, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnLoadDeck))
								.addComponent(listDeck, GroupLayout.PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE)
								.addComponent(listCard, GroupLayout.PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(221)
							.addComponent(btnLeft)
							.addGap(27)
							.addComponent(btnRight)))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnLoadCards)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnRdnDeck)
							.addComponent(btnSaveDecl, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(67, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
