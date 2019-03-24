package windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import daoImpl.CardDaoImplExist;
import daoImpl.DeckDaoImplMongoDB;
import iDao.ICard;
import iDao.IDeck;
import model.Card;
import model.Deck;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.event.ActionEvent;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;

public class configurationDeck extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private List<Card> deck = new ArrayList<Card>();
	private ICard cardDaoImplExist = new CardDaoImplExist();
	private IDeck deckDaoImplMongo = new DeckDaoImplMongoDB();
	private DefaultListModel cardModel = new DefaultListModel();
	private DefaultListModel deckModel = new DefaultListModel();
	private Hashtable<String, Card> hashCards = new Hashtable<String, Card>();
	private int deckValue = 0;
	private boolean isNew = true;
	private List<Card> cards;
	private JButton btnLoadDeck;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnRdnDeck;
	private JButton btnSaveDecl;
	private JButton btnLoadCards;
	private JScrollPane scrollableList;

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
		scrollableList = new JScrollPane(listCard);
		listCard.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JList listDeck = new JList();
		listDeck.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btnLeft = new JButton("<--");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<String> seleccionCard = listDeck.getSelectedValuesList();
				Iterator it = seleccionCard.iterator();
				String element="";
				while ( it.hasNext()) {
					element=(String) it.next();
					
					deckValue = deckValue - hashCards.get(element).getValue();
					
					deckModel.removeElement(element);
					cardModel.addElement(element);
					
					System.out.println(deckValue);
					
				}
				listCard.setModel(cardModel);
				
			}
		});
		
		btnRight = new JButton("-->");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<String> seleccionCard = listCard.getSelectedValuesList();
				Iterator it = seleccionCard.iterator();
				String element="";
				while ( it.hasNext()) {
					element=(String) it.next();
					deckValue = deckValue + hashCards.get(element).getValue();
					if(deckValue <= 20) {
						cardModel.removeElement(element);
						deckModel.addElement(element);
						
					} else {
						deckValue = deckValue - hashCards.get(element).getValue();
						 JOptionPane.showMessageDialog(null,"El valor del mazo no puede ser superior ha 20.\n" +
								 "Valor: " + deckValue);  
					}
					System.out.println(deckValue);
				}
				listDeck.setModel(deckModel);
			}
		});
		
		btnLoadCards = new JButton("Load Cards");
		btnLoadCards.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards = cardDaoImplExist.getAllCards();
				for (Card card : cards) {
					cardModel.addElement(card.toString());
					hashCards.put(card.toString(), card);
				}
				
				listCard.setModel(cardModel);
				
				btnLoadDeck.setEnabled(true);
				btnLeft.setEnabled(true);
				btnRight.setEnabled(true);
				btnRdnDeck.setEnabled(true);
				btnSaveDecl.setEnabled(true);
				btnLoadCards.setEnabled(true);
			}
		});
		
		btnRdnDeck = new JButton("Random Deck");
		btnRdnDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				deckValue = 0;
				deck = new ArrayList<Card>();
				deckModel.removeAllElements();
				cardModel.removeAllElements();
				
				for (Card card : cards) {
					cardModel.addElement(card.toString());
					hashCards.put(card.toString(), card);
				}
				
				while(deckValue < 21) {
					int n = new Random().nextInt(cards.size());
					
					Card newCard = cards.get(n);
					deckValue = deckValue + newCard.getValue();
					
					if(deckValue < 21) {
						cardModel.removeElement(newCard.toString());
						listCard.setModel(cardModel);
						deckModel.addElement(newCard.toString());
						listDeck.setModel(deckModel);
						
						
					}else {
						deckValue = deckValue - newCard.getValue();
						break;
					}
				}
				
				System.out.println(deckValue);
			}
		});
		
		JLabel lblCards = new JLabel("Cards");
		
		JLabel lblDecks = new JLabel("Decks");
		
		btnSaveDecl = new JButton("Save Deck");
		btnSaveDecl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
					String deckName = textField.getText();
					System.out.println("Hello" + deckName);
					
					for(int x = 0; x < deckModel.getSize(); x++) {
						deck.add(hashCards.get(deckModel.get(x)));
					}
					
					Deck newDeck = new Deck(deckName, deckValue, deck);
					
					
					if(!deckName.isEmpty() && !deckModel.isEmpty()) {
						if(isNew && deckDaoImplMongo.getDeckByName(deckName) == null) {
							deckDaoImplMongo.addNewDeck(newDeck);
							System.out.println("allo");
						} else if (!isNew && deckDaoImplMongo.getDeckByName(deckName) != null){
							deckDaoImplMongo.updateDeck(newDeck);
							System.out.println("Jello");
						} else if(isNew && deckDaoImplMongo.getDeckByName(deckName) != null) {
							JOptionPane.showMessageDialog(null,"El nombre " + deckName + "ya existe"); 
						}
						
						deckModel.removeAllElements();
						cardModel.removeAllElements();
						listDeck.setModel(deckModel);
						
						List<Card> cards = cardDaoImplExist.getAllCards();
						for (Card card : cards) {
							cardModel.addElement(card.toString());
						}
						
						listCard.setModel(cardModel);
						
						isNew = true;
						deck = new ArrayList<Card>();
						textField.setText("");
						deckValue = 0;
						
					} else {
						JOptionPane.showMessageDialog(null,"El nombre o la lista de cartas no pueden estar vacios"); 
					}
					
					
			}
		});
		
		JLabel lblNameDeck = new JLabel("Name Deck");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		btnLoadDeck = new JButton("Load Deck");
		btnLoadDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				deck = new ArrayList<Card>();
				isNew = false;
				deckModel.removeAllElements();
				cardModel.removeAllElements();
				String name = textField.getText();
				Deck currentDeck = deckDaoImplMongo.getDeckByName(name);
				
				List<Card> cards = cardDaoImplExist.getAllCards();
				for (Card card : cards) {
					cardModel.addElement(card.toString());
				}
				
				if(currentDeck != null) {
					deckValue = currentDeck.getDeckValue();
					for (Card card : currentDeck.getDeck()) {
						deckModel.addElement(card.toString());
						cardModel.removeElement(card.toString());
					}
					
					listCard.setModel(cardModel);
					listDeck.setModel(deckModel);
				}else {
					JOptionPane.showMessageDialog(null,"El mazo no existe");
				}
			}
		});
		
		btnLoadDeck.setEnabled(false);
		btnLeft.setEnabled(false);
		btnRight.setEnabled(false);
		btnRdnDeck.setEnabled(false);
		btnSaveDecl.setEnabled(false);
		
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
