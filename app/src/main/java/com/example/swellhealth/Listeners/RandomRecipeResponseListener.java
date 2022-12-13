package com.example.swellhealth.Listeners;

import com.example.swellhealth.Models.RandomRecipeAPIResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeAPIResponse response, String message);
    void didError(String message);
}
