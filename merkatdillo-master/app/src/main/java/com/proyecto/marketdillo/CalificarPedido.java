package com.proyecto.marketdillo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CalificarPedido extends AppCompatActivity {

    private String idDocumentPedido;
    private RatingBar ratingProdutos;
    private RatingBar ratingServicio;
    private RatingBar ratingCosto;
    private RatingBar ratingTiempo;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setTitle("Calificar pedido");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView nombreMercadillo = findViewById(R.id.nombreMercadillo);
        ratingProdutos = findViewById(R.id.ratingProdutos);
        ratingServicio = findViewById(R.id.ratingBar);
        ratingCosto = findViewById(R.id.ratingCosto);
        ratingTiempo = findViewById(R.id.ratingTiempo);
        Button enviar = findViewById(R.id.enviar);

        idDocumentPedido = getIntent().getStringExtra("idDocumentPedido");
        String nombre = getIntent().getStringExtra("mercadillo");

        nombreMercadillo.setText(nombre);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviaCalificaion();
            }
        });






    }

    private void enviaCalificaion(){

        double calificacion = (ratingProdutos.getRating() + ratingServicio.getRating() + ratingCosto.getRating() + ratingTiempo.getRating())/4;
        Map<String, Object> update = new HashMap<>();
        update.put("calificacion", calificacion);
        db.collection("Pedidos").document(idDocumentPedido).update(update);

    }
}
