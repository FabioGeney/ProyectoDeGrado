package com.proyecto.marketdillo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CrearProducto extends AppCompatActivity {
    private EditText edtNombre;
    private EditText edtDescripcion;
    private EditText edtCantidad;
    private EditText precioCantidad;
    private ImageView imagen;
    private Button guardar;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "CrearProducto";
    private String idCampesino;
    private File f;

    private final int PICTURE_FROM_CAMERA = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("Crear productos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtNombre = findViewById(R.id.edtnombre);
        edtDescripcion = findViewById(R.id.edtdescripcion);
        edtCantidad = findViewById(R.id.edtcantidad);
        precioCantidad = findViewById(R.id.edtprecio);
        guardar = findViewById(R.id.btnguardar);
        imagen = findViewById(R.id.imagen);

        idCampesino = getIntent().getExtras().getString("id");

        initialize();
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarProducto();
            }
        });

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                /*
                camara.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(f));
                camara.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);*/
                startActivity(Intent.createChooser(camara, "Elige la opción"));
            }
        });

        edtNombre.addTextChangedListener(loginTextWatcher);
        edtDescripcion.addTextChangedListener(loginTextWatcher);
        edtCantidad.addTextChangedListener(loginTextWatcher);
        precioCantidad.addTextChangedListener(loginTextWatcher);

    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode) {
            case PICTURE_FROM_CAMERA

                if(resultCode == Activity.RESULT_OK){
                    String result = data.toUri(0);
                    Toast.makeText(this, "Result " +result, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "No seleccionó la imagen, intente de nuevo", Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
        }
    }*/

    private void guardarProducto(){
        String nombre = edtNombre.getText().toString();
        String descripcion = edtDescripcion.getText().toString();
        String cantidad = edtCantidad.getText().toString();
        String cantidadP = precioCantidad.getText().toString();
        final Map<String, Object> producto = new HashMap<>();
        producto.put("nombre",nombre);
        producto.put("descripcion",descripcion);
        producto.put("cantidad",cantidad);
        producto.put("precioCantidad",cantidadP);
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        producto.put("id",idCampesino);
        db.collection("Producto").document().set(producto);
        Toast.makeText(CrearProducto.this, "Producto Guardado", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(CrearProducto.this, VistaCampesino.class);
        startActivity(i);


    }

    private void initialize(){
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.w(TAG, "onAuthStateChanged - inició sesión" + firebaseUser.getUid());
                    Log.w(TAG, "onAuthStateChanged - inició sesión" + firebaseUser.getEmail());
                } else {
                    Log.w(TAG, "onAuthStateChanged - cerró sesión");
                }
            }
        };
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nombre = edtNombre.getText().toString();
            String descripcion = edtDescripcion.getText().toString();
            String cantidad = edtCantidad.getText().toString();
            String cantidadP = precioCantidad.getText().toString();
            guardar.setEnabled(!nombre.isEmpty() && !descripcion.isEmpty() && !cantidad.isEmpty() && !cantidadP.isEmpty());
            if(!nombre.isEmpty() && !descripcion.isEmpty() && !cantidad.isEmpty() && !cantidadP.isEmpty()){
                guardar.setBackgroundColor(getResources().getColor(R.color.colorPrimary2));
            }else {
                guardar.setBackgroundColor(getResources().getColor(R.color.colorDivider));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!guardar.isEnabled()){
                guardar.setBackgroundColor(getResources().getColor(R.color.colorDivider));
            }else {
                guardar.setBackgroundColor(getResources().getColor(R.color.colorPrimary2));
            }
        }
    };
}
