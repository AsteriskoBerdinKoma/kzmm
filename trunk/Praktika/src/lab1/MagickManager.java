package lab1;

import java.util.Vector;

import magick.ColorspaceType;
import magick.MagickException;
import magick.MagickImage;
import magick.NoiseType;

public class MagickManager {

	private Vector<Vector<MagickImage>> vTrainIrudiFiltroekin;

	private Vector<Vector<MagickImage>> vTestIrudiFiltroekin;

	public MagickManager() {
		vTrainIrudiFiltroekin = new Vector<Vector<MagickImage>>();
		vTestIrudiFiltroekin = new Vector<Vector<MagickImage>>();
	}

	public void filtroaAplikatu(Vector<Irudia> vTrainIrudiak,
			Vector<Irudia> vTestIrudiak) throws MagickException {
		Vector<MagickImage> trainIrudiak = new Vector<MagickImage>();
		Vector<MagickImage> testIrudiak = new Vector<MagickImage>();
		MagickImage lag;

		for (MagickImage iTrain : vTrainIrudiak) {
			// Blur
			trainIrudiak.addElement(iTrain.blurImage(0.25, 10));

			// Sharpen
			trainIrudiak.addElement(iTrain.sharpenImage(0.25, 85));

			// Threshold
			lag = iTrain.cloneImage(0, 0, false);
			if (lag.thresholdImage(10))
				trainIrudiak.addElement(lag);

			// Add Noise
			trainIrudiak.addElement(iTrain
					.addNoiseImage(NoiseType.GaussianNoise));

			// Charcoal
			trainIrudiak.addElement(iTrain.charcoalImage(4, 1));

			// Contrast
			lag = iTrain.cloneImage(0, 0, false);
			if (lag.contrastImage(true))
				trainIrudiak.addElement(lag);

			// Edge
			trainIrudiak.addElement(iTrain.edgeImage(3));

			// Emboss
			trainIrudiak.addElement(iTrain.embossImage(3, 0.25));

			// Equalize
			lag = iTrain.cloneImage(0, 0, false);
			if (lag.equalizeImage())
				trainIrudiak.addElement(lag);

			// Level
			lag = iTrain.cloneImage(0, 0, false);
			if (lag.levelImage("black"))
				trainIrudiak.addElement(lag);

			// Negative
			lag = iTrain.cloneImage(0, 0, false);
			if (lag.negateImage(1))
				trainIrudiak.addElement(lag);

			// Normalize
			lag = iTrain.cloneImage(0, 0, false);
			if (lag.normalizeImage())
				trainIrudiak.addElement(lag);

			// Segment
			lag = iTrain.cloneImage(0, 0, false);
			lag.segmentImage(ColorspaceType.GRAYColorspace, 0.25, 1.5);
			trainIrudiak.addElement(lag);

			// Solarize
			lag = iTrain.cloneImage(0, 0, false);
			lag.solarizeImage(10);
			trainIrudiak.addElement(lag);

			vTrainIrudiFiltroekin.addElement(trainIrudiak);
		}

		for (MagickImage iTest : vTestIrudiak) {
			// Blur
			testIrudiak.addElement(iTest.blurImage(0.25, 10));

			// Sharpen
			testIrudiak.addElement(iTest.sharpenImage(0.25, 85));

			// Threshold
			lag = iTest.cloneImage(0, 0, false);
			if (lag.thresholdImage(10))
				testIrudiak.addElement(lag);

			// Add Noise
			testIrudiak
					.addElement(iTest.addNoiseImage(NoiseType.GaussianNoise));

			// Charcoal
			testIrudiak.addElement(iTest.charcoalImage(4, 1));

			// Contrast
			lag = iTest.cloneImage(0, 0, false);
			if (lag.contrastImage(true))
				testIrudiak.addElement(lag);

			// Edge
			testIrudiak.addElement(iTest.edgeImage(3));

			// Emboss
			testIrudiak.addElement(iTest.embossImage(3, 0.25));

			// Equalize
			lag = iTest.cloneImage(0, 0, false);
			if (lag.equalizeImage())
				testIrudiak.addElement(lag);

			// Level
			lag = iTest.cloneImage(0, 0, false);
			if (lag.levelImage("black"))
				testIrudiak.addElement(lag);

			// Negative
			lag = iTest.cloneImage(0, 0, false);
			if (lag.negateImage(1))
				testIrudiak.addElement(lag);

			// Normalize
			lag = iTest.cloneImage(0, 0, false);
			if (lag.normalizeImage())
				testIrudiak.addElement(lag);

			// Segment
			lag = iTest.cloneImage(0, 0, false);
			lag.segmentImage(ColorspaceType.GRAYColorspace, 0.25, 1.5);
			testIrudiak.addElement(lag);

			// Solarize
			lag = iTest.cloneImage(0, 0, false);
			lag.solarizeImage(10);
			testIrudiak.addElement(lag);

			vTestIrudiFiltroekin.addElement(testIrudiak);
		}
	}

	public void edge() {

	}

	public void scale(Vector<Irudia> ir) throws MagickException {
		// ImageInfo info = new ImageInfo();
		// MagickImage image = new MagickImage(new
		// ImageInfo("BAIimg_00001.pgm"));
		// MagickImage bigger = image.scaleImage(1200, 900);
		// bigger.setFileName("froga/bigger.pgm");
		// bigger.writeImage(info);

		for (Irudia i : ir) {
			// ImageInfo info = new ImageInfo();
			// MagickImage mi = i.scaleImage(2000, 900);
			// mi.setFileName("frogaScale/"+ i.getFitxIzen());
			// //System.out.println(1.1 + mi.getFileName() + " == " +
			// info.getFileName());
			// mi.writeImage(info);
			// System.out.println(1.2);
			// try {
			// //i.irudiaGorde("frogaScale");
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		}
	}

}
