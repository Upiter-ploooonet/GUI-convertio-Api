package caster.ui;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.awt.Toolkit;

import static caster.ui.LocalStrings.*;


public class ErrorController {
    @FXML
    public Label errLabel;
    @FXML
    public Button yes;
    @FXML
    public Button no;

    @FXML
    public AnchorPane topPane;



    private boolean needSave ;



    private Stage stage;


    @FXML
    private void initialize(){
        Toolkit.getDefaultToolkit().beep();
        setText();
        setLambda();
        setDraggedLambda();


    }

    private void setErrText(String err) {
        errLabel.setText(FILE.getValue() + " "+err+" " + FILE_NOT_SAVED.getValue() +"\n"+FILE_NOT_SAVED_1.getValue());
    }

    private void setText() {

        yes.setText(SAVE.getValue());
        no.setText(IGNORE.getValue());
    }


    public void setLambda() {
        no.setOnAction(e -> stage.close());
        yes.setOnAction(e -> {needSave=true; stage.close();});
    }

    public ErrorController startErr(String err)  {
        try{
        FXMLLoader loader = new FXMLLoader(ErrorController.class.getResource("error.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(ConfigHandler.getCssPath());

        Stage secondStage = new Stage();
        secondStage.setScene(scene);
        secondStage.initStyle(StageStyle.TRANSPARENT);


        secondStage.initModality(Modality.APPLICATION_MODAL);


        ErrorController con = loader.getController();
        con.setErrText(err);
        con.setStage(secondStage);
        secondStage.showAndWait();
        return con;
        } catch (Exception ee) {
        Terminal.importantPrint(ERROR_FILE_NOT_SAVED);
    }return null;}



    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean isNeedSave() {var n =  needSave;needSave = false;return n;}


    private double x = 0;
    private double y = 0;

    private void setDraggedLambda() {
        topPane.setOnMousePressed((event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        topPane.setOnMouseDragged((event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
    }
}

