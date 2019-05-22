package com.proyecto.marketdillo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PedidosAdapter extends ArrayAdapter<Pedidos> {
    Context context;
    public PedidosAdapter(Context context, List<Pedidos> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @SuppressLint("ResourceAsColor")
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_pedidos,
                    parent,
                    false);
        }
        ImageView imagen = (ImageView) convertView.findViewById(R.id.imagen);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
        TextView estado = (TextView)convertView.findViewById(R.id.estado);
        TextView total = convertView.findViewById(R.id.total);
        Pedidos pedido = getItem(position);

        //Picasso.with(context).load(pedido.getImagen()).fit().into(imagen);
        nombre.setText( pedido.getNombreMercadillo());
        estado.setText(pedido.getEstado() );
        total.setText( "$ " + pedido.getTotal());
        this.notifyDataSetChanged();

        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        };

        return convertView;
    }
}
