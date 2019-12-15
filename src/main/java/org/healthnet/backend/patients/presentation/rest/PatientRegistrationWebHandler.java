package org.healthnet.backend.patients.presentation.rest;

import org.healthnet.backend.patients.application.dtos.PatientRegistrationDto;

import java.util.function.Consumer;

public class PatientRegistrationWebHandler implements WebHandler {
    private final Consumer<PatientRegistrationDto> patientRegistrationService;

    public PatientRegistrationWebHandler(Consumer<PatientRegistrationDto> patientRegistrationService) {
        this.patientRegistrationService = patientRegistrationService;
    }

    @Override
    public WebResponse handle(WebRequest webRequest) {
        try {
            PatientRegistrationDto patientRegistrationDto = webRequest.deserializeBody(PatientRegistrationDto.class);
            patientRegistrationService.accept(patientRegistrationDto);
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
