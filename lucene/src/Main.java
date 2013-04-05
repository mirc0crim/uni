import java.io.IOException;
import java.util.Arrays;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.util.Version;

public class Main {

	public static String[] stopWordList = new String[] { "the", "to", "of", "and", "or", "a", "in",
		"he", "it", "for", "is", "this", "has", "his" };

	public static String query1 = "city business program improvement income";
	public static String query2 = "business improvement program city city income";
	public static String[] optional = new String[] { "draw", "drawing", "home euro", "di?e?*",
		"diego*", "diego?", "are~", "\"the improvement\"", "home AND euro",
		"home and euro", "home +euro", "home euro^4", "+home euro^4" };

	private static String docPath = "D:\\lucene\\corpus";

	private final static int TP = 4;

	private static Analyzer simpleAnalyzer = new SimpleAnalyzer(Version.LUCENE_36);
	private static Analyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_36);
	private static Analyzer stopAnalyzer = new StopAnalyzer(Version.LUCENE_36);
	private static Analyzer whitespaceAnalyzer = new WhitespaceAnalyzer(Version.LUCENE_36);

	/**
	 * Index will be stored in "D:\lucene\index" and files have to be stored in
	 * "D:\lucene\corpus"
	 */
	public static void main(String[] args) throws IOException, ParseException {

		SearchFiles searcher = new SearchFiles(standardAnalyzer);
		IndexFiles indexer = new IndexFiles(standardAnalyzer, false);
		IndexFiles catIndexer = new IndexFiles(standardAnalyzer, true);

		System.out.println("Stop Words:\n" + Arrays.toString(stopWordList) + "\n");

		switch (TP) {
		case 1:
			indexer.buildIndex(docPath);
			searcher.searchIndex(query1);
			searcher.searchIndex(query2);

			for (String s : optional)
				searcher.searchIndex(s);
			break;
		case 2:
			VectorSpaceModel.searchVSM(query1, docPath);
			VectorSpaceModel.searchVSM(query2, docPath);

			MyAnalysis.doAnalysis("document2.txt");
			break;
		case 3:
			VectorSpaceModel.searchVSM("language call book", "D:\\lucene\\corpus\\TP3");
			break;
		case 4:
			Analyzer[] analyzers = { standardAnalyzer, stopAnalyzer, whitespaceAnalyzer,
					simpleAnalyzer };
			for (Analyzer analyzer : analyzers){
				System.out.println("\n" + analyzer);
				new IndexFiles(analyzer, false).buildIndex("D:\\lucene\\corpus\\TP4");
				new SearchFiles(analyzer).searchIndex("1923");
			}
			indexer.buildIndex("D:\\lucene\\corpus\\TP4");
			searcher.searchIndex("Business");
			catIndexer.buildIndex("D:\\lucene\\corpus\\TP4");
			searcher.searchIndex("Business");
			break;
		}
	}
}