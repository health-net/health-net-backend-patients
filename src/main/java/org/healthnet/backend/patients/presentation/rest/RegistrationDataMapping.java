package org.healthnet.backend.patients.presentation.rest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.healthnet.backend.patients.application.services.PatientRegistrationService;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

public class RegistrationDataMapping implements Function<HttpServletRequest, PatientRegistrationService.RegistrationData> {
    @Override
    public PatientRegistrationService.RegistrationData apply(HttpServletRequest httpServletRequest) {
        try {
            return new Gson().fromJson(httpServletRequest.getReader(), PatientRegistrationService.RegistrationData.class);
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
