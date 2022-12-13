package com.example.swellhealth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swellhealth.Adapters.RandomRecipeAdapter;
import com.example.swellhealth.Listeners.RandomRecipeResponseListener;
import com.example.swellhealth.Listeners.RecipeClickListener;
import com.example.swellhealth.Models.RandomRecipeAPIResponse;

import java.util.ArrayList;
import java.util.List;

public class ShowRecipesActivity extends AppCompatActivity {

    ProgressDialog dialog;
    RequestManager manager;
    RecyclerView recyclerView;
    SearchView searchView;
    RandomRecipeAdapter randomRecipeAdapter;
    List<String> tags = new ArrayList<>();
    String query;

    //Create the launch page of the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the view to the main activity layout XML
        setContentView(R.layout.show_recipes_activity);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");

        //Create a search bar with the created searchView layout that reads user input
        searchView = findViewById(R.id.searchView_recipes);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //When user enters search, add it to tags ArrayList
                tags.clear();
                tags.add(query);
                //Call getRandomRecipes method with given tags to show user search results
                manager.getRandomRecipes(randomRecipeResponseListener, tags);
                dialog.show();
                return true;
            }

            @Override
            //Do nothing when user changes search text
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //Create a new request manager to call API requests
        manager = new RequestManager(this);

        Intent intent = getIntent();
        String query = intent.getExtras().getString("query");

        tags.clear();
        tags.add(query);

        //Show a collection of random recipes on app launch
        manager.getRandomRecipes(randomRecipeResponseListener, tags);
        dialog.show();
    }

    //Call the random recipe listener interface
    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        //If the call is successful, create a layout to show random recipes
        public void didFetch(RandomRecipeAPIResponse response, String message) {
            dialog.dismiss();
            //Find the recycler view from the activity main layout
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(ShowRecipesActivity.this, 1));
            //Call the randomRecipeAdapter to convert API data into the recycler view
            randomRecipeAdapter = new RandomRecipeAdapter(ShowRecipesActivity.this, response.recipes, recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);

        }

        @Override
        //if the listener has an error, display the message to the user
        public void didError(String message) {
            Toast.makeText(ShowRecipesActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    //When the user clicks on a recipe, call the Recipe Details activity to show recipe details
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(ShowRecipesActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id", id));
        }
    };
}