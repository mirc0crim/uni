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

	private static String indexPath = "D:\\lucene\\index";

	public static void searchIndex(String queryString, Analyzer analyzer, boolean cat_subcat)
			throws IOException, ParseException {
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
			score[i][0] = hit.score + " ";
			if (cat_subcat) {
				score[i][1] = doc.get("cat") + " ";
				score[i][2] = doc.get("sub-cat") + " ";
			} else {
				score[i][1] = "";
				score[i][2] = "";
			}
			score[i][3] = doc.get("name");
		}
		Arrays.sort(score, new Comparator<String[]>() {
			@Override
			public int compare(final String[] entry1, final String[] entry2) {
				final String s1 = entry1[2];
				final String s2 = entry2[2];
				return s2.compareTo(s1);
			}
		});
		Arrays.sort(score, new Comparator<String[]>() {
			@Override
			public int compare(final String[] entry1, final String[] entry2) {
				final String s1 = entry1[1];
				final String s2 = entry2[1];
				return s1.compareTo(s2);
			}
		});
		Arrays.sort(score, new Comparator<String[]>() {
			@Override
			public int compare(final String[] entry1, final String[] entry2) {
				final String s1 = entry1[0];
				final String s2 = entry2[0];
				return s2.compareTo(s1);
			}
		});
		for (int i = 0; i < results.totalHits; i++)
			System.out
			.println(score[i][0] + "" + score[i][1] + "" + score[i][2] + "" + score[i][3]);
		searcher.close();
		reader.close();
	}
}