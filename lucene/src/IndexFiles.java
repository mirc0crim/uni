import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexFiles {

	private String indexPath = "D:\\lucene\\index";
	private Analyzer analyzer;
	private boolean cat_subcat;

	private boolean verbose = false;

	public IndexFiles(Analyzer a, boolean csc) {
		analyzer = a;
		cat_subcat = csc;
	}

	public void buildIndex(String docPath) throws IOException {
		for (final File f : new File(indexPath).listFiles())
			if (!f.isDirectory())
				f.delete();
		if (verbose)
			System.out.println("Deleted old index.");
		Directory indexDir = FSDirectory.open(new File(indexPath));
		IndexWriterConfig writerConfig = new IndexWriterConfig(Version.LUCENE_36, analyzer);
		writerConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		IndexWriter indexWriter = new IndexWriter(indexDir, writerConfig);
		final File folder = new File(docPath);
		ArrayList<String> textList = new ArrayList<String>();
		ArrayList<String> catList = new ArrayList<String>();
		ArrayList<String> subcatList = new ArrayList<String>();
		ArrayList<String> fileList = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory())
				continue;
			BufferedReader br = new BufferedReader(new FileReader(folder.getPath() + "\\"
					+ fileEntry.getName()));
			String bodyText = "";
			String cat = "";
			String subcat = "";
			try {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();
				while (line != null) {
					if (subcat == "") {
						if (cat == "")
							cat = line;
						else
							subcat = line;
					} else {
						sb.append(line);
						sb.append("\n");
					}
					line = br.readLine();
				}
				if (verbose) {
					System.out.println("cat: " + cat);
					System.out.println("subcat: " + subcat);
					System.out.println("body:\n" + sb.toString());
				}
				if (cat_subcat)
					bodyText = sb.toString();
				else
					bodyText = cat + "\n" + subcat + "\n" + sb.toString();
			} finally {
				br.close();
			}
			catList.add(cat);
			subcatList.add(subcat);
			textList.add(bodyText);
			fileList.add(fileEntry.getName());
		}
		for (int i = 0; i < textList.size(); i++) {
			Document doc = new Document();
			doc.add(new Field("text", textList.get(i), Field.Store.YES, Field.Index.ANALYZED));
			doc.add(new Field("name", fileList.get(i), Field.Store.YES, Field.Index.ANALYZED));
			if (cat_subcat) {
				doc.add(new Field("cat", catList.get(i), Field.Store.YES, Field.Index.ANALYZED));
				doc.add(new Field("sub-cat", subcatList.get(i), Field.Store.YES,
						Field.Index.ANALYZED));
			}
			indexWriter.addDocument(doc);
			if (verbose)
				System.out.println(fileList.get(i) + " added to index.");
		}
		indexWriter.close();
		indexDir.close();
	}
}