package program;
import program.DataNode;
import program.LibraryInterface;
import program.FileProcessor;
import program.DeckDb;

//Java Imports
import java.util.*;
import java.io.*;

public class Organizer {

	// Put new into constructor
	FileProcessor fp = new FileProcessor();
        LibraryInterface library = new LibraryInterface();
	TreeMap<String, DeckDb> decks = new TreeMap<String, DeckDb>();
	TreeMap<String, DeckNode> requestMap = new TreeMap<String, DeckNode>();
	ArrayList<String> filePtrs = new ArrayList<String>();

	public static void main(String args[]) {

		Organizer core = new Organizer();
		core.readData();
		core.menu();
	}

	// Create menu class? 
	public void menu() {
		String input = "start";
		int option;
		int optionValue;
		while(!input.equals("exit")) {
			input = this.getUserInput("Options: lib get build new change");
			// Use map of string to functions
			// Use as much exceptions as much as I can
			if(input.equals("lib")) {
				library.printAllData();
			} else if(input.equals("get")) {
				this.printListOfDecks();
				input = this.getUserInput("Deck Name:");
				if(!decks.containsKey(input)) {
					System.out.println("Deck nonexistant");
					continue;
				}
				decks.get(input).printAllData();
			} else if(input.equalsIgnoreCase("build")) {
				this.printListOfDecks();
				input = this.getUserInput("Input Deck Name:");
				if(!decks.containsKey(input)) {
					System.out.println("Deck nonexistant");
					continue;
				}
				decks.get(input).printAllData();
				requestMap = decks.get(input).getNeededMap();
				debugPrintNeededData(requestMap);
				assembleDeck(input);
				debugPrintNeededData(requestMap);
			} else if(input.equalsIgnoreCase("new")) {
				this.addDeck();
			} else if(input.equalsIgnoreCase("change")) {

				input = this.getUserInput("Deck to Edit:");
				int num;
				if(!decks.containsKey(input)) {
					System.out.println("Deck doesn't exist");
					continue;
				} 
				String id = this.getUserInput("Card to Edit");
				if(!library.containsCard(id)) {
					System.out.println("Card not in library");
					continue;
				}
				try {
					option = Integer.parseInt(this.getUserInput("1.Add\n2.Count\n3.Required"));
				} catch (NumberFormatException e) {
					continue;
				}
				try {
					optionValue = Integer.parseInt(this.getUserInput("Enter Value:"));
				} catch (NumberFormatException e) {
					continue;
				}
				this.changeCard(input,id, option, optionValue);

			} else	{
				if(!input.equals("exit"))
					System.out.println("Incorrect input");
			}
//			System.out.println("\n");
		}
	}

	// Create target deck's request map, and take requested cards from every other deck and collection.
	public void assembleDeck(String target) {
		decks.forEach((k,v) -> {
			if(k.equals(target)) {
				return;
			}
		        System.out.println(v.getName());
			Iterator<SortedMap.Entry<String,DeckNode>> iterator = requestMap.entrySet().iterator();
			// Maybe use for (range a : b)
			while (iterator.hasNext()) {
				SortedMap.Entry<String,DeckNode> entry = iterator.next();
				String cardId = entry.getKey();
				DeckNode cardCount = entry.getValue();
				if(requestMap.get(cardId).count < requestMap.get(cardId).required && v.checkForCard(cardId)) {
					int num = v.getCardCount(cardId);
					int req = cardCount.required - cardCount.count;
					System.out.print( num + " of " + cardId + " and we request " + req);
					if(req > v.getCardCount(cardId)) {
						req = v.getCardCount(cardId);
						System.out.print(" Not enough. Adjusting request to " + req);
					}
					System.out.print("\n");
					v.changeCount(cardId,0 - req);
					decks.get(target).changeCount(cardId,req);
					requestMap.get(cardId).count += req;
				}
			}
		});
		
	}

	// Auxillary proc for menu. NOTE: Implement exception checks
	public String getUserInput(String prompt) {
		System.out.println(prompt);
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		return input; 
	}
	public void addDeck() {
		String input = this.getUserInput("Enter Deck Name:");
		decks.put(input, new DeckDb(input));

	}

	public void changeCard(String deckName, String id, int func, int value) {
		// 1 = Add card to deck with required
		// 2 = Change count
		// 3 = Change required
		switch(func) {
			case 1:
				System.out.print("Adding card to deck");
				decks.get(deckName).addDeckNode(id, 0, value);
				break;
			case 2:		
				System.out.print("Changing card count");
				int change = decks.get(deckName).changeCount(id, value);
				break;
			case 3:
				decks.get(deckName).setRequired(id, value);
				break;
		}
	}
	// Read file list and import all data
	public void readData() {

		FileProcessor processor = new FileProcessor();
		filePtrs = processor.readFileList("C:\\Users\\chanw\\Desktop\\Java\\data\\files.dat");
		System.out.println("Reading files");
		filePtrs.forEach((s) -> {
				TreeMap<String,String> rawData = new TreeMap<String,String>();
				if(s.contains("library.csv")) {
					System.out.println("Library is: " + s);
					processor.readData(rawData, "C:\\Users\\chanw\\Desktop\\Java\\data\\library.csv" );
					library.putRawData(rawData);
				} else  {
					System.out.println("Reading deck " + s);
					String fName = new String();
					fName = processor.readData(rawData, s);
					System.out.println("Deck fName is" + fName);
					DeckDb temp = new DeckDb();
					temp.putRawData(rawData);
					temp.setName(fName);
					decks.put(fName, temp);
				}
		});
	}

	public void debugPrintMapData(TreeMap<String, String> rawData) {
		Iterator<SortedMap.Entry<String,String>> iterator = rawData.entrySet().iterator();
		while (iterator.hasNext()) {
			SortedMap.Entry<String,String> entry = iterator.next();
			String key = entry.getKey();
			String data = entry.getValue();
			System.out.println(key + " " + data);

		}
	}

	public void debugPrintNeededData(TreeMap<String, DeckNode> rawData) {

		System.out.println("Debug Printing Request List");
		Iterator<SortedMap.Entry<String,DeckNode>> iterator = rawData.entrySet().iterator();
		while (iterator.hasNext()) {
			SortedMap.Entry<String,DeckNode> entry = iterator.next();
			String key = entry.getKey();
			DeckNode data = entry.getValue();
			System.out.println("Acquired " + data.count + " out of " + data.required + " of " + key);
		}
	}
	public void printListOfDecks() {
		System.out.println("Decks Available");
		decks.forEach((k,v) -> { System.out.println(v.getName());});
	}
}

