import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class SearchFiles {

	public SearchFiles() {
	}

	public static void searchIndex(String queryStrings) throws IOException, ParseException {
		Searcher searcher = new IndexSearcher(FSDirectory.getDirectory("D:\\lucene"));
		QueryParser parser = new QueryParser("text", new StandardAnalyzer());
		String[] queryArray = queryStrings.split(" ");
		ArrayList<String[]> resultsList = new ArrayList<String[]>();
		for (String queryString : queryArray) {
			System.out.println("\nsearching for: " + queryString);
			Query query = parser.parse(queryString);
			TopDocs results = searcher.search(query, 10);
			System.out.println("total hits: " + results.totalHits);
			ScoreDoc[] hits = results.scoreDocs;
			String[] foundNames = new String[hits.length];
			for (int i = 0; i < hits.length; i++) {
				Document doc = searcher.doc(hits[i].doc);
				System.out.printf("%5.3f %s \n", hits[i].score, doc.get("name"));
				foundNames[i] = doc.get("name");
			}
			resultsList.add(foundNames);
		}
		System.out.println(Arrays.toString(resultsList.get(1)));
		System.out.println(Arrays.toString(resultsList.get(2)));
		searcher.close();
	}
}
