package caster.ui;

import caster.Application;
import caster.webwork.JsonEntity;

import java.awt.Toolkit;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static caster.ui.LocalStrings.*;


public class ConfigHandler {

    private static final Path configPath = Paths.get(System.getProperty("user.dir")  +(File.separator + "resources" + File.separator + "config.css"));

   public static void readStartParam() {
      try {
         try (
              Stream<String> lines = (Files.lines(configPath))
         ) {
            lines.filter(line -> !line.trim().isEmpty())
                    .forEach(line -> {

                       if (line.contains("API=")) {
                          String key = line.replaceAll("API=(.*?);", "$1").trim();
                          JsonEntity.setApiKey(key);
                       }
                       if (line.contains("LOC=")) {
                          String key = line.replaceAll("LOC=(.*?);", "$1").trim();
                          LocalStrings.setStartLocalization(key);
                       }
                    });
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
      } catch (Exception e) {
         Application.criticalErr("Configuration file " + configPath + " not found");
      }
   }

   public static String[] readParam() {
      try ( Stream<String> lines = (Files.lines(configPath))) {
         String[] color = new String[2];
         boolean[] foundParam = new boolean[3];
         lines.filter(line -> !line.trim().isEmpty())
                 .forEach(line -> {
                    if (line.contains("paint1:")) {
                       color[0] = line.replaceAll("paint1:(.*?);", "$1").trim();
                       foundParam[0] =true;
                    }

                    if (line.contains("paint2:")) {
                       color[1] = line.replaceAll("paint2:(.*?);", "$1").trim();
                       foundParam[1] =true;
                    }
                    if (line.contains("API=")) {
                       String key = line.replaceAll("API=(.*?);", "$1").trim();
                       JsonEntity.setApiKey(key);
                       foundParam[2] =true;
                    }
                    if(foundParam[2] && foundParam[1]&& foundParam[0]) {lines.close();}
                 });
         return color;
      }
      catch (Exception e) {
         Toolkit.getDefaultToolkit().beep();
         Terminal.importantPrint(ERROR_OPTIONS_LOADING);
         return null;
      }
   }

   public static String getCssPath(){
      return new File(configPath.toString()).toURI().toString();
   }


   public static boolean saveParam(String newPaint1, String newPaint2, String newApiKey, String newLocalization) {
      boolean success = false;
      try {
         List<String> update = new ArrayList<>();
         try (Stream<String> lines = (Files.lines(configPath))) {
            lines.forEach(line -> {
               if (newPaint1 != null && line.contains("paint1:")) {
                  update.add("paint1:" + newPaint1 + ";");

               } else if (newPaint2 != null && line.contains("paint2:")) {
                  update.add("paint2:" + newPaint2 + ";");

               } else if (newApiKey != null && line.contains("API=")) {
                  update.add("API=" + newApiKey + ";");

               } else if (newLocalization != null && line.contains("LOC=")) {
                  update.add("LOC=" + newLocalization + ";");
                  setLocalization(newLocalization);
               } else {
                  update.add(line);
               }
            });
            writeParam(update);
            success = true;
         } catch (Exception e) {
            throw e;
         }
      } catch (Exception e) {
         e.printStackTrace();
         Toolkit.getDefaultToolkit().beep();
         Terminal.importantPrint(ERROR_OPTIONS_UPDATE.getValue() + " " + e);
      }
      return success;
   }

   private static void writeParam(List<String> newParams)throws Exception {
    Files.write(configPath,newParams);

}}

