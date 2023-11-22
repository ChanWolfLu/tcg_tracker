
package program;
import program.DataNode;

//JAVA
import java.io.IOException;
import java.nio.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class FileProcessor  {

	public ArrayList<String> readFileList(String pathName) {
		ArrayList<String> filePtrs = new ArrayList<String>();
		Path path = Paths.get(pathName);
		try (Stream<String> lines = Files.lines(path)) {
			lines
				.forEach(s -> {
					filePtrs.add(s);
				});
		} catch (IOException ex) {
		}
		return filePtrs;
	}


	public String readData(TreeMap<String,String> rawMap,String pathName) {
//		Path path = Paths.get("C:\\Users\\chanw\\Desktop\\Java\\data\\library.dat");
		Path path = Paths.get(pathName);

		try (Stream<String> lines = Files.lines(path)) {
			lines
				.forEach(s -> {
					String[] parts = s.split(";");
					rawMap.put(parts[0],parts[1]);
				});
			return path.getFileName().toString();
		} catch (IOException ex) {
		}
		return "Error reading namme";
	}
}
