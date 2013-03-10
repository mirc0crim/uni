import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VectorSpaceModel {

	private static String docPath = "D:\\lucene\\corpus";

	public static void searchVSM(String queryString) throws IOException {

		String[] query = queryString.split(" ");

		int nDocs = 0; // number of documents
		File[] listFiles = new File(docPath).listFiles();
		for (File f : listFiles)
			nDocs++;

		int[][] tf = new int[query.length][nDocs]; // frequency of term in doc
		for (int doc = 0; doc < nDocs; doc++) {
			File f = new File(docPath).listFiles()[doc];
			for (int term = 0; term < query.length; term++) {
				String q = query[term];
				BufferedReader br = new BufferedReader(new FileReader(f.getPath()));
				String everything = "";
				try {
					StringBuilder sb = new StringBuilder();
					String line = br.readLine();
					while (line != null) {
						sb.append(line);
						sb.append("\n");
						line = br.readLine();
					}
					everything = sb.toString();
				} finally {
					br.close();
				}
				Pattern p = Pattern.compile(q);
				Matcher m = p.matcher(everything);
				int termFreq = 0;
				while (m.find())
					termFreq++;
				tf[term][doc] = termFreq;
			}
		}
		for (int term = 0; term < query.length; term++)
			System.out.println("tf" + term + ": " + Arrays.toString(tf[term]));

		int[] df = new int[query.length]; // doc frequency of the term
		for (int term = 0; term < query.length; term++) {
			int docFreq = 0;
			for (int doc = 0; doc < nDocs; doc++)
				if (tf[term][doc] > 0)
					docFreq++;
			df[term] = docFreq;
		}
		System.out.println("\ndf: " + Arrays.toString(df) + "\n");

		double[][] weight = new double[query.length][nDocs];
		for (int term = 0; term < weight.length; term++) {
			double idf = Math.log(nDocs / df[term]);
			for (int doc = 0; doc < nDocs; doc++)
				weight[term][doc] = tf[term][doc] * idf;
		}
		for (int term = 0; term < query.length; term++)
			System.out.println("weight: " + Arrays.toString(weight[term]));

		String[][] score = new String[nDocs][2];
		for (int doc = 0; doc < nDocs; doc++) {
			int total = 0;
			for (int term = 0; term < query.length; term++)
				total += weight[term][doc];
			score[doc][0] = total + "";
			score[doc][1] = new File(docPath).listFiles()[doc].getName();
		}
		Arrays.sort(score, new Comparator<String[]>() {
			@Override
			public int compare(final String[] entry1, final String[] entry2) {
				final String s1 = entry1[0];
				final String s2 = entry2[0];
				return s2.compareTo(s1);
			}
		});
		System.out.println("\nscore:");
		for (int doc = 0; doc < nDocs; doc++)
			if (Integer.parseInt(score[doc][0]) > 0)
				System.out.println(Arrays.toString(score[doc]));

	}
}