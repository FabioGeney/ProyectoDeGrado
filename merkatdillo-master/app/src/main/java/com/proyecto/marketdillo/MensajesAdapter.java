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

public class MensajesAdapter extends RecyclerView.Adapter<MensajesAdapter.ViewHolder> {

    private List<Mensaje> mensajes;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;

    public MensajesAdapter(List<Mensaje> mensajes, int layout, OnItemClickListener itemClickListener ){
        this.mensajes = mensajes;
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
        viewHolder.bind(mensajes.get(i),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nombre;
        private TextView hora;
        private TextView mensajeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            hora = itemView.findViewById(R.id.hora);
            mensajeView = itemView.findViewById(R.id.mensaje);
        }
        public void bind( final Mensaje mensaje, final OnItemClickListener listener){
            nombre.setText(mensaje.getNombreUsuario());
            hora.setText(mensaje.getHora());
            mensajeView.setText(mensaje.getMensaje());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(mensaje, getAdapterPosition());
                }
            });


        }
    }

    public interface OnItemClickListener{
        void OnItemClick(Mensaje mensaje, int posicion);

    }
}
