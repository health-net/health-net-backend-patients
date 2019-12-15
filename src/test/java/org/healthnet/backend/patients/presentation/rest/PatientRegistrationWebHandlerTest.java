package org.healthnet.backend.patients.presentation.rest;

import org.healthnet.backend.patients.application.dtos.PatientRegistrationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientRegistrationWebHandlerTest {
    private final static WebRequest webRequest = mock(WebRequest.class);
    private final static PatientRegistrationDto PATIENT_REGISTRATION_DTO = mock(PatientRegistrationDto.class);
    private final static Consumer<PatientRegistrationDto> patientRegistrationService = mock(Consumer.class);
    private final static PatientRegistrationWebHandler patientRegistrationWebHandler = new PatientRegistrationWebHandler(patientRegistrationService);

    @BeforeEach
    void resetMocks() {
        reset(webRequest, PATIENT_REGISTRATION_DTO, patientRegistrationService);
    }

    @Test
    void Handle_SuccessfulExecution_CreatedResponseHasBeenReturned() {
        when(webRequest.deserializeBody(PatientRegistrationDto.class)).thenReturn(PATIENT_REGISTRATION_DTO);
        WebResponse webResponse = patientRegistrationWebHandler.handle(webRequest);
        verify(patientRegistrationService).accept(PATIENT_REGISTRATION_DTO);
        assertEquals(201, webResponse.getStatusCode());
    }

    @Test
    void Handle_InvalidRequestBodySyntax_BadRequestResponseHasBeenReturned() {
        when(webRequest.deserializeBody(PatientRegistrationDto.class)).thenThrow(IllegalArgumentException.class);
        WebResponse webResponse = patientRegistrationWebHandler.handle(webRequest);
        verifyNoInteractions(patientRegistrationService);
        assertEquals(400, webResponse.getStatusCode());
    }

    @Test
    void Handle_InvalidRegistrationDataValues_BadRequestResponseHasBeenReturned() {
        when(webRequest.deserializeBody(PatientRegistrationDto.class)).thenReturn(PATIENT_REGISTRATION_DTO);
        doThrow(IllegalArgumentException.class).when(patientRegistrationService).accept(PATIENT_REGISTRATION_DTO);
        WebResponse webResponse = patientRegistrationWebHandler.handle(webRequest);
        assertEquals(400, webResponse.getStatusCode());
    }

    @Test
    void Handle_AlreadyRegisteredPatient_ConflictResponseHasBeenReturned() {
        when(webRequest.deserializeBody(PatientRegistrationDto.class)).thenReturn(PATIENT_REGISTRATION_DTO);
        doThrow(IllegalStateException.class).when(patientRegistrationService).accept(PATIENT_REGISTRATION_DTO);
        WebResponse webResponse = patientRegistrationWebHandler.handle(webRequest);
        assertEquals(409, webResponse.getStatusCode());
    }

    @Test
    void Handle_AnUnknownErrorOccurs_InternalServerErrorResponseHasBeenReturned() {
        when(webRequest.deserializeBody(PatientRegistrationDto.class)).thenThrow(RuntimeException.class);
        WebResponse webResponse = patientRegistrationWebHandler.handle(webRequest);
        verifyNoInteractions(patientRegistrationService);
        assertEquals(500, webResponse.getStatusCode());
    }
}
