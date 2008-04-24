package lab1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Vector;

public class ARFFManager {

	public ARFFManager() {

	}

	public String toARFF(Vector<Irudia> vIrudiak) {
		int pixelKop = vIrudiak.firstElement().getPixelKop();
		String arff;

		arff = "@RELATION KZMM\n";
		for (int i = 1; i <= pixelKop; i++)
			arff += "@ATTRIBUTE P" + i + " NUMERIC\n";
		arff += "@ATTRIBUTE KLASEA {BAI, EZ}\n";
		arff += "@DATA\n";

		System.out.println("Kopurua: " + vIrudiak.size());
		for (Irudia irudi : vIrudiak) {
			System.out.println(irudi.getFitxIzen());
			if (irudi.getPixelKop() == pixelKop)
				arff += irudi.getDatuak() + "\n";
		}

		System.out.println("String length: " + arff.length());
		return arff;
	}

	/**
	 * @param filePath
	 *            All diskretizatuaren fitxategia
	 * @param trainKop
	 *            Zenbat train argazki dauden
	 * @return Train eta Test arff-ak diskretizatuta. 0 posizioan TrainD dago
	 *         eta 1 posizioan TestD.
	 * @throws IOException
	 */
	public String[] separateDiscretized(String filePath, int trainKop)
			throws IOException {
		FileInputStream fi = new FileInputStream(filePath);
		BufferedReader bri = new BufferedReader(new InputStreamReader(fi));

		String train = "", test = "";

		String lerroa;
		while (!(lerroa = bri.readLine()).startsWith("\'\\\'")) {
			train += lerroa + "\n";
			test += lerroa + "\n";
		}

		for (int i = 0; i < trainKop; i++) {
			train += lerroa + "\n";
			lerroa = bri.readLine();
		}

		while (lerroa != null) {
			test += lerroa + "\n";
			lerroa = bri.readLine();
		}
		return new String[] { train, test };
	}

	/**
	 * @param filePath
	 * @param trainKop
	 * @return Train eta Test arff-ak diskretizatuta. 0 posizioan TrainD dago
	 *         eta 1 posizioan TestD.
	 * @throws IOException
	 */
	public String[] separateDiscretizedData(String data, int trainKop)
			throws IOException {
		BufferedReader bri = new BufferedReader(new StringReader(data));

		String train = "", test = "";

		String lerroa;
		while (!(lerroa = bri.readLine()).startsWith("\'\\\'")) {
			train += lerroa + "\n";
			test += lerroa + "\n";
		}

		for (int i = 0; i < trainKop; i++) {
			train += lerroa + "\n";
			lerroa = bri.readLine();
		}

		while (lerroa != null) {
			test += lerroa + "\n";
			lerroa = bri.readLine();
		}
		return new String[] { train, test };
	}
}
