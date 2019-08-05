package com.proyecto.marketdillo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    ImageButton add;
    ImageButton remove;
    TextView precioPorCantidad;
    TextView cantidad;
    int contador = 1;
    int envio;
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
        envio = Integer.parseInt(getIntent().getExtras().get("envio").toString());
        String titulo = producto.getNombre();

        this.setTitle("Detalles del producto");
        // inicia variables
        nombre = findViewById(R.id.nombre);
        imagen = findViewById(R.id.imagen);
        descripcion = findViewById(R.id.descripcion);
        agregar = findViewById(R.id.agregar);
        add = findViewById(R.id.add);
        remove = findViewById(R.id.remove);
        cantidad = findViewById(R.id.cantidad);
        precioPorCantidad = findViewById(R.id.precioPorCantidad);
        //obtiene datos enviados por VistaProdutosMercadillos y setea variables
        descripcion.setText(producto.getDescripcion());
        nombre.setText(titulo);
        precioPorCantidad.setText("Lleve 1 " +producto.getPrecioPorCantidad() +" por $ " + Integer.toString(producto.getPrecioCantidad()));
        String img = producto.getImagen();
        Picasso.with(this).load(img).fit().into(imagen);
        agregar.setText("Agregar $" + producto.getPrecioCantidad());

        canastaClass = new CanastaClass(producto.getId(), envio);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VistaProducto.this, VistaProductosMercadillo.class);
                if(singletonCanasta.getCanasta() != null  ){
                    if(singletonCanasta.getCanasta().getId().equals(canastaClass.getId())){
                        canastaClass = singletonCanasta.getCanasta();
                        agregaProductos(producto, index);
                        startActivity(intent);
                    }else{
                        nuevaCanasta(intent , index);

                    }
                }else {
                    agregaProductos(producto, index);
                    startActivity(intent);
                }

               }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador = contador+1;
                cantidad.setText(""+contador);
                agregar.setText("Agregar $"+contador*producto.getPrecioCantidad());

            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(cantidad.getText().toString()) != 1){
                    contador = contador-1;
                    cantidad.setText(""+contador);
                    agregar.setText("Agregar $"+contador*producto.getPrecioCantidad());
                }
            }
        });

    }
    private void agregaProductos(Producto producto, int position){
        Producto productoTemp = canastaClass.getProducto(""+position);
        if(productoTemp!=null){
            producto.setKey(""+position);
            canastaClass.setContador(""+position, contador);

        }else {
            producto.setKey(""+position);
            canastaClass.agregarProducto(""+position, producto);
            canastaClass.setContador(""+position, contador);

        }
        singletonCanasta.setCanasta(canastaClass);
    }
    private void nuevaCanasta( final Intent intent, final int index){

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Aviso");
        alerta.setMessage("Para agregar porductos de este mercadillo debes vaciar la canasta");
        alerta.setPositiveButton("Vaciar Canasta", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                canastaClass = new CanastaClass(producto.getId(), envio);
                agregaProductos( producto, index);
                singletonCanasta.setCanasta(canastaClass);
                startActivity(intent);
            }
        });
        alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        alerta.show();
    }
}
