package oinarrizkoBertsioa;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.TitledBorder;

public class Nagusia extends JFrame implements ActionListener {

	private JTextField textField;

	private JTextField testTextField;

	private JTextField trainTextField;

	private JButton trainButton;

	private JButton testButton;

	private JButton arffFitxategiakSortuButton;

	private JButton arffDiskretizatuakSortuButton;

	private JButton gainbegiratutakoDiskretizazioaButton;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IrudiManager irudiak;

	private ARFFManager arffak;

	private WekaManager weka;

	private Vector<Irudia> vTrain;

	private Vector<Irudia> vTest;

	private Vector<Irudia> vAll;

	private boolean[] checkKatalogo = new boolean[2];

	/**
	 * Create the frame
	 */
	public Nagusia() {
		super();
		setTitle("Irudi Sailkatzailea");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		irudiak = new IrudiManager();
		arffak = new ARFFManager();
		try {
			weka = new WekaManager();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vTrain = new Vector<Irudia>();
		vTest = new Vector<Irudia>();
		vAll = new Vector<Irudia>();

		JPanel panel;
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Sailkatzailearen datuak",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));

		JLabel entrenamendurakoIrudienBideeLabel;
		entrenamendurakoIrudienBideeLabel = new JLabel();
		entrenamendurakoIrudienBideeLabel
				.setText("Entrenamendurako irudien bide-izena:");

		trainTextField = new JTextField();

		JLabel frogarakoIrudienBideizenaLabel;
		frogarakoIrudienBideizenaLabel = new JLabel();
		frogarakoIrudienBideizenaLabel.setText("Frogarako irudien bide-izena:");

		testTextField = new JTextField();

		trainButton = new JButton();
		trainButton.setText("Arakatu...");
		trainButton.addActionListener(this);

		testButton = new JButton();
		testButton.setText("Arakatu...");
		testButton.addActionListener(this);

		JPanel panel_1;
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Ekintzak",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));

		arffFitxategiakSortuButton = new JButton();
		arffFitxategiakSortuButton
				.setToolTipText("TRAIN.arff, TEST.arff eta ALL.arff fitxategiak sortzen ditu.");
		arffFitxategiakSortuButton.addActionListener(this);
		arffFitxategiakSortuButton.setText("ARFF fitxategiak sortu");
		arffFitxategiakSortuButton.setEnabled(false);

		arffDiskretizatuakSortuButton = new JButton();
		arffDiskretizatuakSortuButton
				.setToolTipText("ARFF fitxategiak diskretizatzeko.\nTRAIND.arff, TESTD.arff eta ALLD.arff fitxategiak sortzen ditu.");
		arffDiskretizatuakSortuButton.addActionListener(this);
		arffDiskretizatuakSortuButton
				.setText("Gainbegiratutako Diskretizazioa");
		arffDiskretizatuakSortuButton.setEnabled(false);

		gainbegiratutakoDiskretizazioaButton = new JButton();
		gainbegiratutakoDiskretizazioaButton
				.setToolTipText("Emandako balioaren arabera diskretizatzen ditu fitxategiak.\nTRAINDS.arff, TESTDS.arff, ALLDS.arff fitxategiak sortzen dira.");
		gainbegiratutakoDiskretizazioaButton.addActionListener(this);
		gainbegiratutakoDiskretizazioaButton
				.setText("Gainbegiratu gabeko Diskretizazioa");
		gainbegiratutakoDiskretizazioaButton.setEnabled(false);

		textField = new JTextField();
		final GroupLayout groupLayout = new GroupLayout(
				(JComponent) getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				GroupLayout.Alignment.TRAILING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap().addGroup(
						groupLayout.createParallelGroup(
								GroupLayout.Alignment.TRAILING).addComponent(
								panel_1, GroupLayout.Alignment.LEADING,
								GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
								.addComponent(panel,
										GroupLayout.PREFERRED_SIZE, 475,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 139,
								GroupLayout.PREFERRED_SIZE).addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 136,
								Short.MAX_VALUE).addContainerGap()));
		final GroupLayout groupLayout_1 = new GroupLayout((JComponent) panel);
		groupLayout_1
				.setHorizontalGroup(groupLayout_1
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								groupLayout_1
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout_1
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																GroupLayout.Alignment.TRAILING,
																groupLayout_1
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout_1
																						.createParallelGroup(
																								GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								testTextField,
																								GroupLayout.DEFAULT_SIZE,
																								343,
																								Short.MAX_VALUE)
																						.addComponent(
																								trainTextField,
																								GroupLayout.DEFAULT_SIZE,
																								343,
																								Short.MAX_VALUE))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				groupLayout_1
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								trainButton,
																								GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								testButton,
																								GroupLayout.Alignment.TRAILING)))
														.addComponent(
																entrenamendurakoIrudienBideeLabel)
														.addComponent(
																frogarakoIrudienBideizenaLabel))
										.addContainerGap()));
		groupLayout_1.setVerticalGroup(groupLayout_1.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				groupLayout_1.createSequentialGroup().addComponent(
						entrenamendurakoIrudienBideeLabel).addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED).addGroup(
						groupLayout_1.createParallelGroup(
								GroupLayout.Alignment.BASELINE).addComponent(
								trainButton).addComponent(trainTextField,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)).addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED).addComponent(
						frogarakoIrudienBideizenaLabel).addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED).addGroup(
						groupLayout_1.createParallelGroup(
								GroupLayout.Alignment.BASELINE).addComponent(
								testButton).addComponent(testTextField,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)).addContainerGap(
						16, Short.MAX_VALUE)));
		panel.setLayout(groupLayout_1);
		final GroupLayout groupLayout_2 = new GroupLayout((JComponent) panel_1);
		groupLayout_2
				.setHorizontalGroup(groupLayout_2
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								groupLayout_2
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout_2
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																arffFitxategiakSortuButton)
														.addComponent(
																arffDiskretizatuakSortuButton)
														.addGroup(
																groupLayout_2
																		.createSequentialGroup()
																		.addComponent(
																				gainbegiratutakoDiskretizazioaButton)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				textField,
																				GroupLayout.PREFERRED_SIZE,
																				33,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(156, Short.MAX_VALUE)));
		groupLayout_2
				.setVerticalGroup(groupLayout_2
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								groupLayout_2
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												arffFitxategiakSortuButton)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												arffDiskretizatuakSortuButton)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												groupLayout_2
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																gainbegiratutakoDiskretizazioaButton)
														.addComponent(
																textField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		groupLayout_2.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { arffDiskretizatuakSortuButton,
						arffFitxategiakSortuButton,
						gainbegiratutakoDiskretizazioaButton });
		groupLayout_2.linkSize(javax.swing.SwingConstants.VERTICAL,
				new java.awt.Component[] { arffDiskretizatuakSortuButton,
						arffFitxategiakSortuButton,
						gainbegiratutakoDiskretizazioaButton });
		panel_1.setLayout(groupLayout_2);
		getContentPane().setLayout(groupLayout);
		pack();
		//
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		File katalogoa;

		if (e.getSource() == trainButton) {
			vTrain.removeAllElements();
			vAll.removeAllElements();
			checkKatalogo[0] = false;
			int result = jfc.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {

				try {
					katalogoa = jfc.getSelectedFile();
					if (isKatalogoEgoki(katalogoa)) {
						// Irudi guztien informazioa lortzen dugu
						irudiak.setTrainPath(katalogoa.getPath());
						vTrain = irudiak.getTrain();
						trainTextField.setText(katalogoa.getPath());

//						if (vTrain.size()!=0 && vTest.size()!=0) {
//							if (vTrain.size() != vTest.size()) {
								checkKatalogo[0] = true;
								if (checkKatalogo[0] && checkKatalogo[1])
									arffFitxategiakSortuButton.setEnabled(true);
//							} else {
//								JOptionPane jop = new JOptionPane(
//										"Entrenamendu eta frogarako irudiak berdinak direla dirudi.\nAukeratu beste katalogo bat.",
//										JOptionPane.ERROR_MESSAGE);
//								jop.createDialog(null, "Sarrera-datuetan errorea")
//										.setVisible(true);
//							}
//						}
					} else {
						checkKatalogo[0] = false;
						arffFitxategiakSortuButton.setEnabled(false);
						arffDiskretizatuakSortuButton.setEnabled(false);
						gainbegiratutakoDiskretizazioaButton.setEnabled(false);
						JOptionPane jop = new JOptionPane(
								"Emandako katalogoak ez du irudi nahikorik.\nSaiatu berriz.",
								JOptionPane.ERROR_MESSAGE);
						jop.createDialog(null, "Sarrera-datuetan errorea")
								.setVisible(true);
					}
				} catch (IOException e1) {
					checkKatalogo[0] = false;
					arffFitxategiakSortuButton.setEnabled(false);
					arffDiskretizatuakSortuButton.setEnabled(false);
					gainbegiratutakoDiskretizazioaButton.setEnabled(false);
					System.out
							.println("Errorea Irudiak kargatzerakoan. Aukeratu berriz katalogoa.");
					JOptionPane jop = new JOptionPane(
							"Emandako katalogoak ez du irudirik.\nSaiatu berriz.",
							JOptionPane.ERROR_MESSAGE);
					jop.createDialog(null, "Sarrera-datuetan errorea")
							.setVisible(true);
				}

			}

		} else if (e.getSource() == testButton) {
			vTest.removeAllElements();
			vAll.removeAllElements();
			checkKatalogo[1] = false;
			int result = jfc.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {

				try {
					katalogoa = jfc.getSelectedFile();
					if (isKatalogoEgoki(katalogoa)) {
						// Irudi guztien informazioa lortzen dugu
						irudiak.setTestPath(katalogoa.getPath());
						vTest = irudiak.getTest();
						testTextField.setText(katalogoa.getPath());

//						if (vTrain.size()!=0 && vTest.size()!=0) {
//							if (vTrain.size() != vTest.size()) {
								checkKatalogo[1] = true;
								if (checkKatalogo[0] && checkKatalogo[1])
									arffFitxategiakSortuButton.setEnabled(true);
//							} else {
//								JOptionPane jop = new JOptionPane(
//										"Entrenamendu eta frogarako irudiak berdinak direla dirudi.\nAukeratu beste katalogo bat.",
//										JOptionPane.ERROR_MESSAGE);
//								jop.createDialog(null, "Sarrera-datuetan errorea")
//										.setVisible(true);
//							}
//						}
					} else {
						checkKatalogo[1] = false;
						arffFitxategiakSortuButton.setEnabled(false);
						arffDiskretizatuakSortuButton.setEnabled(false);
						gainbegiratutakoDiskretizazioaButton.setEnabled(false);
						JOptionPane jop = new JOptionPane(
								"Emandako katalogoak ez du irudi nahikorik.\nSaiatu berriz.",
								JOptionPane.ERROR_MESSAGE);
						jop.createDialog(null, "Sarrera-datuetan errorea")
								.setVisible(true);
					}
				} catch (IOException e1) {
					checkKatalogo[1] = false;
					arffFitxategiakSortuButton.setEnabled(false);
					arffDiskretizatuakSortuButton.setEnabled(false);
					gainbegiratutakoDiskretizazioaButton.setEnabled(false);
					System.out
							.println("Errorea Irudiak kargatzerakoan. Aukeratu berriz katalogoa.");
					JOptionPane jop = new JOptionPane(
							"Emandako katalogoak ez du irudirik.\nSaiatu berriz.",
							JOptionPane.ERROR_MESSAGE);
					jop.createDialog(null, "Sarrera-datuetan errorea")
							.setVisible(true);
				}

			}
		} else if (e.getSource() == arffFitxategiakSortuButton) {
			try {
				new File("TRAIN.arff").delete();
				new File("TEST.arff").delete();
				new File("ALL.arff").delete();
				vAll = irudiak.getAll();
				System.out.println("TRAIN.arff fitxategia sortzen...\n");
				arffak.toARFF(vTrain, "TRAIN.arff");
				System.out.println("TEST.arff fitxategia sortzen...\n");
				arffak.toARFF(vTest, "TEST.arff");
				System.out.println("ALL.arff fitxategia sortzen...\n");
				arffak.toARFF(vAll, "ALL.arff");
				arffDiskretizatuakSortuButton.setEnabled(true);
				gainbegiratutakoDiskretizazioaButton.setEnabled(true);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (IllegalMotaException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == arffDiskretizatuakSortuButton) {
			new File("TRAIND.arff").delete();
			new File("TESTD.arff").delete();
			new File("ALLD.arff").delete();
			System.out.println("ALL.arff fitxategia diskretizatzen...\n");
			try {
				weka.discretize("ALL.arff", "ALLD.arff");
				System.out.println("ALLD.arff fitxategia bitan banatzen...\n");
				arffak.separateDiscretized("ALLD.arff", "TRAIND.arff",
						"TESTD.arff",
						new File(trainTextField.getText()).list().length);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (e.getSource() == gainbegiratutakoDiskretizazioaButton) {
			new File("TRAINDS.arff").delete();
			new File("TESTDS.arff").delete();
			new File("ALLDS.arff").delete();
			System.out.println("ALL.arff fitxategia diskretizatzen...\n");
			try {
				int param = Integer.parseInt(textField.getText());
				if (param > 0 && param <= 20) {
					weka
							.discretizeUnsupervised("ALL.arff", "ALLDS.arff",
									param);
					System.out
							.println("ALLDS.arff fitxategia bitan banatzen...\n");
					arffak.separateDiscretized("ALLDS.arff", "TRAINDS.arff",
							"TESTDS.arff", new File(trainTextField.getText())
									.list().length);
				} else {
					JOptionPane jop = new JOptionPane(
							"Gainbegiratu gabeko diskretizazioaren parametroa\n(0-20] tartekoa izango da gure kasuan.",
							JOptionPane.ERROR_MESSAGE);
					jop.createDialog(null, "Parametro errorea")
							.setVisible(true);
				}
			} catch (Exception e1) {
				JOptionPane jop = new JOptionPane(
						"Parametroaren eremuan zenbaki bat sartu behar da.",
						JOptionPane.ERROR_MESSAGE);
				jop.createDialog(null, "Parametro errorea").setVisible(true);
			}
		}

	}

	public String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	public boolean isKatalogoEgoki(File katalogoa) {
		boolean egokia = false;
		if (katalogoa.isDirectory()) {
			File[] edukiak = katalogoa.listFiles(new PGMFitxategi());
			egokia = edukiak.length > 2;
		}
		return egokia;
	}

	class PGMFitxategi implements FileFilter {
		private boolean baimena = false;

		public boolean accept(File pathname) {
			String extension = getExtension(pathname);
			if (extension != null) {
				if (extension.equals("pgm"))
					baimena = true;
			}
			return baimena;
		}

	}

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Nagusia frame = new Nagusia();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

			// } catch (ClassNotFoundException e1) {
			// e1.printStackTrace();
			// } catch (InstantiationException e1) {
			// e1.printStackTrace();
			// } catch (IllegalAccessException e1) {
			// e1.printStackTrace();
			// } catch (UnsupportedLookAndFeelException e1) {
			// e1.printStackTrace();
		} catch (Exception e) {
		}
	}

}
