package org.healthnet.backend.patients.presentation.rest;

public class WebResponse {
    private final Status status;

    public WebResponse(Status status) {
        this.status = status;
    }

    public int getStatusCode() {
        return status.getCode();
    }

    public enum Status {
        CREATED (201),
        BAD_REQUEST (400),
        CONFLICT (409),
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
