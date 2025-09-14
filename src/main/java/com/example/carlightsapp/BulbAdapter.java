package com.example.carlightsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BulbAdapter extends RecyclerView.Adapter<BulbAdapter.BulbViewHolder> {

    private List<Bulb> bulbList;
    private boolean showDeleteButton;
    private OnBulbDeleteListener deleteListener;

    public interface OnBulbDeleteListener {
        void onDelete(int position);
    }
    public BulbAdapter(List<Bulb> bulbList, boolean showDeleteButton, OnBulbDeleteListener deleteListener) {
        this.bulbList = bulbList;
        this.showDeleteButton = showDeleteButton;
        this.deleteListener = deleteListener;
    }
    public BulbAdapter(List<Bulb> bulbList) {
        this.bulbList = bulbList;
    }

    @NonNull
    @Override
    public BulbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bulb, parent, false);
        return new BulbViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BulbViewHolder holder, int position) {
        Bulb bulb = bulbList.get(position);
        holder.textPosition.setText(bulb.getPosition());
        holder.textPower.setText(bulb.getWatt() + "W");
        holder.textVoltage.setText(bulb.getVoltage() + "V");

        if (showDeleteButton) {
            if (bulbList.size() <= 1) {

                holder.deleteButton.setEnabled(false);
                holder.deleteButton.setAlpha(0.3f);
            } else {
                holder.deleteButton.setEnabled(true);
                holder.deleteButton.setAlpha(1f);
                holder.deleteButton.setOnClickListener(v -> {
                    if (deleteListener != null) {
                        deleteListener.onDelete(position);
                    }
                });
            }

        } else {
            holder.deleteButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return bulbList.size();
    }

    static class BulbViewHolder extends RecyclerView.ViewHolder {
        TextView textPosition, textPower, textVoltage;
        ImageButton deleteButton;
        BulbViewHolder(@NonNull View itemView) {
            super(itemView);
            textPosition = itemView.findViewById(R.id.textPosition);
            textPower = itemView.findViewById(R.id.textPower);
            textVoltage = itemView.findViewById(R.id.textVoltage);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
    public void updateData(List<Bulb> newBulbs) {
        this.bulbList = newBulbs;
        notifyDataSetChanged();
    }

}
