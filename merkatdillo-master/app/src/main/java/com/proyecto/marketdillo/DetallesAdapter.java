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

public class DetallesAdapter extends RecyclerView.Adapter<DetallesAdapter.ViewHolder> {

    private List<Producto> canastas;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;



    public  DetallesAdapter(List<Producto> Producto, int layout, OnItemClickListener itemClickListener ){
        this.canastas = Producto;
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
        viewHolder.bind(canastas.get(i),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return canastas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagen;
        public TextView nombre;
        public TextView precioProducto;
        public TextView cantidad;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen =  itemView.findViewById(R.id.imagen);
            nombre =  itemView.findViewById(R.id.nombre);
            precioProducto = itemView.findViewById(R.id.precio);
            cantidad = itemView.findViewById(R.id.cantidad);


        }
        public void bind( final Producto producto, final OnItemClickListener listener){
            Picasso.with(context).load(producto.getImagen()).fit().into(imagen);
            nombre.setText( producto.getNombre());
            precioProducto.setText( "$ " + producto.getPrecioCantidad());
            cantidad.setText("x "+producto.getContador());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(producto, getAdapterPosition());
                }
            });


        }
    }

    public interface OnItemClickListener{
        void OnItemClick(Producto producto, int posicion);

    }

}