import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serial;
import java.io.Serializable;
import javax.swing.*;

/*
 *Micha³ Szymañski I:252768
 *Grudzieñ 2020r.
 */

public class CircuitPanel extends JPanel
		implements MouseListener, MouseMotionListener, KeyListener, Serializable {

	@Serial
	private static final long serialVersionUID = -7987064514327393681L;
	protected Circuit circuit;


	private int mouseX = 0;
	private int mouseY = 0;
	private boolean mouseButtonLeft = false;
	private boolean mouseButtonMiddle = false;
	@SuppressWarnings("unused")
	private boolean mouseButtonRight = false;
	protected int mouseCursor = Cursor.DEFAULT_CURSOR;

	protected CircuitElement elementUnderCursor = null;
	protected Wire wireUnderCursor = null;
	protected CircuitElement elementFrom = null;


	public CircuitPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	    setFocusable(true);
	    requestFocus();
	}

	public Circuit getCircuit() {
		return circuit;
	}

	public void setCircuit(Circuit circuit) {
		this.circuit = circuit;
		repaint();
	}


	private CircuitElement findElement(int mx, int my){
		for(Gate gate : circuit.getGates()){
			if (gate.isMouseOver(mx, my)){
				return gate;
			}
		}
		for(Input input : circuit.getInputs()){
			if(input.isMouseOver(mx,my)){
				return input;
			}
		}
		for(Output output : circuit.getOutputs()){
			if(output.isMouseOver(mx,my)){
				return output;
			}
		}
		return null;
	}

	private CircuitElement findElement(MouseEvent event){
		return findElement(event.getX(), event.getY());
	}

	private Wire findWire(int mx, int my){
		for(Wire wire : circuit.getWires()){
			if (wire.isMouseOver(mx, my)){
				return wire;
			}
		}
		return null;
	}

	private Wire findWire(MouseEvent event){
		return findWire(event.getX(), event.getY());
	}

	protected void setMouseCursor(MouseEvent event) {
		elementUnderCursor = findElement(event);
		wireUnderCursor = findWire(event);
		if (elementUnderCursor != null || wireUnderCursor != null)  {
			mouseCursor = Cursor.HAND_CURSOR;
		} else if (mouseButtonLeft) {
			mouseCursor = Cursor.MOVE_CURSOR;
		} else {
			mouseCursor = Cursor.DEFAULT_CURSOR;
		}
		setCursor(Cursor.getPredefinedCursor(mouseCursor));
		mouseX = event.getX();
		mouseY = event.getY();
	}

	protected void setMouseCursor() {
		elementUnderCursor = findElement(mouseX, mouseY);
		if (elementUnderCursor != null) {
			mouseCursor = Cursor.HAND_CURSOR;
		} else if (mouseButtonLeft) {
			mouseCursor = Cursor.MOVE_CURSOR;
		} else {
			mouseCursor = Cursor.DEFAULT_CURSOR;
		}
		setCursor(Cursor.getPredefinedCursor(mouseCursor));
	}

	private void moveElement(int dx, int dy, CircuitElement element){
		element.setX(element.getX()+dx);
		element.setY(element.getY()+dy);
	}
	private void moveWire(int dx, int dy, Wire wire){
		moveElement(dx,dy, wire.elementFrom);
		moveElement(dx,dy, wire.elementTo);
	}
	private void updateWires(int dx, int dy, CircuitElement element){
		if(!element.equals(null)){
			for(Wire wire : circuit.getWires()){
				if(wire.elementFrom.equals(element)) wire.setCoordinates1(element.getX()+dx, element.getY()+dy);
				if(wire.elementTo.equals(element)) wire.setCoordinates2(element.getX()+dx, element.getY()+dy);
			}
		}
	}



	private void moveAllElements(int dx, int dy) {
		for (Gate gate : circuit.getGates()) {
			moveElement(dx, dy, gate);
		}
		for (Input input : circuit.getInputs()) {
			moveElement(dx, dy, input);
		}
		for (Output output : circuit.getOutputs()) {
			moveElement(dx, dy, output);
		}

	}
	private void moveAllWires(int dx, int dy) {
		for (Wire wire : circuit.getWires()) {
			wire.setCoordinates1(wire.getX1()+dx, wire.getY1()+dy);
			wire.setCoordinates2(wire.getX2()+dx, wire.getY2()+dy);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (circuit ==null) return;
		circuit.draw((Graphics2D) g);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent event) {
	}

	@Override
	public void mouseEntered(MouseEvent event) {
	}

	@Override
	public void mouseExited(MouseEvent event) {
	}

	@Override
	public void mousePressed(MouseEvent event) {
		if (event.getButton()==1) mouseButtonLeft = true;
		if (event.getButton()==2) mouseButtonMiddle = true;
		if (event.getButton()==3) mouseButtonRight = true;
		setMouseCursor(event);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (event.getButton() == 1)
			mouseButtonLeft = false;
		if (event.getButton() == 2)
			mouseButtonMiddle = false;
		if (event.getButton() == 3)
			mouseButtonRight = false;
		setMouseCursor(event);
		if (event.getButton() == 3) {
			if (elementUnderCursor != null) {
				createPopupMenu(event, elementUnderCursor);
			} else if (wireUnderCursor != null){
				createPopupMenu(event, wireUnderCursor);
			}
			else {
				createPopupMenu(event);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		if (mouseButtonLeft) {
			if (elementUnderCursor != null) {
				moveElement(event.getX() - mouseX, event.getY() - mouseY, elementUnderCursor);
				updateWires(event.getX() - mouseX, event.getY() - mouseY, elementUnderCursor);
			} else if (wireUnderCursor != null){
				moveWire(event.getX() - mouseX, event.getY()-mouseY, wireUnderCursor);
				updateWires(event.getX() - mouseX, event.getY() - mouseY, wireUnderCursor.elementFrom);
				updateWires(event.getX() - mouseX, event.getY() - mouseY, wireUnderCursor.elementTo);
			} else {
				moveAllElements(event.getX() - mouseX, event.getY() - mouseY);
				moveAllWires(event.getX()-mouseX, event.getY() - mouseY);
			}
		}
		mouseX = event.getX();
		mouseY = event.getY();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		setMouseCursor(event);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		{  int dist;
	       if (event.isShiftDown()) dist = 10;
	                         else dist = 1;
			switch (event.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				moveAllElements(-dist, 0);
				moveAllWires(-dist,0);
				break;
			case KeyEvent.VK_RIGHT:
				moveAllElements(dist, 0);
				moveAllWires(dist,0);
				break;
			case KeyEvent.VK_UP:
				moveAllElements(0, -dist);
				moveAllWires(0,-dist);
				break;
			case KeyEvent.VK_DOWN:
				moveAllElements(0, dist);
				moveAllWires(0,dist);
				break;
			case KeyEvent.VK_DELETE:
				if (elementUnderCursor != null) {
					circuit.removeElement(elementUnderCursor);
					for(Wire wire : circuit.getWires()) if(wire.elementFrom.equals(elementUnderCursor)|| wire.elementTo.equals(elementUnderCursor)) circuit.removeWire(wire);
					elementUnderCursor = null;
				}
				break;
				case KeyEvent.VK_T:
					if(elementUnderCursor != null){
						if(elementUnderCursor instanceof Input) {
							((Input) elementUnderCursor).toggleLogicalValue();
							circuit.updateLogicalValues();

						}
					}
			}
		}
		repaint();
		setMouseCursor();
	}

	@Override
	public void keyReleased(KeyEvent event) {
	}

	@Override
	public void keyTyped(KeyEvent event) {
		char znak=event.getKeyChar();
		if (elementUnderCursor !=null){
		switch (znak) {
		case 'r':
			elementUnderCursor.setColor(Color.RED);
			break;
		case 'g':
			elementUnderCursor.setColor(Color.GREEN);
			break;
		case 'b':
			elementUnderCursor.setColor(Color.BLUE);
			break;
		case '+':
			int r = elementUnderCursor.getR()+10;
			elementUnderCursor.setR(r);
			break;
		case '-':
			r = elementUnderCursor.getR()-10;
			if (r>=10) elementUnderCursor.setR(r);
			break;
		}
		repaint();
		setMouseCursor();
		}
	}



	protected void createPopupMenu(MouseEvent event) {
        JMenuItem menuItem;


        JPopupMenu popup = new JPopupMenu();

        menuItem = new JMenuItem("Create new gate");
		menuItem.addActionListener((action) -> {
			circuit.addGate(new Gate(event.getX(), event.getY(),  JOptionPane.showInputDialog(  this,
					"Select a gate type: ",
					"Gates",
					JOptionPane.PLAIN_MESSAGE,
					null,
					ElementType.values(),
					ElementType.values()[0]).toString()));
			repaint();
		});
		popup.add(menuItem);

		menuItem = new JMenuItem("Create new Input");
		menuItem.addActionListener((action) -> {
			circuit.addInput(new Input(event.getX(), event.getY()));
			repaint();
		});
		popup.add(menuItem);


		menuItem = new JMenuItem("Create new Output");
		menuItem.addActionListener((action) -> {
			circuit.addOutput(new Output(event.getX(), event.getY()));
			repaint();
		});
		popup.add(menuItem);


        popup.show(event.getComponent(), event.getX(), event.getY());
    }

    protected void createPopupMenu(MouseEvent event, Wire wire) {
        JMenuItem menuItem;


        JPopupMenu popup = new JPopupMenu();

        menuItem = new JMenuItem("Delete this wire");
		menuItem.addActionListener((action) -> {
			circuit.removeWire(wire);
			elementFrom =null;
			repaint();
		});
		popup.add(menuItem);

		menuItem = new JMenuItem("Change color of this wire");
		menuItem.addActionListener((action)-> {
			Color newColor = JColorChooser.showDialog(
					this,
					"Choose Background Color",
					wire.getColor());
			if (newColor!=null){
				wire.setColor(newColor);
			}
			repaint();
		});
		popup.add(menuItem);



        popup.show(event.getComponent(), event.getX(), event.getY());
    }

	protected void createPopupMenu(MouseEvent event, CircuitElement element){
		JMenuItem menuItem;

		JPopupMenu popup = new JPopupMenu();
		menuItem = new JMenuItem("Change node color");

		menuItem.addActionListener((a) -> {
			Color newColor = JColorChooser.showDialog(
                    this,
                    "Choose Background Color",
                    element.getColor());
			if (newColor!=null){
				element.setColor(newColor);
			}
			repaint();
		});


		popup.add(menuItem);
		menuItem = new JMenuItem("Remove this element");

		menuItem.addActionListener((action) -> {
			for(Wire wire : circuit.getWires()){
				if(wire.elementFrom == element || wire.elementTo == element){
					circuit.removeWire(wire);
				}
			}
			circuit.removeElement(element);
			circuit.updateLogicalValues();
			repaint();
		});

		popup.add(menuItem);
		if(elementFrom == null && !(elementUnderCursor instanceof Output)){
		menuItem = new JMenuItem("Create wire from this node");
		menuItem.addActionListener((action) -> {
			elementFrom = element;

		});
			popup.add(menuItem);
		}
		if(elementFrom != null && element != elementFrom){
			menuItem = new JMenuItem("Create wire to this node");
			menuItem.addActionListener((action) -> {
				for(Wire wire : circuit.getWires()){
					if(wire.elementFrom == elementFrom && wire.elementTo == element){
						JOptionPane.showMessageDialog(this,"This wire already exists!");
						return;
					}
					if(element.getGateType()=="NOT" && wire.elementTo == element){
						JOptionPane.showMessageDialog(this,"Only one wire can be connected to NOT!");
						elementFrom=null;
						return;
					}
					if(element.getGateType()=="OUTPUT" && wire.elementTo == element){
						JOptionPane.showMessageDialog(this,"Only one wire can be connected to OUTPUT!");
						elementFrom=null;
						return;
					}
				}
				if(element instanceof Input){
					JOptionPane.showMessageDialog(this,"You can't connect wire to an Input!");
					elementFrom=null;
					return;
				}

				Wire wire = new Wire(elementFrom, element);
				circuit.addWire(wire);
				elementFrom = null;
				circuit.updateLogicalValues();
				repaint();

				});
			popup.add(menuItem);
		}


		popup.show(event.getComponent(), event.getX(), event.getY());
	}



}
