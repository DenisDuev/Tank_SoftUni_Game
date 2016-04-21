package output;

import java.io.*;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Denis on 20.4.2016 ã..
 */
public class ScoreManager {

    public static void saveScore(int tank1Score, String tank1Name, int tank2Score, String tank2Name) {
        TreeMap<Integer, String> scores = readScoreFromFile();
        scores.put(tank1Score, tank1Name);
        scores.put(tank2Score, tank2Name);

        if (!scores.containsKey(tank1Score)){
            scores.remove(scores.firstKey());
        }
        if (!scores.containsKey(tank2Score)){
            scores.remove(scores.firstKey());
        }


        writeScoreToFile(scores);
    }

    private static void writeScoreToFile(TreeMap<Integer, String> scores){
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("maps//score.scr"))){
            writer.writeObject(scores);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static TreeMap<Integer, String> readScoreFromFile(){
        TreeMap<Integer, String> scores = new TreeMap<>();
        try(ObjectInputStream reader = new ObjectInputStream( new FileInputStream("maps//score.scr"))) {
            scores = (TreeMap<Integer, String>) reader.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return scores;
    }

    public static String getScores(){
        TreeMap<Integer, String> scores = readScoreFromFile();
        //reverse map
        TreeMap<Integer, String> newScores = new TreeMap<>(Collections.reverseOrder());
        newScores.putAll(scores);
        scores = newScores;

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, String> score : scores.entrySet()) {
            stringBuilder.append(score.getKey() + " - " + score.getValue() + "\n");
        }

        return stringBuilder.toString();
    }
}
