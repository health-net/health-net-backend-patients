package org.healthnet.backend.patients.presentation.rest;

import org.healthnet.backend.patients.application.services.PatientRegistrationService;

import java.util.function.Consumer;

public class PatientRegistrationWebHandler implements WebHandler {
    private final Consumer<PatientRegistrationService.RegistrationData> patientRegistrationService;

    public PatientRegistrationWebHandler(Consumer<PatientRegistrationService.RegistrationData> patientRegistrationService) {
        this.patientRegistrationService = patientRegistrationService;
    }

    @Override
    public WebResponse handle(WebRequest webRequest) {
        try {
            PatientRegistrationService.RegistrationData registrationData = webRequest.deserializeBody(PatientRegistrationService.RegistrationData.class);
            patientRegistrationService.accept(registrationData);
            return new WebResponse(WebResponse.Status.CREATED);
        } catch (IllegalArgumentException e) {
            return new WebResponse(WebResponse.Status.BAD_REQUEST);
        } catch (IllegalStateException e) {
            return new WebResponse(WebResponse.Status.CONFLICT);
        } catch (Exception e) {
            return new WebResponse(WebResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
