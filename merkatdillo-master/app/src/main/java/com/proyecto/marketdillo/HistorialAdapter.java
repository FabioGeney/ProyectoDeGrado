package com.proyecto.marketdillo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class HistorialAdapter extends ArrayAdapter<Historial> {

    Context context;

    public HistorialAdapter(Context context, List<Historial> objects) {
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
                    R.layout.list_item_historial,
                    parent,
                    false);
        }
        Button detalles = convertView.findViewById(R.id.detalles);
        Button pedir = convertView.findViewById(R.id.pedir);
        ImageView imagen = (ImageView) convertView.findViewById(R.id.imagen);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
        TextView fechaPedido = (TextView)convertView.findViewById(R.id.fecha);
        Historial historial = getItem(position);

        Picasso.with(context).load(historial.getImagen()).fit().into(imagen);
        nombre.setText( historial.getNombreMercadillo());
        fechaPedido.setText( historial.getFecha() );



        this.notifyDataSetChanged();



        return convertView;
    }
}
