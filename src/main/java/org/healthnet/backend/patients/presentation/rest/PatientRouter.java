package org.healthnet.backend.patients.presentation.rest;

public class PatientRouter implements WebHandler {
    private final WebHandler patientRegistrationWebHandler;
    private final WebHandler patientRegisterWebHandler;
    private final WebHandler patientDetailWebHandler;

    public PatientRouter(WebHandler patientRegistrationWebHandler, WebHandler patientRegisterWebHandler, WebHandler patientDetailWebHandler) {
        this.patientRegistrationWebHandler = patientRegistrationWebHandler;
        this.patientRegisterWebHandler = patientRegisterWebHandler;
        this.patientDetailWebHandler = patientDetailWebHandler;
    }

    @Override
    public WebResponse handle(WebRequest webRequest) {
        if (webRequest.getPath().matches("(/?)$")) {
            if (webRequest.getMethod().equals("POST")) {
                return patientRegistrationWebHandler.handle(webRequest);
            } else if (webRequest.getMethod().equals("GET")) {
                return patientRegisterWebHandler.handle(webRequest);
            }
        } else if (webRequest.getPath().matches("(/?)(.+)(/?)$") && webRequest.getMethod().equals("GET")) {
            return patientDetailWebHandler.handle(webRequest);
        }
        return new WebResponse(WebResponse.Status.NOT_FOUND);
    }
}
