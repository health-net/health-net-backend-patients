package org.healthnet.backend.patients.presentation.rest;

import org.healthnet.backend.patients.application.services.PatientRegisterService;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class PatientRegisterWebHandler implements WebHandler {
    private final Supplier<List<PatientRegisterService.PatientSummaryData>> patientRegisterService;
    private final Function<List<PatientRegisterService.PatientSummaryData>, String> parsing;

    public PatientRegisterWebHandler(Supplier<List<PatientRegisterService.PatientSummaryData>> patientRegisterService,
                                     Function<List<PatientRegisterService.PatientSummaryData>, String> parsing) {
        this.patientRegisterService = patientRegisterService;
        this.parsing = parsing;
    }

    @Override
    public WebResponse handle(WebRequest webRequest) {
        try {
            List<PatientRegisterService.PatientSummaryData> summaries = patientRegisterService.get();
            return new WebResponse(WebResponse.Status.OK, parsing.apply(summaries));
        } catch (RuntimeException e) {
            return new WebResponse(WebResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
