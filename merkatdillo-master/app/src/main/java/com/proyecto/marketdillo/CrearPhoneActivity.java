package com.proyecto.marketdillo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class CrearPhoneActivity extends AppCompatActivity {

    private static final String TAG = "CrearPhoneActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText etnombres;
    private EditText etapellidos;
    private String etemail = "no tiene";
    private EditText etcelular;
    private EditText etfechanacimiento;
    private EditText etdidentidad;
    private EditText etdireccion;
    private Button bnsiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_phone);

        etnombres = findViewById(R.id.etnombres);
        etapellidos = findViewById(R.id.etapellidos);
        etcelular = findViewById(R.id.etcelular);
        etfechanacimiento = findViewById(R.id.etfechanacimiento);
        etdidentidad = findViewById(R.id.etdidentidad);
        etdireccion = findViewById(R.id.etdireccion);
        bnsiguiente = findViewById(R.id.bnsiguiente);

        initialize();

        bnsiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        etnombres.addTextChangedListener(loginTextWatcher);
        etapellidos.addTextChangedListener(loginTextWatcher);
        etcelular.addTextChangedListener(loginTextWatcher);
        etfechanacimiento.addTextChangedListener(loginTextWatcher);
        etdidentidad.addTextChangedListener(loginTextWatcher);
        etdireccion.addTextChangedListener(loginTextWatcher);
    }

    private void initialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Log.w(TAG, "onAuthStateChanged - inició sesión" + firebaseUser.getUid());
                    Log.w(TAG, "onAuthStateChanged - inició sesión" + firebaseUser.getPhoneNumber());
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
            String nombresentrada = etnombres.getText().toString().trim();
            String apellidosentrada = etapellidos.getText().toString().trim();
            String celularentrada = etcelular.getText().toString().trim();
            String fechaentrada = etfechanacimiento.getText().toString().trim();
            String didentidadentrada = etdidentidad.getText().toString().trim();
            String direccionentrada = etdireccion.getText().toString().trim();
            bnsiguiente.setEnabled(!nombresentrada.isEmpty() && !apellidosentrada.isEmpty() && !celularentrada.isEmpty() && !fechaentrada.isEmpty() && !didentidadentrada.isEmpty() && !direccionentrada.isEmpty());
            if (!nombresentrada.isEmpty() && !apellidosentrada.isEmpty() && !celularentrada.isEmpty() && !fechaentrada.isEmpty() && !didentidadentrada.isEmpty() && !direccionentrada.isEmpty()) {
                bnsiguiente.setBackgroundColor(getResources().getColor(R.color.colorPrimary2));
            } else {


            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!bnsiguiente.isEnabled()) {
                bnsiguiente.setBackgroundColor(getResources().getColor(R.color.colorDivider));
            } else {


            }

        }
    };
}
