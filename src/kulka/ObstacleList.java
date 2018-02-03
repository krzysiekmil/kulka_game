package kulka;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Paint;

/**
 * Created by misimik on 05.12.16.
 */
public class ObstacleList {

    private List<Rectangle2D.Double> obstacleList = new ArrayList<>();
    
    public List<Rectangle2D.Double> GetObstacleList()
    {
    	return obstacleList;
    }

    public void addObstacle(double positionX, double positionY, double width, double height){
        obstacleList.add(new Rectangle2D.Double(positionX, positionY, width, height));
    }

    public void paintObstacles(Graphics2D g2d) {

        Paint tempPaint = g2d.getPaint(); // Just for case, the default paint is stored and restored at the and of method

        g2d.setPaint(Color.LIGHT_GRAY);

        for (Rectangle2D.Double anObstacleList : obstacleList) g2d.fill(anObstacleList);

        g2d.setPaint(tempPaint);
    }
    public int size(){
    	return obstacleList.size();
    }
    public void clear(){
    	obstacleList.clear();
    }
    public double getPositionX(int numer){
    	return obstacleList.get(numer).x;
    }
    public double getPositionY(int numer){
    	return obstacleList.get(numer).y;
    }
    public double getHeight(int numer){
    	return obstacleList.get(numer).height;
    }
    public double getWidth(int numer){
    	return obstacleList.get(numer).width;
    }
}
