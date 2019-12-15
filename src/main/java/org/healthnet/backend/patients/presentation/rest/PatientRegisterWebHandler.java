package org.healthnet.backend.patients.presentation.rest;

import org.healthnet.backend.patients.application.dtos.PatientSummaryDto;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class PatientRegisterWebHandler implements WebHandler {
    private final Supplier<List<PatientSummaryDto>> patientRegisterService;
    private final Function<List<PatientSummaryDto>, String> serialization;

    public PatientRegisterWebHandler(Supplier<List<PatientSummaryDto>> patientRegisterService,
                                     Function<List<PatientSummaryDto>, String> serialization) {
        this.patientRegisterService = patientRegisterService;
        this.serialization = serialization;
    }

    @Override
    public WebResponse handle(WebRequest webRequest) {
        try {
            List<PatientSummaryDto> summaries = patientRegisterService.get();
            return new WebResponse(WebResponse.Status.OK, serialization.apply(summaries));
        } catch (RuntimeException e) {
            return new WebResponse(WebResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
