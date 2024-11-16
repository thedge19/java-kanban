package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.time.LocalDateTime;

public class GsonConverter {
    private final static Gson defaultGson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new DateTimeAdapter())
            .create();
    public static Gson getDefaultGson() {
        return defaultGson;
    }
}
