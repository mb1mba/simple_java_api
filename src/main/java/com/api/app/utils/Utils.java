package com.api.app.utils;

import java.io.IOException;
import java.io.InputStream;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Utils {
    private static final Gson gson = new Gson();

    // https://stackoverflow.com/questions/43822262/get-post-data-from-httpexchange
    public static String readRequestBody(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        int i;

        while ((i = is.read()) != -1) {
            sb.append((char) i);
        }

        String requestBody = sb.toString();
        return requestBody;
    }

    public static <T> T deserializeJsonToEntity(String requestBody, Class<T> entityClass) {
        try {
            return gson.fromJson(requestBody, entityClass);
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Invalid JSON format for " + entityClass.getSimpleName(), e);
        }
    }
}
