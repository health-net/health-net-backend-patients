package org.healthnet.backend.patients.presentation.rest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.Reader;

public class WebRequest {
    private final String method;
    private final String path;
    private final Reader reader;

    public WebRequest(String method, String path, Reader reader) {
        this.method = method;
        this.path = (path != null) ? path : "";
        this.reader = reader;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getParameter(int position) {
        String[] parts = path.split("/");
        return parts[position + 1];
    }

    public <T> T deserializeBody(Class<T> classOfT) {
        try {
            return new Gson().fromJson(reader, classOfT);
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException();
        }
    }
}
