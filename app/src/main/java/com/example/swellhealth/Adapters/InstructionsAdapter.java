package com.example.swellhealth.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swellhealth.Models.InstructionsResponse;
import com.example.swellhealth.R;

import java.util.List;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsViewHolder>{

    Context context;
    List<InstructionsResponse> list;

    public InstructionsAdapter(Context context, List<InstructionsResponse> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder holder, int position) {

        holder.recycler_instructions_steps.setHasFixedSize(true);
        holder.recycler_instructions_steps.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        InstructionsStepAdapter instructionsStepAdapter = new InstructionsStepAdapter(context, list.get(position).steps);
        holder.recycler_instructions_steps.setAdapter(instructionsStepAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class InstructionsViewHolder extends RecyclerView.ViewHolder {

    RecyclerView recycler_instructions_steps;
    public InstructionsViewHolder(@NonNull View itemView) {
        super(itemView);
        recycler_instructions_steps = itemView.findViewById(R.id.recycler_instructions_steps);
    }
}
