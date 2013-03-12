import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
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

	public static void searchIndex(String queryString) throws IOException, ParseException {
		QueryParser parser = new QueryParser(Version.LUCENE_36, "text", new SnowballAnalyzer(
				Version.LUCENE_36, "English", Main.stopWordList));
		Directory indexDir = FSDirectory.open(new File(indexPath));
		IndexReader reader = IndexReader.open(indexDir);
		IndexSearcher searcher = new IndexSearcher(reader);
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