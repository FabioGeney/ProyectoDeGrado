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


public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private List<Producto> productos;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;
    private SingletonCanasta singletonCanasta = SingletonCanasta.getInstance();
    private CanastaClass canastaClass;
    private String id;
    private int domi;
    public  ProductoAdapter(String id, int domi, List<Producto> productos, int layout, OnItemClickListener itemClickListener ){
        this.productos = productos;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
        this.domi = domi;
        this.id = id;
        canastaClass = new CanastaClass(id, domi);
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
            if(producto.getPrecioPorCantidad()!=null){
                costoCantidad.setText("Lleve 1 " +producto.getPrecioPorCantidad() +" por $ " + Integer.toString(producto.getPrecioCantidad()));
            }else {
                costoCantidad.setText("Lleve 1 gr por $ " + Integer.toString(producto.getPrecioCantidad()));
            }
            agregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(singletonCanasta.getCanasta() != null  ){
                        if(singletonCanasta.getCanasta().getId().equals(canastaClass.getId())){
                            canastaClass = singletonCanasta.getCanasta();
                            agregaProductos(producto, getAdapterPosition());
                        }else{
                            nuevaCanasta(producto, getAdapterPosition());
                        }
                    }else {
                        agregaProductos(producto, getAdapterPosition());

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

    private void agregaProductos(Producto producto, int position){
        Producto productoTemp = canastaClass.getProducto(producto.getIdDocument());
        if(productoTemp!=null){
            producto.setKey(producto.getIdDocument());
            canastaClass.aumentaContador(producto.getIdDocument());

        }else {
            producto.setKey(producto.getIdDocument());
            canastaClass.agregarProducto(producto.getIdDocument(), producto);
            canastaClass.aumentaContador(producto.getIdDocument());

        }
        singletonCanasta.setCanasta(canastaClass);
    }

    public void setFilter(ArrayList<Producto> productosFilter){
        productos = new ArrayList<>();
        productos.addAll(productosFilter);
        notifyDataSetChanged();
    }

    private void nuevaCanasta(final Producto producto, final int index){

        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        alerta.setTitle("Aviso");
        alerta.setMessage("Para agregar porductos de este mercadillo debes vaciar la canasta");
        alerta.setPositiveButton("Vaciar Canasta", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                canastaClass = new CanastaClass(producto.getId(), domi);
                agregaProductos( producto, index);
                singletonCanasta.setCanasta(canastaClass);
            }
        });
        alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });

        alerta.show();
    }
}
