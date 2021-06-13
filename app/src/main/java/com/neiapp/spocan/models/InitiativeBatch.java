package com.neiapp.spocan.models;

import com.neiapp.spocan.backend.ParseJsonException;

import org.json.JSONObject;

import java.util.List;

public class InitiativeBatch {
    private List<Initiative> initiatives;
    private boolean lastBatch;

    public InitiativeBatch(List<Initiative> initiatives, boolean lastBatch) {
        this.initiatives = initiatives;
        this.lastBatch = lastBatch;
    }


    public static InitiativeBatch convertJson(String jsonToTransform) throws ParseJsonException {
        try {
            JSONObject dto = new JSONObject(jsonToTransform);

            List<Initiative> initiatives = Initiative.convertJsonList(dto.getString("initiatives"));
            boolean lastBatch = dto.getBoolean("last_batch");

            return new InitiativeBatch(initiatives, lastBatch);

        } catch (Exception e) {
            String message = "failed to convert initiative batch "+ e.getMessage();
            System.out.println(message);
            throw new ParseJsonException(message);
        }
    }

    public List<Initiative> getInitiatives() {
        return initiatives;
    }

    public boolean isLastBatch() {
        return lastBatch;
    }
}
