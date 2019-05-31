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
    private List<Producto> canastas;

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
        ArrayList<Producto> getProductos = (ArrayList<Producto>) getIntent().getSerializableExtra("productos");


        //setea textviews
        nombre.setText(nombreMercadillo);
        direccion.setText(direccionEntrega);
        total.setText(totalPedido);


        canastas = getProductos;

        mRecyclerView = findViewById(R.id.listview);
        layoutManager = new LinearLayoutManager(this);

        mercadilloAdapter = new DetallesAdapter(canastas, R.layout.list_detalles, new DetallesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Producto producto, int posicion) {

            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mercadilloAdapter);


    }

    private ArrayList<Producto> getCanasta(){
        ArrayList<Producto> producto = new ArrayList<>();
        SingletonCanasta singletonCanasta = SingletonCanasta.getInstance();
        for (Producto temp : singletonCanasta.getHistorial()){
            producto.add(temp);
        }
        return producto;
    }

}
