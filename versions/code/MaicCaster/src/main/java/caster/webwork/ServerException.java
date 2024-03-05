package caster.webwork;

import caster.ui.LocalStrings;
import caster.ui.Terminal;

import java.awt.Toolkit;

import static caster.ui.LocalStrings.*;

public class ServerException extends Exception{


    ServerException(String serverErr){ printErr(serverErr);}

    private void printErr(String err) {
        Toolkit.getDefaultToolkit().beep();
        var errPrint= switchErr(err);
        if(switchErr(err).equals(SERVER_ERROR)) Terminal.importantPrint(errPrint.getValue() + err);
        else Terminal.importantPrint(errPrint);

    }

    private LocalStrings switchErr(String err) {
        if(err.contains("No convertion minutes left")) return ERROR_NO_MINUTES_LEFT;
        if(err.contains("The Type of Output File is not supported yet")) return  ERROR_FILE_TYPE_NOT_SUPPORTED;
        if(err.contains("We can't convert files in the")) return   ERROR_CANNOT_CONVERT_TO_SPECIFIED_TYPE;
        if(err.contains("API Key is invalid")) return   ERROR_INVALID_API_KEY;
        if(err.contains("Uploaded file is empty")) return  ERROR_EMPTY_FILE;
        return SERVER_ERROR;

    }}





