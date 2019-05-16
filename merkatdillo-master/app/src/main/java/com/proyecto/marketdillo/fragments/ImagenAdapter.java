package com.proyecto.marketdillo.fragments;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.proyecto.marketdillo.Producto;
import com.proyecto.marketdillo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImagenAdapter extends RecyclerView.Adapter<ImagenAdapter.ImagenViewHolder> {

    private ArrayList<ImagenCard> imagenes;
    private int layout;
    private Activity activity;

    public ImagenAdapter(ArrayList<ImagenCard> imagenes, int layout, Activity activity) {
        this.imagenes = imagenes;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ImagenViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        return new ImagenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagenViewHolder imagenViewHolder, int i) {
        ImagenCard imagen = imagenes.get(i);
        imagenViewHolder.mercadillo.setText(imagen.getMercadillo());
        imagenViewHolder.nombre.setText(imagen.getNombre());
        imagenViewHolder.precioCantidad.setText(imagen.getPrecioCantidad());
        Picasso.with(activity).load(imagen.getImagen()).into(imagenViewHolder.imagen);
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }

    public class ImagenViewHolder extends RecyclerView.ViewHolder{

        private ImageView imagen;
        private TextView nombre;
        private TextView precioCantidad;
        private TextView mercadillo;

        public ImagenViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.picturecard);
            nombre = itemView.findViewById(R.id.nameprod);
            precioCantidad = itemView.findViewById(R.id.preciocard);
            mercadillo = itemView.findViewById(R.id.user);



        }
    }

}
