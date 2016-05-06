package utilities;

public class Settings {
    static String firstPlayerName;
    static String secondPlayerName;
    static boolean enableSound;

    public static void setFirstPlayerName(String firstPlayerName) {
        Settings.firstPlayerName = firstPlayerName;
    }

    public static void setSecondPlayerName(String secondPlayerName) {
        Settings.secondPlayerName = secondPlayerName;
    }

    public static void setEnableSound(boolean enableSound) {
        Settings.enableSound = enableSound;
    }

    public static String getFirstPlayerName() {

        return firstPlayerName;
    }

    public static String getSecondPlayerName() {
        return secondPlayerName;
    }

    public static boolean isEnableSound() {
        return enableSound;
    }
}
