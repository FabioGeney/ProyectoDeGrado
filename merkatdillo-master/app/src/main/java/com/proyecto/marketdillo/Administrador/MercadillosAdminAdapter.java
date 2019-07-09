package com.proyecto.marketdillo.Administrador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.proyecto.marketdillo.Mercadillo;
import com.proyecto.marketdillo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MercadillosAdminAdapter extends RecyclerView.Adapter<MercadillosAdminAdapter.ViewHolder> {

    private List<Mercadillo> mercadillos;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;

    public  MercadillosAdminAdapter(List<Mercadillo> mercadillos, int layout, OnItemClickListener itemClickListener ){
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
        public ImageView borrar;
        public ImageView imagen;
        public TextView nombre ;
        public TextView costoEnvio ;
        public TextView tiempoEnvio;
        public TextView calificacion ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            borrar = (ImageView) itemView.findViewById(R.id.close);
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



            borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CharSequence[] item = {"Contactar","Eliminar"};
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setItems(item, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(item[which].equals("Contactar")){

                            } else if(item[which].equals("Eliminar")){
                                deleteMercadillo(mercadillo.getId(), getAdapterPosition());
                            }else {
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
                    listener.OnItemClick(mercadillo, getAdapterPosition());
                }
            });


        }
    }

    private void deleteMercadillo(final String id, final int index){
        final CharSequence[] item = {"Si","No"};
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("¿Esta seguro de borrar este mercadillo?");
        alert.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(item[which].equals("Si")){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Mercadillo").document(id)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(context, "Mercadillo eliminado", Toast.LENGTH_SHORT).show();
                                    mercadillos.remove(index);
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

    public interface OnItemClickListener{
        void OnItemClick(Mercadillo mercadillo, int posicion);

    }
}

