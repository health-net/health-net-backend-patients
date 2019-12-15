package org.healthnet.backend.patients.presentation.rest;

import org.healthnet.backend.patients.application.services.PatientRegisterService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PatientRegisterWebHandlerTest {
    private final static WebRequest webRequest = mock(WebRequest.class);
    private final static List<PatientRegisterService.PatientSummaryData> summaries = mock(List.class);
    private final static Function<List<PatientRegisterService.PatientSummaryData>, String> toJson = mock(Function.class);
    private final static PatientRegisterService patientRegisterService = mock(PatientRegisterService.class);
    private final static PatientRegisterWebHandler patientRegisterWebHandler = new PatientRegisterWebHandler(
            patientRegisterService,
            toJson);

    @Test
    void Handle_SuccessfulExecution_OkResponseWithBodyHasBeenReturned() {
        String json = "json";
        when(patientRegisterService.get()).thenReturn(summaries);
        when(toJson.apply(summaries)).thenReturn(json);

        WebResponse webResponse = patientRegisterWebHandler.handle(webRequest);
        assertEquals(200, webResponse.getStatusCode());
        assertEquals(json, webResponse.getBodyContent());
    }

    @Test
    void Handle_AnUnknownErrorOccurs_InternalServerErrorHasBeenReturned() {
        when(patientRegisterService.get()).thenThrow(RuntimeException.class);
        WebResponse webResponse = patientRegisterWebHandler.handle(webRequest);
        assertEquals(500, webResponse.getStatusCode());
    }
}
