package com.example.swellhealth.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swellhealth.Models.Step;
import com.example.swellhealth.R;

import java.util.List;

public class InstructionsStepAdapter extends RecyclerView.Adapter<InstructionStepViewHolder> {

    Context context;
    List<Step> list;

    public InstructionsStepAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionStepViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_steps, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionStepViewHolder holder, int position) {
        holder.textView_instructions_number.setText(String.valueOf(list.get(position).number));
        holder.textView_instructions_title.setText(list.get(position).step);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class InstructionStepViewHolder extends RecyclerView.ViewHolder {

    TextView textView_instructions_number, textView_instructions_title;

    public InstructionStepViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_instructions_number = itemView.findViewById(R.id.textView_instructions_number);
        textView_instructions_title = itemView.findViewById(R.id.textView_instructions_title);


    }
}
