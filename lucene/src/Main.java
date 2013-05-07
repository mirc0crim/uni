import java.io.IOException;
import java.util.Arrays;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;

public class Main {

	private final static int TP = 7;

	public static void main(String[] args) throws IOException, ParseException {

		System.out.println("started");

		Attributes att = new Attributes();

		SearchFiles searcher = new SearchFiles(att.standardAnalyzer);
		IndexFiles indexer = new IndexFiles(att.standardAnalyzer, false);
		IndexFiles catIndexer = new IndexFiles(att.standardAnalyzer, true);

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

			System.out.println("Stop Words:\n" + Arrays.toString(att.stopWordList) + "\n");
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
		case 7:
			CacmParser cp = new CacmParser(att.cacmPath);
			String[] docs = cp.parseDocsInArray();
			System.out.println("Total " + docs.length);
			for (int i = 1; i < docs.length; i++) {
				String kw = cp.parseKW(docs[i]);
				String ti = cp.parseTitle(docs[i]);
				String ab = cp.parseAbst(docs[i]);
				if (kw.length() == 0 && ti.length() == 0 && ab.length() == 0) {
					System.out.println("Empty Doc " + i);
				}
				indexer.buildIndex(cp.parseID(docs[i]), kw, ti, ab);
				if (i % 320 == 0)
					System.out.println(i / 32);
			}

			String[] queries = cp.parseQueryInArray();
			String[] no = { "1", "3", "4", "6", "11", "24", "46", "56" };
			for (int i = 0; i < queries.length; i++) {
				String id = cp.parseID(queries[i]);
				if (Arrays.asList(no).contains(id)) {
					searcher.searchIndex((cp.parseQueryText(queries[i]).trim()));
				}
			}
			break;
		}

		System.out.print("done");
	}
}