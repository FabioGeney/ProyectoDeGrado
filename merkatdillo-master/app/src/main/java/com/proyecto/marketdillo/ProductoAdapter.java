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


public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private List<Producto> productos;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;
    private SingletonCanasta singletonCanasta = SingletonCanasta.getInstance();

    public  ProductoAdapter(List<Producto> productos, int layout, OnItemClickListener itemClickListener ){
        this.productos = productos;
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
        viewHolder.bind(productos.get(i),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagen;
        public TextView nombre ;
        public TextView descripcion;
        public TextView costoCantidad;
        public Button agregar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = (ImageView) itemView.findViewById(R.id.imagen);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            descripcion = (TextView)itemView.findViewById(R.id.descripcion);
            costoCantidad = (TextView)itemView.findViewById(R.id.precio);
            agregar = itemView.findViewById(R.id.agregar);


        }
        public void bind( final Producto producto, final OnItemClickListener listener){
            Picasso.with(context).load(producto.getImagen()).fit().into(imagen);
            nombre.setText( producto.getNombre());
            descripcion.setText( producto.getDescripcion() );
            costoCantidad.setText(Integer.toString(producto.getPrecioCantidad()));
            agregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(producto.getContador() ==0){
                        producto.setContador(1);
                        singletonCanasta.setCanastas(producto);
                    }else{
                        singletonCanasta.setContador(producto);
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
        void OnItemClick(Producto mercadillo, int posicion);

    }
}
