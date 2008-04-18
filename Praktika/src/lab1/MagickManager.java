package lab1;

import java.util.Vector;

import magick.ColorspaceType;
import magick.ImageMagick;
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

	public void filtroaAplikatu(Vector<Irudia> vTrainIrudiak, Vector<Irudia> vTestIrudiak) throws MagickException{
		Vector<MagickImage> trainIrudiak = new Vector<MagickImage>();
		Vector<MagickImage> testIrudiak = new Vector<MagickImage>();
		
		for(MagickImage iTrain: vTrainIrudiak){
			trainIrudiak.addElement(iTrain.blurImage(3, 0.25));
			trainIrudiak.addElement(iTrain.sharpenImage(3, 0.25));
			MagickImage lag = iTrain.cloneImage(0, 0, false);
			if(lag.thresholdImage(10))
				trainIrudiak.addElement(lag);
			trainIrudiak.addElement(iTrain.addNoiseImage(NoiseType.GaussianNoise));
			trainIrudiak.addElement(iTrain.charcoalImage(4, 1));
			lag=iTrain.cloneImage(0, 0, false);
			if(lag.contrastImage(true))
				trainIrudiak.addElement(lag);
			trainIrudiak.addElement(iTrain.edgeImage(3));
			trainIrudiak.addElement(iTrain.embossImage(3, 0.25));
			lag=iTrain.cloneImage(0, 0, false);
			if(lag.equalizeImage())
				trainIrudiak.addElement(lag);
			lag=iTrain.cloneImage(0, 0, false);
			if(lag.levelImage("black"))
				trainIrudiak.addElement(lag);
			lag=iTrain.cloneImage(0, 0, false);
			if(lag.negateImage(1))
				trainIrudiak.addElement(lag);
			lag=iTrain.cloneImage(0, 0, false);
			if(lag.normalizeImage())
				trainIrudiak.addElement(lag);
			lag=iTrain.cloneImage(0, 0, false);
			lag.segmentImage(ColorspaceType.GRAYColorspace, 0.25, 1.5);
			trainIrudiak.addElement(lag);
			lag=iTrain.cloneImage(0, 0, false);
			lag.solarizeImage(10);
			trainIrudiak.addElement(lag);
			
			vTrainIrudiFiltroekin.addElement(trainIrudiak);
		}
	}
	
	public void edge() {

	}

	
	public void scale(Vector<Irudia> ir) throws MagickException {
//		ImageInfo info = new ImageInfo();
//		MagickImage image = new MagickImage(new ImageInfo("BAIimg_00001.pgm"));
//		MagickImage bigger = image.scaleImage(1200, 900);
//		bigger.setFileName("froga/bigger.pgm");
//		bigger.writeImage(info);
		
		for(Irudia i: ir){
//			ImageInfo info = new ImageInfo();
//			MagickImage mi = i.scaleImage(2000, 900);
//			mi.setFileName("frogaScale/"+ i.getFitxIzen());
//			//System.out.println(1.1 + mi.getFileName() + " == " + info.getFileName());
//			mi.writeImage(info);
//			System.out.println(1.2);
//			try {
//				//i.irudiaGorde("frogaScale");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}

}
