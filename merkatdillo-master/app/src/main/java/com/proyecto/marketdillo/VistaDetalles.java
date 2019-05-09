package com.proyecto.marketdillo;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
        setContentView(R.layout.activity_vista_canasta);
        this.setTitle("");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        canastas = getCanastas();

        mRecyclerView = findViewById(R.id.listview);
        layoutManager = new LinearLayoutManager(this);

        mercadilloAdapter = new CanastaAdapter(canastas, R.layout.list_item_canasta, new CanastaAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Canasta mercadillo, int posicion) {

            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mercadilloAdapter);

        TextView textView = findViewById(R.id.metodoPago);

        textView.setText("Efectivo");


    }

    private List<Canasta> getCanastas() {
        ArrayList<Canasta> canasta = new ArrayList<>();
        canasta.add(new Canasta("Manzana", 3000, 1, R.mipmap.ic_fruit));
        canasta.add(new Canasta("Pera", 5000, 1, R.mipmap.ic_fruit));
        canasta.add(new Canasta("Mango", 3000, 1, R.mipmap.ic_fruit));
        canasta.add(new Canasta("Piña", 4000, 1, R.mipmap.ic_fruit));
        canasta.add(new Canasta("Lulo", 1000, 1, R.mipmap.ic_fruit));
        return canasta;
    }
}
