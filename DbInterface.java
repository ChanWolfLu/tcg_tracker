package program;
import program.Card;
import program.FileProcessor;

//import java.io.*;
import java.io.IOException;
import java.nio.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

abstract class DbInterface {
	abstract void putRawData(TreeMap<String,String> rawData); 
	abstract void printAllData();
}

//public class CardDb <T> {
//
//	private SortedMap<String,Object> tracker = new TreeMap<String,Class<T>>();
//	public void addToCardDb(String id, String data) {
//		Object<T> obj = new Object<T>;
//		tracker.put(id,obj(id, data));
//	}
//	public void printData(String id) {
//		System.out.println("Printing card: %s ", getData(id).getString());
//	}
//
//	public String getData(String id) {
//		return tracker.get(id);
//	}
//	public void printDb() {
//		Iterator<SortedMap.Entry<String,Card>> iterator = tracker.entrySet().iterator();
//		while (iterator.hasNext()) {
//			SortedMap.Entry<String,Card> entry = iterator.next();
//			String key = entry.getKey();
//			Class<T> data = entry.getValue();
//			System.out.println("Name " + card.getCardName() + " ID: " + data.printData());
//		}
//	}
//
//	public void readData() {
//		Path path = Paths.get("C:\\Users\\chanw\\Desktop\\Java\\data\\library.dat");
//		try (Stream<String> lines = Files.lines(path)) {
//			lines
//				.forEach(s -> {
//					String[] parts = s.split(",");
//					addToCardDb(parts[0],new Object<T>(parts[0],parts[1]));
//				});
//		} catch (IOException ex) {
//		}
//	}

//}
