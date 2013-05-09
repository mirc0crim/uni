import java.io.IOException;
import java.util.Arrays;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.ext.PorterStemmer;

public class Main {

	private static int TP = 7;

	public static void main(String[] args) throws IOException, ParseException {

		System.out.println("started");

		Attributes att = new Attributes();

		SearchFiles searcher = new SearchFiles(att.standardAnalyzer, false);
		IndexFiles indexer = new IndexFiles(att.standardAnalyzer, false);
		IndexFiles catIndexer = new IndexFiles(att.standardAnalyzer, true);

		if (TP == 1){
			indexer.buildIndex(att.docPath);
			searcher.searchIndex(att.query1);
			searcher.searchIndex(att.query2);

			for (String s : att.optional)
				searcher.searchIndex(s);
		}
		if (TP == 2) {
			VectorSpaceModel.searchVSM(att.query1, att.docPath);
			VectorSpaceModel.searchVSM(att.query2, att.docPath);

			MyAnalysis.doAnalysis("document2.txt");

			System.out.println("Stop Words:\n" + Arrays.toString(att.stopWordList) + "\n");
		}
		if (TP == 3)
			VectorSpaceModel.searchVSM("language call book", "D:\\lucene\\corpus\\TP3");
		if (TP == 4) {
			Analyzer[] analyzers = { att.standardAnalyzer, att.stopAnalyzer,
					att.whitespaceAnalyzer, att.simpleAnalyzer };
			indexer.buildIndex("D:\\lucene\\corpus\\TP4");
			for (Analyzer analyzer : analyzers){
				System.out.println("\n" + analyzer);
				new SearchFiles(analyzer, false).searchIndex("1923");
			}
			searcher.searchIndex("Business");
			catIndexer.buildIndex("D:\\lucene\\corpus\\TP4");
			searcher.searchIndex("Business");
		}
		if (TP == 7 || TP == 8) {
			CacmParser cp = new CacmParser(att.cacmPath);
			String[] docs = cp.parseDocsInArray();
			SearchFiles searcher7 = new SearchFiles(att.standardAnalyzer, true);
			System.out.println("Total " + docs.length);
			PorterStemmer stemmer = new PorterStemmer();
			boolean needIndex = true;
			if (needIndex) {
				String[] ids = new String[docs.length];
				String[] kws = new String[docs.length];
				String[] tis = new String[docs.length];
				String[] abs = new String[docs.length];
				for (int i = 1; i < docs.length; i++) {
					String id = cp.parseID(docs[i]);
					String kw = cp.parseKW(docs[i]).toLowerCase();
					String ti = cp.parseTitle(docs[i]).toLowerCase();
					String ab = cp.parseAbst(docs[i]).toLowerCase();
					if (kw.length() == 0 && ti.length() == 0 && ab.length() == 0) {
						System.out.println("Empty Doc " + i);
					}
					if (TP == 8) {
						kw = stem(stemmer, kw);
						ti = stem(stemmer, ti);
						ab = stem(stemmer, ab);
					}
					ids[i] = id;
					kws[i] = kw;
					tis[i] = ti;
					abs[i] = ab;
				}
				indexer.buildIndex(ids, kws, tis, abs);
			}
			String[] queries = cp.parseQueryInArray();
			String[] sw = cp.getStopwords();
			if (TP == 7) {
				String[] no = { "1", "3", "4", "6", "11", "24", "46", "56" };
				for (int i = 0; i < queries.length; i++) {
					String id = cp.parseID(queries[i]);
					if (Arrays.asList(no).contains(id)) {
						String qt = cp.parseQueryText(queries[i]).replace("\n", " ").trim();
						qt = remSW(qt.toLowerCase(), sw);
						searcher7.setQueryRels(cp.relDocsForID(cp.parseID(queries[i])));
						searcher7.searchIndex(qt);
					}
				}
			} else if (TP == 8) {
				for (int i = 1; i < 11; i++) {
					String qt = cp.parseQueryText(queries[i]).replace("\n", " ").trim();
					qt = remSW(qt.toLowerCase(), sw);
					qt = stem(stemmer, qt);
					searcher7.setQueryRels(cp.relDocsForID(cp.parseID(queries[i])));
					searcher7.searchIndex(qt);
				}
			}
		}

		System.out.print("done");
	}

	public static String remSW(String s, String[] sw) {
		for (int i = 0; i < sw.length; i++) {
			s = s.replaceAll(" " + sw[i] + " ", " ");
			s = s.replaceAll("^" + sw[i] + " ", "");
			s = s.replaceAll(" " + sw[i] + "$", "");
		}
		return s;
	}

	public static String stem(SnowballProgram stemmer, String s) {
		String[] tokens = s.split(" ");
		for (int i = 0; i < tokens.length; i++) {
			stemmer.setCurrent(tokens[i]);
			stemmer.stem();
			tokens[i] = stemmer.getCurrent();
		}
		String out = "";
		for (int j = 0; j < tokens.length; j++) {
			out += tokens[j] + " ";
		}
		return out;
	}
}