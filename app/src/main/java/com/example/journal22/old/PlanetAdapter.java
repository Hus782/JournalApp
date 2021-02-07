package com.example.journal22.old;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journal22.R;

import java.util.ArrayList;
import java.util.Locale;

public class PlanetAdapter extends RecyclerView.Adapter<PlanetAdapter.PlanetHolder> {

    private Context context;
    private ArrayList<Planet> planets;

    public PlanetAdapter(Context context, ArrayList<Planet> planets) {
        this.context = context;
        this.planets = planets;
    }

    @NonNull
    @Override
    public PlanetHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.entry_row, parent, false);
        return new PlanetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanetHolder holder, int position) {
        Planet planet = planets.get(position);
        holder.setDetails(planet);

       // Word current = getItem(position);
       // holder.setDetails(current.getWord());
    }

    @Override
    public int getItemCount() {
        return planets.size();
    }

    class PlanetHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtDistance, txtGravity, txtDiameter;

        PlanetHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtDistance = itemView.findViewById(R.id.txtContent);
            txtGravity = itemView.findViewById(R.id.txtDate);
            txtDiameter = itemView.findViewById(R.id.txtWeekDay);
        }

        void setDetails(Planet planet) {
            txtName.setText(planet.getTitle());
            txtDistance.setText(planet.getContent());
            txtGravity.setText(String.format(Locale.US, "Surface Gravity : %d N/kg", planet.getGravity()));
            txtDiameter.setText(String.format(Locale.US, "Diameter : %d KM", planet.getDiameter()));
        }
    }
}