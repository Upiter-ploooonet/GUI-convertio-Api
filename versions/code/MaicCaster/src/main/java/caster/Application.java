package caster;

import caster.ui.Controller;

import caster.ui.ConfigHandler;
import caster.ui.Terminal;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import static caster.ui.LocalStrings.LOADING_ICON_ERROR;


public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) {

        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("main.fxml"));

            Scene scene = new Scene(loader.load());

            scene.getStylesheets().add(ConfigHandler.getCssPath());

            stage.setScene(scene);

            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setMinWidth(650);
            stage.setMinHeight(450);
            stage.show();

            Controller con = loader.getController();
            con.setStage(stage);
            try {
                stage.getIcons().add(new Image(Application.class.getResourceAsStream("icon.png")));
            } catch (Exception e) {
                Terminal.print(LOADING_ICON_ERROR);
            }
        } catch (Exception e) {
            criticalErr(e.toString());
        }

    }

    public static void criticalErr(String err) {
        System.err.println(err);
        Terminal.importantPrint(err);
        try {
            Thread.sleep(10000);
        } catch (Exception g) {
        }
        System.exit(404);
    }

     public static void main(String[] args) {
        launch();
    }
}