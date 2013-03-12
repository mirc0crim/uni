import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyAnalysis {

	private static String docPath = "D:\\lucene\\corpus\\";

	public static void doAnalysis(String document) throws IOException {

		System.out.println("Analysis of Document: " + document);

		File f = new File(docPath + document);
		BufferedReader br = new BufferedReader(new FileReader(f.getPath()));
		String everything = "";
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			everything = sb.toString();
		} finally {
			br.close();
		}
		System.out.println("\nDocument Text:");
		System.out.println(everything);
		everything = everything.replace(",", "").replace(".", "").replace("(", "")
				.replace(")", "").toLowerCase()
				+ " ";
		String[] words = everything.split(" ");
		Set<String> noDupSet = new HashSet<String>();
		for (String word : words)
			noDupSet.add(word);
		String[] noDupWords = noDupSet.toArray(new String[noDupSet.size()]);
		String[][] wf = new String[noDupSet.size()][2]; // word frequency
		for (int i = 0; i < noDupSet.size(); i++) {
			Pattern p = Pattern.compile(" " + noDupWords[i] + " ");
			Matcher m = p.matcher(everything);
			int termFreq = 0;
			while (m.find())
				termFreq++;
			wf[i][1] = noDupWords[i];
			wf[i][0] = termFreq + "";
		}

		Arrays.sort(wf, new Comparator<String[]>() {
			@Override
			public int compare(final String[] entry1, final String[] entry2) {
				final int s1 = Integer.parseInt(entry1[0]);
				final int s2 = Integer.parseInt(entry2[0]);
				return s2 - s1;
			}
		});
		System.out.println("\nWord Frequency:");
		System.out.println("Appear " + wf[0][0] + " times");
		String out = "";
		for (int i = 0; i < wf.length; i++) {
			if (i > 0)
				if (Integer.parseInt(wf[i - 1][0]) > Integer.parseInt(wf[i][0])) {
					System.out.println(out.substring(0, out.length() - 2));
					System.out.println("\nAppear " + wf[i][0] + " times");
					out = "";
				}
			out += wf[i][1] + ", ";
		}
		System.out.println(out.substring(0, out.length() - 2));
	}
}
