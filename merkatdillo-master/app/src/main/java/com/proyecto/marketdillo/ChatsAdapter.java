package com.proyecto.marketdillo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {

    private List<Contacto> mensajes = new ArrayList<>();
    private Map<String, Contacto> temp = new HashMap<>();

    private OnItemClickListener itemClickListener;
    private Context context;

    public ChatsAdapter(Context context, ChatsAdapter.OnItemClickListener itemClickListener ){
        this.context = context;
        this.itemClickListener =  itemClickListener;
    }

    public void agregarMensaje(String key, Contacto contacto){
        temp.put(key, contacto);
        Collection<Contacto> getValues = temp.values();
        mensajes = new ArrayList<> (getValues);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_mensajes, viewGroup, false);
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
        private TextView ultimoMensaje;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            ultimoMensaje = itemView.findViewById(R.id.mensaje);
        }
        public void bind( final Contacto contacto, final OnItemClickListener listener){
            nombre.setText(contacto.getNombre());
            ultimoMensaje.setText(contacto.getUltimoMensaje());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(contacto, getAdapterPosition());
                }
            });


        }
    }

    public interface OnItemClickListener{
        void OnItemClick(Contacto contacto, int posicion);

    }
}
