package bertsioAurreratua;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

public class Irudia extends MagickImage {

	/**
	 * Irudi originalaren bidea (fitxategiaren izenarekin)
	 */
	private final String originalPath;

	/**
	 * Uneko irudiaren bidea (fitxategiaren izenarekin)
	 */
	private String path;

	/**
	 * Irudiaren fitxategiaren izena
	 */
	private final String fitxIzen;

	private String formatua;

	/**
	 * Irudiaren pixel kopurua (Zutabeak x Errenkadak)
	 */
	private int[] matrizeTam;

	/**
	 * Gelaxka bakoitzak har dezakeen balio maximoa
	 */
	private int koloreKop;

	/**
	 * Irudiaren pixelen datuak
	 */
	private Vector<Integer> datuak;

	/**
	 * Irudian pertsona bat agertzen bada BAI bestela EZ
	 */
	private final String klasea;

	/**
	 * Irudiaren ImageMagicken informazioa
	 */
	private ImageInfo info;

	public Irudia(ImageInfo info) throws IOException, MagickException {
		super(info);
		this.info = info;
		this.path = info.getFileName();
		this.originalPath = this.path;
		String[] s = path.split(File.separator);
		this.fitxIzen = s[s.length - 1];
		this.datuak = new Vector<Integer>();
		if (fitxIzen.startsWith("BAI"))
			this.klasea = "BAI";
		else
			this.klasea = "EZ";
		this.fitxIrakurri(this.path);
	}

	private void fitxIrakurri(String path) throws IOException {
		FileInputStream fi = new FileInputStream(path);
		BufferedReader bri = new BufferedReader(new InputStreamReader(fi));
		this.formatua = bri.readLine();
		String mTamaina = bri.readLine();
		this.koloreKop = Integer.valueOf(bri.readLine());
		String[] tam = mTamaina.split(" ");
		this.matrizeTam = new int[] { Integer.valueOf(tam[0]),
				Integer.valueOf(tam[1]) };
		String line;
		String[] rawDatuak;
		while ((line = bri.readLine()) != null) {
			rawDatuak = line.split(" ");
			for (int j = 0; j < rawDatuak.length; j++)
				if (!rawDatuak[j].equals(""))
					this.datuak.addElement(Integer.parseInt(rawDatuak[j]));
		}
	}

	public int getPixelKop() {
		return this.matrizeTam[0] * this.matrizeTam[1];
	}

	public String getDatuak() {
		String datuak = "";
		for (Integer i : this.datuak)
			datuak += String.valueOf(i) + ", ";
		datuak += klasea;
		return datuak;
	}

	public void originalaKargatu() throws IOException {
		this.path = this.originalPath;
		fitxIrakurri(this.path);
	}

//	/**
//	 * @param filePath
//	 *            fitxategiaren bidea <U>fitxategiaren izena gabe</U>. Izena,
//	 *            fitxategi originalarena izango da.
//	 * @throws IOException
//	 * @throws MagickException
//	 */
//	public void irudiaGorde(String filePath) throws IOException,
//			MagickException {
//		this.path = filePath + File.separator + fitxIzen;
//		this.setCompression(CompressionType.NoCompression);
//		info.setFileName(this.path);
//		this.setFileName(this.path);
//		this.writeImage(info);
//		//fitxIrakurri(this.path);
//	}

	public String getFitxIzen() {
		return fitxIzen;
	}
}
