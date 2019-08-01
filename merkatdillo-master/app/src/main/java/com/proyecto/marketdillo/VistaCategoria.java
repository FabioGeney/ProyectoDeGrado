package com.proyecto.marketdillo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class VistaCategoria extends AppCompatActivity {

    private RecyclerView mRecycler;
    private RecyclerView.Adapter mercadilloAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Mercadillo> mercadillos;
    private DividerItemDecoration dividerItemDecoration;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_categoria);

        //inicializa el toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //obtiene datos que se enviaron en el anterior activity
        tipo = getIntent().getStringExtra("categoria");
        // cambia el nombre del toolbar
        this.setTitle(tipo);
        //busca el objeto en la vista
        mRecycler = findViewById(R.id.mercados);
        layoutManager = new LinearLayoutManager(this);
        dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        mercadillos = getMercadillo();

    }
    //Busca los mercadillos en la base de datos para el tipo seleccionado
    private ArrayList<Mercadillo> getMercadillo(){

        final ArrayList<Mercadillo> mercadillosTemp = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Categorias").document("mercadillos").collection(tipo)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //obtiene los mercadillos de la base de datos y los almacena en mercadillosTemp
                                Mercadillo mercadillo = document.toObject(Mercadillo.class);
                                mercadillo.setId(document.getId());
                                mercadillo.setImagen(R.mipmap.ic_merca_image);
                                mercadillosTemp.add(mercadillo);


                            }
                            // Inicializar el adaptador con la fuente de datos.
                            mercadilloAdapter = new MercadilloAdapter(mercadillos, R.layout.list_item_mercadillo, new MercadilloAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(Mercadillo mercadillo, int posicion) {
                                    Intent intent = new Intent(VistaCategoria.this, VistaProductosMercadillo.class);
                                    //crea el singleotnMercadillo para almacenar en memoria los detos del mercadillo seleccionado por el usuario
                                    SingletonMercadillo singletonMercadillo = SingletonMercadillo.getInstance();
                                    //en caso de que ya haya un mercadillo almacenado será reemplazado por otro seleccionado por el usuario
                                    singletonMercadillo.setMercadillo(mercadillo);

                                    startActivity(intent);
                                }
                            });
                            //setea el recycler con el layoutManager
                            mRecycler.setLayoutManager(layoutManager);
                            //setea el recycler con el adaptador
                            mRecycler.setAdapter(mercadilloAdapter);
                            //agrega divider al recyclerView
                            mRecycler.addItemDecoration(dividerItemDecoration);


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return mercadillosTemp;
    }
}
