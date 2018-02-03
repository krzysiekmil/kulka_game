package kulka;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;


public class Goal extends Rectangle2D.Double {

    private Rectangle2D.Double goal;

    public Goal(double positionX, double positionY, double sizeFactorX,double sizeFactorY) {
        goal = new Rectangle2D.Double( positionX, positionY, sizeFactorX, sizeFactorY );
    }

    public void paintGoal(Graphics2D g2d) {

        Paint tempPaint = g2d.getPaint(); 

        g2d.setPaint(Color.BLACK);
        g2d.draw(goal);

        g2d.setPaint(tempPaint);
    }
}
