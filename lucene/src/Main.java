import java.io.IOException;
import java.util.Arrays;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;

public class Main {

	private final static int TP = 4;

	public static void main(String[] args) throws IOException, ParseException {

		Attributes att = new Attributes();

		SearchFiles searcher = new SearchFiles(att.standardAnalyzer);
		IndexFiles indexer = new IndexFiles(att.standardAnalyzer, false);
		IndexFiles catIndexer = new IndexFiles(att.standardAnalyzer, true);

		System.out.println("Stop Words:\n" + Arrays.toString(att.stopWordList) + "\n");

		switch (TP) {
		case 1:
			indexer.buildIndex(att.docPath);
			searcher.searchIndex(att.query1);
			searcher.searchIndex(att.query2);

			for (String s : att.optional)
				searcher.searchIndex(s);
			break;
		case 2:
			VectorSpaceModel.searchVSM(att.query1, att.docPath);
			VectorSpaceModel.searchVSM(att.query2, att.docPath);

			MyAnalysis.doAnalysis("document2.txt");
			break;
		case 3:
			VectorSpaceModel.searchVSM("language call book", "D:\\lucene\\corpus\\TP3");
			break;
		case 4:
			Analyzer[] analyzers = { att.standardAnalyzer, att.stopAnalyzer,
					att.whitespaceAnalyzer, att.simpleAnalyzer };
			indexer.buildIndex("D:\\lucene\\corpus\\TP4");
			for (Analyzer analyzer : analyzers){
				System.out.println("\n" + analyzer);
				new SearchFiles(analyzer).searchIndex("1923");
			}
			searcher.searchIndex("Business");
			catIndexer.buildIndex("D:\\lucene\\corpus\\TP4");
			searcher.searchIndex("Business");
			break;
		}
	}
}