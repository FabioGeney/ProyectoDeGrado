package com.proyecto.marketdillo;


import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

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

        // consulta los datos del usuario almacenados en el SessionManager
        SessionManager sessionManager = new SessionManager(this);
        Gson gson = new Gson();
        String userGson = sessionManager.getUsuario();
        Usuario usuario = gson.fromJson(userGson,Usuario.class);



        //Declara e inicia variables

        TextView nombre = findViewById(R.id.nombreMercadillo);
        TextView direccion = findViewById(R.id.direccion);
        TextView total = findViewById(R.id.total);
        TextView calificacion = findViewById(R.id.calificacion);
        ConstraintLayout layout = findViewById(R.id.layout);
        Button pedirNuevo = findViewById(R.id.button);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        //obtiene datos del intent
        Pedidos pedidos = (Pedidos) getIntent().getSerializableExtra("pedido");
        final String nombreMercadillo = pedidos.getNombreMercadillo();
        final String idDocumentPedido = pedidos.getIdDocument();
        final String idMercadillo = pedidos.getIdCampesino();
        String nombreUsuario = pedidos.getNombreComprador();
        double cal = pedidos.getCalificacion();
        String direccionEntrega = pedidos.getDireccionEntrega();
        String totalPedido = pedidos.getTotal();
        String boton = getIntent().getStringExtra("visible");
        SingletonHistorial singletonHistorial = SingletonHistorial.getInstance();
        ArrayList<Producto> getProductos = singletonHistorial.getHistorial();

        if(boton.equals("no")){
            pedirNuevo.setVisibility(View.GONE);
            calificacion.setVisibility(View.GONE);
        }


        if(cal!=0){
            float numStar = (float)cal;
            layout.setVisibility(View.VISIBLE);
            calificacion.setVisibility(View.GONE);
            ratingBar.setRating(numStar);
        }


        //setea textviews
        if(usuario.getTipoUsuario().equals("campesino")){
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
                intent.putExtra("idMercadillo", idMercadillo);
                startActivity(intent);
            }
        });

    }

    private ArrayList<Producto> getCanasta(){
        ArrayList<Producto> producto = new ArrayList<>();
        SingletonHistorial singletonCanasta = SingletonHistorial.getInstance();
        for (Producto temp : singletonCanasta.getHistorial()){
            producto.add(temp);
        }
        return producto;
    }

}
