package program;
import program.Card;
import program.FileProcessor;
import program.DbInterface;
import program.DeckNode;

//import java.io.*;
import java.io.IOException;
import java.nio.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import java.lang.Integer;

class DeckDb extends DbInterface {
	private String deckName = new String();
	private TreeMap<String,DeckNode> deck = new TreeMap<String,DeckNode>();

	DeckDb() {}
	DeckDb(String name) {
		deckName = name;
	}

	public void putRawData(TreeMap<String,String> rawData) {
		Iterator<SortedMap.Entry<String,String>> iterator = rawData.entrySet().iterator();
		while (iterator.hasNext()) {
			SortedMap.Entry<String,String> entry = iterator.next();
			String key = entry.getKey();
			String[] data = entry.getValue().split(",");
			int count = Integer.parseInt(data[0]);
			int required = Integer.parseInt(data[1]);
			deck.put(key,new DeckNode(count,required));

		}
	}
	public void setName(String name) {
		deckName = name;
	}

	public String getName() {
		return deckName;
	}
	public boolean checkForCard(String id) {
		return (deck.containsKey(id) && deck.get(id).count > 0);
	}
	public int getCardCount(String id) {
		return deck.get(id).count;
	}
	public int changeCount(String id, int change) {
		DeckNode temp = deck.get(id);
		if(temp.count + change > temp.required) {
			System.out.println("Change exceeding required");
			change = temp.required - temp.count;
		}
		if(temp.count + change < 0) {
			System.out.println("Change under 0");
			change = -1*temp.count;
		}
		deck.get(id).count += change;
		return change;
	}

	public void setRequired(String id, int req) {
		if(req > 0) {
			deck.get(id).required = req;
		} else {
			this.removeDeckNode(id);
		}
	}

	public void addDeckNode(String key, int count, int required) {
		deck.put(key, new DeckNode(count, required));
	}

	public void removeDeckNode(String id) {
		deck.remove(id);
	}

	public TreeMap<String, DeckNode> getNeededMap() {
		TreeMap<String,DeckNode> neededMap = new TreeMap<String, DeckNode>();
		Iterator<SortedMap.Entry<String,DeckNode>> iterator = deck.entrySet().iterator();
		while (iterator.hasNext()) {
			SortedMap.Entry<String,DeckNode> entry = iterator.next();
			String key = entry.getKey();
			DeckNode data = entry.getValue();
			if(data.count < data.required) {
				neededMap.put(key, new DeckNode(0, data.required - data.count));
			}
		}
		return neededMap;

	}
	public void printAllData() {

		System.out.println("Printing Deck" + deckName);
		Iterator<SortedMap.Entry<String,DeckNode>> iterator = deck.entrySet().iterator();
		while (iterator.hasNext()) {
			SortedMap.Entry<String,DeckNode> entry = iterator.next();
			String key = entry.getKey();
			DeckNode data = entry.getValue();
			System.out.println(key + " " + data.count + " out of " + data.required);
		}
	}

}

