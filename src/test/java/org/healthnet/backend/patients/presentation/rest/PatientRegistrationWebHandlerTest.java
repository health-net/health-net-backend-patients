package org.healthnet.backend.patients.presentation.rest;

import org.healthnet.backend.patients.application.services.PatientRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientRegistrationWebHandlerTest {
    private final static WebRequest webRequest = mock(WebRequest.class);
    private final static PatientRegistrationService.RegistrationData registrationData = mock(PatientRegistrationService.RegistrationData.class);
    private final static Consumer<PatientRegistrationService.RegistrationData> patientRegistrationService = mock(Consumer.class);
    private final static PatientRegistrationWebHandler patientRegistrationWebHandler = new PatientRegistrationWebHandler(patientRegistrationService);

    @BeforeEach
    void resetMocks() {
        reset(webRequest, registrationData, patientRegistrationService);
    }

    @Test
    void Handle_SuccessfulExecution_CreatedResponseHasBeenReturned() {
        when(webRequest.deserializeBody(PatientRegistrationService.RegistrationData.class)).thenReturn(registrationData);
        WebResponse webResponse = patientRegistrationWebHandler.handle(webRequest);
        verify(patientRegistrationService).accept(registrationData);
        assertEquals(201, webResponse.getStatusCode());
    }

    @Test
    void Handle_InvalidRequestBodySyntax_BadRequestResponseHasBeenReturned() {
        when(webRequest.deserializeBody(PatientRegistrationService.RegistrationData.class)).thenThrow(IllegalArgumentException.class);
        WebResponse webResponse = patientRegistrationWebHandler.handle(webRequest);
        verifyNoInteractions(patientRegistrationService);
        assertEquals(400, webResponse.getStatusCode());
    }

    @Test
    void Handle_InvalidRegistrationDataValues_BadRequestResponseHasBeenReturned() {
        when(webRequest.deserializeBody(PatientRegistrationService.RegistrationData.class)).thenReturn(registrationData);
        doThrow(IllegalArgumentException.class).when(patientRegistrationService).accept(registrationData);
        WebResponse webResponse = patientRegistrationWebHandler.handle(webRequest);
        assertEquals(400, webResponse.getStatusCode());
    }

    @Test
    void Handle_AlreadyRegisteredPatient_ConflictResponseHasBeenReturned() {
        when(webRequest.deserializeBody(PatientRegistrationService.RegistrationData.class)).thenReturn(registrationData);
        doThrow(IllegalStateException.class).when(patientRegistrationService).accept(registrationData);
        WebResponse webResponse = patientRegistrationWebHandler.handle(webRequest);
        assertEquals(409, webResponse.getStatusCode());
    }

    @Test
    void Handle_AnUnknownErrorOccurs_InternalServerErrorResponseHasBeenReturned() {
        when(webRequest.deserializeBody(PatientRegistrationService.RegistrationData.class)).thenThrow(RuntimeException.class);
        WebResponse webResponse = patientRegistrationWebHandler.handle(webRequest);
        verifyNoInteractions(patientRegistrationService);
        assertEquals(500, webResponse.getStatusCode());
    }
}
