package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

public class GsonConverter {

    public static final Gson defaultGson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new DateTimeAdapter())
            .create();

    private GsonConverter() {
    }

    public static Gson getDefaultGson() {
        return defaultGson;
    }
}
