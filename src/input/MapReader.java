package input;

import output.MapLevel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by Denis on 18.4.2016 ã..
 */
public class MapReader {
    public static MapLevel readMap(String path){
        MapLevel map = null;
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(path))) {
            Object obj = reader.readObject();
            map = (MapLevel) obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return map;
    }
}
