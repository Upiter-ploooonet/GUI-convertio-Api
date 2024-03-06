package caster.ui;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public enum LocalStrings {
    RESET_COLORS, CONFIG_ERROR, GET_API_KEY, FILE_READY, CONVERT_SUCCESS_WILL_SAVE, FORMAT, APPLY, FUNNY_MESSAGE, CHANGE_APP_COLORS, CHECK_CONVERT_IO, PASTE, COPY, SAVE, IGNORE, UPLOAD_SUCCESS, FILE_NOT_SAVED, FILE_NOT_SAVED_1, ERROR_FILE_NOT_SAVED, CLEAR_FILE_BUFFER, ENTER_FORMAT, RESET, START, ADD_FILE_UPPER, ADD_FILE_LOWER, CONVERT_STEP1, CONVERT_STEP2, CONVERT_STEP3, FILE_ADDED, SPECIFY_CONVERT_TYPE, FILE_TOO_BIG, ERROR_SERVER, SERVER_ERROR, UPLOADING_FILE, TIMEOUT_ERROR, API_KEY, ERROR_OPTIONS_LOADING, ERROR_OPTIONS_UPDATE, API_KEY_LOADED, FILE, ERROR_NO_MINUTES_LEFT, ERROR_FILE_TYPE_NOT_SUPPORTED, ERROR_CANNOT_CONVERT_TO_SPECIFIED_TYPE, ERROR_INVALID_API_KEY, ERROR_EMPTY_FILE, ENTER_API_KEY_HERE, LOADING_ICON_ERROR, PLEASE, NEED_ADD_API_KEY;
    private static String localization;
    private static ResourceBundle bundle;




    public String getValue() {
        try {
            if (bundle == null) readParam(localization);
            String key = this.name();
            if (this.equals(FUNNY_MESSAGE)) key += new Random().nextInt(16);
            return bundle.getString(key);
        } catch (Exception e) {
            return this.name();
        }
    }

    public static void setLocalization(String loc) {
        if (localization == null || !localization.equals(loc)) readParam(loc);
        localization = loc;
    }
    public static void setStartLocalization(String loc) {
        if (loc == null) loc ="ENG";
        localization = loc;
    }

    private static void readParam(String locale) {
        try {

            Locale currentLocale = new Locale.Builder().setLanguage(locale).build();
            File file = new File(System.getProperty("user.dir")+"\\resources\\localization");
            URL[] urls = {file.toURI().toURL()};
            ClassLoader loader = new URLClassLoader(urls);
           bundle = ResourceBundle.getBundle("Loc", currentLocale, loader);

        } catch (Exception e) {
            e.printStackTrace();
            Terminal.importantPrint("Error loading localization file");
        }
    }

    public static String getLocalization() {
        return localization;
    }

}
