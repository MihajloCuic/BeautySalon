package obradaPodataka;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderWriter {
	public static List<String[]> read(String putanja) throws IOException {
		List<String[]> data = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(putanja))){
			String linija;
			while((linija = reader.readLine()) != null) {
				String[] podatak = linija.split("\\|");
				data.add(podatak);
			}
		}
		return data;
	}
	
	public static void write(String putanja, List<String[]> data) throws IOException {
		File file = new File(putanja);
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			for(String[] podatak: data) {
				String linija = String.join("|", podatak);
				writer.write(linija);
				writer.newLine();
			}
		}
	}
}
