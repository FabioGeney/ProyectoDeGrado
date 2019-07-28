package com.proyecto.marketdillo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.proyecto.marketdillo.fragments.ImagenAdapter;
import com.proyecto.marketdillo.fragments.ImagenCard;
import com.proyecto.marketdillo.fragments.ImagenDetalle;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class Busqueda extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter productoAdapter;
    private RecyclerView.Adapter mercadilloAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Producto> productos;
    private List<Mercadillo> mercadillos;
    private Button btnMercadillos;
    private Button btnProductos;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        query = getIntent().getStringExtra("query");
        this.setTitle(query);

        mRecyclerView = findViewById(R.id.recycler_result);
        btnMercadillos = findViewById(R.id.mercadillos);
        btnProductos = findViewById(R.id.productos);

        layoutManager = new LinearLayoutManager(this);
        mercadillos = getMercadillos();

        btnMercadillos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMercadillos.setBackgroundResource(R.drawable.boton_busqueda);
                btnMercadillos.setTextColor(getResources().getColor(R.color.blanco));
                btnProductos.setBackgroundResource(R.drawable.boton_espera);
                btnProductos.setTextColor(getResources().getColor(R.color.colorPrimary2));
                mercadillos = getMercadillos();
            }
        });

        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMercadillos.setBackgroundResource(R.drawable.boton_espera);
                btnMercadillos.setTextColor(getResources().getColor(R.color.colorPrimary2));
                btnProductos.setBackgroundResource(R.drawable.boton_busqueda);
                btnProductos.setTextColor(getResources().getColor(R.color.blanco));
                productos =getProductos();
            }
        });

    }

    public List<Mercadillo> getMercadillos() {
        final String queryTemp = query.toLowerCase();
        final ArrayList<Mercadillo> mercadillos = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Mercadillo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Mercadillo mercadillo = document.toObject(Mercadillo.class);
                                mercadillo.setId(document.getId());
                                mercadillo.setImagen(R.mipmap.ic_merca_image);

                                String temp = mercadillo.getNombre().toLowerCase();
                                if(temp.contains(queryTemp) || queryTemp.contains(temp)){
                                    mercadillos.add(mercadillo);
                                }



                            }
                            // Inicializar el adaptador con la fuente de datos.
                            mercadilloAdapter = new MercadilloAdapter(mercadillos, R.layout.list_item_mercadillo, new MercadilloAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(Mercadillo mercadillo, int posicion) {
                                    Intent intent = new Intent(Busqueda.this, VistaProductosMercadillo.class);
                                    //crea el singleotnMercadillo para almacenar en memoria los detos del mercadillo seleccionado por el usuario
                                    SingletonMercadillo singletonMercadillo = SingletonMercadillo.getInstance();
                                    //en caso de que ya haya un mercadillo almacenado será reemplazado por otro seleccionado por el usuario
                                    singletonMercadillo.setMercadillo(mercadillo);
                                    Toast.makeText(Busqueda.this, mercadillo.getId(), Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                            });
                            //Relacionando la lista con el adaptador
                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setAdapter(mercadilloAdapter);



                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return mercadillos;
    }

    public ArrayList<Producto> getProductos(){
        final String queryTemp = query.toLowerCase();
        final ArrayList<Producto> queryResult = new ArrayList<>();
        //imagenes.add(new ImagenCard(R.drawable.limone, "Limon", "2000", "Finca Fabio"));
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Mercadillo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                db.collection("Mercadillo").document(document.getId()).collection("Productos").
                                        get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for(QueryDocumentSnapshot document : task.getResult()){
                                                //si encuentra almacena la indormacion de la base de datos en el objeto
                                                Producto producto = document.toObject(Producto.class);
                                                producto.setIdDocument(document.getId());
                                                //agrega porducto de la base de dato al arreglo
                                                String temp = producto.getNombre().toLowerCase();
                                                if(temp.contains(queryTemp) || queryTemp.contains(temp)){
                                                    queryResult.add(producto);
                                                }

                                            }
                                            /*
                                            // Inicializar el adaptador con la fuente de datos.
                                            productoAdapter = new ProductoAdapter(productos, R.layout.list_item_productos, new ProductoAdapter.OnItemClickListener() {
                                                @Override
                                                public void OnItemClick(Producto producto, int posicion) {
                                                    //al hacer click en un mercadillo se va a otro activity
                                                    Intent intent = new Intent(Busqueda.this, VistaProducto.class);
                                                    Toast.makeText(Busqueda.this,producto.getImagen(), Toast.LENGTH_SHORT).show();
                                                    //envia informacion del mercadillo al otro activity
                                                    intent.putExtra("producto", producto);
                                                    intent.putExtra("index", posicion);
                                                    startActivity(intent);
                                                }
                                            });
                                            */
                                            //Relacionando la lista con el adaptador
                                            mRecyclerView.setLayoutManager(layoutManager);
                                            mRecyclerView.setAdapter(productoAdapter);
                                        }else {

                                        }
                                    }
                                });

                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return queryResult;
    }
}
