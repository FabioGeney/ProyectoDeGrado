package com.proyecto.marketdillo;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class MercadilloAdapter extends RecyclerView.Adapter<MercadilloAdapter.ViewHolder> {

    private List<Mercadillo> mercadillos;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;

    public  MercadilloAdapter(List<Mercadillo> mercadillos, int layout, OnItemClickListener itemClickListener ){
        this.mercadillos = mercadillos;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        context = viewGroup.getContext();
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(mercadillos.get(i),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return mercadillos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView favorito;
        public ImageView imagen;
        public TextView nombre ;
        public TextView costoEnvio ;
        public TextView tiempoEnvio;
        public TextView calificacion ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             favorito = (ImageView) itemView.findViewById(R.id.favorito);
             imagen = (ImageView) itemView.findViewById(R.id.imagen);
             nombre = (TextView) itemView.findViewById(R.id.nombre);
             costoEnvio = (TextView)itemView.findViewById(R.id.costoEnvio);
             tiempoEnvio = (TextView)itemView.findViewById(R.id.tiempoEnvio);
             calificacion = (TextView) itemView.findViewById(R.id.calificacion);
        }
        public void bind( final Mercadillo mercadillo, final OnItemClickListener listener){
            Picasso.with(context).load(mercadillo.getImagen()).fit().into(imagen);
            nombre.setText( mercadillo.getNombre());
            costoEnvio.setText("Envio $ " +mercadillo.getCostoEnvio() );
            tiempoEnvio.setText( mercadillo.getTiempoEntrega());
            calificacion.setText(  mercadillo.getCalificacion());

            final boolean[] click = {true};

            favorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(click[0]){
                        favorito.setImageResource(R.drawable.ic_fav);
                        click[0] = false;
                    }else {
                        favorito.setImageResource(R.drawable.ic_favorito);
                        click[0] = true;
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(mercadillo, getAdapterPosition());
                }
            });


        }
    }

    public interface OnItemClickListener{
        void OnItemClick(Mercadillo mercadillo, int posicion);

    }
}

