package input;

import utilities.Settings;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SettingsReader {
    public static void loadSettings(){
        try (BufferedReader reader = new BufferedReader(new FileReader("settings/settings.txt"))) {
            Settings.setFirstPlayerName(reader.readLine());
            Settings.setSecondPlayerName(reader.readLine());
            Settings.setEnableSound(Boolean.parseBoolean(reader.readLine()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
