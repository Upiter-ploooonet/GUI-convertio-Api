package caster.webwork;

import caster.ui.Terminal;
import okhttp3.*;

import static caster.ui.LocalStrings.*;


public class WebWorker {
    private final OkHttpClient client = new OkHttpClient();
    private final JsonParser parser = new JsonParser();


    public String convertFile(String encodedFile, String form) throws Exception {

            String json = parser.getJson(encodedFile, form);

            String response = sendFile(json);
            checkValid(response);
            String id = parser.getId(response);

            getStatusFileLoop(id, parser);

            String fileResult = parser.getFile(getResult(id));

            Terminal.print(CONVERT_SUCCESS_WILL_SAVE);
            return fileResult;

    }

    private void checkValid(String response) throws Exception {
        String err = parser.getError(response);
        if (err != null) {throw new ServerException(err);}
    }




    private String sendFile(String json) throws Exception {
        String url = "https://api.convertio.co/convert";
        var body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response resp = client.newCall(request).execute()) {
            return  resp.body().string();}

    }


    private String getStatusFile(String id) throws Exception {
        String url = "http://api.convertio.co/convert/" + id + "/status";
        Request request3 = new Request.Builder().url(url).build();
        try (Response resp = client.newCall(request3).execute()) {
            return resp.body().string();}
    }

    private void getStatusFileLoop(String id, JsonParser parser) throws Exception {
        int i = 0;
        String status;
        String response;
        do {
         response = getStatusFile(id);
         if(parser.getError(response) != null)throw new ServerException(parser.getError(response));
         status = parser.getSuccess(response);
         Terminal.printOnceClear(FUNNY_MESSAGE);
         Thread.sleep(2000);
         i++;
         }while(!status.equals("finish") || i == 761);
         if (i == 760){throw new ServerException(TIMEOUT_ERROR.getValue());}
        Terminal.print(FILE_READY);
       }



       private String getResult(String id) throws Exception {
           String url = "http://api.convertio.co/convert/" + id + "/dl";
           Request request1 = new Request.Builder().url(url).build();
           try (Response resp = client.newCall(request1).execute()) {
               return resp.body().string();
           }
       }}









