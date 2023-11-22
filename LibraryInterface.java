package program;
import program.Card;
import program.FileProcessor;
import program.DbInterface;

//import java.io.*;
import java.io.IOException;
import java.nio.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

class LibraryInterface extends DbInterface {
	TreeMap<String,String> library = new TreeMap<String,String>();
	public void putRawData(TreeMap<String,String> rawData) {
		library.putAll(rawData);
	}
	public boolean containsCard(String id) {
		return library.containsKey(id);
	}
	public void printAllData() {
		Iterator<SortedMap.Entry<String,String>> iterator = library.entrySet().iterator();
		while (iterator.hasNext()) {
			SortedMap.Entry<String,String> entry = iterator.next();
			String key = entry.getKey();
			String data = entry.getValue();
			System.out.println(key + " " + data);
		}
	}

}

