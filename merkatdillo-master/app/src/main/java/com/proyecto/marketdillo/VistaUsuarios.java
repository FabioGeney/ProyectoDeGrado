package com.proyecto.marketdillo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class VistaUsuarios extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private String direccion;
    private TextView nombreUsuario;
    private TextView correo;
    private SessionManager sessionManager;
    Menu menu;
    private boolean twice = false;
    private final String TAG = this.getClass().getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercadillos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(this);
        //almacena los datos del sessionManger en el objeto usuario
        Gson gson = new Gson();
        String userGson = sessionManager.getUsuario();
        final Usuario usuario = gson.fromJson(userGson,Usuario.class);
        //llama singleton para alamcenar datos del usuario
        SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
        //almacena los datos del singleton en la variable usuario
        singletonUsuario.setUsuario(usuario);

        String nombreDestinatario = getIntent().getStringExtra("nombreDestinatario");
        String idDestinatario = getIntent().getStringExtra("idDestinatario");
        //Solo se ejecuta cuando llega una notificacion y la app esta cerrada
        if(idDestinatario!=null && nombreDestinatario != null){
            Intent intent = new Intent(VistaUsuarios.this, Chat.class);
            intent.putExtra("nombreDestinatario", nombreDestinatario);
            intent.putExtra("idDestinatario", idDestinatario);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //Cambia el titulo del Activity por la direccion del usuario
        direccion = usuario.getDireccion();
        this.setTitle(direccion);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //instancia el header del navigationView para modificar editTex
        View header = navigationView.getHeaderView(0);
        //instancia editText del header
        nombreUsuario = header.findViewById(R.id.nombre_usuario);
        //cambia los valores del TextView
        nombreUsuario.setText(usuario.getNombre());

        correo = header.findViewById(R.id.usuariocorreo);
        correo.setText(usuario.getEmail());
        navigationView.setNavigationItemSelectedListener(this);
        // crea un FragmentManager para llamar al FragmentMercadillo
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mercadillos_container, new MercadilloFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Log.d(TAG, "click");

            if(twice){
                Intent i = new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_HOME);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                System.exit(0);
            }
            twice = true;
            Log.d(TAG, "twice" + twice);

            Toast.makeText(VistaUsuarios.this, "Vuelva a oprimir para salir", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    twice = false;
                    Log.d(TAG, "twice" + twice);
                }
            }, 2000);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_busqueda, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        this.menu = menu;

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //crea fragmentManager para llamar fragments segun la opcion seleccionada
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.mercadillos) {
            fragmentManager.beginTransaction().replace(R.id.mercadillos_container, new MercadilloFragment()).commit();
            this.setTitle(direccion);
            menu.findItem(R.id.search).setVisible(true);


        } else if (id == R.id.pedidos) {
            fragmentManager.beginTransaction().replace(R.id.mercadillos_container, new PedidosFragment()).commit();
            this.setTitle("Pedidos");
            menu.findItem(R.id.search).setVisible(false);

        } else if (id == R.id.historial) {
            fragmentManager.beginTransaction().replace(R.id.mercadillos_container, new HistorialFragment()).commit();
            this.setTitle("Historial");
            menu.findItem(R.id.search).setVisible(false);


        } else if (id == R.id.nav_favoritos) {
            fragmentManager.beginTransaction().replace(R.id.mercadillos_container, new FavoritosFragment()).commit();
            this.setTitle("Favoritos");
            menu.findItem(R.id.search).setVisible(false);


        } else if (id == R.id.confi) {
            fragmentManager.beginTransaction().replace(R.id.mercadillos_container, new ConfiguracionUsuarioFragment()).commit();
            this.setTitle("Perfil");
            menu.findItem(R.id.search).setVisible(false);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Intent intent = new Intent(VistaUsuarios.this, Busqueda.class);
        intent.putExtra("query",s);
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
