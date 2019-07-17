package com.example.manga;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manga.objetos.caratula;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorCaratula  extends RecyclerView.Adapter<AdaptadorCaratula.ViewHolder> {
    ArrayList<caratula>caratulas;

    public AdaptadorCaratula(ArrayList<caratula> caratulas) {
        this.caratulas = caratulas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.caratula,null,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        caratula  objeto_actual = caratulas.get(position);

        holder.nombreCaratula.setText( objeto_actual.getNombre());
        // Aqui seteamos la imagen  y su url , y donde lo pondremos:3
        Picasso.get().load(objeto_actual.getFoto()).into(holder.fotoCaratula);
    }

    @Override
    public int getItemCount() {
        return caratulas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreCaratula;
        ImageView fotoCaratula;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreCaratula = itemView.findViewById(R.id.idnombrecaratula);
            fotoCaratula = itemView.findViewById(R.id.idfotocaratula);
        }
    }
}
