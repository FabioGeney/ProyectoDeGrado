package com.proyecto.marketdillo;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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

        SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();



        //Declara e inicia variables

        TextView nombre = findViewById(R.id.nombreMercadillo);
        TextView direccion = findViewById(R.id.direccion);
        TextView total = findViewById(R.id.total);
        TextView calificacion = findViewById(R.id.calificacion);
        Button pedirNuevo = findViewById(R.id.button);

        //obtiene datos del intent

        final String nombreMercadillo = getIntent().getStringExtra("nombre");
        final String idDocumentPedido = getIntent().getStringExtra("idDocumento");
        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        String direccionEntrega = getIntent().getStringExtra("direccion");
        String totalPedido = getIntent().getStringExtra("total");
        String boton = getIntent().getStringExtra("visible");
        ArrayList<Producto> getProductos = (ArrayList<Producto>) getIntent().getSerializableExtra("productos");

        if(boton.equals("no")){
            pedirNuevo.setVisibility(View.GONE);
        }


        //setea textviews
        if(singletonUsuario.getUsuario().getTipoUsuario().equals("campesino")){
            nombre.setText(nombreUsuario);
            calificacion.setVisibility(View.GONE);
            pedirNuevo.setVisibility(View.GONE);
        }else{
            nombre.setText(nombreMercadillo);
        }

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
        calificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VistaDetalles.this, CalificarPedido.class);
                intent.putExtra("mercadillo", nombreMercadillo);
                intent.putExtra("idDocumentPedido", idDocumentPedido);
                startActivity(intent);
            }
        });

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
