package output;

import java.io.*;
import java.util.Objects;

public class MapWriter {
    public static boolean addMap(int[][] matrix, String mapName){
        if (Objects.equals(mapName, "")){
            return false;
        }
        //TODO check with existing maps
        System.out.println("saving...");
        MapLevel mapLevel = new MapLevel(matrix, mapName);
        String path = "maps//" + mapName + ".map";
        try (ObjectOutputStream input = new ObjectOutputStream(new FileOutputStream(path))) {
            input.writeObject(mapLevel);
            input.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("maps//mapsNames.txt", true))){
            writer.append(mapLevel.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
