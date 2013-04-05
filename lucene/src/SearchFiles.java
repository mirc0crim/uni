import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchFiles {

	private String indexPath = "D:\\lucene\\index";
	private Analyzer analyzer;

	public SearchFiles(Analyzer a) {
		analyzer = a;
	}

	public void searchIndex(String queryString)
			throws IOException, ParseException {
		boolean cat_subcat = false;
		QueryParser parser = new QueryParser(Version.LUCENE_36, "text", analyzer);
		Directory indexDir = FSDirectory.open(new File(indexPath));
		IndexReader reader = IndexReader.open(indexDir);
		IndexSearcher searcher = new IndexSearcher(reader);
		System.out.println("\nsearching for: " + queryString);
		TopDocs results = searcher.search(parser.parse(queryString), 100);
		System.out.println("total hits: " + results.totalHits);
		String[][] score = new String[results.totalHits][4];
		for (int i = 0; i < results.totalHits; i++) {
			ScoreDoc hit = results.scoreDocs[i];
			Document doc = searcher.doc(hit.doc);
			score[i][0] = hit.score + "";
			if (doc.get("cat") != null) {
				cat_subcat = true;
				score[i][1] = doc.get("cat");
				score[i][2] = doc.get("sub-cat");
			}
			score[i][3] = doc.get("name");
		}
		if (cat_subcat) {
			System.out.println("\nSorted by score (desc)");
			sortArray(score, 0, false);
			for (int i = 0; i < results.totalHits; i++)
				System.out.printf("%5.3f %s %s %s\n", Float.parseFloat(score[i][0]), score[i][1],
						score[i][2], score[i][3]);
			sortArray(score, 1, true);
			sortArray(score, 0, false);
			System.out.println("\nSorted by score (desc) and category (asc)");
			for (int i = 0; i < results.totalHits; i++)
				System.out.printf("%5.3f %s %s %s\n", Float.parseFloat(score[i][0]), score[i][1],
						score[i][2], score[i][3]);
			sortArray(score, 2, false);
			sortArray(score, 1, true);
			sortArray(score, 0, false);
			System.out.println("\nSorted by score (desc), category (asc) and subcategory (desc)");
			for (int i = 0; i < results.totalHits; i++)
				System.out.printf("%5.3f %s %s %s\n", Float.parseFloat(score[i][0]),
						score[i][1], score[i][2], score[i][3]);
		} else {
			sortArray(score, 0, false);
			for (int i = 0; i < results.totalHits; i++)
				System.out.printf("%5.3f %s\n", Float.parseFloat(score[i][0]), score[i][3]);
		}
		searcher.close();
		reader.close();
		indexDir.close();
	}

	private void sortArray(String[][] score, final int arg, final boolean ascending) {
		Arrays.sort(score, new Comparator<String[]>() {
			@Override
			public int compare(final String[] entry1, final String[] entry2) {
				final String s1 = entry1[arg];
				final String s2 = entry2[arg];
				if (ascending)
					return s1.compareTo(s2);
				else
					return s2.compareTo(s1);
			}
		});
	}
}