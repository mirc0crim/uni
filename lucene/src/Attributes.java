import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

/**
 * Collection of default Attributes and never changing Arguments
 */
public class Attributes {

	String indexPath = "D:\\lucene\\index";
	String docPath = "D:\\lucene\\corpus";
	String cacmPath = "D:\\lucene\\cacm\\";

	Analyzer simpleAnalyzer = new SimpleAnalyzer(Version.LUCENE_36);
	Analyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_36);
	Analyzer stopAnalyzer = new StopAnalyzer(Version.LUCENE_36);
	Analyzer whitespaceAnalyzer = new WhitespaceAnalyzer(Version.LUCENE_36);

	String query1 = "city business program improvement income";
	String query2 = "business improvement program city city income";
	String[] optional = new String[] { "draw", "drawing", "home euro", "di?e?*", "diego*",
			"diego?", "are~", "\"the improvement\"", "home AND euro", "home and euro",
			"home +euro", "home euro^4", "+home euro^4" };

	String[] stopWordList = new String[] { "the", "to", "of", "and", "or", "a", "in", "he", "it",
			"for", "is", "this", "has", "his" };

	public Attributes() {

	}
}
