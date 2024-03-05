package caster.ui;

import javafx.application.Platform;
import javafx.scene.control.TextArea;


public class Terminal {



   static int str;
   static long lastMessage = 0;


    static TextArea terminal;
    public synchronized static void printOnceClear(LocalStrings message){
        if( System.currentTimeMillis() - lastMessage >1900){
        importantPrint(message.getValue());
        lastMessage = System.currentTimeMillis();
    }}

    public static void print(LocalStrings message){
        print(message.getValue());
    }
    public static void print(String message){

        if(str%2==0)  Platform.runLater(()->terminal.clear());
        Platform.runLater(()->{
            terminal.setText(terminal.getText() +message +"\n");
        str++;
        });
    }

    public synchronized static void importantPrint(LocalStrings s){
        importantPrint(s.getValue());
    }
    public synchronized static void importantPrint(String s) {
        Platform.runLater(()->terminal.clear());
        print(s);

    }

    public static void setTerminal(TextArea t) {
        terminal = t;
    }

}
