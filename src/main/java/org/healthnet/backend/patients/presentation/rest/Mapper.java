package org.healthnet.backend.patients.presentation.rest;

@FunctionalInterface
public interface Mapper<InputT, OutputT> {
    OutputT map(InputT input);
}
