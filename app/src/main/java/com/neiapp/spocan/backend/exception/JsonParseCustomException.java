package com.neiapp.spocan.backend.exception;

import androidx.annotation.Nullable;

public class JsonParseCustomException extends Exception {

    String nameClass;
    String customMessage;

   public JsonParseCustomException(String nameClass, String message){
           super();
           this.nameClass = nameClass;
           this.customMessage = message;
   }

    @Nullable
    @Override
    public String getMessage() {
        return "The Class"+this.nameClass+"fail because"+this.customMessage;
    }
}
