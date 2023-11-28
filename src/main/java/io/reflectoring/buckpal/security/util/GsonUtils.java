package io.reflectoring.buckpal.security.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {

    private static final Gson gson = new GsonBuilder()
            .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public static Gson getLowerCaseWithUnderscores() {
        return gson;
    }
}
