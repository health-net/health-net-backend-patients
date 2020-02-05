package org.healthnet.backend.patients.presentation.rest;

import org.healthnet.backend.patients.application.dtos.PatientDetailDto;
import org.healthnet.backend.patients.application.services.PatientDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientDetailWebHandlerTest {
    private final static WebRequest webRequest = mock(WebRequest.class);
    private final static PatientDetailDto dto = mock(PatientDetailDto.class);
    private final static Function<PatientDetailDto, String> serialization = mock(Function.class);
    private final static Function<String, PatientDetailDto> patientDetailService = mock(PatientDetailService.class);
    private final static WebHandler patientDetailWebHandler = new PatientDetailWebHandler(patientDetailService, serialization);

    @BeforeEach
    void resetMocks() {
        reset(webRequest, dto, serialization, patientDetailService);
    }

    @Test
    void Handle_PatientFound_OkResponseWithPatientDataInBodyHasBeenReturned() {
        String expectedContent = "expectedContent";
        String id = "";

        when(webRequest.getParameter(0)).thenReturn(id);
        when(patientDetailService.apply(id)).thenReturn(dto);
        when(serialization.apply(dto)).thenReturn(expectedContent);

        WebResponse webResponse = patientDetailWebHandler.handle(webRequest);
        assertEquals(200, webResponse.getStatusCode());
        assertEquals(expectedContent, webResponse.getBodyContent());
    }

    @Test
    void Handle_PatientNotFound_NotFoundResponseHasBeenReturned() {
        String id = "";

        when(webRequest.getParameter(0)).thenReturn(id);
        when(patientDetailService.apply(id)).thenThrow(NoSuchElementException.class);

        WebResponse webResponse = patientDetailWebHandler.handle(webRequest);
        verifyNoInteractions(serialization);
        assertEquals(404, webResponse.getStatusCode());
    }

    @Test
    void Handle_UnknownErrorOccurs_InternalServerErrorHasBeenReturned() {
        String id = "";

        when(webRequest.getParameter(0)).thenReturn(id);
        when(patientDetailService.apply(id)).thenThrow(RuntimeException.class);

        WebResponse webResponse = patientDetailWebHandler.handle(webRequest);
        verifyNoInteractions(serialization);
        assertEquals(500, webResponse.getStatusCode());
    }
}
