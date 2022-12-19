package com.example.swellhealth;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swellhealth.Models.ExtendedIngredient;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

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

public class MainActivity2 extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();
    public static List<ExtendedIngredient> ingredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Query the Calorie Ninja API
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
                            // Parse the JSON response from the API
                            JSONArray foods = new JSONArray(response.body().string());

                            // Create a list of BarEntry objects
                            List<BarEntry> entries = new ArrayList<>();
                            for (int i = 0; i < foods.length(); i++) {
                                JSONObject food = foods.getJSONObject(i);
                                float fibers = (float) food.getDouble("fibers");
                                float sugar = (float) food.getDouble("sugar");
                                float sodium = (float) food.getDouble("sodium");
                                entries.add(new BarEntry(i, new float[]{fibers, sugar, sodium}));


                            }

                            // Create a BarDataSet object and add the list of BarEntry objects to it
                            BarDataSet set = new BarDataSet(entries, "Nutrition Information");
                            set.setColors(Color.RED, Color.GREEN, Color.BLUE);
                            set.setStackLabels(new String[]{"Fibers", "Sugar", "Sodium"});

                            // Create a BarChart object and add the BarDataSet object to it as a BarData object
                            BarChart chart = new BarChart(MainActivity2.this);
                            BarData data = new BarData(set);
                            data.setBarWidth(0.9f); // set the width of each bar
                            chart.setData(data);

                            // Customize the appearance of the chart
                            XAxis xAxis = chart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"Food 1", "Food 2", "Food 3"}));
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setDrawGridLines(false);
                            xAxis.setGranularity(1f); // only intervals of 1 day

                            // Add the BarChart object to the layout


                            // Customize the chart title
                            chart.setDrawBarShadow(false);
                            chart.setDrawValueAboveBar(true);
                            chart.setMaxVisibleValueCount(50);
                            chart.setPinchZoom(false);
                            chart.setDrawGridBackground(true);
                            chart.setFitBars(true);
                            chart.setBackgroundColor(Color.WHITE);

                            // Customize the legend
                            chart.getLegend().setEnabled(true);
                            chart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                            chart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                            chart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
                            chart.getLegend().setDrawInside(true);
                            chart.getLegend().setYOffset(0f);
                            chart.getLegend().setXOffset(10f);
                            chart.getLegend().setYEntrySpace(0f);
                            chart.getLegend().setTextSize(8f);
                            chart.addView(chart);

                            // Customize the x-axis labels
                            chart.getXAxis().setDrawLabels(true);
                            chart.getXAxis().setTextSize(12f);
                            chart.getXAxis().setTextColor(Color.BLACK);
                            chart.getXAxis().setTypeface(Typeface.DEFAULT_BOLD);

                            // Customize the y-axis labels
                            chart.getAxisRight().setEnabled(false); // disable the right y-axis
                            chart.getAxisLeft().setDrawLabels(true);
                            chart.getAxisLeft().setTextSize(12f);
                            chart.getAxisLeft().setTextColor(Color.BLACK);

                            chart.setData(data);
                            chart.invalidate();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
