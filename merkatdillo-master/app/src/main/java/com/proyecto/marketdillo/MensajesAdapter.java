package com.proyecto.marketdillo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MensajesAdapter extends RecyclerView.Adapter<MensajesAdapter.ViewHolder> {


    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private List<Mensaje> mensajes = new ArrayList<>();
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;
    Mensaje mensaje;

    public MensajesAdapter(Context context, MensajesAdapter.OnItemClickListener itemClickListener ){
        this.context = context;
        this.itemClickListener =  itemClickListener;
    }

    public void agregarMensaje(Mensaje mensaje){
        mensajes.add(mensaje);
        this.mensaje = mensaje;
        notifyItemInserted(mensajes.size());
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if(i == MSG_TYPE_RIGHT){
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_mensaje_right, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }else {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_mensaje_left, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(mensajes.get(i),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    @Override
    public int getItemViewType(int position){
        SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
        Usuario usuario = singletonUsuario.getUsuario();
        if(mensajes.get(position).getDe().equals(usuario.getId())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView hora;
        private TextView mensajeView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mensajeView = itemView.findViewById(R.id.mensaje);
            hora = itemView.findViewById(R.id.hora);
        }
        public void bind( final Mensaje mensaje, final OnItemClickListener listener){
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
