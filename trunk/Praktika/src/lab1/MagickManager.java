package lab1;

import java.io.IOException;
import java.util.Vector;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

public class MagickManager {

	public MagickManager() {
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
