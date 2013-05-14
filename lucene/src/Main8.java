import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.util.Version;
import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.ext.LovinsStemmer;
import org.tartarus.snowball.ext.PorterStemmer;

public class Main8 {

	private static String TP = "8a";
	private static String indexPath = "D:\\lucene\\index";
	private static String cacmPath = "D:\\lucene\\cacm\\";

	public static void main(String[] args) throws IOException, ParseException {

		Analyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_36);

		IndexFiles indexer = new IndexFiles(standardAnalyzer, false);
		CacmParser cp = new CacmParser(cacmPath);
		String[] docs = cp.parseDocsInArray();
		SearchFiles searcher7 = new SearchFiles(standardAnalyzer, true);
		SnowballProgram stemmer;
		if (TP == "8a") {
			stemmer = null;
			System.out.println("No Stemming");
		} else if (TP == "8b") {
			stemmer = new PorterStemmer();
			System.out.println("Porter Stemming");
		} else {
			stemmer = new LovinsStemmer();
			System.out.println("Lovins Stemming");
		}
		for (final File f : new File(indexPath).listFiles())
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
			if (TP != "8a") {
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

		String[] queries = cp.parseQueryInArray();
		String[] sw = cp.getStopwords();
		for (int i = 1; i < 11; i++) {
			String qt = cp.parseQueryText(queries[i]).replace("\n", " ").trim();
			qt = remSW(qt.toLowerCase(), sw);
			if (TP != "8a")
				qt = stem(stemmer, qt);
			searcher7.setQueryRels(cp.relDocsForID(cp.parseID(queries[i])));
			searcher7.searchIndex(qt);
		}
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