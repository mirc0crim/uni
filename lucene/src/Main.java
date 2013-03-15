import java.io.IOException;
import java.util.Arrays;

import org.apache.lucene.queryParser.ParseException;

public class Main {

	public static String[] stopWordList = new String[] { "the", "to", "of", "and", "or", "a",
			"in", "he", "it", "for", "business", "is", "this", "has", "his" };

	public static String query1 = "city business program improvement income";
	public static String query2 = "business improvement program city city income";
	public static String[] optional = new String[] { "draw", "drawing", "home euro", "di?e?*",
			"diego*", "diego?", "are~", "\"the improvement\"", "home AND euro",
			"home and euro", "home +euro", "home euro^4", "+home euro^4" };
	public static String queryTP3 = "language call book";

	private static String docPath = "D:\\lucene\\corpus";
	private static String docPathTP3 = "D:\\lucene\\corpus\\TP3";

	/**
	 * Index will be stored in "D:\lucene\index" and files have to be stored in
	 * "D:\lucene\corpus"
	 */
	public static void main(String[] args) throws IOException, ParseException {

		IndexFiles.buildIndex();
		System.out.println("\nStop Words:\n" + Arrays.toString(stopWordList));
		SearchFiles.searchIndex(query1);
		SearchFiles.searchIndex(query2);
		for (String s : optional)
			SearchFiles.searchIndex(s);

		VectorSpaceModel.searchVSM(query1, docPath);
		VectorSpaceModel.searchVSM(query2, docPath);
		MyAnalysis.doAnalysis("document2.txt");

		VectorSpaceModel.searchVSM(queryTP3, docPathTP3);
	}
}