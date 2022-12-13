package com.example.swellhealth.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swellhealth.Models.ExtendedIngredient;
import com.example.swellhealth.R;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder>{
    Context context;
    List<ExtendedIngredient> list;

    //Create an adapter constructor with class context and the list from the Extended Ingredient model
    public IngredientsAdapter(Context context, List<ExtendedIngredient> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    //create a view with the list meal ingredients layout
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_meal_ingredients, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        //Set the view holder text to the corresponding recipe text
        holder.textView_ingredients_quantity.setText(list.get(position).original);
        holder.textView_ingredients_quantity.setSelected(true);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
//Create a class to list recipe ingredients with textViews
class IngredientsViewHolder extends RecyclerView.ViewHolder {
    TextView textView_ingredients_quantity;
    public IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_ingredients_quantity = itemView.findViewById(R.id.textView_ingredients_quantity);

    }
}
