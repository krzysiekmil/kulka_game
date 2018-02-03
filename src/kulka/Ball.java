package kulka;
import java.awt.geom.Ellipse2D;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Paint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;

public class Ball implements KeyListener {

    private Ellipse2D.Double ball;
    
    
    

    public Ball( double positionX, double positionY, double sizeFactorX, double sizeFactorY) {

        ball = new Ellipse2D.Double( positionX, positionY, sizeFactorX, sizeFactorY );
    }

    public void paintBall(Graphics2D g2d) {

        Paint tempPaint = g2d.getPaint(); // Just for case, the default paint is stored and restored at the and of method

        g2d.setPaint(Color.BLACK);
        g2d.fill(ball);
       
        g2d.setPaint(tempPaint);
    }
    public void move(){
    	
    	
    }

    public Ellipse2D.Double GetBall()
    {
    	return ball;
    }
    public void SetBall(Ellipse2D.Double newBall)
    {
    	ball = newBall;
    }

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
