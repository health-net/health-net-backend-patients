package org.healthnet.backend.patients.application.dtos;

public class PatientRegistrationDto {
    public final String id;
    public final String fullName;

    public PatientRegistrationDto(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
}
