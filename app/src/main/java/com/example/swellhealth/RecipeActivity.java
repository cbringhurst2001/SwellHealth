package com.example.swellhealth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    ProgressDialog dialog;
    RequestManager manager;
    RecyclerView recyclerView;
    SearchView searchView;
    List<String> tags = new ArrayList<>();
    ImageButton mex, ind, ital, china, euro, cajun;
    String query;

    //Create the launch page of the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the view to the main activity layout XML
        setContentView(R.layout.recipes_start_screen);

        mex = (ImageButton) findViewById(R.id.mexicanButton);
        ind = (ImageButton) findViewById(R.id.indianButton);
        ital = (ImageButton) findViewById(R.id.italianButton);
        china = (ImageButton) findViewById(R.id.chineseButton);
        euro = (ImageButton) findViewById(R.id.europeanButton);
        cajun = (ImageButton) findViewById(R.id.cajunButton);



        //Create a search bar with the created searchView layout that reads user input
        searchView = findViewById(R.id.searchView_start);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //Call getRandomRecipes method with given tags to show user search results
                startActivity(new Intent(RecipeActivity.this, ShowRecipesActivity.class)
                        .putExtra("query", query));
                return true;
            }

            @Override
            //Do nothing when user changes search text
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        mex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = "mexican";
                openShowRecipes();
            }
        });
        ind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = "indian";
                openShowRecipes();
            }
        });
        ital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = "italian";
                openShowRecipes();
            }
        });
        china.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = "chinese";
                openShowRecipes();
            }
        });
        euro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = "european";
                openShowRecipes();
            }
        });
        cajun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = "caribbean";
                openShowRecipes();
            }
        });


    }



    public void openShowRecipes(){
        startActivity(new Intent(RecipeActivity.this, ShowRecipesActivity.class)
                .putExtra("query", query));
    }
}
