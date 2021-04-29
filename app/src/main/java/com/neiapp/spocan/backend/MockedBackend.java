package com.neiapp.spocan.backend;

import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.backend.callback.CallbackCollection;
import com.neiapp.spocan.backend.callback.CallbackVoid;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MockedBackend implements Backend {
    public static ArrayList<Initiative> initiative_store  = new ArrayList<>();

    protected MockedBackend() {}

    @Override
    public void createInitiative(Initiative initiative, CallbackVoid callback) {
        String assignedId = Integer.toString(initiative_store.size() + 1);
        initiative.setId(assignedId);
        initiative_store.add(initiative);
        callback.onSuccess();
    }

    @Override
    public void getAll( CallbackCollection<Initiative> collection) {
        collection.onSuccess(initiative_store);
    }

    public static void resetStorage(){
        initiative_store.clear();
    }
}
