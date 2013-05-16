import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.tartarus.snowball.ext.PorterStemmer;

public class Main {

	public static TPs runTP = TPs.TP_8c;

	public static void main(String[] args) throws IOException, ParseException {

		System.out.println("started");

		Attributes att = new Attributes();

		SearchFiles searcher = new SearchFiles(att.standardAnalyzer, false);
		IndexFiles indexer = new IndexFiles(att.standardAnalyzer, false);
		IndexFiles catIndexer = new IndexFiles(att.standardAnalyzer, true);

		if (runTP == TPs.TP_1) {
			indexer.buildIndex(att.docPath);
			searcher.searchIndex(att.query1);
			searcher.searchIndex(att.query2);

			for (String s : att.optional)
				searcher.searchIndex(s);
		}
		if (runTP == TPs.TP_2) {
			VectorSpaceModel.searchVSM(att.query1, att.docPath);
			VectorSpaceModel.searchVSM(att.query2, att.docPath);

			MyAnalysis.doAnalysis("document2.txt");

			System.out.println("Stop Words:\n" + Arrays.toString(att.stopWordList) + "\n");
		}
		if (runTP == TPs.TP_3)
			VectorSpaceModel.searchVSM("language call book", "D:\\lucene\\corpus\\TP3");
		if (runTP == TPs.TP_4) {
			Analyzer[] analyzers = { att.standardAnalyzer, att.stopAnalyzer,
					att.whitespaceAnalyzer, att.simpleAnalyzer };
			indexer.buildIndex(att.docPathTP4);
			for (Analyzer analyzer : analyzers){
				System.out.println("\n" + analyzer);
				new SearchFiles(analyzer, false).searchIndex("1923");
			}
			searcher.searchIndex("Business");
			catIndexer.buildIndex(att.docPathTP4);
			searcher.searchIndex("Business");
		}
		if (runTP == TPs.TP_7 || runTP == TPs.TP_8a || runTP == TPs.TP_8b || runTP == TPs.TP_8c) {
			CacmParser cp = new CacmParser(att.cacmPath);
			String[] docs = cp.parseDocsInArray();
			SearchFiles searcher7 = new SearchFiles(att.standardAnalyzer, true);
			System.out.println("Total " + (docs.length - 1));
			for (final File f : new File(att.indexPath).listFiles())
				if (!f.isDirectory())
					f.delete();

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
				if (runTP == TPs.TP_8b) {
					kw = stemPorter(kw);
					ti = stemPorter(ti);
					ab = stemPorter(ab);
				} else if (runTP == TPs.TP_8c) {
					kw = stemS(kw);
					ti = stemS(ti);
					ab = stemS(ab);
				}
				ids[i] = id;
				kws[i] = kw;
				tis[i] = ti;
				abs[i] = ab;
			}
			indexer.buildIndex(ids, kws, tis, abs);

			String[] queries = cp.parseQueryInArray();
			String[] sw = cp.getStopwords();
			if (runTP == TPs.TP_7) {
				for (int i = 0; i < queries.length; i++) {
					String id = cp.parseID(queries[i]);
					if (Arrays.asList(att.queryNo).contains(id)) {
						String qt = cp.parseQueryText(queries[i]).replace("\n", " ").trim();
						qt = remSW(qt.toLowerCase(), sw);
						searcher7.setQueryRels(cp.relDocsForID(cp.parseID(queries[i])));
						searcher7.searchIndex(qt);
					}
				}
			} else {
				for (int i = 1; i < 11; i++) {
					String qt = cp.parseQueryText(queries[i]).replace("\n", " ").trim();
					qt = remSW(qt.toLowerCase(), sw);
					if (runTP == TPs.TP_8b)
						qt = stemPorter(qt);
					if (runTP == TPs.TP_8c)
						qt = stemS(qt);
					searcher7.setQueryRels(cp.relDocsForID(cp.parseID(queries[i])));
					searcher7.searchIndex(qt);
				}
			}
		}

		System.out.print("done");
	}
	
	public enum TPs {
		TP_1, TP_2, TP_3, TP_4, TP_7, TP_8a, TP_8b, TP_8c
	}

	public static String remSW(String s, String[] sw) {
		for (int i = 0; i < sw.length; i++) {
			s = s.replaceAll(" " + sw[i] + " ", " ");
			s = s.replaceAll("^" + sw[i] + " ", "");
			s = s.replaceAll(" " + sw[i] + "$", "");
		}
		return s;
	}

	public static String stemPorter(String s) {
		String[] tokens = s.split(" ");
		PorterStemmer stemmer = new PorterStemmer();
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

	public static String stemS(String s) {
		String[] tokens = s.split(" ");
		for (int i = 0; i < tokens.length; i++) {
			String input = tokens[i].trim();
			if (input.length() < 5)
				continue;
			boolean iesEnd = input.substring(input.length() - 3, input.length()).equals("ies");
			boolean eiesEnd = input.substring(input.length() - 4, input.length()).equals("eies");
			boolean aiesEnd = input.substring(input.length() - 4, input.length()).equals("aies");
			if (iesEnd && !eiesEnd && !aiesEnd) {
				tokens[i] = input.substring(0, input.length() - 3) + "y";
				continue;
			}
			boolean esEnd = input.substring(input.length() - 2, input.length()).equals("es");
			boolean aesEnd = input.substring(input.length() - 3, input.length()).equals("aes");
			boolean eesEnd = input.substring(input.length() - 3, input.length()).equals("ees");
			boolean oesEnd = input.substring(input.length() - 3, input.length()).equals("oes");
			if (esEnd && !aesEnd && !eesEnd && !oesEnd) {
				tokens[i] = input.substring(0, input.length() - 2) + "e";
				continue;
			}
			boolean sEnd = input.substring(input.length() - 1, input.length()).equals("s");
			boolean usEnd = input.substring(input.length() - 2, input.length()).equals("us");
			boolean ssEnd = input.substring(input.length() - 2, input.length()).equals("ss");
			if (sEnd && !usEnd && !ssEnd) {
				tokens[i] = input.substring(0, input.length() - 1);
			}
		}
		String out = "";
		for (int j = 0; j < tokens.length; j++) {
			out += tokens[j] + " ";
		}
		return out;
	}
}