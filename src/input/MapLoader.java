package input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapLoader {
    private List<String> maps;
    private int index;

    public MapLoader() {
        maps = new ArrayList<>();
        getLevelsFromFile();
        this.index = 0;
    }

    private void getLevelsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("maps//mapsNames.txt"))){
            String map;
            while ((map = reader.readLine()) != null){
                this.maps.add(map);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO exception.");
        }
    }

    public String getNextLevelName(){
        return this.maps.get(index++);
    }


}
