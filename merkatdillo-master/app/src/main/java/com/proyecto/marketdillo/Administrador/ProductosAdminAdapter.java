package com.proyecto.marketdillo.Administrador;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.proyecto.marketdillo.Producto;
import com.proyecto.marketdillo.R;
import com.proyecto.marketdillo.SingletonCanasta;
import com.squareup.picasso.Picasso;

import java.util.List;
public class ProductosAdminAdapter extends RecyclerView.Adapter<ProductosAdminAdapter.ViewHolder> {

    private List<Producto> productos;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;
    private SingletonCanasta singletonCanasta = SingletonCanasta.getInstance();

    public  ProductosAdminAdapter(List<Producto> productos, int layout, OnItemClickListener itemClickListener ){
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
        public ImageView borrar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = (ImageView) itemView.findViewById(R.id.imagen);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            descripcion = (TextView)itemView.findViewById(R.id.descripcion);
            costoCantidad = (TextView)itemView.findViewById(R.id.precio);
            borrar = (ImageView) itemView.findViewById(R.id.borrar);


        }
        public void bind( final Producto producto, final OnItemClickListener listener){
            Picasso.with(context).load(producto.getImagen()).fit().into(imagen);

            nombre.setText( producto.getNombre());
            descripcion.setText( producto.getDescripcion() );
            costoCantidad.setText(Integer.toString(producto.getPrecioCantidad()));
            borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CharSequence[] item = {"Si","No"};
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("¿Esta seguro de borrar este producto: " + producto.getNombre() +" ?");
                    alert.setItems(item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                       if(item[which].equals("Si")){
                           FirebaseFirestore db = FirebaseFirestore.getInstance();
                           db.collection("Mercadillo").document(producto.getId()).collection("Productos").document(producto.getIdDocument()).delete()
                                   .addOnSuccessListener(new OnSuccessListener<Void>() {
                                       @Override
                                       public void onSuccess(Void aVoid) {
                                           Toast.makeText(context, "Mercadillo eliminado", Toast.LENGTH_SHORT).show();
                                           productos.remove(getAdapterPosition());
                                           notifyDataSetChanged();
                                       }
                                   })
                                   .addOnFailureListener(new OnFailureListener() {
                                       @Override
                                       public void onFailure(@NonNull Exception e) {
                                           Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                       }
                                   });
                                } else if(item[which].equals("No")){
                                    dialog.dismiss();
                                }
                            }
                        });
                        alert.show();

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

