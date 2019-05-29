package com.proyecto.marketdillo.fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.proyecto.marketdillo.R;
import com.squareup.picasso.Picasso;

public class ImagenDetalle extends AppCompatActivity {

    private TextView descripcion;
    private TextView producto;
    private ImagenCard imagen;
    private TextView precio;
    private ImageView picture;
    private TextView vendedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_detalle);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagen = (ImagenCard) getIntent().getSerializableExtra("imagen");
        String titulo = imagen.getNombre();
        this.setTitle(titulo);

        picture = findViewById(R.id.imageview1);
        producto = findViewById(R.id.producto);
        vendedor = findViewById(R.id.vendedor);
        precio = findViewById(R.id.precio);
        descripcion = findViewById(R.id.descripcion);

        precio.setText("Precio: " + imagen.getPrecioCantidad());
        descripcion.setText("Descripción: "+imagen.getDescripcion());
        producto.setText(imagen.getNombre());
        int img = imagen.getImagen();
        Picasso.with(this).load(img).fit().into(picture);
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
