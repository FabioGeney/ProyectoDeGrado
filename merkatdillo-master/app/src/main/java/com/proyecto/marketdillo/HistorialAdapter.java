package com.proyecto.marketdillo;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ViewHolder> {

    private List<Pedidos> pedidos;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;

    public HistorialAdapter(List<Pedidos> mercadillos, int layout, OnItemClickListener itemClickListener ){
        this.pedidos = mercadillos;
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
        viewHolder.bind(pedidos.get(i),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagen;
        public TextView nombre ;
        public Button verDetalles;
        public Button pedirDeNuevo;
        public TextView fechaPedido;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            verDetalles = itemView.findViewById(R.id.detalles);
            pedirDeNuevo = itemView.findViewById(R.id.pedir);
            imagen = itemView.findViewById(R.id.imagen);
            nombre = (itemView.findViewById(R.id.nombre));
            fechaPedido = itemView.findViewById(R.id.fecha);
        }
        public void bind( final Pedidos pedidos, final OnItemClickListener listener){

            Picasso.with(context).load(R.mipmap.ic_merca_image).fit().into(imagen);
            nombre.setText(pedidos.getNombreMercadillo());
            fechaPedido.setText(pedidos.getFecha());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(pedidos, getAdapterPosition());
                }
            });


        }
    }

    public interface OnItemClickListener{
        void OnItemClick(Pedidos pedidos, int posicion);

    }
}
