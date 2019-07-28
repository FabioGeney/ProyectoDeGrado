package com.proyecto.marketdillo;


import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CanastaAdapter extends RecyclerView.Adapter<CanastaAdapter.ViewHolder> {

    private List<Producto> canastas;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;
    public TextView textTotal;


    public  CanastaAdapter(List<Producto> canastas, int layout, OnItemClickListener itemClickListener ){
        this.canastas = canastas;
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
        public ImageButton add;
        public ImageView imagen;
        public TextView nombre;
        public ImageButton remove;
        public TextView precioProducto;
        public TextView cantidad;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            add = itemView.findViewById(R.id.add);
            remove = itemView.findViewById(R.id.remove);
            imagen =  itemView.findViewById(R.id.imagen);
            nombre =  itemView.findViewById(R.id.nombre);
            precioProducto = itemView.findViewById(R.id.precio);
            cantidad = itemView.findViewById(R.id.cantidad);
            textTotal = ((VistaCanasta)context).findViewById(R.id.total);

        }
        public void bind( final Producto producto, final OnItemClickListener listener){
            final SingletonCanasta singletonCanasta = SingletonCanasta.getInstance();
            //productosCanasta.add(producto);
            Picasso.with(context).load(producto.getImagen()).fit().into(imagen);
            nombre.setText( producto.getNombre());
            precioProducto.setText( "$ " + producto.getPrecioCantidad());
            cantidad.setText(""+producto.getContador());
            getTotal();
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    singletonCanasta.getCanasta().aumentaContador(producto.getKey());
                    cantidad.setText(""+ producto.getContador());
                    setTotal(producto, true);
                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(producto.getContador()  != 1) {

                        singletonCanasta.getCanasta().restaContador(producto.getKey());
                        cantidad.setText(""+ producto.getContador());
                        setTotal(producto, false);
                    }else{
                        singletonCanasta.getCanasta().borraProducto(producto.getKey());
                        canastas.remove(producto);
                        notifyDataSetChanged();
                    }
                }
            });

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

    private void getTotal(){

       textTotal.setText(""+SingletonCanasta.getInstance().getCanasta().getTotal());
    }

    private void setTotal(Producto producto, boolean index){

        textTotal.setText(""+SingletonCanasta.getInstance().getCanasta().getTotal());
    }
}

