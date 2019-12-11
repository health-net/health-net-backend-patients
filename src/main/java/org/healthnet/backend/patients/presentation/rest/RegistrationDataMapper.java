package org.healthnet.backend.patients.presentation.rest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.healthnet.backend.patients.application.services.PatientRegistrationService;

import javax.servlet.http.HttpServletRequest;

public class RegistrationDataMapper implements Mapper<HttpServletRequest, PatientRegistrationService.RegistrationData> {
    @Override
    public PatientRegistrationService.RegistrationData map(HttpServletRequest input) {
        try {
            return new Gson().fromJson(input.getReader(), PatientRegistrationService.RegistrationData.class);
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
