package com.proyecto.marketdillo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private android.support.v7.widget.RecyclerView.Adapter mensajelAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Mensaje> mensajeList;
    private Button enviar;
    private EditText texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        layoutManager = new LinearLayoutManager(this);
        // Instancia del ListView.
        mRecyclerView = findViewById(R.id.mesaje_recycler);

        enviar = findViewById(R.id.enviar);
        texto = findViewById(R.id.texto);

    }

    private ArrayList<Mensaje> getMensajes(){
        ArrayList<Mensaje> mensajes = new ArrayList<>();

        return mensajes;
    }
}
