import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;

public class IndexFiles {

	private static String indexPath = "D:\\lucene\\index";

	public static void buildIndex() throws IOException {
		for (final File f : new File(indexPath).listFiles())
			f.delete();
		System.out.println("Deleted old index.");
		IndexWriter indexWriter = new IndexWriter(FSDirectory.getDirectory(indexPath),
				new StandardAnalyzer(), IndexWriter.MaxFieldLength.LIMITED);
		final File folder = new File("D:\\lucene\\corpus");
		ArrayList<String> textList = new ArrayList<String>();
		ArrayList<String> fileList = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			BufferedReader br = new BufferedReader(new FileReader(folder.getPath() + "\\"
					+ fileEntry.getName()));
			String everything = "";
			try {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();
				while (line != null) {
					sb.append(line);
					sb.append("\n");
					line = br.readLine();
				}
				everything = sb.toString();
			} finally {
				br.close();
			}
			textList.add(everything);
			fileList.add(fileEntry.getName());
		}
		for (int i = 0; i < textList.size(); i++) {
			Document doc = new Document();
			doc.add(new Field("text", textList.get(i), Field.Store.YES, Field.Index.ANALYZED));
			doc.add(new Field("name", fileList.get(i), Field.Store.YES, Field.Index.ANALYZED));
			indexWriter.addDocument(doc);
			System.out.println(fileList.get(i) + " added to index.");
		}
		indexWriter.close();
	}
}