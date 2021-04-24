package com.neiapp.spocan.backend.callback;

public interface CallbackInstance <T> extends Fallible {
    void onSuccess(T instance);
}
