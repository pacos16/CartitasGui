package com.pacosignes.guicartitas.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacosignes.guicartitas.R;
import com.pacosignes.guicartitas.model.Carta;

import java.util.ArrayList;

public class AdapterCartas extends RecyclerView.Adapter<AdapterCartas.CartasViewHolder> {
    private ArrayList<Carta> cartas;

    public AdapterCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }

    @NonNull
    @Override
    public CartasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_carta,parent,false);
        return new CartasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartasViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cartas.size();
    }

    public class CartasViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivCocheListItem;
        private TextView tvMarca;
        private TextView tvModelo;
        private TextView tvMotor;
        private TextView tvCilindros;
        private TextView tvPotencia;
        private TextView tvRevoluciones;
        private TextView tvVelocidad;
        private TextView tvConsumo;
        public CartasViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCocheListItem=itemView.findViewById(R.id.ivCocheListItem);
            tvMarca=itemView.findViewById(R.id.tvMarca);
            tvModelo=itemView.findViewById(R.id.tvModelo);
            tvMotor=itemView.findViewById(R.id.tvMotor);
            tvCilindros=itemView.findViewById(R.id.tvCilindros);
            tvPotencia=itemView.findViewById(R.id.tvPotencia);
            tvRevoluciones=itemView.findViewById(R.id.tvRevoluciones);
            tvVelocidad=itemView.findViewById(R.id.tvVelocidad);
            tvConsumo=itemView.findViewById(R.id.tvConsumo);

        }

        public void bind(int position){
            Carta c=cartas.get(position);
            tvMarca.setText(c.getMarca());
            tvModelo.setText(c.getModelo());
            tvMotor.setText(String.valueOf(c.getMotor()));
            tvCilindros.setText(String.valueOf(c.getCilindros()));
            tvPotencia.setText(c.getPotencia());
            tvRevoluciones.setText(String.valueOf(c.getRevolucinoes()));
            tvVelocidad.setText(String.valueOf(c.getVelocidad()));
            tvConsumo.setText(String.valueOf(c.getConsumo()));
        }
    }

    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
        notifyDataSetChanged();
    }
}
