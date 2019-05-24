package com.proyecto.marketdillo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


        public TextView nombre ;
        public TextView total ;
        public TextView estado ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.nombre);
            estado = (TextView)itemView.findViewById(R.id.estado);
            total = itemView.findViewById(R.id.total);

        }
        public void bind( final Pedidos pedidos, final OnItemClickListener listener){

            nombre.setText(pedidos.getNombreMercadillo());
            estado.setText("Estado: "+ pedidos.getEstado());
            total.setText(pedidos.getTotal());

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