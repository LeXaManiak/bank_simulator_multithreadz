import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LogSaver {
	String filename;
	ArrayList<?> arraylist;

	public LogSaver(String filename, ArrayList<?> arraylist) {
		this.filename = filename;
		this.arraylist = arraylist;
	}

	public void savelog() {
		FileWriter filewriter;
		try {
			filewriter = new FileWriter(filename);

			BufferedWriter writer = new BufferedWriter(filewriter);

			for (Object obj : arraylist) {
				
				writer.write("\n\r");
				if (obj!=null) 	writer.write(obj.toString());

			}

			writer.flush();
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block

			return;
		}

	}
}
