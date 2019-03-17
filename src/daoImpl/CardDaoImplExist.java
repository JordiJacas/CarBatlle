package daoImpl;

import java.util.ArrayList;
import java.util.List;

import config.AccesDBExist;
import iDao.ICard;
import model.Card;
import org.exist.xmldb.XQueryService;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
public class CardDaoImplExist implements ICard{
	
	private List<Card> cards;
	private String resourceName = AccesDBExist.getResourceName();

	@Override
	public List<Card> getAllCards(){
		
		cards = new ArrayList<Card>();
		XQueryService service;
		try {
			service = AccesDBExist.connectExistDb();
		
	        String xQuery = "for $x in doc('" + resourceName + "')//card " 
	                + "return data(("
	                + "$x/[@id],"
	                + "$x/name,"
	                + "$x/summonCost,"
	                + "$x/attack,"
	                + "$x/defense,"
	                + "$x/value))"; 
	        
	        System.out.println("Execute xQuery = " + xQuery);
	        
	     // Execute the query, print the result 
	        ResourceSet result = service.query(xQuery); 
	        ResourceIterator i = result.getIterator(); 
	        while (i.hasMoreResources()) {
	        	String card = "";
	        	for (int j = 0; j < 6; j++) {
	        		 Resource r = i.nextResource();
	        		 card = card + r.getContent().toString() + "/";
				}
	
	        	String[] cardString = card.split("/");
	        	cards.add(new Card(Integer.parseInt(cardString[0]), 
	        						cardString[1], 
	        						Integer.parseInt(cardString[2]), 
	        						Integer.parseInt(cardString[3]),
	        						Integer.parseInt(cardString[4]), 
	        						Integer.parseInt(cardString[5])));
	        	card = "";
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cards;
	}

}
