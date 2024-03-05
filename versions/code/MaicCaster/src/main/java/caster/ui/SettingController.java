package caster.ui;

import caster.webwork.JsonEntity;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static caster.ui.LocalStrings.*;


public class SettingController {
    @FXML
    public BorderPane settingMainPane;
    @FXML
    private Button reset;
    @FXML
    private Button apply;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private AnchorPane topPane;
    @FXML
    private Label exitSettings;

    @FXML
    private ColorPicker colorPicker1;

    @FXML
    private ColorPicker colorPicker2;

    @FXML
    private TextField apiField;
    @FXML
    private Button setApiBut;
    @FXML
    private Label colorLabel;
    @FXML
    private Label hyperlink;

    private Stage stage;



    private String currentPaint1;
    private String currentPaint2;

    private String newPaint1;
    private String newPaint2;
    private String newApiKey;

    private Stage parentStage;



    private Controller parentController;
    private Scene scene;
    private String newLocalization;
    private List<String> LocalizationName;

    @FXML
    private void initialize() {

        setLambda();
        setSupportLambda();
        setDraggedLambda();
        setText();

        apiField.setFocusTraversable(false);

        setColorParam();
        setParam(currentPaint1,currentPaint2);

        readLocalizationNames();
        choiceBox.getItems().addAll(LocalizationName);
        choiceBox.setValue(getLocalization());}

    private void setColorParam() {
        var paints = ConfigHandler.readParam();
        if(paints != null){
        currentPaint1 = paints[0];
        currentPaint2 = paints[1];
        }
    }

    private void setParam(String paint1,String paint2) {
        colorPicker1.setValue(Color.web(paint1));
        colorPicker2.setValue(Color.web(paint2));
    }

    private void setSupportLambda() {
        apiField.setContextMenu(createContextMenuLambda(apiField));

        colorPicker1.setOnHidden(e -> {
            parentStage.toFront();stage.toFront();writeParam(colorPicker1.getValue().toString(),1);});
        colorPicker2.setOnHidden(e -> {
            parentStage.toFront();stage.toFront();writeParam(colorPicker2.getValue().toString(),2);});

        choiceBox.setOnAction(event ->  newLocalization = choiceBox.getValue());
    }

    private ContextMenu createContextMenuLambda(TextField f) {
        ContextMenu contextMenu = new ContextMenu();

        var item1 = new MenuItem();
        item1.setText(COPY.getValue());
        item1.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();

          if(!f.getText().isEmpty())  content.putString(f.getText());
          else content.putString(f.getPromptText());
          clipboard.setContent(content);
        });


        var item2 = new MenuItem();
        item2.setText(PASTE.getValue());
        item2.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            String text = clipboard.getString();
            f.setText(text);
        });
        contextMenu.getItems().addAll(item1, item2);
        return contextMenu;

    }


    public Stage startSettings(Stage parrentStage, Controller parentController) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/caster/ui/settings.fxml"));

            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(ConfigHandler.getCssPath());


            Stage stage = new Stage();
            stage.setScene(scene);

            SettingController con = loader.getController();
            con.setParentStage(parrentStage);


            con.setStage(stage);
            con.setScene(scene);
            con.setParentController(parentController);

            stage.setMinWidth(550);
            stage.setMinHeight(370);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();

              return stage;
        } catch (Exception ee) {
            Terminal.importantPrint(CONFIG_ERROR);
            return new Stage();

    }}

    private void setScene(Scene scene) {this.scene = scene;
    }

    public void setParentStage(Stage parrentStage) {
        this.parentStage = parrentStage;
    }

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
        }
        );
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLambda() {

        exitSettings.setOnMousePressed(e -> stage.close());

        hyperlink.setOnMousePressed(e -> {
            try {
                Desktop.getDesktop().browse(new URI(("https://developers.convertio.co")));
            } catch (Exception err) {
                hyperlink.setText(CHECK_CONVERT_IO.getValue());
            }
        });

        setApiBut.setOnAction(getApplyApiLambda());
        apiField.setOnAction(getApplyApiLambda());
        reset.setOnAction(e -> {
            String defaultPaint1 ="000000" ;
            String defaultPaint2 = "d8002b";
            writeParam(defaultPaint1, 1);
            writeParam(defaultPaint2, 2);

            saveParam();
            setParam(defaultPaint1,defaultPaint2);

        });
        apply.setOnAction(e->saveParam());
    }



    private void setText() {
        colorLabel.setText(CHANGE_APP_COLORS.getValue());
        apply.setText(APPLY.getValue());
        hyperlink.setText(GET_API_KEY.getValue());
        reset.setText(RESET_COLORS.getValue());
        if(JsonEntity.getApiKey().isEmpty()) apiField.setPromptText(ENTER_API_KEY_HERE.getValue());
        else apiField.setPromptText(API_KEY.getValue()+": "+JsonEntity.getApiKey());

    }

    private void writeParam(String param,int num) {
        if(num ==3) {newApiKey = param; return;}

        if  (param.contains("0x")) param = param.replaceFirst("0x","");
        param="#"+param.trim( );

        if(num ==1) newPaint1= param;
        else{newPaint2= param;}

    }

    private void saveParam() {
        try {
            if(newApiKey == null && !apiField.getText().isEmpty()) writeParam(apiField.getText(), 3);
            boolean suc = ConfigHandler.saveParam(newPaint1,newPaint2,newApiKey,newLocalization);
              if (suc) {
                  parentController.setText();
                  this.setText();
                  newPaint1 = null;
                  newPaint2 = null;
                  newLocalization = null;

                  if(newApiKey != null){
                      ConfigHandler.readParam();
                      apiField.clear();
                      apiField.setPromptText(API_KEY.getValue()+": " + JsonEntity.getApiKey());
                      apiField.getStyleClass().add("ready-text-field");
                      newApiKey = null;
                  }
                  applyStylesheet();
              }
        } catch (Exception e) {

            Terminal.importantPrint(ERROR_OPTIONS_UPDATE.getValue()+ " " + e);}
        }

        private void applyStylesheet(){
            String cssPath = ConfigHandler.getCssPath();
            scene.getStylesheets().clear();
            scene.getStylesheets().setAll(cssPath);
            parentStage.getScene().getStylesheets().clear();
            parentStage.getScene().getStylesheets().setAll(cssPath);

        }


    public void setParentController(Controller parentController) {
        this.parentController = parentController;
    }


    private double xOffset = 0;
    private double yOffset = 0;
    public void resizableRight(MouseEvent event) {
        double deltaX = event.getSceneX() - xOffset;
        double deltaY = event.getSceneY() - yOffset;

        stage.setWidth(stage.getWidth() + deltaX);
        stage.setHeight(stage.getHeight() + deltaY);

        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }


    public void mousePressed(MouseEvent event) {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        }


        private EventHandler<ActionEvent> getApplyApiLambda(){
          return  event -> {
                if(!apiField.getText().isEmpty()){
                    writeParam(apiField.getText(), 3);
                    saveParam();
                    ConfigHandler.readParam();
                    apiField.clear();
                    apiField.setPromptText(API_KEY.getValue()+": " + JsonEntity.getApiKey());
                    apiField.getStyleClass().add("ready-text-field");
                    scene.getRoot().requestFocus();
                    Terminal.importantPrint(API_KEY_LOADED);
                }
            };

        }
    private void  readLocalizationNames(){
        LocalizationName = new LinkedList<>();
      try( var files = Files.walk(Paths.get(System.getProperty("user.dir")+"\\resources\\localization"))){
                files
                .filter(Files::isRegularFile)
                .filter(file ->file.getFileName().toString().matches("Loc_.+\\.properties"))
                .forEach(file-> LocalizationName.add(file.getFileName().toString().replaceAll("Loc_(.+)\\.properties", "$1")));
    }catch (Exception e){Toolkit.getDefaultToolkit().beep();Terminal.importantPrint(ERROR_OPTIONS_LOADING);}
}}

