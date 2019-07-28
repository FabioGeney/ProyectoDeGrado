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
    TextView nombre;
    Producto producto;
    SingletonCanasta singletonCanasta;
    CanastaClass canastaClass;
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
        final int index = Integer.parseInt(getIntent().getExtras().get("index").toString());
        final int envio = Integer.parseInt(getIntent().getExtras().get("envio").toString());
        String titulo = producto.getNombre();

        this.setTitle("Detalles del producto");
        // inicia variables
        nombre = findViewById(R.id.nombre);
        imagen = findViewById(R.id.imagen);
        descripcion = findViewById(R.id.descripcion);
        agregar = findViewById(R.id.agregar);

        //obtiene datos enviados por VistaProdutosMercadillos y setea variables
        descripcion.setText(producto.getDescripcion());
        nombre.setText(titulo);
        String img = producto.getImagen();
        Picasso.with(this).load(img).fit().into(imagen);

        canastaClass = new CanastaClass(producto.getId(), envio);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(singletonCanasta.getCanasta() != null  ){
                    if(singletonCanasta.getCanasta().getId().equals(canastaClass.getId())){
                        canastaClass = singletonCanasta.getCanasta();
                        agregaProductos(producto, index);
                    }
                }else {
                    agregaProductos(producto, index);

                }
                Intent intent = new Intent(VistaProducto.this, VistaProductosMercadillo.class);
                startActivity(intent);
               }
        });


    }
    private void agregaProductos(Producto producto, int position){
        Producto productoTemp = canastaClass.getProducto(""+position);
        if(productoTemp!=null){
            producto.setKey(""+position);
            canastaClass.aumentaContador(""+position);

        }else {
            producto.setKey(""+position);
            canastaClass.agregarProducto(""+position, producto);
            canastaClass.aumentaContador(""+position);

        }
        singletonCanasta.setCanasta(canastaClass);
    }
}
