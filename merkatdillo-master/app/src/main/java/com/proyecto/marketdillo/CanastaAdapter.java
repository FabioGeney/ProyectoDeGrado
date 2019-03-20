package com.proyecto.marketdillo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CanastaAdapter extends ArrayAdapter<Canasta> {

    public CanastaAdapter(Context context, List<Canasta> objects) {
        super(context, 0, objects);

    }

    @SuppressLint("ResourceAsColor")
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_canasta,
                    parent,
                    false);
        }
        ImageButton add = convertView.findViewById(R.id.add);
        ImageButton remove = convertView.findViewById(R.id.remove);
        ImageView imagen = (ImageView) convertView.findViewById(R.id.imagen);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
        TextView precioProducto = (TextView)convertView.findViewById(R.id.precio);
        final TextView cantidad = convertView.findViewById(R.id.cantidad);

        final Canasta canasta = getItem(position);

        imagen.setImageResource(canasta.getImagen());
        nombre.setText( canasta.getNombreProducto());
        precioProducto.setText( "$ " + canasta.getPrecioProducto());
         cantidad.setText("1");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canasta.setCantidad( canasta.getCantidad() + 1);
                cantidad.setText(""+ canasta.getCantidad());
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canasta.getCantidad()  == 1)
                {
                }else {
                canasta.setCantidad(canasta.getCantidad() - 1);
                cantidad.setText(""+canasta.getCantidad());
                }
            }
        });


        this.notifyDataSetChanged();


        return convertView;
    }
}
