package stages;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class BasicStage{
    protected Stage stage;
    protected Scene mainMenuScene;

    public BasicStage(Stage stage, Scene mainMenuScene) {
        this.stage = stage;
        this.mainMenuScene = mainMenuScene;
    }

    public abstract void show();
    public void show(String message){

    }
}
