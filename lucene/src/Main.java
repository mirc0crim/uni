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

	private static int TP = 4;

	private static Analyzer simpleAnalyzer = new SimpleAnalyzer(Version.LUCENE_36);
	private static Analyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_36);
	private static Analyzer stopAnalyzer = new StopAnalyzer(Version.LUCENE_36);
	private static Analyzer whitespaceAnalyzer = new WhitespaceAnalyzer(Version.LUCENE_36);

	/**
	 * Index will be stored in "D:\lucene\index" and files have to be stored in
	 * "D:\lucene\corpus"
	 */
	public static void main(String[] args) throws IOException, ParseException {

		System.out.println("Stop Words:\n" + Arrays.toString(stopWordList) + "\n");

		switch (TP) {
		case 1:
			IndexFiles.buildIndex(docPath, standardAnalyzer, false);
			SearchFiles.searchIndex(query1, standardAnalyzer, false);
			SearchFiles.searchIndex(query2, standardAnalyzer, false);

			for (String s : optional)
				SearchFiles.searchIndex(s, standardAnalyzer, false);
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
				IndexFiles.buildIndex("D:\\lucene\\corpus\\TP4", analyzer, false);
				SearchFiles.searchIndex("1923", analyzer, false);
			}

			IndexFiles.buildIndex("D:\\lucene\\corpus\\TP4", standardAnalyzer, false);
			SearchFiles.searchIndex("Business", standardAnalyzer, false);
			IndexFiles.buildIndex("D:\\lucene\\corpus\\TP4", standardAnalyzer, true);
			SearchFiles.searchIndex("Business", standardAnalyzer, true);
			break;
		}
	}
}