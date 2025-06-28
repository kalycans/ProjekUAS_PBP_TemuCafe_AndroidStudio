//RecommendationAdapter.java
package com.example.temucafe.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temucafe.R;
import com.example.temucafe.models.Cafe;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    private List<Cafe> cafeList;
    private List<Double> distanceList;

    public RecommendationAdapter(List<Cafe> cafeList, List<Double> distanceList) {
        this.cafeList = cafeList;
        this.distanceList = distanceList;
    }

    @NonNull
    @Override
    public RecommendationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recommendation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationAdapter.ViewHolder holder, int position) {
        Cafe cafe = cafeList.get(position);
        double distance = distanceList.get(position); // in meters

        holder.tvName.setText(cafe.getName());
        holder.tvAddress.setText(cafe.getAddress());
        holder.tvDistance.setText(String.format("Jarak: %.0f meter", distance));
    }

    @Override
    public int getItemCount() {
        return cafeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvDistance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCafeName);
            tvAddress = itemView.findViewById(R.id.tvCafeAddress);
            tvDistance = itemView.findViewById(R.id.tvCafeDistance);
        }
    }
}
