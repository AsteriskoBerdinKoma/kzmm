package bertsioAurreratua;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import magick.ColorspaceType;
import magick.CompressionType;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import magick.NoiseType;

public class MagickManager {

	private Vector<MagickImages> vTrainIrudiFiltroekin;

	private int kontTrain;

	private Vector<MagickImages> vTestIrudiFiltroekin;

	private int kontTest;

	private MagickImages unekoTrains;

	private MagickImages unekoTests;

	private String unekoFiltroa;

	private String unekoAtrib;

	private String unekoTrainPath;

	private String unekoTestPath;

	public MagickManager() {
		vTrainIrudiFiltroekin = new Vector<MagickImages>();
		vTestIrudiFiltroekin = new Vector<MagickImages>();
		kontTrain = 0;
		kontTest = 0;
	}

	private MagickImages blur(Vector<Irudia> vIrudiak) throws MagickException {
		MagickImages irudiak = new MagickImages("Blur");
		irudiak.setAtributuak("radius:0.25, sigma:10");
		for (MagickImage irudia : vIrudiak)
			irudiak.addElement(irudia.blurImage(0.25, 10));
		return irudiak;
	}

	private MagickImages sharpen(Vector<Irudia> vIrudiak)
			throws MagickException {
		MagickImages irudiak = new MagickImages("Sharpen");
		irudiak.setAtributuak("radius: 0.25, sigma: 85");
		for (MagickImage irudia : vIrudiak)
			irudiak.addElement(irudia.sharpenImage(0.25, 85));
		return irudiak;
	}

	private MagickImages threshold(Vector<Irudia> vIrudiak)
			throws MagickException {
		MagickImages irudiak = new MagickImages("Threshold");
		irudiak.setAtributuak("threshold: 10");
		MagickImage lag;
		for (MagickImage irudia : vIrudiak) {
			lag = irudia.cloneImage(0, 0, false);
			if (lag.thresholdImage(10))
				irudiak.addElement(lag);
		}
		return irudiak;
	}

	private MagickImages addNoise(Vector<Irudia> vIrudiak)
			throws MagickException {
		MagickImages irudiak = new MagickImages("Add Noise");
		irudiak.setAtributuak("noise type: Gaussian Noise");
		for (MagickImage irudia : vIrudiak)
			irudiak.addElement(irudia.addNoiseImage(NoiseType.GaussianNoise));
		return irudiak;
	}

	private MagickImages charcoal(Vector<Irudia> vIrudiak)
			throws MagickException {
		MagickImages irudiak = new MagickImages("Charcoal");
		irudiak.setAtributuak("radius: 4, sigma: 1");
		for (MagickImage irudia : vIrudiak)
			irudiak.addElement(irudia.charcoalImage(4, 1));
		return irudiak;
	}

	private MagickImages contrast(Vector<Irudia> vIrudiak)
			throws MagickException {
		MagickImages irudiak = new MagickImages("Contrast");
		irudiak.setAtributuak("sharpen: true");
		MagickImage lag;
		for (MagickImage irudia : vIrudiak) {
			lag = irudia.cloneImage(0, 0, false);
			if (lag.contrastImage(true))
				irudiak.addElement(lag);
		}
		return irudiak;
	}

	private MagickImages chop(Vector<Irudia> vIrudiak) throws MagickException {
		MagickImages irudiak = new MagickImages("Chop");
		irudiak.setAtributuak("3x3");
		for (MagickImage irudia : vIrudiak)
			irudiak.addElement(irudia.chopImage(new Rectangle(new Dimension(3, 3))));
		return irudiak;
	}

	private MagickImages edge(Vector<Irudia> vIrudiak) throws MagickException {
		MagickImages irudiak = new MagickImages("Edge");
		irudiak.setAtributuak("radius: 3");
		for (MagickImage irudia : vIrudiak)
			irudiak.addElement(irudia.edgeImage(3));
		return irudiak;
	}

	private MagickImages emboss(Vector<Irudia> vIrudiak) throws MagickException {
		MagickImages irudiak = new MagickImages("Emboss");
		irudiak.setAtributuak("radius: 3, sigma: 0,25");
		for (MagickImage irudia : vIrudiak)
			irudiak.addElement(irudia.embossImage(3, 0.25));
		return irudiak;
	}

	private MagickImages equalize(Vector<Irudia> vIrudiak)
			throws MagickException {
		MagickImages irudiak = new MagickImages("Equalize");
		// irudiak.setAtributuak("");
		MagickImage lag;
		for (MagickImage irudia : vIrudiak) {
			lag = irudia.cloneImage(0, 0, false);
			if (lag.equalizeImage())
				irudiak.addElement(lag);
		}
		return irudiak;
	}

	private MagickImages level(Vector<Irudia> vIrudiak) throws MagickException {
		MagickImages irudiak = new MagickImages("Level");
		irudiak.setAtributuak("levels: black");
		MagickImage lag;
		for (MagickImage irudia : vIrudiak) {
			lag = irudia.cloneImage(0, 0, false);
			if (lag.levelImage("black"))
				irudiak.addElement(lag);
		}
		return irudiak;
	}

	private MagickImages negative(Vector<Irudia> vIrudiak)
			throws MagickException {
		MagickImages irudiak = new MagickImages("Negative");
		irudiak.setAtributuak("grayscale: 1");
		MagickImage lag;
		for (MagickImage irudia : vIrudiak) {
			lag = irudia.cloneImage(0, 0, false);
			if (lag.negateImage(1))
				irudiak.addElement(lag);
		}
		return irudiak;
	}

	private MagickImages normalize(Vector<Irudia> vIrudiak)
			throws MagickException {
		MagickImages irudiak = new MagickImages("Normalize");
		// irudiak.setAtributuak("");
		MagickImage lag;
		for (MagickImage irudia : vIrudiak) {
			lag = irudia.cloneImage(0, 0, false);
			if (lag.normalizeImage())
				irudiak.addElement(lag);
		}
		return irudiak;
	}

	private MagickImages segment(Vector<Irudia> vIrudiak)
			throws MagickException {
		MagickImages irudiak = new MagickImages("Segment");
		irudiak
				.setAtributuak("colorspace: GRAY Colorspace, cluster threshold: 0,25, smoothing threshold: 1,5");
		MagickImage lag;
		for (MagickImage irudia : vIrudiak) {
			lag = irudia.cloneImage(0, 0, false);
			lag.segmentImage(ColorspaceType.GRAYColorspace, 0.25, 1.5);
			irudiak.addElement(lag);
		}
		return irudiak;
	}

	private MagickImages solarize(Vector<Irudia> vIrudiak)
			throws MagickException {
		MagickImages irudiak = new MagickImages("Solarize");
		irudiak.setAtributuak("threshold: 10");
		MagickImage lag;
		for (MagickImage irudia : vIrudiak) {
			lag = irudia.cloneImage(0, 0, false);
			lag.solarizeImage(10);
			irudiak.addElement(lag);
		}
		return irudiak;
	}

	public void filtroaAplikatu(Vector<Irudia> vTrainIrudiak,
			Vector<Irudia> vTestIrudiak) throws MagickException {

		this.vTrainIrudiFiltroekin = new Vector<MagickImages>();
		this.kontTrain = 0;
		this.vTestIrudiFiltroekin = new Vector<MagickImages>();
		this.kontTest = 0;

		vTrainIrudiFiltroekin.addElement(blur(vTrainIrudiak));
		vTestIrudiFiltroekin.addElement(blur(vTestIrudiak));

		vTrainIrudiFiltroekin.addElement(sharpen(vTrainIrudiak));
		vTestIrudiFiltroekin.addElement(sharpen(vTestIrudiak));

		vTrainIrudiFiltroekin.addElement(threshold(vTrainIrudiak));
		vTestIrudiFiltroekin.addElement(threshold(vTestIrudiak));

		vTrainIrudiFiltroekin.addElement(addNoise(vTrainIrudiak));
		vTestIrudiFiltroekin.addElement(addNoise(vTestIrudiak));

		vTrainIrudiFiltroekin.addElement(charcoal(vTrainIrudiak));
		vTestIrudiFiltroekin.addElement(charcoal(vTestIrudiak));

		vTrainIrudiFiltroekin.addElement(contrast(vTrainIrudiak));
		vTestIrudiFiltroekin.addElement(contrast(vTestIrudiak));

		vTrainIrudiFiltroekin.addElement(edge(vTrainIrudiak));
		vTestIrudiFiltroekin.addElement(edge(vTestIrudiak));

		vTrainIrudiFiltroekin.addElement(emboss(vTrainIrudiak));
		vTestIrudiFiltroekin.addElement(emboss(vTestIrudiak));

		vTrainIrudiFiltroekin.addElement(equalize(vTrainIrudiak));
		vTestIrudiFiltroekin.addElement(equalize(vTestIrudiak));

		vTrainIrudiFiltroekin.addElement(level(vTrainIrudiak));
		vTestIrudiFiltroekin.addElement(level(vTestIrudiak));

		vTrainIrudiFiltroekin.addElement(negative(vTrainIrudiak));
		vTestIrudiFiltroekin.addElement(negative(vTestIrudiak));

		vTrainIrudiFiltroekin.addElement(normalize(vTrainIrudiak));
		vTestIrudiFiltroekin.addElement(normalize(vTestIrudiak));

		vTrainIrudiFiltroekin.addElement(segment(vTrainIrudiak));
		vTestIrudiFiltroekin.addElement(segment(vTestIrudiak));

		vTrainIrudiFiltroekin.addElement(solarize(vTrainIrudiak));
		vTestIrudiFiltroekin.addElement(solarize(vTestIrudiak));
		
		vTrainIrudiFiltroekin.addElement(chop(vTrainIrudiak));
		vTestIrudiFiltroekin.addElement(chop(vTestIrudiak));
	}

	private class MagickImages extends Vector<MagickImage> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private final String filtroa;

		private String atributuak;

		public MagickImages(String filtroa) {
			super();
			this.filtroa = filtroa;
		}

		public String getFiltroa() {
			return filtroa;
		}

		public String getAtributuak() {
			return atributuak;
		}

		public void setAtributuak(String atributuak) {
			this.atributuak = atributuak;
		}
	}

	public Vector<MagickImages> getVTestIrudiFiltroekin() {
		return vTestIrudiFiltroekin;
	}

	public Vector<MagickImages> getVTrainIrudiFiltroekin() {
		return vTrainIrudiFiltroekin;
	}

	public boolean next() throws MagickException, IOException {
		System.out.println(vTrainIrudiFiltroekin.size() - kontTrain +" filtro gelditzen dira");
		if (kontTrain < vTrainIrudiFiltroekin.size()
				&& kontTest < vTestIrudiFiltroekin.size()) {
			unekoTrains = vTrainIrudiFiltroekin.elementAt(kontTrain);
			kontTrain++;
			unekoTests = vTestIrudiFiltroekin.elementAt(kontTest);
			kontTest++;
			unekoFiltroa = unekoTrains.getFiltroa();
			unekoAtrib = unekoTrains.getAtributuak();
			unekoTrainPath = "irudiak" + File.separator + unekoFiltroa
					+ File.separator + "train";
			unekoTestPath = "irudiak" + File.separator + unekoFiltroa
					+ File.separator + "test";

			for (MagickImage imageTrain : unekoTrains)
				irudiaGorde(imageTrain, this.unekoTrainPath);

			for (MagickImage imageTest : unekoTests)
				irudiaGorde(imageTest, this.unekoTestPath);

			return true;
		} else {
			unekoTrains = null;
			unekoTests = null;
			unekoFiltroa = null;
			unekoAtrib = null;
			unekoTrainPath = "";
			unekoTestPath = "";
			return false;
		}

	}

	public boolean hasMoreElements() {
		return (kontTrain < vTrainIrudiFiltroekin.size() && kontTest < vTestIrudiFiltroekin
				.size());
	}

	public void reset() {
		unekoTrains = null;
		kontTrain = 0;
		unekoTests = null;
		kontTest = 0;
		unekoFiltroa = null;
		unekoAtrib = null;
	}

	public String getUnekoFiltroa() {
		return unekoFiltroa;
	}

	public String getUnekoAtributuak() {
		return unekoAtrib;
	}

	// public Vector<Irudia> getUnekoTrain() throws MagickException, IOException
	// {
	// Vector<Irudia> irudiak = new Vector<Irudia>();
	//		
	// for (MagickImage image : unekoTrains)
	// irudiak.addElement(irudiaGorde(image, this.unekoTrainPath));
	//
	// return irudiak;
	// }
	//
	// public Vector<Irudia> getUnekoTest() throws MagickException, IOException
	// {
	// Vector<Irudia> irudiak = new Vector<Irudia>();
	//		
	// for (MagickImage image : unekoTests)
	// irudiak.addElement(irudiaGorde(image, this.unekoTestPath));
	//
	// return irudiak;
	// }

	private Irudia irudiaGorde(MagickImage irudia, String path)
			throws MagickException, IOException {

		File f = new File(path);
		// Irudi zaharrak ezabatu eta karpeta berria sortu
		// deleteDirectory(f);
		f.mkdirs();

		// Irudiaren fitxategiaren izena lortu
		String[] s = irudia.getFileName().split(File.separator);
		String fitxIzen = s[s.length - 1];

		String pathOsoa = path + File.separator + fitxIzen;
		irudia.setCompression(CompressionType.NoCompression);
		ImageInfo info = new ImageInfo(pathOsoa);
		irudia.setFileName(pathOsoa);
		irudia.writeImage(info);

		return new Irudia(new ImageInfo(pathOsoa));
	}

//	private boolean deleteDirectory(File path) {
//		if (path.exists()) {
//			File[] files = path.listFiles();
//			for (int i = 0; i < files.length; i++) {
//				if (files[i].isDirectory()) {
//					deleteDirectory(files[i]);
//				} else {
//					files[i].delete();
//				}
//			}
//		}
//		return (path.delete());
//	}

	public String getUnekoTestPath() {
		return unekoTestPath;
	}

	public String getUnekoTrainPath() {
		return unekoTrainPath;
	}
}
