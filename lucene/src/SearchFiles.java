import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchFiles {

	public static void searchIndex(String queryString) throws IOException, ParseException {
		Searcher searcher = new IndexSearcher(FSDirectory.open(new File("D:\\lucene\\index")));
		QueryParser parser = new QueryParser(Version.LUCENE_36, "text", new StandardAnalyzer(
				Version.LUCENE_36));
		System.out.println("\nsearching for: " + queryString);
		TopDocs results = searcher.search(parser.parse(queryString), 10);
		System.out.println("total hits: " + results.totalHits);
		for (ScoreDoc hit : results.scoreDocs) {
			Document doc = searcher.doc(hit.doc);
			System.out.printf("%5.3f %s\n", hit.score, doc.get("name"));
		}
		searcher.close();
	}
}