package com.proyecto.marketdillo.fragments;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.proyecto.marketdillo.BottomActivity;
import com.proyecto.marketdillo.Producto;
import com.proyecto.marketdillo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImagenAdapter extends RecyclerView.Adapter<ImagenAdapter.ImagenViewHolder> {

    private List<ImagenCard> imagenes;
    private int layout;
    private Activity activity;
    private OnItemClickListener itemClickListener;

    public ImagenAdapter(List<ImagenCard> imagenes, int layout, Activity activity, OnItemClickListener itemClickListener) {
        this.imagenes = imagenes;
        this.layout = layout;
        this.activity = activity;
        this.itemClickListener = itemClickListener;
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
        imagenViewHolder.bind(imagenes.get(i), itemClickListener);

        /*imagenViewHolder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, ImagenDetalle.class);
                activity.startActivity(i);
            }
        });*/
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

        public void bind(final ImagenCard imagen, final OnItemClickListener listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(imagen, getAdapterPosition());
                }
            });


        }
    }

    public interface OnItemClickListener{
        void OnItemClick(ImagenCard imagen, int posicion);

    }

}
