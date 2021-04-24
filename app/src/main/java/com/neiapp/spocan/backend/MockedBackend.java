package com.neiapp.spocan.backend;

import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.backend.callback.CallbackVoid;

public class MockedBackend implements Backend {
    protected MockedBackend(){}

    @Override
    public void createInitiative(Initiative initiative, CallbackVoid callback) {
        // todo implementar
    }
}
