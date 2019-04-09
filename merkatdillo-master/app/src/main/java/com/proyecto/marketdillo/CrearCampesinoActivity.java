package com.proyecto.marketdillo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CrearCampesinoActivity extends AppCompatActivity {

    private static final String TAG = "CrearCampesinoActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText edtnombres;
    private EditText edtapellidos;
    private EditText edtemail;
    private EditText edtcelular;
    private EditText edtfechanacimiento;
    private EditText edtdidentidad;
    private EditText edtdireccion;
    private EditText edtmercadillo;
    private EditText edttiempoaprox;
    private Button btncrearcuenta;
    private EditText edtpassword;
    private EditText edtpassword2;

    private TextInputLayout textInputLayoutNombres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_campesino);
        Toolbar toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Comencemos");

        edtnombres =findViewById(R.id.edtnombres);
        edtapellidos = findViewById(R.id.edtapellidos);
        edtemail = findViewById(R.id.edtemail);
        edtcelular = findViewById(R.id.edtcelular);
        edtfechanacimiento = findViewById(R.id.edtfechanacimiento);
        edtdidentidad = findViewById(R.id.edtdidentidad);
        edtdireccion = findViewById(R.id.edtdireccion);
        edtmercadillo = findViewById(R.id.edtmercadillo);
        edttiempoaprox = findViewById(R.id.edttiempoaprox);
        btncrearcuenta = findViewById(R.id.btncrearcuenta);
        edtpassword = findViewById(R.id.edtpassword);
        edtpassword2 = findViewById(R.id.edtpassword2);

        textInputLayoutNombres = findViewById(R.id.textInputLayoutNombres);


        initialize();

        btncrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuentanueva();
            }
        });

        edtnombres.addTextChangedListener(loginTextWatcher);
        edtapellidos.addTextChangedListener(loginTextWatcher);
        edtemail.addTextChangedListener(loginTextWatcher);
        edtcelular.addTextChangedListener(loginTextWatcher);
        edtfechanacimiento.addTextChangedListener(loginTextWatcher);
        edtdidentidad.addTextChangedListener(loginTextWatcher);
        edtdireccion.addTextChangedListener(loginTextWatcher);
        edtmercadillo.addTextChangedListener(loginTextWatcher);
        edttiempoaprox.addTextChangedListener(loginTextWatcher);
        edtpassword.addTextChangedListener(loginTextWatcher);
        edtpassword2.addTextChangedListener(loginTextWatcher);


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

    private void cuentanueva(){
        String nombres = edtnombres.getText().toString();
        String apellidos = edtapellidos.getText().toString();
        String email = edtemail.getText().toString();
        String celular = edtcelular.getText().toString();
        String fecha = edtfechanacimiento.getText().toString();
        String didentidad = edtdidentidad.getText().toString();
        String direccion = edtdireccion.getText().toString();
        String mercadillo = edtmercadillo.getText().toString();
        String tiempoaprox = edttiempoaprox.getText().toString();
        String password = edtpassword.getText().toString();
        String password2 = edtpassword2.getText().toString();
        if(password.equals(password2)){
            final Map<String, Object> user = new HashMap<>();
            final Map<String, Object> mercadilloCamp = new HashMap<>();
            user.put("nombre",nombres);
            user.put("apellidos",apellidos);
            user.put("email",email);
            user.put("celular",celular);
            user.put("fecha",fecha);
            user.put("doc.identidad",didentidad);
            user.put("direccion",direccion);
            mercadilloCamp.put("mercadillo",mercadillo);
            mercadilloCamp.put("envio",tiempoaprox);
            user.put("password",password);
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        user.put("id",firebaseUser.getUid());
                        mercadilloCamp.put("id",firebaseUser.getUid());
                        db.collection("Campesino").document().set(user);

                        Toast.makeText(CrearCampesinoActivity.this, "Cuenta Creada", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(CrearCampesinoActivity.this, MainActivity.class);
                        startActivity(i);
                    } else{
                        Toast.makeText(CrearCampesinoActivity.this, "Error creando cuenta", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(CrearCampesinoActivity.this, "Las contraseñas son diferentes, por favor vuelve a intentarlo", Toast.LENGTH_SHORT).show();
        }


    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String correoentrada = edtemail.getText().toString().trim();
            String claveentrada = edtpassword.getText().toString().trim();
            String clave2entrada = edtpassword2.getText().toString().trim();
            String nombresentrada = edtnombres.getText().toString().trim();
            String apellidosentrada = edtapellidos.getText().toString().trim();
            String celularentrada = edtcelular.getText().toString().trim();
            String fechaentrada = edtfechanacimiento.getText().toString().trim();
            String didentidadentrada = edtdidentidad.getText().toString().trim();
            String direccionentrada = edtdireccion.getText().toString().trim();
            String mercadilloentrada = edtmercadillo.getText().toString().trim();
            String tiempoaproxentrada = edttiempoaprox.getText().toString().trim();
            btncrearcuenta.setEnabled(!correoentrada.isEmpty() && !claveentrada.isEmpty() && !clave2entrada.isEmpty() && !nombresentrada.isEmpty() && !apellidosentrada.isEmpty() && !celularentrada.isEmpty() && !fechaentrada.isEmpty() && !didentidadentrada.isEmpty() && !direccionentrada.isEmpty() && !mercadilloentrada.isEmpty() && !tiempoaproxentrada.isEmpty());
            if(!correoentrada.isEmpty() && !claveentrada.isEmpty() && !clave2entrada.isEmpty() && !nombresentrada.isEmpty() && !apellidosentrada.isEmpty() && !celularentrada.isEmpty() && !fechaentrada.isEmpty() && !didentidadentrada.isEmpty() && !direccionentrada.isEmpty() && !mercadilloentrada.isEmpty() && !tiempoaproxentrada.isEmpty()){
                btncrearcuenta.setBackgroundColor(getResources().getColor(R.color.colorPrimary2));
            }else {


            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!btncrearcuenta.isEnabled()){
                btncrearcuenta.setBackgroundColor(getResources().getColor(R.color.colorDivider));
            }else {


            }

        }
    };


}
