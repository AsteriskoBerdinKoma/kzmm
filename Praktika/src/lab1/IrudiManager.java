package lab1;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import magick.ImageInfo;
import magick.MagickException;

public class IrudiManager {

	public static String PATH_TRAIN;

	public static String PATH_TEST;

	public static String TRAIN_SAVE_PATH;
	
	public static String TEST_SAVE_PATH;

	private Vector<Irudia> vTrain;

	private Vector<Irudia> vTest;
	
	private Vector<Irudia> vTrainUnekoa;
	private Vector<Irudia> vTestUnekoa;

	public IrudiManager(String pathTrain, String pathTest) {
		IrudiManager.PATH_TRAIN = pathTrain;
		IrudiManager.PATH_TEST = pathTest;
	}

	private Vector<Irudia> trainIrudiakKargatu() throws IOException,
			MagickException {
		String[] fz = new File(PATH_TRAIN).list();

		Vector<Irudia> vIrudiak = new Vector<Irudia>();
		for (int i = 0; i < fz.length; i++) {
			String filePath = PATH_TRAIN + File.separatorChar + fz[i];
			vIrudiak.addElement(new Irudia(new ImageInfo(filePath)));
		}
		return vIrudiak;
	}

	private Vector<Irudia> testIrudiakKargatu() throws IOException,
			MagickException {
		String[] fz = new File(PATH_TEST).list();

		Vector<Irudia> vIrudiak = new Vector<Irudia>();
		for (int i = 0; i < fz.length; i++) {
			String filePath = PATH_TEST + File.separatorChar + fz[i];
			vIrudiak.addElement(new Irudia(new ImageInfo(filePath)));
		}
		return vIrudiak;
	}

	public Vector<Irudia> getTrainIrudiak() throws IOException, MagickException {
		if (vTrain == null)
			vTrain = trainIrudiakKargatu();
		return vTrain;
	}

	public Vector<Irudia> getTestIrudiak() throws IOException, MagickException {
		if (vTest == null)
			vTest = testIrudiakKargatu();
		return vTest;
	}

	public Vector<Irudia> getAllIrudiak() throws IOException, MagickException {
		if (vTrain == null)
			vTrain = trainIrudiakKargatu();
		if (vTest == null)
			vTest = testIrudiakKargatu();

		Vector<Irudia> emaitza = new Vector<Irudia>(vTrain);
		emaitza.addAll(vTest);
		return emaitza;
	}

}
