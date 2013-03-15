import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VectorSpaceModel {

	private static DecimalFormat form = new DecimalFormat("#.###");

	public static void searchVSM(String queryString, String docPath) throws IOException {

		System.out.println("Query: " + queryString);

		queryString = removePluralS(queryString);
		String[] query = queryString.split(" ");
		Set<String> noDupSet = new HashSet<String>();
		for (String el : query)
			noDupSet.add(el);
		String[] noDupQuery = noDupSet.toArray(new String[noDupSet.size()]);
		String[][] qf = new String[noDupSet.size()][2]; // query frequency
		for (int i = 0; i < noDupSet.size(); i++) {
			Pattern p = Pattern.compile(noDupQuery[i]);
			Matcher m = p.matcher(queryString);
			int termFreq = 0;
			while (m.find())
				termFreq++;
			qf[i][0] = noDupQuery[i];
			qf[i][1] = termFreq + "";
		}
		query = noDupQuery;
		System.out.println("Queryterm Frequency: [term, no of appearances]");
		for (String[] element : qf)
			System.out.println(Arrays.toString(element));

		int nDocs = 0; // number of documents
		for (File f : new File(docPath).listFiles())
			if (!f.isDirectory())
				nDocs++;

		int[][] tf = new int[query.length][nDocs]; // frequency of term in doc
		for (int doc = 0; doc < nDocs; doc++) {
			File f = new File(docPath).listFiles()[doc];
			if (f.isDirectory())
				continue;
			for (int term = 0; term < query.length; term++) {
				String q = query[term];
				BufferedReader br = new BufferedReader(new FileReader(f.getPath()));
				String everything = "";
				try {
					StringBuilder sb = new StringBuilder();
					String line = br.readLine();
					sb.append(" ");
					while (line != null) {
						sb.append(line);
						sb.append(" ");
						line = br.readLine();
					}
					sb.append(" ");
					everything = sb.toString().toLowerCase();
					everything = removeStopWords(everything);
					everything = removePluralS(everything);
				} finally {
					br.close();
				}
				Pattern p = Pattern.compile(" " + q + " ");
				Matcher m = p.matcher(everything);
				int termFreq = 0;
				while (m.find())
					termFreq++;
				tf[term][doc] = termFreq;
			}
		}
		System.out.println("\nTerm Frequency: [doc1, ..., doc" + nDocs + "]");
		for (int term = 0; term < query.length; term++)
			System.out.println(query[term] + ": " + Arrays.toString(tf[term]));

		int[] df = new int[query.length]; // doc frequency of the term
		for (int term = 0; term < query.length; term++) {
			int docFreq = 0;
			for (int doc = 0; doc < nDocs; doc++)
				if (tf[term][doc] > 0)
					docFreq++;
			df[term] = docFreq;
		}
		System.out.println("\nDocument Frequency:");
		System.out.println(Arrays.toString(query));
		System.out.println(Arrays.toString(df));
		System.out.println("\nidf:");
		double[][] weightT = new double[query.length][nDocs];
		for (int term = 0; term < weightT.length; term++) {
			double idf = Math.log((double) nDocs / (double) df[term]);
			if (df[term] == 0)
				idf = 0;
			System.out.println(Double.valueOf(form.format(idf)));
			for (int doc = 0; doc < nDocs; doc++)
				weightT[term][doc] = Double.valueOf(form.format(tf[term][doc] * idf));
		}
		System.out.println("\nTerm Weights:");
		for (int term = 0; term < query.length; term++)
			System.out.println(Arrays.toString(weightT[term]));

		System.out.println("\nidf:");
		double[] weightQ = new double[query.length];
		for (int term = 0; term < weightQ.length; term++) {
			double idf = Math.log((double) nDocs / (double) df[term]);
			if (df[term] == 0)
				idf = 0;
			System.out.println(Double.valueOf(form.format(idf)));
			weightQ[term] = Double.valueOf(form.format(Double.parseDouble(qf[term][1]) * idf));
		}
		System.out.println("\nQuery Weights:");
		System.out.println(Arrays.toString(weightQ));

		String[][] score = new String[nDocs][2];
		for (int doc = 0; doc < nDocs; doc++) {
			double total = 0;
			for (int term = 0; term < query.length; term++)
				total += weightT[term][doc] * weightQ[term];
			score[doc][0] = form.format(total);
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
			if (Double.parseDouble(score[doc][0]) > 0)
				System.out.println(Arrays.toString(score[doc]));

	}

	public static String removePluralS(String text) {
		String[] t = text.split(" ");
		String everything = "";
		for (String s : t){
			s = s.trim();
			s = s.replace("'", "");
			if (s.length() > 2)
				if (s.endsWith("s")) {
					s = s.substring(0, s.length() - 1);
				}
			everything += s + " ";
		}
		return everything;
	}

	public static String removeStopWords(String text) {
		String everything = text;
		for (String stop : Main.stopWordList)
			everything = everything.replace(" " + stop + " ", " ");
		return everything;
	}
}