package com.proyecto.marketdillo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

public class VistaCampesino extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();;
    private TextView nombre;
    private TextView correo;
    private SessionManager sessionManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_campesino);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(this);
        //almacena los datos del sessionManger en el objeto usuario
        Gson gson = new Gson();
        String userGson = sessionManager.getUsuario();
        final Usuario usuario = gson.fromJson(userGson,Usuario.class);

        //almacena los datos en singleton
        SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
        singletonUsuario.setUsuario(usuario);

        //Obtiene datos del intent
        String idPedido = getIntent().getStringExtra("idPedido");

        if(idPedido!=null){
            //Modifica titulo del Toolbar
            this.setTitle("Pedidos");
            PedidosFragment pedidosFragment = new PedidosFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.campesinos_content, pedidosFragment).commit();
        }else {
            //Modifica titulo del Toolbar
            this.setTitle("Mis Productos");
            PtsCampesinoFragment ptsCampesinoFragment = new PtsCampesinoFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.campesinos_content, ptsCampesinoFragment).commit();
        }


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VistaCampesino.this, CrearProducto.class);

                //envia datos del id del campesino, para almacenar los productos con su id
                intent.putExtra("id", usuario.getId());
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        nombre = header.findViewById(R.id.usuarionombre);
        nombre.setText(usuario.getNombre() + " " + usuario.getApellidos());
        correo = header.findViewById(R.id.usuariocorreo);
        correo.setText(usuario.getEmail());
        navigationView.setNavigationItemSelectedListener(this);






    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vista_campesino, menu);
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
    */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //crea fragmentManager para llamar fragments segun la opcion seleccionada
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.mimercadillo) {

            fragmentManager.beginTransaction().replace(R.id.campesinos_content,  new PtsCampesinoFragment()).commit();
            this.setTitle("Mis Productos");
        } else if (id == R.id.pedidos) {
            fragmentManager.beginTransaction().replace(R.id.campesinos_content, new PedidosFragment()).commit();
            this.setTitle("Pedidos");


        } else if (id == R.id.historial) {
            fragmentManager.beginTransaction().replace(R.id.campesinos_content, new HistorialFragment()).commit();
            this.setTitle("Historial");

        } else if (id == R.id.confi) {
            fragmentManager.beginTransaction().replace(R.id.campesinos_content, new ConfiguracionFragment()).commit();
            this.setTitle("Mi Perfil");

        } else if (id == R.id.mensajes) {
            fragmentManager.beginTransaction().replace(R.id.campesinos_content, new ChatsFragment()).commit();
            this.setTitle("Mensajes");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void hideFloatingActionButton(){
        fab.hide();
    }

    public void showFloatingActionButton(){
        fab.show();
    }
}
