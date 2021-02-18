import javax.swing.plaf.metal.MetalIconFactory;
import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
/*
 *Micha³ Szymañski I:252768
 *Grudzieñ 2020r.
 */

public class Wire implements Serializable {

    @Serial
    private static final long serialVersionUID = 5840698744173206001L;


    public CircuitElement elementFrom;
    public CircuitElement elementTo;



    private Color color;
    private float arrowStrokeSize;
    private float defaultStrokeSize ;


    private int x1;
    private int y1;
    private int x2;
    private int y2;

    private int xsr;
    private int ysr;

    private int d = 10;

    private int h = 10;


    private final int  hookRadius =3;

    public Wire(CircuitElement elementFrom, CircuitElement elementTo) {

        this.elementFrom = elementFrom;
        this.elementTo = elementTo;

        this.x1 = elementFrom.getX();
        this.y1 = elementFrom.getY();

        this.x2 = elementTo.getX();
        this.y2 = elementTo.getY();


        this.arrowStrokeSize = 3.0F;
        this.defaultStrokeSize= 1.0F;
    }


    public  void setCoordinates1(int x1, int y1){
        this.x1 = x1;
        this.y1 = y1;
    }

    public  void setCoordinates2(int x2, int y2){
        this.x2 = x2;
        this.y2 = y2;
}

    public Color getColor() {
        return color;
    }


    public void setColor(Color color) {
        this.color = color;
    }


    public int getX1() {
        return x1;
    }


    public int getY1() {
        return y1;
    }


    public int getX2() {
        return x2;
    }


    public int getY2() {
        return y2;
    }
    public CircuitElement getElementFrom(){return elementFrom;}

    void drawArrowLine(Graphics g) {

        int dx = x2 - x1, dy = y2 - y1;

        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;

        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;

        yn = xn*sin + yn*cos + y1;
        xn = x;

          xsr = ((this.x1+ x2-(int)(elementTo.r*cos))/2);
          ysr = (this.y1+y2-(int)(elementTo.r*sin))/2;

        int[] xpoints = { x2-(int)(elementTo.r*cos), (int) xm-(int)(elementTo.r*cos) , (int) xn-(int)(elementTo.r*cos)};
        int[] ypoints = {y2-(int)(elementTo.r*sin), (int) ym-(int)(elementTo.r*sin) , (int) yn-(int)(elementTo.r*sin)};

        BasicStroke arrowStroke = new BasicStroke( arrowStrokeSize);
        BasicStroke defaultStroke = new BasicStroke(defaultStrokeSize);

        ((Graphics2D)g).setStroke(arrowStroke);
        if(this.elementFrom.logicalValue == 1)setColor(Color.GREEN);
        else if(this.elementFrom.logicalValue == -1) setColor(Color.BLUE);
        else setColor(Color.GRAY);

        g.setColor(this.color);
        g.drawLine(x1, y1, x2, y2);

        ((Graphics2D)g).setStroke(defaultStroke);
        g.setColor(Color.BLACK);

        g.fillOval(xsr-2, ysr-2, 2*hookRadius, 2*hookRadius);
        g.drawOval(xsr-2, ysr-2, 2*hookRadius, 2*hookRadius);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    public boolean isMouseOver(int mx, int my) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double sin = dy / D, cos = dx / D;


        xsr = ((this.x1+ x2-(int)(elementTo.r*cos))/2);
        ysr = (this.y1+y2-(int)(elementTo.r*sin))/2;
        return (xsr-mx)*(xsr-mx)+(ysr-my)*(ysr-my)<=hookRadius*hookRadius;
    }


    @Override
    public String toString(){
        return ("(" + x1 +", " + y1 +", "+ "#"+Integer.toHexString(color.getRGB()).substring(2)+ "   -->    " +x2  + ", " + y2) +", " +  "#"+Integer.toHexString(color.getRGB()).substring(2)+ ")";
    }

}
