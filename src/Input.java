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


public class Input extends CircuitElement implements Serializable {

    @Serial
    private static final long serialVersionUID = -4754613455396444944L;






    public Input(int x, int y) {
        super(x,y);
        this.logicalValue=0;
        this.color = Color.GRAY;
    }

 public void toggleLogicalValue(){

    if(this.logicalValue == 0){
         this.logicalValue = 1;
         setColor(Color.GREEN);
     }
    else if(this.logicalValue == 1){
        this.logicalValue =0;
        setColor(Color.GRAY);
    }

 }



    public boolean isMouseOver(int mx, int my){
        return (x-mx)*(x-mx)+(y-my)*(y-my)<=r*r;
    }
    void draw(Graphics g) {
        g.setColor(color);

        g.fillOval(x-r, y-r, 2*r, 2*r);
        g.setColor(Color.BLACK);
        g.drawOval(x-r, y-r, 2*r, 2*r);
        g.setFont(new Font("Monospaced", Font.BOLD, 24));
        g.drawString(String.valueOf(logicalValue), x-7,y+7);
    }

    @Override
    public String toString(){
        return ("(" + x +", " + y + ", " + r +", " + "#"+Integer.toHexString(color.getRGB()).substring(2) +",  val: "+ logicalValue+")");
    }

}

