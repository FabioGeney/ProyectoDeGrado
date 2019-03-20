package com.proyecto.marketdillo;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class CrearCuentaActivity extends AppCompatActivity {

    private static final String TAG = "CrearCuentaActivity";
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
    private Button btncrearcuenta;
    private EditText edtpassword;
    private EditText edtpassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
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
        btncrearcuenta = findViewById(R.id.btncrearcuenta);
        edtpassword = findViewById(R.id.edtpassword);
        edtpassword2 = findViewById(R.id.edtpassword2);

        initialize();

        btncrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuentanueva(edtemail.getText().toString(), edtpassword.getText().toString());
            }
        });

        edtemail.addTextChangedListener(loginTextWatcher);
        edtpassword.addTextChangedListener(loginTextWatcher);





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

    private void cuentanueva(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CrearCuentaActivity.this, "Cuenta Creada", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(CrearCuentaActivity.this, "Error creando cuenta", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            btncrearcuenta.setEnabled(!correoentrada.isEmpty() && !claveentrada.isEmpty() && !clave2entrada.isEmpty() && !nombresentrada.isEmpty() && !apellidosentrada.isEmpty() && !celularentrada.isEmpty() && !fechaentrada.isEmpty() && !didentidadentrada.isEmpty() && !direccionentrada.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}
