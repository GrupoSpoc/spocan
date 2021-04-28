package com.neiapp.spocan.backend.callback;

import java.util.List;

public interface CallbackCollection <T> extends Fallible {
    void onSuccess(List<T> collection);
}
