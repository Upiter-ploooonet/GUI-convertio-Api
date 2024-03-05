package caster.webwork;


public class JsonEntity {

  private volatile static String currentApiKey ;
  private String apikey = currentApiKey;
  private String file;
  private String outputformat ;
  private final String input = "base64";
  private final String filename = "file";
  private String error ;
  private ForSaveEntity data = new ForSaveEntity();


  public JsonEntity(String file, String outputformat) {this.outputformat = outputformat;this.file = file;}


    public static void setApiKey(String apikey) {
        currentApiKey = apikey;
    }
    public static String getApiKey() {
        return  currentApiKey ;
    }

  public String getError(){return error;}
  public String getId() {return data.id;}
  public String getContent() {return data.content;}
  public String getStep(){return data.step;}



 private static class ForSaveEntity{
  private  String id ;
  private  String step ;
  private  String content ;



}}


