package kulka;

import java.util.*;
import java.io.*;

public class HighscoreManager {
    public ArrayList<Player> scores;
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;
    public void addScore(String name, int score) throws ClassNotFoundException {
        loadScoreFile();
        scores.add(new Player(name, score));
        updateScoreFile();
    }
    public HighscoreManager() {
        scores = new ArrayList<Player>();
    }
    public ArrayList<Player> getScores() throws ClassNotFoundException {
        loadScoreFile();
        sort();
        return scores;
    }

    private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scores, comparator);
    }

    public void loadScoreFile() throws ClassNotFoundException {
        try {
            inputStream = new ObjectInputStream(new FileInputStream("src\\kulka\\HighScore.txt"));
            scores = (ArrayList<Player>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println( e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Laad] IO Error: " + e.getMessage());
            }
        }
    }

    public void updateScoreFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream("src\\kulka\\HighScore.txt"));
            outputStream.writeObject(scores);
        } catch (FileNotFoundException e) {
            System.out.println( e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String getshow() throws Exception  {
        String highscoreString = "";
        int max = 10;
        scores = getScores();
        int i = 0;
        int x = scores.size();
        if (x > max) {
            x = max;
        }
        while (i < x) {
            highscoreString += (i + 1) + ". " + scores.get(i).getName() + "		 : 	" + scores.get(i).getScore() + "\n";
            i++;
        }
        return highscoreString;
    }
}
