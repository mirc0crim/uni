import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
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
	private boolean multiField;
	private String[] queryRel;
	private float aprec;
	private float arelprec;

	private boolean output = true;

	public SearchFiles(Analyzer a, boolean multi) {
		analyzer = a;
		multiField = multi;
		aprec = 0;
		arelprec = 0;
	}

	public void searchIndex(String queryString)
			throws IOException, ParseException {
		boolean cat_subcat = false;
		QueryParser parser;
		if (multiField)
			parser = new MultiFieldQueryParser(Version.LUCENE_36, new String[] { "title",
					"keywords", "abstract" }, analyzer);
		else
			parser = new QueryParser(Version.LUCENE_36, "text", analyzer);
		Directory indexDir = FSDirectory.open(new File(indexPath));
		IndexReader reader = IndexReader.open(indexDir);
		IndexSearcher searcher = new IndexSearcher(reader);
		if (output)
			System.out.println("\nsearching for: " + queryString);
		TopDocs results = searcher.search(parser.parse(queryString), 100);
		if (output)
			System.out.println("total hits: " + results.totalHits);
		int len = Math.min(10, results.totalHits);
		String[][] score = new String[len][4];
		int[] rel = new int[len];
		float[] prec = new float[len];
		int sum = 0;
		float avgprec = 0;
		float avgrelprec = 0;
		for (int i = 0; i < len; i++) {
			ScoreDoc hit = results.scoreDocs[i];
			Document doc = searcher.doc(hit.doc);
			score[i][0] = hit.score + "";
			if (doc.get("cat") != null) {
				cat_subcat = true;
				score[i][1] = doc.get("cat");
				score[i][2] = doc.get("sub-cat");
			}
			score[i][3] = doc.get("id");
			if (queryRel != null && Arrays.asList(queryRel).contains(doc.get("id"))) {
				rel[i] = 1;
				sum++;
			} else
				rel[i] = 0;
			prec[i] = sum / (float) (i + 1);
			avgprec += prec[i];
			if (rel[i] == 1)
				avgrelprec += prec[i];
		}
		avgprec /= len;
		if (sum > 0)
			avgrelprec /= sum;
		aprec = avgprec;
		arelprec = avgrelprec;
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
			if (output) {
				System.out.println("Rank; Score; ID; Rel; Precision");
				for (int i = 0; i < len; i++)
					System.out.printf("%2d; %6.3f; %4s; %d; %5.3f\n", i + 1,
							Float.parseFloat(score[i][0]), score[i][3], rel[i], prec[i]);
				System.out.println("Avg Prec: " + avgprec);
				System.out.println("Avg Rel Prec: " + avgrelprec);
			}
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

	public void setQueryRels(String[] rels) {
		queryRel = rels;
	}

	public float getAPrec() {
		return aprec;
	}

	public float getARelPrec() {
		return arelprec;
	}
}