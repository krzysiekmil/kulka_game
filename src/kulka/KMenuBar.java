package kulka;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class KMenuBar extends JMenuBar  {
	Manager p =new Manager();
    public KMenuBar() {
        addMenuElements();
    }

    private void addMenuElements(){

        JMenu menu = new JMenu("Gra");
        JMenuItem Start = new JMenuItem(new AbstractAction("Start") {
            public void actionPerformed(ActionEvent e) {
            	p.player.name=null;
            	p.player.life=3;
            	p.player.score=0;
            	p.t.start();
            	p.world=1;
            	p.firstRun=true;
            	p.bx=p.config.ballX;
            	p.by=p.config.ballY;
            	p.game=true;
            	
            }
        });
        menu.add(Start);
        JMenuItem Pauza = new JMenuItem(new AbstractAction("Pauza") {
            public void actionPerformed(ActionEvent e) {
               p.t.stop();
                
            }
        });
        menu.add(Pauza);
        JMenuItem Koniec = new JMenuItem(new AbstractAction("Koniec") {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(Koniec);
        add(menu);
        
        JMenu Opcje= new JMenu("Opcje");
        add(Opcje);
        JMenu Grawitacja = new JMenu("Grawitacja");
        JMenuItem Slaba = new JMenuItem(new AbstractAction("Slaba") {
            public void actionPerformed(ActionEvent e) {
                p.grawitacja=0.75;
                repaint();
            }
        });
        Grawitacja.add(Slaba);
        JMenuItem Normalna = new JMenuItem(new AbstractAction("Normalna") {
            public void actionPerformed(ActionEvent e) {
                p.grawitacja=1;
                repaint();
            }
        });
        Grawitacja.add(Normalna);
      
        JMenuItem Silna = new JMenuItem(new AbstractAction("Silna") {
            public void actionPerformed(ActionEvent e) {
                p.grawitacja=1.25;
                repaint();
            }
        });
        Grawitacja.add(Silna);
        JMenuItem Zasady = new JMenuItem(new AbstractAction("Zasady"){
        public void actionPerformed(ActionEvent e) {
        	p.t.stop();
        	p.Rules();
        	}
        });
        Opcje.add(Zasady);
        JMenuItem Wyniki = new JMenuItem(new AbstractAction("Wyniki"){
            public void actionPerformed(ActionEvent e) {
            	try {
            		p.HighList();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            	}
            });
            Opcje.add(Wyniki);
    	Opcje.add(Grawitacja);
    }
}
