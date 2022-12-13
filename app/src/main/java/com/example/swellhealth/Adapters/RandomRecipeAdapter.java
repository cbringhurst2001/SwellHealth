package com.example.swellhealth.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swellhealth.Listeners.RecipeClickListener;
import com.example.swellhealth.Models.Recipe;
import com.example.swellhealth.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder>{
    Context context;
    List<Recipe> list;
    RecipeClickListener listener;

    //Create an adapter constructor class which calls for class context, a list of recipes, and a recipe click listener
    public RandomRecipeAdapter(Context context, List<Recipe> list, RecipeClickListener listener){
        this.context = context;
        this.list = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    //create a view with the list random recipe layout XML
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    //set the text view data to the corresponding recipe data
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        holder.textView_title.setText(list.get(position).title);
        holder.textView_title.setSelected(true);
        holder.textView_servings.setText(list.get(position).servings + " Servings");
        holder.textView_time.setText(list.get(position).readyInMinutes+ " Minutes");
        Picasso.get().load(list.get(position).image).into(holder.imageView_food);

        //create an on click listener for the recipe that receives the recipe ID
        holder.random_list_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClicked(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class RandomRecipeViewHolder extends RecyclerView.ViewHolder {

    CardView random_list_container;
    TextView textView_title, textView_servings, textView_time;
    ImageView imageView_food;


    //call the list random recipe layout views for each text/image view
    public RandomRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        random_list_container = itemView.findViewById(R.id.random_list_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_servings = itemView.findViewById(R.id.textView_servings);
        textView_time = itemView.findViewById(R.id.textView_time);
        imageView_food = itemView.findViewById(R.id.imageView_food);
    }
}

