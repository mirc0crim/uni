import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;

public class main {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException {
		IndexFiles.buildIndex();
		SearchFiles.searchIndex("business improvement program city city income");
	}
}
