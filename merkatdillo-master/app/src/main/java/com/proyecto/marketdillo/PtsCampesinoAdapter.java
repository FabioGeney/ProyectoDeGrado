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

import com.squareup.picasso.Picasso;

import java.util.List;

public class PtsCampesinoAdapter extends ArrayAdapter<Producto> {
    Context context;
    public PtsCampesinoAdapter(Context context, List<Producto> objects) {
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
                    R.layout.list_item_campesino_prdto,
                    parent,
                    false);
        }
        ImageView imagen = (ImageView) convertView.findViewById(R.id.imagen);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
        TextView descripcion = (TextView)convertView.findViewById(R.id.descripcion);
        TextView costoCantidad = (TextView)convertView.findViewById(R.id.precio);
        ImageButton editar = convertView.findViewById(R.id.editar);
        ImageButton borrar = convertView.findViewById(R.id.borrar);
        Producto producto = getItem(position);

        Picasso.with(context).load(producto.getImagen()).fit().into(imagen);
        nombre.setText( producto.getNombre());
        descripcion.setText( producto.getDescripcion() );
        costoCantidad.setText(producto.getPrecioCantidad());

        this.notifyDataSetChanged();

        return convertView;
    }
}