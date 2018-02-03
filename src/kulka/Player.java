package kulka;

import java.io.Serializable;
import javax.swing.JPanel;

public class Player  implements Serializable {
    public int score=0;
    public String name="";
    public int life=3;
    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public Player(String name, int score) {
        this.score = score;
        this.name = name;
    }
    public void Point (int score){
    	score++;	
    }
    
}