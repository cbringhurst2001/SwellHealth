package com.example.swellhealth.Adapters;

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
import com.example.swellhealth.Models.SimilarRecipeResponse;
import com.example.swellhealth.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarRecipeAdapter extends RecyclerView.Adapter<SimilarRecipeViewHolder>{

    Context context;
    List<SimilarRecipeResponse> list;
    RecipeClickListener listener;

    //Create an adapter constructor class that calls for class context, a list of similar recipes, and a recipe click listener
    public SimilarRecipeAdapter(Context context, List<SimilarRecipeResponse> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    //create a view with the list similar recipes layout XMl
    public SimilarRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_similar_recipes, parent, false));
    }

    @Override
    //set the similar recipe data to the corresponding similar recipe data from the API
    public void onBindViewHolder(@NonNull SimilarRecipeViewHolder holder, int position) {
        holder.textView_similar_title.setText(list.get(position).title);
        holder.textView_similar_title.setSelected(true);
        holder.textView_similar_servings.setText(list.get(position).servings+" people");
        Picasso.get().load("https://spoonacular.com/recipeImages/" +list.get(position).id+"-556x370."+list.get(position).imageType).into(holder.imageView_similar);

        //Create an on click listener to receive the selected recipe ID
        holder.similar_recipe_holder.setOnClickListener(new View.OnClickListener() {
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

class SimilarRecipeViewHolder extends RecyclerView.ViewHolder {
    CardView similar_recipe_holder;
    TextView textView_similar_title, textView_similar_servings;
    ImageView imageView_similar;

    //Set the class item views to the corresponding item views from the similar recipes layout XML
    public SimilarRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        similar_recipe_holder = itemView.findViewById(R.id.similar_recipe_holder);
        textView_similar_title = itemView.findViewById(R.id.textView_similar_title);
        textView_similar_servings = itemView.findViewById(R.id.textView_similar_servings);
        imageView_similar = itemView.findViewById(R.id.imageView_similar);

    }
}
