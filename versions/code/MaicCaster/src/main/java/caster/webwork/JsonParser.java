package caster.webwork;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;



public class JsonParser {

    Moshi moshi = new Moshi.Builder().build();
    JsonAdapter<JsonEntity> jsonAdapter = moshi.adapter(JsonEntity.class);

    public  String getJson(String file,String format) {
        return jsonAdapter.toJson(new JsonEntity(file, format));
    }


    public  String getId(String file) throws Exception {
        return jsonAdapter.fromJson(file).getId();

    }

    public  String getFile(String file) throws Exception {
        return jsonAdapter.fromJson(file).getContent();
    }

    public  String getError(String file) throws Exception {
        return jsonAdapter.fromJson(file).getError();
    }

    public String getSuccess(String json) throws Exception {
        return jsonAdapter.fromJson(json).getStep();
    }

}

