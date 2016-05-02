package input;

import output.MapLevel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by Denis on 18.4.2016 �..
 */
public class MapReader {
    public static MapLevel readMap(String path) {
        MapLevel map = null;
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(path))) {
            Object obj = reader.readObject();
            map = (MapLevel) obj;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("class not found, may be changed. Please check for differences.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
