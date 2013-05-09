import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class CacmParser {

	private String cacmPath;
	private String cacmContent;
	private String queryContent;
	private List<String> stopwords;
	private String qrels;

	final static Charset ENCODING = StandardCharsets.UTF_8;

	public CacmParser(String cPath) throws IOException {
		cacmPath = cPath;
		List<String> c = Files.readAllLines(Paths.get(cacmPath + "cacm.all"), ENCODING);
		cacmContent = listToString(c);
		List<String> q = Files.readAllLines(Paths.get(cacmPath + "query.text"), ENCODING);
		queryContent = listToString(q);
		stopwords = Files.readAllLines(Paths.get(cacmPath + "common_words"), ENCODING);
		List<String> qr = Files.readAllLines(Paths.get(cacmPath + "qrels.text"), ENCODING);
		qrels = listToString(qr);
	}

	public String[] parseDocsInArray() {
		return cacmContent.split("(?=\\.I )");
	}

	public String parseID(String doc) {
		Pattern pattern = Pattern.compile("\\.I \\d*");
		Matcher matcher = pattern.matcher(doc);
		String s = "";
		while (matcher.find()) {
			s += matcher.group().replace(".I ", "");
		}
		return s;
	}

	public String parseKW(String doc) {
		Pattern pattern = Pattern.compile("\\.K[\\w\\W]*?\\.[A-Z]");
		Matcher matcher = pattern.matcher(doc);
		String s = "";
		while (matcher.find()) {
			String g = matcher.group().replace(".K", "");
			if (g.length() > 3)
				s += g.substring(0, g.length() - 3);
		}
		return s;
	}

	public String parseTitle(String doc) {
		Pattern pattern = Pattern.compile("\\.T[\\w\\W]*?\\.[A-Z]");
		Matcher matcher = pattern.matcher(doc);
		String s = "";
		while (matcher.find()) {
			String g = matcher.group().replace(".T", "");
			s += g.substring(0, g.length() - 3);
		}
		return s;
	}

	public String parseAbst(String doc) {
		Pattern pattern = Pattern.compile("\\.W[\\w\\W]*?\\.[A-Z]");
		Matcher matcher = pattern.matcher(doc);
		String s = "";
		while (matcher.find()) {
			String g = matcher.group().replace(".W", "");
			s += g.substring(0, g.length() - 3);
		}
		return s;
	}

	public String[] parseQueryInArray() {
		return queryContent.split("(?=\\.I )");
	}

	public String parseQueryText(String doc) {
		Pattern pattern = Pattern.compile("\\.W[\\w\\W]*?\\.[A-Z]");
		Matcher matcher = pattern.matcher(doc);
		String s = "";
		while (matcher.find()) {
			String g = matcher.group().replace(".W", "");
			s += g.substring(0, g.length() - 3);
		}
		return s;
	}

	private String listToString(List<String> list) {
		StringBuilder out = new StringBuilder();
		for (Object o : list) {
			out.append(o.toString());
			out.append("\n");
		}
		return out.toString();
	}

	public String[] getStopwords() {
		String sw = listToString(stopwords);
		return sw.split("\n");
	}

	public String relDocsForID(String id) {
		if (id.length() == 1)
			id = "0" + id;
		Pattern pattern = Pattern.compile(id + " \\d{4}");
		Matcher matcher = pattern.matcher(qrels);
		String s = "";
		while (matcher.find()) {
			s += matcher.group().replace(id + " ", "") + "\n";
		}
		return s;
	}

}
