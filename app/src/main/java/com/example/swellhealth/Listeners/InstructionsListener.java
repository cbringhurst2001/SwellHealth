package com.example.swellhealth.Listeners;

import com.example.swellhealth.Models.InstructionsResponse;

import java.util.List;

public interface InstructionsListener {
    void didFetch(List<InstructionsResponse> response, String message);
    void didError(String message);
}
