package com.proyecto.marketdillo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.List;



public class VistaProductosMercadillo extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView listView;
    ProductoAdapter productoAdapter;
    List<Producto> productoList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_productos_mercadillo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.setTitle("Mercadillo de frutas");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VistaProductosMercadillo.this, VistaCanasta.class);
                startActivity(intent);
            }
        });

        productoList.add(new Producto("Manzana", "la descripcion es muy grande y no se q mas ponert", "$3000 por lb", R.mipmap.ic_fruit));
        productoList.add(new Producto("Pera", " null point", "$2000 por lb", R.mipmap.ic_fruit));

        listView = findViewById(R.id.listaProductos);
        productoAdapter = new ProductoAdapter(this, productoList);
        listView.setAdapter(productoAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_busqueda, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);

            return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // User pressed the search button

        return false;

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // User changed the text
        return false;

    }



}
