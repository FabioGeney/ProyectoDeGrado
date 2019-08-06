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

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.ViewHolder> {

    private List<Pedidos> pedidos;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;

    public  PedidosAdapter(List<Pedidos> pedidos, int layout, OnItemClickListener itemClickListener ){
        this.pedidos = pedidos;
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
        viewHolder.bind(pedidos.get(i),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagen;
        public TextView nombre ;
        public TextView estado ;
        public TextView fecha ;
        public TextView pendiente ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            fecha = (TextView)itemView.findViewById(R.id.fecha);
            estado = (TextView)itemView.findViewById(R.id.estado);
            pendiente = (TextView)itemView.findViewById(R.id.nuevo);

        }
        public void bind( final Pedidos pedidos, final OnItemClickListener listener){

            SingletonUsuario singletonUsuario  =SingletonUsuario.getInstance();
            estado.setText( " "+ pedidos.getEstado() + " ");

            if(singletonUsuario.getUsuario().getTipoUsuario().equals("campesino")){
                nombre.setText(pedidos.getNombreComprador());
                imagen.setVisibility(View.GONE);
                if(pedidos.getEstado().equals("Creado")){
                    pendiente.setText(" NUEVO ");
                }else {
                    pendiente.setText(" PENDIENTE! ");
                    pendiente.setBackgroundColor(context.getResources().getColor(R.color.pendiente));
                    pendiente.setTextColor(context.getResources().getColor(R.color.colorIcons));
                }
            }else {
                pendiente.setVisibility(View.GONE);
                nombre.setText(pedidos.getNombreMercadillo());
                Picasso.with(context).load(R.mipmap.ic_merca_image).fit().into(imagen);
            }


            fecha.setText(pedidos.getFecha());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(pedidos, getAdapterPosition());
                }
            });


        }
    }

    public interface OnItemClickListener{
        void OnItemClick(Pedidos pedidos, int posicion);

    }
}