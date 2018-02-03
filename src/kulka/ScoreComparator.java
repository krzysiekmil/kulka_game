package kulka;


import java.util.Comparator;

public class ScoreComparator implements Comparator<Player> {
    public int compare(Player score1, Player score2) {

        int sc1 = score1.getScore();
        int sc2 = score2.getScore();

        if (sc1 > sc2){
            return -1;
        }else if (sc1 < sc2){
            return +1;
        }else{
            return 0;
        }
    }
}