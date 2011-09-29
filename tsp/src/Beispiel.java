import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Beispiel {

	public static void main(String[] args) {
		try {
			if (args.length > 0) {
				Scanner sc = new Scanner(new File(args[0])); // Objekt fuer den
																// Filezugriff
				if (sc.hasNextInt()) { // gibt es einen Integer im File?
					int n = sc.nextInt(); // Anzahl Staedte
					if ((n < 2) || (n > 100)) { // ist die Anzahl Staedte in
												// Ordnung?
						System.out.println("Die erste Zahl in der Datei "
								+ args[0] + " ist zu klein oder zu gross.");
					} else {
						int[] result = new int[n + 1]; // Stadt 0 bis n-1, die
														// Distanz an n-ter
														// Stelle
						int[][] d = new int[n][n]; // symmetrische Distanzmatrix
						boolean fehler = false; // Hilfsvariable
						for (int i = 0; i < n - 1; i++) {
							for (int j = i + 1; j < n; j++) { // immer i
																// ungleich j
								if (sc.hasNextInt()) { // ist noch ein Integer
														// vorhanden?
									int m = sc.nextInt(); // Distanz zwischen
															// Stadt i und Stadt
															// j
									if (m > 0) { // macht die Distanz Sinn?

										d[i][j] = m;
										d[j][i] = m; // symmetrische Matrix
									} else {
										j--; // eine Distanz <= 0 wird ignoriert
									}
								} else {
									fehler = true; // es hat zuwenige ganze
													// Zahlen > 0 im File
								}
							}
						}
						if (!fehler) {
							if (sc.hasNextInt()) { // hat es immer noch einen
													// Integer im File?
								System.out.println("Achtung: die Datei "
										+ args[0]
										+ " enthaelt zu viele Zahlen."); // nur
																			// Warnung
																			// ausgeben
							}

							long time = 0; // die Nanosekunden brauchen ein long
							printMatrix(n, d); // Ausgabe der Distanzmatrix
							time = System.nanoTime(); // Zeitmessung
							result = bestPath(n, d); // ermittlung der besten
														// Rundreise
							time = System.nanoTime() - time; // Zeitmessung
							System.out
									.println("\nLaenge eines zufaelligen Weges: "
											+ result[n]
											+ "   Zeit: "
											+ timeToString(time)); // Distanz
																	// und Zeit
							System.out.print("Zufaelliger Weg: ");
							printPath(n, result); // Ausgabe der Rundreise
						} else {
							System.out
									.println("Die Datei "
											+ args[0]
											+ " enthaelt zu wenige ganze Zahlen groesser als 0."); // Abbruch
						}
					}
				} else {
					System.out.println("Die Datei " + args[0] // Abbruch
							+ " enthaelt keine Zahlen.");
				}
			} else {
				System.out.println("Geben Sie einen Dateinamen an!"); // Abbruch
			}
		} catch (FileNotFoundException ex) {
			System.out.print(ex); // Datei wurde nicht gefunden
		}
	}

	private static int[] bestPath(int n, int[][] d) {
		int[] path = new int[n + 1];
		int bestkm = 100000;
		while (bestkm != 0) {
			bestkm = 1;
		}
		return path;
	}

	public static int[] randomPath(int n, int[][] d) { // diese Methode ist nur
														// ein Beispiel

		int[] path = new int[n + 1];
		int dist = 0;
		int swap = 0;
		int randomNumber = 0;

		for (int i = 1; i < n; i++) {

			path[i] = i;
		}

		for (int i = 1; i < n - 1; i++) {

			randomNumber = (int) Math.floor((n - i) * Math.random());
			randomNumber += i;
			swap = path[i];
			path[i] = path[randomNumber];
			path[randomNumber] = swap;
			dist += d[path[i - 1]][path[i]];
		}

		path[n] = dist + d[path[n - 2]][path[n - 1]] + d[path[n - 1]][path[0]];
		return path;
	}

	public static void printMatrix(int n, int[][] d) {
		int max = 0;
		String s = "";

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (d[i][j] > max) { // die groesste Distanz wird ermittelt
					max = d[i][j];
				}
			}
		}

		max = ("" + max).length(); // wieviele Stellen braucht die maximale
									// Distanz?
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				s = "" + d[i][j];
				while (s.length() < max) { // jede Zahl soll gleichviel Platz
											// brauchen
					s = " " + s;
				}
				System.out.print(s + " ");
			}
			System.out.println();
		}
	}

	public static void printPath(int n, int[] path) {
		for (int i = 0; i < n; i++) {
			System.out.print((path[i] + 1) + "-");
		}
		System.out.println(path[0] + 1); // die Rundreise beginnt bei Stadt 1
											// und endet bei Stadt 1
	}

	public static String timeToString(long time) { // Umrechnung von
													// Nanosekunden in
													// vernuenftige Einheit

		if (time >= (long) 1000 * 1000 * 1000 * 3600 * 24) {
			return time / 1000.0 / 1000 / 1000 / 3600 / 24 + " Tage";
		}
		if (time >= (long) 1000 * 1000 * 1000 * 3600) {
			return time / 1000.0 / 1000 / 1000 / 3600 + " Stunden";
		}
		if (time >= (long) 1000 * 1000 * 1000 * 60) {
			return time / 1000.0 / 1000 / 1000 / 60 + " Minuten";
		}
		if (time >= 1000 * 1000 * 1000) {
			return time / 1000.0 / 1000 / 1000 + " Sekunden";
		}
		if (time >= 1000 * 1000) {
			return time / 1000.0 / 1000 + " Millisekunden";
		}
		if (time >= 1000) {
			return time / 1000.0 + " Mikrosekunden";
		}
		return time + " Nanosekunden";
	}
}
