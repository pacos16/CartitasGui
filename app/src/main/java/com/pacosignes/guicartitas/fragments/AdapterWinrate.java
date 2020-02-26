package com.pacosignes.guicartitas.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacosignes.guicartitas.R;
import com.pacosignes.guicartitas.model.CartaEstadistica;

import java.util.ArrayList;


public class AdapterWinrate extends RecyclerView.Adapter<AdapterWinrate.WinrateViewHolder> {

    private ArrayList<CartaEstadistica> cartaEstadisticas;
    public AdapterWinrate(ArrayList<CartaEstadistica> cartas){
        cartaEstadisticas=cartas;
    }


    @NonNull
    @Override
    public WinrateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_winrate_carta,parent,false);
        return new WinrateViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WinrateViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cartaEstadisticas.size();
    }

    public class WinrateViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivWinrate;
        private TextView tvGanadas;
        private TextView tvPerdidas;
        private TextView tvEmpatadas;
        private TextView tvWinrate;
        public WinrateViewHolder(@NonNull View itemView) {
            super(itemView);

            ivWinrate=itemView.findViewById(R.id.ivWinrate);
            tvGanadas=itemView.findViewById(R.id.tvVictorias);
            tvPerdidas=itemView.findViewById(R.id.tvDerrotas);
            tvEmpatadas=itemView.findViewById(R.id.tvEmpates);
            tvWinrate=itemView.findViewById(R.id.tvWinrate);

        }

        public void bind(int position){
            CartaEstadistica cartaEstadistica=cartaEstadisticas.get(position);
            int id=itemView.getResources().getIdentifier(
                    "_"+cartaEstadistica.getIdCarta(),
                    "drawable",ivWinrate.getContext().getPackageName());

            ivWinrate.setImageResource(id);
            tvGanadas.setText("Ganadas: "+cartaEstadistica.getGanadas());
            tvEmpatadas.setText("Empatadas: "+cartaEstadistica.getEmpatadas());
            tvPerdidas.setText("Perdidas: "+cartaEstadistica.getPerdidas());
            tvPerdidas.setText("Perdidas: "+cartaEstadistica.getPerdidas());
            tvWinrate.setText("Winrate: "+cartaEstadistica.getWinrate()+"%");
        }
    }
}
