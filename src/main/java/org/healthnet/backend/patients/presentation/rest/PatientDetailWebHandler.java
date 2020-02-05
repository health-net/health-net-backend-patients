package org.healthnet.backend.patients.presentation.rest;

import org.healthnet.backend.patients.application.dtos.PatientDetailDto;

import java.util.NoSuchElementException;
import java.util.function.Function;

public class PatientDetailWebHandler implements WebHandler {
    private final Function<String, PatientDetailDto> service;
    private final Function<PatientDetailDto, String> serialization;

    public PatientDetailWebHandler(Function<String, PatientDetailDto> service, Function<PatientDetailDto, String> serialization) {
        this.service = service;
        this.serialization = serialization;
    }

    @Override
    public WebResponse handle(WebRequest webRequest) {
        try {
            String id = webRequest.getParameter(0);
            PatientDetailDto dto = service.apply(id);
            String serialized = serialization.apply(dto);
            return new WebResponse(WebResponse.Status.OK, serialized);
        } catch (NoSuchElementException e) {
            return new WebResponse(WebResponse.Status.NOT_FOUND);
        } catch (RuntimeException e) {
            return new WebResponse(WebResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
