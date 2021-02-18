import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
/*
 *Micha³ Szymañski I:252768
 *Grudzieñ 2020r.
 */


public class Output extends CircuitElement implements Serializable {

    @Serial
    private static final long serialVersionUID = -4754613455396444944L;






    public Output(int x, int y) {
        super(x,y);
        this.logicalValue=0;
        this.r =15;
        this.color = Color.GRAY;
        this.gateType = "OUTPUT";
    }

    public boolean isMouseOver(int mx, int my){
        return (x-mx)*(x-mx)+(y-my)*(y-my)<=r*r;
    }
    void draw(Graphics g) {
        g.setColor(color);

        g.fillRect(x-r, y-r, 2*r, 2*r);
        g.setColor(Color.BLACK);
        g.drawRect(x-r, y-r, 2*r, 2*r);
        g.setFont(new Font("Monospaced", Font.BOLD, 24));
        g.drawString(String.valueOf(logicalValue), x-7,y+7);
    }


@Override
public void setLogicalValue(Wire[] wires){
        if(wires.length != 0){
           this.logicalValue = wires[0].elementFrom.logicalValue;
           if(this.logicalValue == 1) setColor(Color.GREEN);
           else setColor(Color.GRAY);
        }
}

    @Override
    public String toString(){
        return ("(" + x +", " + y + ", " + r +", " + logicalValue +", "+"#"+Integer.toHexString(color.getRGB()).substring(2) +")");
    }

}

