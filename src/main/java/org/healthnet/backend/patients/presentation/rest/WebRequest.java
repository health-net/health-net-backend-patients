package org.healthnet.backend.patients.presentation.rest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.Reader;

public class WebRequest {
    private final Reader reader;

    public WebRequest(Reader reader) {
        this.reader = reader;
    }


    public <T> T deserializeBody(Class<T> classOfT) {
        try {
            return new Gson().fromJson(reader, classOfT);
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException();
        }
    }
}
