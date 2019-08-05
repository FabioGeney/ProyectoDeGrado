package com.proyecto.marketdillo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterProductosBusqueda extends RecyclerView.Adapter<AdapterProductosBusqueda.ViewHolder> {
    private List<Producto> productos;
    private int layout;
    private ProductoAdapter.OnItemClickListener itemClickListener;
    private Context context;


    public  AdapterProductosBusqueda( List<Producto> productos, int layout, ProductoAdapter.OnItemClickListener itemClickListener ){
        this.productos = productos;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public AdapterProductosBusqueda.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        context = viewGroup.getContext();
        AdapterProductosBusqueda.ViewHolder viewHolder = new AdapterProductosBusqueda.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProductosBusqueda.ViewHolder viewHolder, int i) {
        viewHolder.bind(productos.get(i),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagen;
        public TextView nombre ;
        public TextView costoCantidad;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = (ImageView) itemView.findViewById(R.id.picturecard);
            nombre = (TextView) itemView.findViewById(R.id.nameprod);
            costoCantidad = (TextView)itemView.findViewById(R.id.preciocard);


        }
        public void bind( final Producto producto, final ProductoAdapter.OnItemClickListener listener){
            Picasso.with(context).load(producto.getImagen()).fit().into(imagen);
            nombre.setText( producto.getNombre());
            costoCantidad.setText("$ "+ producto.getPrecioCantidad());


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
