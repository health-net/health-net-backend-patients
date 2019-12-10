package org.healthnet.backend.patients.application.services;

import org.healthnet.backend.patients.application.shared.Creator;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PatientRegistrationServiceTest {
    private final static Patient patient = mock(Patient.class);
    private final static PatientRegistrationService.RegistrationData registrationData = mock(PatientRegistrationService.RegistrationData.class);
    private final static PatientRepository patientRepository = mock(PatientRepository.class);
    private final static Creator<PatientRegistrationService.RegistrationData, Patient> patientCreation = mock(Creator.class);
    private final static PatientRegistrationService patientRegistrationService = new PatientRegistrationService(
            patientRepository,
            patientCreation
    );

    @BeforeEach
    void resetMocks() {
        reset(patient, patientRepository, patientCreation);
    }

    @Test
    void Accept_SuccessfulExecution_PatientHasBeenRegistered() {
        when(patientCreation.from(registrationData)).thenReturn(patient);
        patientRegistrationService.accept(registrationData);

        verify(patientRepository).add(patient);
    }

    @Test
    void Accept_PatientIsAlreadyRegistered_IllegalStateExceptionHasBeenThrown() {
        when(patientCreation.from(registrationData)).thenReturn(patient);
        doThrow(IllegalStateException.class).when(patientRepository).add(patient);

        assertThrows(IllegalStateException.class, () -> patientRegistrationService.accept(registrationData));
    }

    @Test
    void Accept_InvalidPatientRegistrationData_IllegalArgumentExceptionHasBeenThrown() {
        when(patientCreation.from(registrationData)).thenThrow(IllegalArgumentException.class);

        verifyNoInteractions(patientRepository);
        assertThrows(IllegalArgumentException.class, () -> patientRegistrationService.accept(registrationData));
    }
}
