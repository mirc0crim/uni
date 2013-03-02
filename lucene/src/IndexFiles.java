import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;

public class IndexFiles {

	public IndexFiles() {
	}

	public static void buildIndex() throws IOException {
		IndexWriter indexWriter = new IndexWriter(FSDirectory.getDirectory("D:\\lucene"),
				new StandardAnalyzer(), IndexWriter.MaxFieldLength.LIMITED);
		String[] texts = new String[] { "hello world", "hello everybody", "bye" };
		for (String text : texts) {
			Document doc = new Document();
			doc.add(new Field("text", text, Field.Store.YES, Field.Index.ANALYZED));
			indexWriter.addDocument(doc);
		}
		indexWriter.close();
	}
}
