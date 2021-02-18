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
public class CircuitElement implements Serializable {

    @Serial
    private static final long serialVersionUID = 7034220822653338701L;
    protected int x;
    protected int y;
    protected int r;
    protected Color color;
    int logicalValue = -1;
    protected String gateType;


    public CircuitElement(int x, int y) {
        this.x = x;
        this.y = y;
        this.r = 20;
        this.color = Color.WHITE;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public Color getColor() {
        return color;
    }


    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isMouseOver(int mx, int my){
        return (x-mx)*(x-mx)+(y-my)*(y-my)<=r*r;
    }

    void draw(Graphics g) {
    }

    public Wire[] getConnectedWires(Wire[] allWires){
        List<Wire> wires = new ArrayList<>();
        for(Wire wire:allWires){
            if(wire.elementTo== this){
                wires.add(wire);
            }
        }
        Wire[] array = new Wire[0];
        return wires.toArray(array);
    }

public void setLogicalValue(Wire[] wires){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CircuitElement that = (CircuitElement) o;
        return x == that.x && y == that.y && r == that.r && logicalValue == that.logicalValue && Objects.equals(color, that.color) && Objects.equals(gateType, that.gateType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, r, color, logicalValue, gateType);
    }

    public String getGateType() { return gateType;}
}
