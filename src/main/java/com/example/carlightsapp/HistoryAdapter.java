package com.example.carlightsapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<BulbLogEntry> logList;

    public HistoryAdapter(List<BulbLogEntry> logList) {
        this.logList = logList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bulb_log, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        BulbLogEntry log = logList.get(position);
        holder.tvTimestamp.setText(log.getFormattedTimestamp());
        holder.tvBulbInfo.setText(log.getFormattedBulbInfo());
        holder.tvDuration.setText(log.getFormattedDuration());
    }

    @Override
    public int getItemCount() {
        return logList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvTimestamp, tvBulbInfo, tvDuration;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimestamp = itemView.findViewById(R.id.tvLogTimestamp);
            tvBulbInfo = itemView.findViewById(R.id.tvBulbInfo);
            tvDuration = itemView.findViewById(R.id.tvDuration);
        }
    }
}