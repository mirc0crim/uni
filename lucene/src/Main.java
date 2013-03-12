import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;

public class Main {

	/**
	 * Index will be stored in "D:\lucene\index" and files have to be stored in
	 * "D:\lucene\corpus"
	 */
	public static void main(String[] args) throws IOException, ParseException {

		String query1 = "city business program improvement income";
		String query2 = "business improvement program city city income";
		String[] optional = new String[] { "draw", "drawing", "home euro", "di?e?*", "diego*",
				"diego?", "are~", "\"the improvement\"", "home AND euro", "home and euro",
				"home +euro", "home euro^4", "+home euro^4" };

		IndexFiles.buildIndex();
		SearchFiles.searchIndex(query1);
		SearchFiles.searchIndex(query2);
		for (String s : optional)
			SearchFiles.searchIndex(s);

		// VectorSpaceModel.searchVSM(query1);
		// VectorSpaceModel.searchVSM(query2);
		// MyAnalysis.doAnalysis("document2.txt");
	}
}