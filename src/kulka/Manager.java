/**
 * 
 */
package kulka;
import javax.swing.*;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.IOException;
import java.util.List;



public class Manager extends JPanel implements ActionListener,KeyListener, ComponentListener {
	
	/**
	 * @param args
	 */
	//Game frame
	static JFrame gameFrame;
	static double currentWidth;
	static double currentHeight;
    double bx=config.ballX;
	double by=config.ballY; 
    double gx=config.goalX;
    double gy=config.goalY;
    double size=config.size;
    double grawitacja=1; 
	double velx=0,vely=0;
	boolean firstRun = true;
	boolean firstLoose =true;
	boolean firstWin=true;
	double heightScale=1;
	double widthScale=1;
	int world=1;
	public boolean game=false;
	String name;
	//Game components
	HighscoreManager hm =new HighscoreManager();
	ObstacleList obstacleList= new ObstacleList();
	Ball ball;
	Goal goal;
	Timer t = new Timer(50,this);
	boolean collided;
	public String HighScorePath=Config.HighScoreFile;
	
	Player player = new Player(name,0);
	public static Config config= new Config ();
	public Manager(){
	gameFrame.addComponentListener(this);
	addKeyListener(this);
	setFocusable(true);
	setFocusTraversalKeysEnabled(false);
	
	}
	public void paint(Graphics g){
		double heightScale = gameFrame.getHeight()/currentHeight;
		double widthScale = gameFrame.getWidth()/currentWidth;
		
		super.paint(g);
		Graphics2D g2d= (Graphics2D) g; 
		if(game){
        if(firstRun)
        {	
        	firstWin=true;
        	firstLoose=true;
        	if(obstacleList == null)
        		 obstacleList = new ObstacleList();
        	ball = new Ball(bx,by,size*gameFrame.getWidth()/config.FrameWidth,size*gameFrame.getHeight()/config.FrameHeight);
    		goal = new Goal(gx,gy,size*gameFrame.getWidth()/config.FrameWidth,size*gameFrame.getHeight()/config.FrameHeight);
        	try {
        		if(world==1){
        		obstacleList.clear();
				config.worldConfiguration(obstacleList, config.world1);
				RepaintWindow();
				currentHeight = gameFrame.getSize().getHeight();
		    	currentWidth = gameFrame.getSize().getWidth();
		        firstRun = false;}
				else
				{obstacleList.clear();
				grawitacja=1.5;
        		config.worldConfiguration(obstacleList, config.world2);}	
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
		}
        
        }
        if(currentHeight != gameFrame.getSize().getHeight() || currentWidth != gameFrame.getSize().getWidth())
        {
        	//Resize here
        	RepaintWindow();
    		currentHeight = gameFrame.getSize().getHeight();
        	currentWidth = gameFrame.getSize().getWidth();
        }
        updatePosition(ball, bx, by);
        ball.paintBall(g2d);
        goal.paintGoal(g2d);
        obstacleList.paintObstacles(g2d);
        g2d.setFont(new Font ("Courier New", (int)(100), (int) (20)));
        g2d.setPaint(Color.BLACK);
        g2d.drawString("Czas:" + player.score, 10,50);
        g2d.drawString("Kulki: "+ player.life, 140, 80);
        Logika();
		}
	}
	public void Score(){
		if(t.isRunning())
		player.score++;
	}
	public void Logika(){
		if(game){
		CheckWin();
        CheckCollison();
        endDimension(ball);
        Score();
        End();
		}
	}
	public void End(){
		if(player.life==0||world==3){
			game=false;
			
			t.stop();
			repaint();
			if(firstWin||firstLoose){
				firstWin=false;
				firstLoose=false;
				player.name = JOptionPane.showInputDialog("Koniec gry podaj swoje swoje imie");
				if(player.name==null)
					player.name="Nieznajomy";
				if(player.life==0)
		        JOptionPane.showMessageDialog(this, player.name + " twoj wynik to " +  (int) (player.score*Math.pow(world,2)));
				else
					JOptionPane.showMessageDialog(this, player.name + " twoj wynik to " + (int) (player.score*Math.pow(world,2)*Math.pow(player.life,2)));	
			try {
				if(player.life==0)
					hm.addScore(player.name, (int)(player.score*Math.pow(world,2)));
				else
					hm.addScore(player.name,(int) (player.score*Math.pow(world,2)*Math.pow(player.life,2)));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			}
			
		}
	}
	public void HighList() throws Exception, Exception{
		hm.loadScoreFile();
		JOptionPane.showMessageDialog(this,(String)(hm.getshow()),"Najlepsze wyniki",JOptionPane.INFORMATION_MESSAGE);
	}
	private void endDimension(Ball b){
		if(b.GetBall().getCenterX()>=getSize().getWidth()||0>=b.GetBall().getCenterX()||0>=b.GetBall().getCenterY()||b.GetBall().getCenterY()>=getSize().getHeight()){
			bx = config.ballX*widthScale;
			by = config.ballY*heightScale;
			player.life--;
		}
	}
	
	public void CheckWin() {
		if(bx>=gx && by>=gy && bx<=gx+75 && by<=gy+75)
		{
			bx = config.ballX*widthScale;
			by = config.ballY*heightScale;
			t.stop();
			repaint();
			world++;
			if(firstWin&&world<=2){
				firstWin=false;
				firstRun=true;
			JOptionPane.showMessageDialog(this,
				      "Gratulacje ukonczy³eœ poziom przechodzisz do nastêpnego","Sukces",JOptionPane.INFORMATION_MESSAGE);
			repaint();}
			
			
			
	}
	}
	private void CheckCollison(){
		if(Collison(ball, obstacleList.GetObstacleList())==true){
			repaint();
			bx=config.ballX*widthScale;
			by=config.ballY*heightScale;
			player.life--;
		}
	}
	private void updatePosition(Ball b, double x, double y)
	{
		b.GetBall().x = x;
		b.GetBall().y = y;
	}
	private boolean Collison(Ball b, List<Rectangle2D.Double> obstacleList){
		
			for(Rectangle2D.Double obstacle : obstacleList)
			{
				if(Collided(b, obstacle))
					return true;				
			}
			return false;
	
	}
	private boolean Collided(Ball b, Rectangle2D.Double obstacle)
	{
		double ballDistanceX = Math.abs(b.GetBall().getCenterX() - obstacle.getCenterX());
		double ballDistanceY = Math.abs(b.GetBall().getCenterY() - obstacle.getCenterY());
		
		if(ballDistanceX > (obstacle.width/2 + b.GetBall().width/2)){
			return false;
		}
		if( ballDistanceY > (obstacle.height/2 + b.GetBall().width/2)){
			return false;
		}
		if(ballDistanceX <= obstacle.width/2 ){
			return true;
		}
		if(ballDistanceY <= obstacle.height/2){
			return true;
				}
		double cornerDistance = Math.pow((ballDistanceX - obstacle.width/2), 2) + Math.pow((ballDistanceY - obstacle.height/2),2);
		
		return (cornerDistance <= Math.pow(b.GetBall().height/2, 2));
	} 
	public void Rules(){
		JOptionPane.showMessageDialog(this,"Zadanie gracza jest kierowanie kulka do punktu kocowego. \n               				 Punkty naliczane za osiagniecie celu\n           				       Uwaga gracz ma tylko 3 kulki","Zasady",JOptionPane.INFORMATION_MESSAGE);
	}
	public void actionPerformed(ActionEvent e)
	{
		repaint();
		bx+=velx;
		by+=vely;
		down();
	}
	public void up(){
		vely=-6*grawitacja*heightScale;;
		velx=0;
		repaint();
	}
	public void down(){
		vely=2*grawitacja*heightScale;
		velx=0;
	}
	public void left(){
		vely=1*grawitacja*heightScale;
		velx=-2*grawitacja*widthScale;
	}
	public void right(){
		vely=1*grawitacja*widthScale;
		velx=2*grawitacja*heightScale;
	}
	public void keyPressed(KeyEvent e){
		int code = e.getKeyCode();
		
		if(code== KeyEvent.VK_UP){
			up();
		}
		else if(code== KeyEvent.VK_DOWN){
			down();
		}
		else if(code== KeyEvent.VK_LEFT){
			left();
		}
		else if(code== KeyEvent.VK_RIGHT){
			right();
		}
		else if(code== KeyEvent.VK_SPACE&&game){
			if(t.isRunning()){
					t.stop();
			}
			else
			{
				t.start();
			}
		}
	}
	public void RepaintWindow()
	{
		double heightScale = gameFrame.getHeight()/currentHeight;
		double widthScale = gameFrame.getWidth()/currentWidth;
		for(Rectangle2D.Double obstacle : obstacleList.GetObstacleList())
		{
			obstacle.x = obstacle.getX() * widthScale;
			obstacle.y = obstacle.getY() * heightScale;
			obstacle.height = obstacle.getHeight()*heightScale;
			obstacle.width = obstacle.getWidth()*widthScale;
		}
		gx=gx*widthScale;
		gy=gy*heightScale;
		bx=bx*widthScale;
		by=by*heightScale;
		ball = new Ball(bx,by,size*gameFrame.getWidth()/config.FrameWidth,size*gameFrame.getHeight()/config.FrameHeight);
		goal = new Goal(gx,gy,size*gameFrame.getWidth()/config.FrameWidth,size*gameFrame.getHeight()/config.FrameHeight);
		
		
		
		
		
		
	}
	
	public static void main(String[] args) throws JAXBException, IOException {
		gameFrame = new JFrame();
		KMenuBar menuBar = new KMenuBar();
        prepareGameWindow(menuBar);
		}
	
	
	private static void prepareGameWindow(KMenuBar menuBar) throws JAXBException
	{
		config.Configuration();
		gameFrame.setSize(config.FrameWidth,config.FrameHeight);
		currentHeight = gameFrame.getHeight();
		currentWidth = gameFrame.getWidth();
		gameFrame.setTitle(Config.Title);
		gameFrame.setLocation(config.FrameSetX, config.FrameSetY);
		gameFrame.setJMenuBar(menuBar);
		gameFrame.add(menuBar);
		gameFrame.add(menuBar.p);
		gameFrame.setVisible(true);
		gameFrame.setResizable(true);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
	
	@Override
	public void componentResized(ComponentEvent arg0) {
				
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

}
