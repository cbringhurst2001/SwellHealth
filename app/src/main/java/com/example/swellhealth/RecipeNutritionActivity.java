        package com.example.swellhealth;

        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import com.example.swellhealth.Models.ExtendedIngredient;
        import com.github.mikephil.charting.charts.PieChart;
        import com.github.mikephil.charting.data.PieData;
        import com.github.mikephil.charting.data.PieDataSet;
        import com.github.mikephil.charting.data.PieEntry;

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

        import okhttp3.Call;
        import okhttp3.Callback;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.Response;

        /**
         * RecipeNutritionActivity is the main activity of the app.
         * It displays the nutrition information for a recipe.
         */
public class RecipeNutritionActivity extends AppCompatActivity {
            // Button for navigating back to the main screen

            private Button button;

            private TextView mtextViewResult;
            // List of PieEntry objects for the pie chart

            private List<PieEntry> entries = new ArrayList<>();
            // List of ingredients for the recipe

            public static List<ExtendedIngredient> ingredients = new ArrayList<>();


            @SuppressLint({})
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_recipe_nutrition);

                mtextViewResult = findViewById(R.id.text_view_result);
               // button = (Button) findViewById(R.id.button);
                //button.setOnClickListener(new View.OnClickListener() {
                  //  @Override
                   // public void onClick(View view) {
                     //   openMain1();
              //      }
            //    });


// Initialize OkHttpClient for making API request
                OkHttpClient client = new OkHttpClient();

// Initialize variables for nutrition information
                double calorie = 0;

// Set up TextView for displaying carb information
                TextView parentView = findViewById(R.id.fat_label);
                parentView.setTypeface(null, Typeface.BOLD);
                parentView.setText("\n\n   CARB                                                                                     " + calorie);

// Set up TextView for displaying carb information
                TextView parentView1 = findViewById(R.id.fat_label2);
                parentView1.setTypeface(null, Typeface.BOLD);
                parentView1.setText("   \n\n     PROTEIN                                                                           " + calorie);

// Set up TextView for displaying protein information
                TextView parentView2 = findViewById(R.id.fat_label21);
                parentView2.setTypeface(null, Typeface.BOLD);
                parentView2.setText("   \n\n   FAT                                                                                        " + calorie);

// Loop through ingredients and get nutrition information for each one
                for (ExtendedIngredient i : ingredients) {
                    // Construct search term for API request
                    final String searchTerm = String.format("%f %s %s", i.amount, i.unit, i.name);

                    // Create API request

                    Request request = new Request.Builder().url("https://api.calorieninjas.com/v1/nutrition?query=" + searchTerm)
                            .addHeader("X-Api-Key", "YIMN0leBWKmqR4uA0tyXRw==LR8NdMtDlyBluc1q").build();
                    // Make API request and handle response

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) {
                            try {
                                if (response.isSuccessful()) {


                                    //  final String myResponse = response.body().string();
                                    //  JSONObject jsonDocument = new JSONObject(myResponse);
                                    //  JSONArray array = jsonDocument.getJSONArray("items");
                                    //  JSONObject firstObject = array.getJSONObject(0);
                                    //  String name = firstObject.getString("name");
                                    // double fat = firstObject.getDouble("fat");
                                    // double protein = firstObject.getDouble("protein");
                                    // double carbs = firstObject.getDouble("carbs");
                                    // double fiber = firstObject.getDouble("fiber");
                                    // double sugar = firstObject.getDouble("sugar");
                                    // totalFats += fat * i.amount;
                                    // totalProtein += protein * i.amount;
                                    // totalCarbs += carbs * i.amount;
                                    // calorie[0] += firstObject.getDouble("calories");
                                    //entries.add(new PieEntry((float) calorie[0], ""));


                                    // entries.add(new PieEntry((float) totalFats, 0));
                                    //entries.add(new PieEntry((float) totalCarbs, 1));
                                    // entries.add(new PieEntry((float) totalProtein, 2));


                                    //RecipeNutritionActivity.this.runOnUiThread(new Runnable() {
                                    // @Override
                                    // public void run() {
                                    ///mtextViewResult.append(name + "\n" + calories + "\n");

//for (ExtendedIngredient i : ingredients) {
//
//   // Parse API response
//   JSONObject jsonObject = new JSONObject(response.body().string());
//   JSONArray jsonArray = jsonObject.getJSONArray("results");
//   JSONObject firstObject = jsonArray.getJSONObject(0);
//   // Get nutrition information from API response
//   double calories = firstObject.getDouble("calories");
//   double carbs = firstObject.getDouble("carbs");
//   double proteins = firstObject.getDouble("proteins");
//   // Add nutrition information to totals
//   totalCalories += calories;
//   totalCarbs += carbs;
//   totalProteins += proteins;
//}


                                    // Parse API response and extract nutrition information
                                    final String myResponse = response.body().string(); // Get the response from the API call as a string
                                    JSONObject jsonDocument = new JSONObject(myResponse); // Parse the string into a JSON object
                                    JSONArray array = jsonDocument.getJSONArray("items"); // Get the "items" array from the JSON object
                                    JSONObject firstObject = array.getJSONObject(0); // Get the first object in the array
                                    String name = firstObject.getString("name"); // Get the value of the "name" field as a string
                                    double fat = firstObject.getDouble("fat_total_g"); // Get the value of the "fat_total_g" field as a double
                                    double carb = firstObject.getDouble("carbohydrates_total_g"); // Get the value of the "carbohydrates_total_g" field as a double
                                    double protein = firstObject.getDouble("protein_g"); // Get the value of the "protein_g" field as a double
                                    double calories = firstObject.getDouble("calories"); // Get the value of the "calories" field as a double

// Add the nutrition information to a list of pie chart entries
                                    entries.add(new PieEntry((float) calorie, "")); // Add a pie chart entry for the calorie value
                                    entries.add(new PieEntry((float) fat, "carb")); // Add a pie chart entry for the fat value
                                    entries.add(new PieEntry((float) carb, "fat ")); // Add a pie chart entry for the carbohydrate value
                                    entries.add(new PieEntry((float) protein, "protien")); // Add a pie chart entry for the protein value

// Update the UI on the main thread
                                    RecipeNutritionActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //mtextViewResult.append(name + "\n" + calories + "\n");
                                        }
                                    });
                                }

                                PieChart pieChart = findViewById(R.id.pie_chart);

                                View carbSquare = new View(RecipeNutritionActivity.this); // Create a new View object
                                carbSquare.setBackgroundColor(Color.parseColor("#FF0000")); // Set the


                                int color1 = Color.parseColor("#FFB6C1");  // blue
                                int color2 = Color.parseColor("#DB7093");  // green
                                int color3 = Color.parseColor("#ADD8E6");  // red

                                List<Integer> colors = new ArrayList<>();
                                colors.add(color1);
                                colors.add(color2);
                                colors.add(color3);
                                pieChart.setHoleColor(Color.parseColor("#FFEFD5"));

                                PieDataSet dataSet = new PieDataSet(entries, " ");
                                dataSet.setColors(colors);
                                dataSet.setColors(colors); // Set the colors for the data set
                                pieChart.setDescription(null); // Remove the chart description

// Set the center text for the pie chart
                                pieChart.setCenterText(calorie + "\n" + "Calories"); // Set the center text to be the calorie value and the string "Calories"
                                pieChart.setCenterTextSize(20f); // Set the font size for the center text
                                pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD); // Set the font style for the center text to be bold
                                pieChart.setCenterTextRadiusPercent(100f); // Set the radius of the center text to be 100%
                                dataSet.setFormSize(20); // Set the size of the data set's value text

// Set the slice space and value text size for the data set
                                dataSet.setSliceSpace(2); // Set the space between slices to be 2
                                dataSet.setValueTextSize(10f); // Set the font size for the value text
                                dataSet.setValueTextColor(Color.WHITE); // Set the color for the value text to be white
                                PieData data = new PieData(dataSet); // Create a pie data object using the data set

// Set the hole radius and data for the pie chart
                                pieChart.setHoleRadius(70f); // Set the hole radius to be 70
                                pieChart.setData(data); // Set the data for the pie chart
                                pieChart.invalidate(); // Invalidate the pie chart to redraw it


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                // public void openMain1() {

            }


        }
