package com.example.swellhealth;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.swellhealth.Listeners.InstructionsListener;
import com.example.swellhealth.Listeners.RandomRecipeResponseListener;
import com.example.swellhealth.Listeners.RecipeDetailsListener;
import com.example.swellhealth.Listeners.SimilarRecipesListener;
import com.example.swellhealth.Models.InstructionsResponse;
import com.example.swellhealth.Models.RandomRecipeAPIResponse;
import com.example.swellhealth.Models.RecipeDetailsResponse;
import com.example.swellhealth.Models.SimilarRecipeResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {
    Context context;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    //call the retrofit API to help with internet transactions
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();


    public RequestManager(Context context) {
        this.context = context;
    }

    //Create a getRandomRecipes method with the spoonacular API
    public void getRandomRecipes(RandomRecipeResponseListener listener, List<String> tags){
        //use retrofit and the CallRandomRecipes interface to call to the web API
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        //request a call using our API key, the amount of recipes requested, and tags to search
        Call<RandomRecipeAPIResponse> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key), "15", tags);
        call.enqueue(new Callback<RandomRecipeAPIResponse>() {
            @Override
            public void onResponse(@NonNull Call<RandomRecipeAPIResponse> call, @NonNull Response<RandomRecipeAPIResponse> response) {
                //If the response fails, show a message. If successful, receive the API body and message
                if(!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(@NonNull Call<RandomRecipeAPIResponse> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    //Create a getRecipeDetails method with the spoonacular API
    public void getRecipeDetails(RecipeDetailsListener listener, int id){
        //use retrofit and CallRecipeDetails interface to call to the web API
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecipeDetailsResponse> call, @NonNull Response<RecipeDetailsResponse> response) {
                //If the response fails, show a message. If successful, receive API body and message
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(@NonNull Call<RecipeDetailsResponse> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());

            }
        });
    }

    //Create a getSimilarRecipes method with the spoonacular API
    public void getSimilarRecipes(SimilarRecipesListener listener, int id){
        //use retrofit and callSimilarRecipes interface to call to the web API
        CallSimilarRecipes callSimilarRecipes = retrofit.create(CallSimilarRecipes.class);
        //request a call using the current recipe ID, amount of similar recipes, and our API key
        Call<List<SimilarRecipeResponse>> call = callSimilarRecipes.callSimilarRecipes(id, "3", context.getString(R.string.api_key));
        call.enqueue(new Callback<List<SimilarRecipeResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<SimilarRecipeResponse>> call, @NonNull Response<List<SimilarRecipeResponse>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(@NonNull Call<List<SimilarRecipeResponse>> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    //Create a get recipe instructions method with the spoonacular API
    public void getRecipeInstructions(InstructionsListener listener, int id){
        //use retrofit and callRecipeInstructions interface to call to the web API
        CallInstructions callInstructions = retrofit.create(CallInstructions.class);
        //Request a call using the current recipe ID and the API key
        Call<List<InstructionsResponse>> call = callInstructions.callInstructions(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<List<InstructionsResponse>>() {
            @Override
            public void onResponse(Call<List<InstructionsResponse>> call, Response<List<InstructionsResponse>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<InstructionsResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }


    private interface CallRandomRecipes{
        @GET("recipes/random")
        Call<RandomRecipeAPIResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }

    private interface CallRecipeDetails{
        @GET ("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

    private interface CallSimilarRecipes{
        @GET ("recipes/{id}/similar")
        Call<List<SimilarRecipeResponse>> callSimilarRecipes(
                @Path("id") int id,
                @Query("number") String number,
                @Query("apiKey") String apiKey
        );
    }

    private interface CallInstructions{
        @GET ("recipes/{id}/analyzedInstructions")
        Call<List<InstructionsResponse>> callInstructions(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

}

