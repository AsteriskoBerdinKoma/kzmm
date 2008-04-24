package lab1;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import magick.ImageInfo;
import magick.MagickException;

public class IrudiManager {

	public static String PATH_TRAIN;

	public static String PATH_TEST;

	public static String UNEKO_TRAIN_PATH;
	
	public static String UNEKO_TEST_PATH;

	private Vector<Irudia> vTrainOriginalak;

	private Vector<Irudia> vTestOriginalak;
	
	private Vector<Irudia> vTrainUnekoa;
	private Vector<Irudia> vTestUnekoa;
	
	private final MagickManager magickKud;

	public IrudiManager(String pathTrain, String pathTest) throws MagickException, IOException {
		IrudiManager.PATH_TRAIN = pathTrain;
		IrudiManager.PATH_TEST = pathTest;
		IrudiManager.UNEKO_TRAIN_PATH = pathTrain;
		IrudiManager.UNEKO_TEST_PATH = pathTest;
		magickKud = new MagickManager();
		magickKud.filtroaAplikatu(this.getTrainOriginalak(), this.getTestOriginalak());
	}

	private Vector<Irudia> irudiakKargatu(String path) throws IOException,
			MagickException {
		String[] fz = new File(path).list();

		Vector<Irudia> vIrudiak = new Vector<Irudia>();
		for (int i = 0; i < fz.length; i++) {
			String filePath = path + File.separatorChar + fz[i];
			vIrudiak.addElement(new Irudia(new ImageInfo(filePath)));
		}
		return vIrudiak;
	}

	public Vector<Irudia> getTrainOriginalak() throws IOException, MagickException {
		if (vTrainOriginalak == null)
			vTrainOriginalak = irudiakKargatu(PATH_TRAIN);
		return vTrainOriginalak;
	}

	public Vector<Irudia> getTestOriginalak() throws IOException, MagickException {
		if (vTestOriginalak == null)
			vTestOriginalak = irudiakKargatu(PATH_TEST);
		return vTestOriginalak;
	}

	public Vector<Irudia> getAllOriginalak() throws IOException, MagickException {
		if (vTrainOriginalak == null)
			vTrainOriginalak = irudiakKargatu(PATH_TRAIN);
		if (vTestOriginalak == null)
			vTestOriginalak = irudiakKargatu(PATH_TEST);

		Vector<Irudia> emaitza = new Vector<Irudia>(vTrainOriginalak);
		emaitza.addAll(vTestOriginalak);
		return emaitza;
	}

	public Vector<Irudia> getUnekoTrain() throws IOException, MagickException{
		if (vTrainUnekoa == null)
			vTrainUnekoa = irudiakKargatu(UNEKO_TRAIN_PATH);
		return vTrainUnekoa;
	}
	
	public Vector<Irudia> getUnekoTest() throws IOException, MagickException{
		if (vTestUnekoa == null)
			vTestUnekoa = irudiakKargatu(UNEKO_TEST_PATH);
		return vTestUnekoa;
	}
	
	public Vector<Irudia> getUnekoAllIrudiak() throws IOException, MagickException {
		if (vTrainUnekoa == null)
			vTrainUnekoa = irudiakKargatu(UNEKO_TRAIN_PATH);
		if (vTestUnekoa == null)
			vTestUnekoa = irudiakKargatu(UNEKO_TEST_PATH);

		Vector<Irudia> emaitza = new Vector<Irudia>(vTrainUnekoa);
		emaitza.addAll(vTestUnekoa);
		return emaitza;
	}
	
	public boolean next() throws MagickException, IOException{
		boolean b = magickKud.next();
		UNEKO_TRAIN_PATH = magickKud.getUnekoTrainPath();
		UNEKO_TEST_PATH = magickKud.getUnekoTestPath();
		return b;
	}
	
	public String getUnekoInfo(){
		String filtro = magickKud.getUnekoFiltroa();
		String atributuak = magickKud.getUnekoAtributuak();
		
		return "Filtroa: " + filtro + "\nAtributuak:\n\t" + atributuak;
	}
	
	public String getUnekoFiltroa(){
		return magickKud.getUnekoFiltroa();
	}
}
