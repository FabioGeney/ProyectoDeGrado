package com.proyecto.marketdillo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class VistaProductosMercadillo extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView mRecyclerView;
    private ProductoAdapter productoAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Producto> productos;
    private Mercadillo mercadillo;
    private CollapsingToolbarLayout ctlLayout;
    private DividerItemDecoration dividerItemDecoration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_productos_mercadillo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Llama al singletonMercadillo para obtener los datos almacenados
        SingletonMercadillo singletonMercadillo = SingletonMercadillo.getInstance();
        //almacena los datos del singleton en la variable mercadillo
        mercadillo = singletonMercadillo.getMercadillo();
        //cambia titulo del activity, con datos del MercadillsoFragment
        String nombreMercadillo = mercadillo.getNombre();

        ctlLayout = findViewById(R.id.ctlLayout);
        ctlLayout.setTitle(nombreMercadillo);

        dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VistaProductosMercadillo.this, VistaCanasta.class);
                startActivity(intent);
            }
        });

        FloatingActionButton mensaje = findViewById(R.id.enviarMensaje);
        mensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VistaProductosMercadillo.this, Chat.class);
                intent.putExtra("idDestinatario", mercadillo.getId());
                intent.putExtra("nombreDestinatario", mercadillo.getNombre());
                startActivity(intent);
            }
        });


        productos = getProductos();
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView = findViewById(R.id.listaProductos);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(productoAdapter);

        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    public List<Producto> getProductos(){
        final ArrayList<Producto> request = new ArrayList<>();
        //request.add(new Producto("manzana", "Testeando ancho del EdiText para que no se pase hasta el boton", "3500", R.drawable.fruit));
        //obtienen la id del mecadillo
        String id = mercadillo.getId();
        //hace consulta en la base de datos para buscar los prodcutos del mercadillo
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Mercadillo").document(id).collection("Productos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //si encuentra almacena la indormacion de la base de datos en el objeto
                                Producto producto = document.toObject(Producto.class);
                                producto.setIdDocument(document.getId());
                                //agrega porducto de la base de dato al arreglo
                                request.add(producto);

                            }
                            // Inicializar el adaptador con la fuente de datos.
                            productoAdapter = new ProductoAdapter(mercadillo.getId(), mercadillo.getCostoEnvio(), productos, R.layout.list_item_productos, new ProductoAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(Producto producto, int posicion) {
                                    //al hacer click en un mercadillo se va a otro activity
                                    Intent intent = new Intent(VistaProductosMercadillo.this, VistaProducto.class);
                                    //envia informacion del mercadillo al otro activity
                                    intent.putExtra("envio", mercadillo.getCostoEnvio());
                                    intent.putExtra("producto", producto);
                                    intent.putExtra("index", posicion);
                                    startActivity(intent);
                                }
                            });

                            //Relacionando la lista con el adaptador
                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setAdapter(productoAdapter);


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return request;
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

        return false;

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<Producto> productosFilter = filter(productos, newText);
        productoAdapter.setFilter(productosFilter);
        return false;

    }

    private ArrayList<Producto> filter(List<Producto> productosTemp, String query){
        ArrayList<Producto> temp= new ArrayList<>();
        String queryTemp = query.toLowerCase();
        for (Producto producto: productosTemp){
            String nombre = producto.getNombre().toLowerCase();
            String descr = producto.getDescripcion().toLowerCase();
            if( nombre.contains(queryTemp) || queryTemp.contains(nombre) || descr.contains(queryTemp) || queryTemp.contains(descr)){
                temp.add(producto);
            }
        }
        return temp;
    }


}
