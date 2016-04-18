package output;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Denis on 18.4.2016 �..
 */
public class MapWriter {
    public static boolean addMap(int[][] matrix, String mapName){
        //TODO check for string correctness
        System.out.println("saving...");
        MapLevel mapLevel = new MapLevel(matrix, mapName);
        String path = "maps//" + mapName + ".map";
        try (ObjectOutputStream input = new ObjectOutputStream(new FileOutputStream(path))) {
            input.writeObject(mapLevel);
            input.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
