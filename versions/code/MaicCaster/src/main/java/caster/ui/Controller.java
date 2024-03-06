package caster.ui;


import caster.Model;
import caster.webwork.JsonEntity;
import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;


import javafx.scene.control.*;


import java.io.File;

import java.util.LinkedList;
import java.util.List;


import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static caster.ui.LocalStrings.*;


public class Controller {

    @FXML
    public Button dropFiles;
    @FXML
    private BorderPane mainPane;
    @FXML
    private GridPane centerPane;

    private Stage stage;
    final private List<String> paths = new LinkedList<>();
    private String form;
    private File optionalPath;

    @FXML
    private ProgressIndicator progIndicator;

    @FXML
    private Label settings;
    @FXML
    private TextArea terminal;
    @FXML
    private Label exit;
    @FXML
    private Node titleBar;

    @FXML
    private Button goButton;
    @FXML
    private Button openPluginButton;
    @FXML
    private Label downButton;
    @FXML
    private TextField textField;

    @FXML
    private MenuItem menuCopy;
    @FXML
    private MenuItem menuPast;
    @FXML
    private MenuItem menuCopy1;


    private Stage settingsStage = new Stage();



        @FXML
        private void initialize()  {
            ConfigHandler.readStartParam();
            setFocus();
            setMainLambda();
            setDraggedLambda();
            setSupportLambda();
            Terminal.setTerminal(terminal);
            setText();
    }


    private void checkAndSetPath(File path) {
        if (path != null) {
            this.paths.add(path.toString());  Terminal.importantPrint(FILE.getValue() +": "+path.getName()+" "+ UPLOAD_SUCCESS.getValue());
        }
    }


    private void checkAndSetForm(String form) {
        if (form != null) this.form = form.replaceAll(" ", "");
    }

    private void setFocus(){
        dropFiles.setFocusTraversable(false);
        openPluginButton.setFocusTraversable(false);


    }
    private void setMainLambda() {
        goButton.focusedProperty().addListener(( observable, oldValue, newValue) ->{
            if (!textField.isFocused() && !newValue) {
                goButton.requestFocus();
            }
        });
        openPluginButton.setOnAction(e -> {
            FileChooser pathPlugin = new FileChooser();


            if(optionalPath !=null) {File file = optionalPath.getParentFile();
           pathPlugin.setInitialDirectory(file);
            }


            File pluginStartPath = pathPlugin.showOpenDialog(stage);
            if (pluginStartPath != null) this.optionalPath = pluginStartPath;

            checkAndSetPath(pluginStartPath);
        });

        textField.setOnAction(getGoLambda());
        goButton.setOnAction(getGoLambda());

        exit.setOnMousePressed((event) -> Platform.exit());
        settings.setOnMousePressed((event) -> {
            if(!settingsStage.isShowing()) settingsStage = new SettingController().startSettings(stage,this);
            else {settingsStage.toFront();}
        });


        downButton.setOnMousePressed((event) ->
                stage.setIconified(true));



        dropFiles.setOnAction(e->{paths.clear();Terminal.importantPrint(CLEAR_FILE_BUFFER);});

    }

    private void checkAndConvert() {
        if (!paths.isEmpty() && form != null) {
            startCast();
        }else{


            Terminal.print( PLEASE.getValue() +",  "+ (form !=null?ADD_FILE_LOWER.getValue():SPECIFY_CONVERT_TYPE.getValue()));}

    }
      private void startCast(){

          List<String> herePaths = new LinkedList<>(paths);
          paths.clear();
          final int[] i = {0};
          for(String path: herePaths) {
              Thread thread = new Thread(() -> {
                  i[0]++;
                  progIndicator.setDisable(false);
                  new Model(path, form, optionalPath, stage).startCast();
                  --i[0];
                 if (i[0] == 0) progIndicator.setDisable(true);
              });

              thread.setDaemon(true);
              thread.start();
          }}





    private double x = 0;
    private double y = 0;

    private void setDraggedLambda() {
        titleBar.setOnMousePressed((event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        titleBar.setOnMouseDragged((event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
    }

    public  void setStage(Stage st){
        stage = st;
    }

    private void setSupportLambda() {


        menuCopy1.setOnAction(getCopyLambda(terminal));
        menuCopy.setOnAction(getCopyLambda(textField));
        menuPast.setOnAction(e->{
            Clipboard clipboard = Clipboard.getSystemClipboard();
            String text = clipboard.getString();
            textField.setText(text);
        });
        setDragAndDrop();}

    private void setDragAndDrop(){


        mainPane.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) event.acceptTransferModes(TransferMode.COPY);
        });

        mainPane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                List<File> files = db.getFiles();
                if (!files.isEmpty()) {
                    File file = files.getFirst();
                    paths.add(file.getAbsolutePath());
                    Terminal.print(FILE_ADDED.getValue() + ": " + file.getName());
                    success = true;
                    optionalPath = file;
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });


    }
protected void setText() {
        String welcomeText = JsonEntity.getApiKey().isEmpty()? NEED_ADD_API_KEY.getValue():CONVERT_STEP1.getValue() +"\n"+ CONVERT_STEP2.getValue()+ "\n"+CONVERT_STEP3.getValue();
        Terminal.importantPrint(welcomeText);
        textField.setPromptText(ENTER_FORMAT.getValue());
        menuCopy1.setText(COPY.getValue());
        menuCopy.setText(COPY.getValue());
        menuPast.setText(PASTE.getValue());
        dropFiles.setText(RESET.getValue());
        goButton.setText(START.getValue());
        openPluginButton.setText(ADD_FILE_UPPER.getValue());
    }


    private EventHandler<ActionEvent> getCopyLambda(TextInputControl field){
        return e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(field.getText() );
            clipboard.setContent(content);
        };

    }


   private EventHandler<ActionEvent> getGoLambda(){

   return e -> {
        checkAndSetForm(textField.getText());
        checkAndConvert();
    };}


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

}
