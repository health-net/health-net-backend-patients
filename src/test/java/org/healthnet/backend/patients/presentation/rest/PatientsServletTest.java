package org.healthnet.backend.patients.presentation.rest;

import org.healthnet.backend.patients.application.services.PatientRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PatientsServletTest {
    private final static HttpServletRequest request = mock(HttpServletRequest.class);
    private final static HttpServletResponse response = new MockHttpServletResponse();
    private final static Mapper<HttpServletRequest, PatientRegistrationService.RegistrationData> mapper = mock(Mapper.class);
    private final static PatientRegistrationService.RegistrationData registrationData = mock(PatientRegistrationService.RegistrationData.class);
    private final static PatientRegistrationService registrationService = mock(PatientRegistrationService.class);
    private final static PatientsServlet servlet = new PatientsServlet(registrationService, mapper);

    @BeforeEach
    void resetMocks() {
        reset(request, mapper, registrationData, registrationService);
    }

    @Test
    void doPost_SuccessfulExecution_CreatedResponseHasBeenReturned() throws IOException {
        when(mapper.map(request)).thenReturn(registrationData);
        doNothing().when(registrationService).accept(registrationData);

        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response);
        servlet.doPost(request, wrapper);
        assertEquals(201, response.getStatus());
    }

    @Test
    void doPost_InvalidRequestBodyValues_BadRequestResponseHasBeenReturned() throws IOException {
        when(mapper.map(request)).thenThrow(IllegalArgumentException.class);

        verifyNoInteractions(registrationService);

        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response);
        servlet.doPost(request, wrapper);
        assertEquals(404, response.getStatus());
    }

    @Test
    void doPost_AlreadyRegisteredPatient_ConflictResponseHasBeenReturned() throws IOException {
        when(mapper.map(request)).thenReturn(registrationData);
        doThrow(IllegalStateException.class).when(registrationService).accept(registrationData);

        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response);
        servlet.doPost(request, wrapper);
        assertEquals(409, response.getStatus());
    }
}
