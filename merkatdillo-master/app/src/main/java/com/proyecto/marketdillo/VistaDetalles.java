package com.proyecto.marketdillo;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VistaDetalles extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mercadilloAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Canasta> canastas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setTitle("Detalles del Pedido");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Declara e inicia variables

        TextView nombre = findViewById(R.id.nombreMercadillo);
        TextView direccion = findViewById(R.id.direccion);
        TextView total = findViewById(R.id.total);

        //obtiene datos del intent

        String nombreMercadillo = getIntent().getStringExtra("nombre");
        String direccionEntrega = getIntent().getStringExtra("direccion");
        String totalPedido = getIntent().getStringExtra("total");

        //setea textviews
        nombre.setText(nombreMercadillo);
        direccion.setText(direccionEntrega);
        total.setText(totalPedido);


        canastas = getCanastas();

        mRecyclerView = findViewById(R.id.listview);
        layoutManager = new LinearLayoutManager(this);

        mercadilloAdapter = new DetallesAdapter(canastas, R.layout.list_detalles, new DetallesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Canasta canasta, int posicion) {

            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mercadilloAdapter);






    }

    private ArrayList<Canasta> getCanastas(){
        //llama al singleton de canasta para acceder a la informaccion
        SingletonCanasta singletonCanasta = SingletonCanasta.getInstance();
        final  ArrayList<Canasta> canasta = new ArrayList<>();
        //obtiene los productos del singleton de canasta
        ArrayList<Producto> productos = singletonCanasta.getCanastas();
        //recorre el arreglo de los productos seleccionados por el consumidor para llenar la lista de productos de la canasta
        for(Producto producto:productos){
            canasta.add(new Canasta(producto.getNombre(), producto.getPrecioCantidad() , producto.getContador(), R.drawable.fruit));
        }

        return canasta;
    }
}
