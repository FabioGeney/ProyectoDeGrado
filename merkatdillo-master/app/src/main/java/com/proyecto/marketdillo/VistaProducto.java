package com.proyecto.marketdillo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class VistaProducto extends AppCompatActivity {

    ImageView imagen;
    TextView descripcion;
    Button agregar;
    Producto producto;
    SingletonCanasta singletonCanasta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_producto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //inicializa SingletonCanasta
        singletonCanasta = SingletonCanasta.getInstance();
        //declara el objeto enviado desde VistaMercadilloPrducto
        producto = (Producto) getIntent().getSerializableExtra("producto");

        String titulo = producto.getNombre();

        this.setTitle(titulo);
        // inicia variablres
        imagen = findViewById(R.id.imagen);
        descripcion = findViewById(R.id.descripcion);
        agregar = findViewById(R.id.agregar);

        //obtiene datos enviados por VistaProdutosMercadillos y setea variables
        descripcion.setText(producto.getDescripcion());
        int img = producto.getImagen();
        Picasso.with(this).load(img).fit().into(imagen);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                producto.setContador(1);
                singletonCanasta.setCanastas(producto);
            }
        });


    }
}
