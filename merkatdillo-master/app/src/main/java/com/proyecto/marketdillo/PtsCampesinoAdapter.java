package com.proyecto.marketdillo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.proyecto.marketdillo.fragments.ImagenDetalle;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PtsCampesinoAdapter extends RecyclerView.Adapter<PtsCampesinoAdapter.ViewHolder> {

    private List<Producto> productos;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;
    private FirebaseStorage mStorage = FirebaseStorage.getInstance();

    public  PtsCampesinoAdapter(List<Producto> productos, int layout, OnItemClickListener itemClickListener ){
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
        public TextView nombre;
        public TextView descripcion;
        public TextView costoCantidad ;
        public ImageView options;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = (ImageView) itemView.findViewById(R.id.imagen);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            descripcion = (TextView)itemView.findViewById(R.id.descripcion);
            costoCantidad = (TextView)itemView.findViewById(R.id.precio);
            options = itemView.findViewById(R.id.options);

        }
        public void bind( final Producto producto, final OnItemClickListener listener){
            Picasso.with(context).load(producto.getImagen()).fit().into(imagen);
            nombre.setText( producto.getNombre());
            descripcion.setText( producto.getDescripcion() );
            costoCantidad.setText("$ "+producto.getPrecioCantidad());

            options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CharSequence[] itemm = {"Editar","Borrar"};
                    AlertDialog.Builder alerta = new AlertDialog.Builder(context);
                    alerta.setItems(itemm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(itemm[which].equals("Editar")){
                                editarProducto(producto);
                            } else if(itemm[which].equals("Borrar")){
                                borrarProducto(producto, getAdapterPosition());
                            }else {
                                dialog.dismiss();
                            }

                        }
                    });
                    alerta.show();
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

    private void borrarProducto(final Producto producto, final int position){
        final CharSequence[] item = {"Si","No"};
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("¿Esta seguro de borrar el producto?");
        alert.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(item[which].equals("Si")){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Mercadillo").document(producto.getId()).collection("Productos").document(producto.getIdDocument())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    StorageReference imageRef = mStorage.getReferenceFromUrl(producto.getImagen());
                                    imageRef.delete();
                                    Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show();
                                    productos.remove(position);
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

    private void editarProducto(final Producto producto){
        final CharSequence[] itemm = {"Si","No"};
        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        alerta.setTitle("¿Quiere editar este producto?");
        alerta.setItems(itemm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(itemm[which].equals("Si")){
                    Intent intent = new Intent(context, Actualizar.class);
                    intent.putExtra("produ", producto);
                    context.startActivity(intent);
                                /*FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("Mercadillo").document(producto.getId()).collection("Productos").document(producto.getIdDocument()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            for(QueryDocumentSnapshot document : task.getResult()){
                                                Producto producto = new Producto(document.getId(),)

                                           }
                                        }
                                    }
                                });*/

                } else if(itemm[which].equals("No")){
                    dialog.dismiss();
                }

            }
        });
        alerta.show();
    }

    public interface OnItemClickListener{
        void OnItemClick(Producto producto, int posicion);

    }
}
