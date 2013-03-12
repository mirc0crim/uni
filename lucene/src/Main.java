import java.io.IOException;
import java.util.Arrays;

import org.apache.lucene.queryParser.ParseException;

public class Main {

	public static String[] stopWordList = new String[] { "the", "to", "of", "and", "or", "a", "in",
		"he", "it", "for", "business" };

	public static String query1 = "city business program improvement income";
	public static String query2 = "business improvement program city city income";
	public static String[] optional = new String[] { "draw", "drawing", "home euro", "di?e?*",
		"diego*", "diego?", "are~", "\"the improvement\"", "home AND euro", "home and euro",
		"home +euro", "home euro^4", "+home euro^4" };

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

		VectorSpaceModel.searchVSM(query1);
		VectorSpaceModel.searchVSM(query2);
		MyAnalysis.doAnalysis("document2.txt");
	}
}