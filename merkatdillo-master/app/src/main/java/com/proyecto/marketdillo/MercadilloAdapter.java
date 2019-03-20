package com.proyecto.marketdillo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MercadilloAdapter extends ArrayAdapter<Mercadillo> {
    Context context;

    public MercadilloAdapter(Context context, List<Mercadillo> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @SuppressLint({"ResourceAsColor", "ClickableViewAccessibility"})
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_mercadillo,
                    parent,
                    false);
        }
        final ImageView favorito = convertView.findViewById(R.id.favorito);
        ImageView imagen = (ImageView) convertView.findViewById(R.id.imagen);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
        TextView costoEnvio = (TextView)convertView.findViewById(R.id.costoEnvio);
        TextView tiempoEnvio = (TextView)convertView.findViewById(R.id.tiempoEnvio);
        TextView calificacion = convertView.findViewById(R.id.calificacion);
        Mercadillo mercadillo = getItem(position);

        Picasso.with(context).load(mercadillo.getImagen()).fit().into(imagen);
        nombre.setText( mercadillo.getNombre());
        costoEnvio.setText("Envio $ " +mercadillo.getCostoEnvio() );
        tiempoEnvio.setText( mercadillo.getTiempoEntrega());
        calificacion.setText(  Float.toString(mercadillo.getCalificacion()));

        final boolean[] click = {true};

        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(click[0]){
                    favorito.setImageResource(R.drawable.ic_fav);
                    click[0] = false;
                }else {
                    favorito.setImageResource(R.drawable.ic_favorito);
                    click[0] = true;
                }
            }
        });


        this.notifyDataSetChanged();
        return convertView;
    }
}

