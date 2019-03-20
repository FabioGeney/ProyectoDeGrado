package com.proyecto.marketdillo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductoAdapter extends ArrayAdapter<Producto> {

    public ProductoAdapter(Context context, List<Producto> objects) {
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
                    R.layout.list_item_productos,
                    parent,
                    false);
        }
        ImageView imagen = (ImageView) convertView.findViewById(R.id.imagen);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
        TextView descripcion = (TextView)convertView.findViewById(R.id.descripcion);
        TextView costoCantidad = (TextView)convertView.findViewById(R.id.precio);
        Producto producto = getItem(position);

        imagen.setImageResource(producto.getImagen());
        nombre.setText( producto.getNombre());
        descripcion.setText( producto.getDescripcion() );
        costoCantidad.setText(producto.getPrecioCantidad());

        this.notifyDataSetChanged();

        return convertView;
    }
}
