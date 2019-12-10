package org.healthnet.backend.patients.application.shared;

@FunctionalInterface
public interface Creator<InputT, OutputT> {
    OutputT from(InputT input);
}
