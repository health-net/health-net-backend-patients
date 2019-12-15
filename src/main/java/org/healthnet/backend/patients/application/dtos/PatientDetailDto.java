package org.healthnet.backend.patients.application.dtos;

public class PatientDetailDto {
    public final String id;
    public final String fullName;

    public PatientDetailDto(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
}
