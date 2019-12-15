package org.healthnet.backend.patients.presentation.rest;

public class WebResponse {
    private final Status status;
    private final String content;

    public WebResponse(Status status, String content) {
        this.status = status;
        this.content = content;
    }

    public WebResponse(Status status) {
        this(status, "");
    }

    public int getStatusCode() {
        return status.getCode();
    }

    public String getBodyContent() {
        return content;
    }

    public enum Status {
        OK (200),
        CREATED (201),
        BAD_REQUEST (400),
        CONFLICT (409),
        NOT_FOUND (404),
        INTERNAL_SERVER_ERROR (500);

        private final int code;

        Status(int code) {
            this.code = code;
        }

        private int getCode() {
            return code;
        }
    }
}
