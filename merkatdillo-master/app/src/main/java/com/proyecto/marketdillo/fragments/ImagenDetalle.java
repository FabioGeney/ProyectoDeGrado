package com.proyecto.marketdillo.fragments;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.proyecto.marketdillo.MainActivity;
import com.proyecto.marketdillo.Mercadillo;
import com.proyecto.marketdillo.R;
import com.proyecto.marketdillo.SingletonMercadillo;
import com.proyecto.marketdillo.SingletonUsuario;
import com.proyecto.marketdillo.VistaProductosMercadillo;
import com.squareup.picasso.Picasso;

public class ImagenDetalle extends AppCompatActivity {

    private TextView descripcion;
    private TextView producto;
    private ImagenCard imagen;
    private TextView precio;
    private ImageView picture;
    private TextView vendedor;
    private Button botonMercadillo;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_detalle);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagen = (ImagenCard) getIntent().getSerializableExtra("imagen");
        String titulo = imagen.getNombre();
        this.setTitle(titulo);

        picture = findViewById(R.id.imageview1);
        producto = findViewById(R.id.producto);
        vendedor = findViewById(R.id.vendedor);
        precio = findViewById(R.id.precio);
        descripcion = findViewById(R.id.descripcion);
        botonMercadillo  = findViewById(R.id.mercadillos);

        precio.setText("Precio: " + imagen.getPrecioCantidad() +" • " + imagen.getUnidad());
        descripcion.setText("Descripción: "+imagen.getDescripcion());
        producto.setText(imagen.getNombre());
        String img = imagen.getImagen();
        Picasso.with(this).load(img).fit().into(picture);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Mercadillo").document(imagen.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Mercadillo mercadillo = document.toObject(Mercadillo.class);
                    SingletonMercadillo singletonMercadillo = SingletonMercadillo.getInstance();
                    singletonMercadillo.setMercadillo(mercadillo);
                    vendedor.setText(mercadillo.getNombre());
                }
            }
        });

        botonMercadillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
                if(singletonUsuario.getUsuario() != null){
                    Intent intent = new Intent(ImagenDetalle.this, VistaProductosMercadillo.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(ImagenDetalle.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
