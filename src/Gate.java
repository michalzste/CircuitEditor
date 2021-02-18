import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/*
*Micha³ Szymañski I:252768
*Grudzieñ 2020r.
 */

public class Gate extends CircuitElement implements Serializable {



	private final String gateType;
	@Serial
	private static final long serialVersionUID = -4754613455396444944L;

	public Gate(int x, int y, String gateType) {
		super(x, y);
		this.gateType = gateType;
		this.r=30;
	}




	@Override
	void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x-r, y-r, 2*r, 2*r);
		g.setColor(Color.BLACK);
		g.drawOval(x-r, y-r, 2*r, 2*r);
		if(gateType!= null){
			g.setFont(new Font("Monospaced", Font.BOLD, 24));
			g.setColor(Color.BLACK);
			g.drawString(gateType, x-(gateType.length()*7),y+6);
		}
	}

	public void setLogicalValue(Wire[] connectedWires) {


		if(connectedWires.length==0){
			this.logicalValue = -1;
			return;
		}
		switch(this.gateType){
			case "OR":
				for(Wire wire : connectedWires){
					if(wire.elementFrom.logicalValue == -1){
						this.logicalValue = -1;
						return;
					}
				}
				for(Wire wire : connectedWires){
					if(wire.elementFrom.logicalValue == 1){
						this.logicalValue =1;
						return;
					}
					this.logicalValue = 0;
				}
				break;
			case "AND":
				for(Wire wire : connectedWires) {
					if(wire.elementFrom.logicalValue == -1){
						this.logicalValue = -1;
						return;
					}
				}
				for(Wire wire : connectedWires){
					if(wire.elementFrom.logicalValue == 0){
						this.logicalValue =0;
						return;
					}
					this.logicalValue = 1;
				}
				break;
			case "NOT":
				for(Wire wire : connectedWires) {
					if(wire.elementFrom.logicalValue == -1){
						this.logicalValue = -1;
						return;
					}
				}
				for(Wire wire : connectedWires){
					if(wire.elementFrom.logicalValue == 0){
						this.logicalValue = 1;
					}else this.logicalValue = 0;

				}
				break;
			case "NOR":
				for(Wire wire : connectedWires){
					if(wire.elementFrom.logicalValue == -1){
						this.logicalValue = -1;
						return;
					}
				}
				for(Wire wire : connectedWires){
					if(wire.elementFrom.logicalValue == 1){
						this.logicalValue =0;
						return;
					}
					this.logicalValue = 1;
				}
				break;
			case "NAND":
				int buff = 0;
				for(Wire wire : connectedWires) {
					if(wire.elementFrom.logicalValue == -1){
						this.logicalValue = -1;
						return;
					}
				}
				for(Wire wire : connectedWires){
				 if(wire.elementFrom.logicalValue == 1)buff++;
				}
				if(connectedWires.length == buff){
					this.logicalValue=0;
				}else {
					this.logicalValue =1;
					return;
				}
				break;
			case "XOR":
				buff = 0;
				for(Wire wire : connectedWires) {
					if(wire.elementFrom.logicalValue == -1){
						this.logicalValue = -1;
						return;
					}
				}
				for(Wire wire : connectedWires){
					if(wire.elementFrom.logicalValue == 1)buff++;
				}
				if(buff == 1){
					this.logicalValue=1;
				}else {
					this.logicalValue =0;
					return;
				}
				break;
		}
	}
	@Override
	public String getGateType() {
		return gateType;
	}
	public void setLogicalValue(int logicalValue){
		this.logicalValue=logicalValue;
	}
	@Override
	public String toString(){
		return ("(" + x +", " + y + ", " + r +", " + gateType+", "+"#"+Integer.toHexString(color.getRGB()).substring(2) +",  val: "+ logicalValue+")");
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Gate gate = (Gate) o;
		return x == gate.x && y == gate.y && r == gate.r && Objects.equals(color, gate.color);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, r, color);
	}
}

