package output;

import utilities.Settings;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SettingsWriter {
    public static void saveSettings(){
        try (PrintWriter writer = new PrintWriter("settings/settings.txt")){
            writer.println(Settings.getFirstPlayerName());
            writer.println(Settings.getSecondPlayerName());
            writer.println(Settings.isEnableSound());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
