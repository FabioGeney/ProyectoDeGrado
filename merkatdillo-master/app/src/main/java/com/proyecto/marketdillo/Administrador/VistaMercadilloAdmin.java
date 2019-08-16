package com.proyecto.marketdillo.Administrador;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.proyecto.marketdillo.Chat;
import com.proyecto.marketdillo.Mercadillo;
import com.proyecto.marketdillo.Producto;
import com.proyecto.marketdillo.ProductoAdapter;
import com.proyecto.marketdillo.R;
import com.proyecto.marketdillo.SingletonMercadillo;
import com.proyecto.marketdillo.VistaCanasta;
import com.proyecto.marketdillo.VistaProducto;
import com.proyecto.marketdillo.VistaProductosMercadillo;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class VistaMercadilloAdmin extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter productoAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Producto> productos;
    private Mercadillo mercadillo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_mercadillo_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Llama al singletonMercadillo para obtener los datos almacenados
        SingletonMercadillo singletonMercadillo = SingletonMercadillo.getInstance();
        //almacena los datos del singleton en la variable mercadillo
        mercadillo = singletonMercadillo.getMercadillo();
        //cambia titulo del activity, con datos del MercadillsoFragment
        String nombreMercadillo = mercadillo.getNombre();
        this.setTitle(nombreMercadillo);






        productos = getProductos();
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView = findViewById(R.id.listaProductos);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(productoAdapter);
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
                            productoAdapter = new ProductosAdminAdapter(productos, R.layout.list_producto_admin, new ProductosAdminAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(Producto producto, int posicion) {
                                   /* //al hacer click en un mercadillo se va a otro activity
                                    Intent intent = new Intent(VistaMercadilloAdmin.this, VistaProducto.class);
                                    Toast.makeText(VistaMercadilloAdmin.this,producto.getImagen(), Toast.LENGTH_SHORT).show();
                                    //envia informacion del mercadillo al otro activity
                                    intent.putExtra("producto", producto);
                                    intent.putExtra("index", posicion);
                                    startActivity(intent);
                                    */
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
