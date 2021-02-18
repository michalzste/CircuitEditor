import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.Serial;

/*
 *Micha³ Szymañski I:252768
 *Grudzieñ 2020r.
 */

public class CircuitEditor extends JFrame implements ActionListener {
	
	@Serial
	private static final long serialVersionUID = 1L;

	private static final String APP_AUTHOR = "Autor: Micha³ Szymañski\nData: Grudzieñ 2020\nIndeks: 252768";
	private static final String APP_TITLE = "Edytor uk³adów logicznych";
	
	private static final String APP_INSTRUCTION =
			"""
					                        O P I S   P R O G R A M U     \s

					Aktywne klawisze:
					   strza³ki ==> przesuwanie ca³ego obwodu
					   SHIFT + strza³ki ==> szybkie przesuwanie ca³ego obwodu

					ponadto gdy kursor wskazuje element:
					   DEL   ==> kasowanie elementu
					   +, -   ==> powiêkszanie, pomniejszanie elementu

					gdy wskazywanym elementem jest input:\s
					t,  ==> zmiana wartoœci logicznej.

					Operacje myszka:
					   przeci¹ganie ==> przesuwanie wszystkich elementów
					   PPM ==> tworzenie nowego elementu w miejscu kursora
					ponadto gdy kursor wskazuje element:
					   przeci¹ganie ==> przesuwanie elementu
					   PPM ==> usuwanie elementu
					""";
	
	
	public static void main(String[] args) {
		new CircuitEditor();
	}

	

	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu menuCircuit = new JMenu("Circuit");
	private final JMenu menuHelp = new JMenu("Help");
	private final JMenu menuFile = new JMenu("File");
	private final JMenuItem menuNew = new JMenuItem("New", KeyEvent.VK_N);
	private final JMenuItem menuShowExample = new JMenuItem("Example", KeyEvent.VK_X);
	private final JMenuItem menuExit = new JMenuItem("Exit", KeyEvent.VK_E);
	private final JMenuItem menuGraphStructure = new JMenuItem("Circuit structure", KeyEvent.VK_N);

	private final JMenuItem menuAuthor = new JMenuItem("Author", KeyEvent.VK_A);
	private final JMenuItem menuInstruction = new JMenuItem("Instruction", KeyEvent.VK_I);

	private final JMenuItem menuSave = new JMenuItem("Save to file");
	private final JMenuItem menuLoad = new JMenuItem("Load from file");

	private final CircuitPanel panel = new CircuitPanel();
	
	
	public CircuitEditor() {
		super(APP_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1280,720);
		setLocationRelativeTo(null);
		setContentPane(panel);
		createMenu();
		showBuildinExample();
		setVisible(true);
	}

	private void showCircuitStructure(Circuit circuit) {
		Gate[] array = circuit.getGates();
		int i = 0;
		StringBuilder message = new StringBuilder("Liczba bramek: " + array.length + "\n");
		for (Gate gate : array) {
			message.append(gate).append("    ");
			if (++i % 3 == 0)
				message.append("\n");
		}
		Wire[] array2 = circuit.getWires();
		i = 0;
		message.append("\nLiczba kabli: ").append(array2.length).append("\n");
		for (Wire wire : array2) {
			message.append(wire).append("    ");
			if (++i % 3 == 0)
				message.append("\n");
		}
		Input[] array3 = circuit.getInputs();
		i = 0;
		message.append("\nLiczba wejœæ: ").append(array3.length).append("\n");
		for (Input input : array3) {
			message.append(input).append("    ");
			if (++i % 3 == 0)
				message.append("\n");
		}

		Output[] array4 = circuit.getOutputs();
		i = 0;
		message.append("\nLiczba wyjœæ: ").append(array4.length).append("\n");
		for (Output output : array4) {
			message.append(output).append("    ");
			if (++i % 3 == 0)
				message.append("\n");
		}
		JOptionPane.showMessageDialog(this, message, APP_TITLE + " - Struktura uk³adu:", JOptionPane.PLAIN_MESSAGE);
	}

	private void createMenu() {
		menuNew.addActionListener(this);
		menuShowExample.addActionListener(this);
		menuExit.addActionListener(this);
		menuGraphStructure.addActionListener(this);
		menuAuthor.addActionListener(this);
		menuInstruction.addActionListener(this);
		menuSave.addActionListener(this);
		menuLoad.addActionListener(this);
		
		menuCircuit.setMnemonic(KeyEvent.VK_G);
		menuCircuit.add(menuNew);
		menuCircuit.add(menuShowExample);
		menuCircuit.addSeparator();
		menuCircuit.add(menuGraphStructure);



		menuFile.add(menuSave);
		menuFile.add(menuLoad);
		menuFile.addSeparator();
		menuFile.add(menuExit);
		
		menuHelp.setMnemonic(KeyEvent.VK_H);
		menuHelp.add(menuInstruction);
		menuHelp.add(menuAuthor);

		menuBar.add(menuFile);
		menuBar.add(menuCircuit);
		menuBar.add(menuHelp);
		setJMenuBar(menuBar);
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == menuNew) {
			panel.setCircuit(new Circuit());
		}
		if (source == menuShowExample) {
			showBuildinExample();
		}
		if (source == menuGraphStructure) {
			showCircuitStructure(panel.getCircuit());
		}
		if (source == menuAuthor) {
			JOptionPane.showMessageDialog(this, APP_AUTHOR, APP_TITLE, JOptionPane.INFORMATION_MESSAGE);
		}
		if (source == menuInstruction) {
			JOptionPane.showMessageDialog(this, APP_INSTRUCTION, APP_TITLE, JOptionPane.PLAIN_MESSAGE);
		}
		if (source == menuExit) {
			System.exit(0);
		}
		if (source == menuSave) {
			Circuit circuit = panel.getCircuit();
			try {
				Circuit.writeToFile(JOptionPane.showInputDialog("Give file name: "), circuit);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (source == menuLoad) {
			try {
				 Circuit circuit = Circuit.openFromFile(JOptionPane.showInputDialog("Give file name: "));
				 if(circuit != null){
				 	panel.setCircuit(circuit);
				 }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void showBuildinExample() {
		Circuit circuit = new Circuit();

		Gate n3 = new Gate(200, 100,"OR");
		Gate n2 = new Gate(200, 200,"OR");
		Gate n1 = new Gate(200, 300,"OR");

		circuit.addGate(n1);
		circuit.addGate(n2);
		circuit.addGate(n3);

		Input i7 = new Input(100,100);
		Input i6 = new Input(100,150);
		Input i5 = new Input(100,200);
		Input i4 = new Input(100,250);
		Input i3 = new Input(100,300);
		Input i2 = new Input(100,350);
		Input i1 = new Input(100,400);
		Input i0 = new Input(100,450);

		circuit.addInput(i0);
		circuit.addInput(i1);
		circuit.addInput(i2);
		circuit.addInput(i3);
		circuit.addInput(i4);
		circuit.addInput(i5);
		circuit.addInput(i6);
		circuit.addInput(i7);

		i0.toggleLogicalValue();

		Wire e71 = new Wire(i7,n1);
		Wire e72 = new Wire(i7,n2);
		Wire e73 = new Wire(i7,n3);

		Wire e63 = new Wire(i6,n3);
		Wire e62 = new Wire(i6,n2);

		Wire e51 = new Wire(i5,n1);
		Wire e53 = new Wire(i5,n3);

		Wire e43 = new Wire(i4,n3);

		Wire e32 = new Wire(i3,n2);
		Wire e31 = new Wire(i3,n1);

		Wire e22 = new Wire(i2,n2);

		Wire e11 = new Wire(i1,n1);

		circuit.addWire(e71);
		circuit.addWire(e72);
		circuit.addWire(e73);

		circuit.addWire(e63);
		circuit.addWire(e62);

		circuit.addWire(e51);
		circuit.addWire(e53);

		circuit.addWire(e43);

		circuit.addWire(e32);
		circuit.addWire(e31);

		circuit.addWire(e22);

		circuit.addWire(e11);

		Output o2 = new Output(350,100);
		Output o1 = new Output(350,150);
		Output o0 = new Output(350,200);

		circuit.addOutput(o2);
		circuit.addOutput(o1);
		circuit.addOutput(o0);

		Wire n1o0 = new Wire(n1,o0);
		Wire n2o1 = new Wire(n2,o1);
		Wire n3o2 = new Wire(n3,o2);

		circuit.addWire(n1o0);
		circuit.addWire(n2o1);
		circuit.addWire(n3o2);

		panel.setCircuit(circuit);
		circuit.updateLogicalValues();

		JOptionPane.showMessageDialog(panel,"Decoder one of 8.");
	}
}
