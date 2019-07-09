package com.proyecto.marketdillo.Administrador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.proyecto.marketdillo.Chat;
import com.proyecto.marketdillo.R;
import com.proyecto.marketdillo.Usuario;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {

    private List<Usuario> usuarios;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;

    public  UsuarioAdapter(List<Usuario> usuarios, int layout, OnItemClickListener itemClickListener ){
        this.usuarios = usuarios;
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
        viewHolder.bind(usuarios.get(i),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView nombre ;
        public TextView tipo ;
        public TextView telefono;
        public ImageView opciones;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.nombre);
            tipo = (TextView)itemView.findViewById(R.id.tipo);
            telefono = (TextView)itemView.findViewById(R.id.telefono);
            opciones =  itemView.findViewById(R.id.opciones);
        }
        public void bind( final Usuario usuario, final OnItemClickListener listener){
            //Picasso.with(context).load(usuario.getImagen()).fit().into(imagen);
            nombre.setText( usuario.getNombre() + " " + usuario.getApellidos());
            tipo.setText(usuario.getTipoUsuario() );
            telefono.setText(  usuario.getCelular());

            opciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CharSequence[] item = {"Contactar","Mas información","Eliminar Usuario"};
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);

                    alert.setItems(item, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (item[which].toString()){
                                case "Contactar":
                                    Intent intent = new Intent(context, Chat.class);
                                    intent.putExtra("nombreDestinatario", usuario.getNombre() + " " + usuario.getApellidos());
                                    intent.putExtra("idDestinatario", usuario.getId());
                                    context.startActivity(intent);
                                    break;
                                case "Mas información":
                                    break;
                                case "Eliminar Usuario":
                                    //FirebaseAuth.getInstance()
                                    break;
                                default:
                                    dialog.dismiss();
                                    break;

                            }
                        }
                    });
                    alert.show();
                }
            });




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(usuario, getAdapterPosition());
                }
            });


        }
    }

    public interface OnItemClickListener{
        void OnItemClick(Usuario usuario, int posicion);

    }
}


