package org.healthnet.backend.patients.presentation.rest;

import org.healthnet.backend.patients.application.services.PatientRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PatientsServletTest {
    private final static HttpServletRequest request = mock(HttpServletRequest.class);
    private final static HttpServletResponse response = new MockHttpServletResponse();
    private final static Function<HttpServletRequest, PatientRegistrationService.RegistrationData> registrationDataMapping = mock(Function.class);
    private final static PatientRegistrationService.RegistrationData registrationData = mock(PatientRegistrationService.RegistrationData.class);
    private final static Consumer<PatientRegistrationService.RegistrationData> registrationService = mock(PatientRegistrationService.class);
    private final static PatientsServlet servlet = new PatientsServlet(registrationService, registrationDataMapping);

    @BeforeEach
    void resetMocks() {
        reset(request, registrationDataMapping, registrationData, registrationService);
    }

    @Test
    void doPost_SuccessfulExecution_CreatedResponseHasBeenReturned() {
        when(registrationDataMapping.apply(request)).thenReturn(registrationData);
        doNothing().when(registrationService).accept(registrationData);

        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response);
        servlet.doPost(request, wrapper);
        assertEquals(201, response.getStatus());
    }

    @Test
    void doPost_InvalidRequestBodyValues_BadRequestResponseHasBeenReturned() {
        when(registrationDataMapping.apply(request)).thenThrow(IllegalArgumentException.class);

        verifyNoInteractions(registrationService);

        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response);
        servlet.doPost(request, wrapper);
        assertEquals(400, response.getStatus());
    }

    @Test
    void doPost_AlreadyRegisteredPatient_ConflictResponseHasBeenReturned() {
        when(registrationDataMapping.apply(request)).thenReturn(registrationData);
        doThrow(IllegalStateException.class).when(registrationService).accept(registrationData);

        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response);
        servlet.doPost(request, wrapper);
        assertEquals(409, response.getStatus());
    }

    @Test
    void doPost_AnUnknownErrorOccurs_InternalServerErrorResponseHasBeenReturned() {
        when(registrationDataMapping.apply(request)).thenThrow(RuntimeException.class);

        verifyNoInteractions(registrationService);

        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response);
        servlet.doPost(request, wrapper);
        assertEquals(500, response.getStatus());
    }
}
