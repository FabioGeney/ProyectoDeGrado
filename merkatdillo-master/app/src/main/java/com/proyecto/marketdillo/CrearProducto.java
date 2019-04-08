package com.proyecto.marketdillo;

import android.content.Intent;
import android.support.annotation.NonNull;
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

        initialize();
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarProducto();
            }
        });

        edtNombre.addTextChangedListener(loginTextWatcher);
        edtDescripcion.addTextChangedListener(loginTextWatcher);
        edtCantidad.addTextChangedListener(loginTextWatcher);
        precioCantidad.addTextChangedListener(loginTextWatcher);





    }
    
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
        producto.put("id",firebaseUser.getUid());
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
