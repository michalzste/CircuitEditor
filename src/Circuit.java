import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/*
 *Micha³ Szymañski I:252768
 *Grudzieñ 2020r.
 */

public class Circuit implements Serializable {


	@Serial
	private static final long serialVersionUID = -1835936029342454928L;

	private final List<Gate> gates;

	private final List<Wire> wires;

	private final List<Input> inputs;

	private final List<Output> outputs;

	public Circuit() {
		this.gates = new ArrayList<>();
		this.wires = new ArrayList<>();
		this.inputs = new ArrayList<>();
		this.outputs = new ArrayList<>();
	}


	public void addGate(Gate gate){
		gates.add(gate);
	}

	public void addWire(Wire wire){
		wires.add(wire);
	}

	public void addInput(Input input) { inputs.add(input);}

	public void addOutput(Output output) { outputs.add(output);}



	public void removeElement(CircuitElement el) {
		if(el instanceof Gate) gates.remove(el);
		if(el instanceof Input) inputs.remove(el);
		if(el instanceof Output) outputs.remove(el);
	}

	public Gate[] getGates(){
		Gate[] array = new Gate[0];
		return gates.toArray(array);
	}


	public Wire[] getWires(){
		Wire[] array = new Wire[0];
		return wires.toArray(array);
	}
	public Input[] getInputs(){
		Input[] array = new Input[0];
		return inputs.toArray(array);
	}
	public Output[] getOutputs(){
		Output[] array = new Output[0];
		return outputs.toArray(array);
	}

	public void draw(Graphics2D g){
		for(Wire wire : wires){
			wire.drawArrowLine(g);
		}
		for(Gate gate : gates){
			gate.draw(g);
		}
		for(Input input : inputs){
			input.draw(g);
		}
		for(Output output : outputs){
			output.draw(g);
		}
	}


	public void removeWire(Wire wire) {
		wires.remove(wire);
	}


	public static void writeToFile(final String fileName, final Circuit circuit) throws Exception {
		try{
			final ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
			outputStream.writeObject(circuit);
			outputStream.close();
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("An error occured!");
		}
	}


	public static Circuit openFromFile(final String fileName) throws Exception {

		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			Circuit circuit = (Circuit) objectIn.readObject();
			objectIn.close();
			return circuit;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("An error occured!");
		}
	}


	public void updateLogicalValues() {
		for(Gate gate : gates){
			gate.setLogicalValue(gate.getConnectedWires(this.getWires()));
		}
		for(Output output : outputs){
			output.setLogicalValue(output.getConnectedWires(this.getWires()));
		}
	}
}
