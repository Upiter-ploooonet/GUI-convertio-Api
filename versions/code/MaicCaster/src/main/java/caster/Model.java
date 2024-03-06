package caster;

import caster.ui.ErrorController;
import caster.ui.Terminal;
import caster.webwork.WebWorker;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

import static caster.ui.LocalStrings.*;


public class Model {
    final private String path;
    final private String form;
    final private File optionalPath;
    final private Stage stage;
    private String newFileName;

     public Model(String path, String form, File optionalSavePath, Stage stage){
           this.path = path;
           this.form = form;
           this.optionalPath = optionalSavePath;
           this.stage = stage;
         getAndSetNewFileName();
     }



    public void startCast() {
        try {
            Terminal.importantPrint(UPLOADING_FILE);
            String readyFile = new WebWorker().convertFile(codeFile(path), form);
            Platform.runLater(() -> {
                        boolean needSave = false;
                        do {
                            try {
                                saveFile(readyFile,optionalPath);
                            } catch (Exception e) {
                                var er = new ErrorController().startErr(newFileName);
                                needSave = er.isNeedSave();
                            }
                        } while (needSave);
                    }
            );

        } catch (OutOfMemoryError d) {Terminal.importantPrint(FILE_TOO_BIG);}
        catch (Exception e){if (e instanceof NullPointerException)Terminal.importantPrint("\n"+ERROR_SERVER.getValue());}

     }


    private void saveFile(String file,File optionalPath) throws Exception {
        byte[] dc = Base64.getDecoder().decode(file);

        var plugin = new FileChooser();

        if (optionalPath != null) {
            File f = optionalPath.getParentFile();
            plugin.setInitialDirectory(f);
        }
        plugin.setInitialFileName(newFileName);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter( FORMAT.getValue()+" ", "*." + form);
        plugin.getExtensionFilters().add(extFilter);

        Files.write(plugin.showSaveDialog(stage).toPath(), dc, StandardOpenOption.CREATE);

    }

   private void getAndSetNewFileName(){

       newFileName = path.replaceAll("\\..*", "").replaceAll(".*\\\\", "")+"."+form;

    }


    private String codeFile(String path) throws Exception {

        return Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(path)));
    }
}















