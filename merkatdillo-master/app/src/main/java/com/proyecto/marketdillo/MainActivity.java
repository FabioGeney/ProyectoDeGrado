package com.proyecto.marketdillo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;

import static android.support.constraint.Constraints.TAG;

/*Se le da el nombre de main activity porque es donde se inicia sesion*/
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private Button btninicio;
    private TextView txcrearcuenta;
    private TextView txproductor;
    private EditText edtemail;
    private EditText edtpassword;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Primero, lo primero");


        btninicio = findViewById(R.id.btninicio);
        txcrearcuenta = findViewById(R.id.txcrearcuenta);
        txproductor = findViewById(R.id.txproductor);
        edtemail = findViewById(R.id.edtemail);
        edtpassword = findViewById(R.id.edtpassword);

        initialize();
        /*el boton y el clicklistener, q al clickear llama al metodo iniciar sesion, con el email y la contraseña*/
        btninicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicio(edtemail.getText().toString(), edtpassword.getText().toString());

            }
        });
        /*al oprimir el textview lo envia al activity de crear cuenta del comprador*/
        txcrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( MainActivity.this, CrearCuentaActivity.class);
                startActivity(i);
            }
        });
        /*al oprimir el textview lo envia al activity de crear cuenta del campesino*/
        txproductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( MainActivity.this, CrearCampesinoActivity.class);
                startActivity(i);
            }
        });

        edtemail.addTextChangedListener(loginTextWatcher);
        edtpassword.addTextChangedListener(loginTextWatcher);
        /*se agrega el metodo addTextChangedListener y de parametro el loginTextWatcher para que haga
         * realidad el codigo puesto mas abajo */

    }
    /*se inicializa la instancia de firebase para autenticar*/
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
    /*metodo para iniciar sesion con los parametros de email y password, autentica con firebase y revisa si
    * existe tal cuenta en la base de datos*/
    private void inicio(final String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Inicio exitoso", Toast.LENGTH_SHORT).show();
                    getTipo(email);
                } else{
                    Toast.makeText(MainActivity.this, "Error iniciando cuenta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /*agregar el listener*/
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
    /*quitar el listener*/
    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
    /*Codigo para habilitar el boton de inicio, solo se habilita hasta que los dos textview esten llenos
     * y cambia de color. Se deshabilita cuando no hay nada escrito en los dos textview o solo en un textview */
    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String correoentrada = edtemail.getText().toString().trim();
            String claveentrada = edtpassword.getText().toString().trim();
            btninicio.setEnabled(!correoentrada.isEmpty() && !claveentrada.isEmpty());
            if(!correoentrada.isEmpty() && !claveentrada.isEmpty()){
                btninicio.setBackgroundColor(getResources().getColor(R.color.colorPrimary2));
            }else {
                btninicio.setBackgroundColor(getResources().getColor(R.color.colorDivider));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!btninicio.isEnabled()){
                btninicio.setBackgroundColor(getResources().getColor(R.color.colorDivider));
            }else {
                btninicio.setBackgroundColor(getResources().getColor(R.color.colorPrimary2));
            }


        }
    };

    private void getTipo( String correo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Consumidor").whereEqualTo("email",correo)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               usuario = document.toObject(Usuario.class);
                               usuario.setTipoUsuario("consumidor");
                            }

                        }else{
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                        if(usuario!=null && usuario.getTipoUsuario().equals("consumidor")){
                            Intent intent = new Intent(MainActivity.this, VistaUsuarios.class);
                            SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
                            singletonUsuario.setUsuario(usuario);
                            startActivity(intent);

                        }
                    }
        });

        db.collection("Campesino").whereEqualTo("email",correo)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                usuario = document.toObject(Usuario.class);
                                usuario.setTipoUsuario("campesino");
                            }

                        }else{
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                        if(usuario!=null && usuario.getTipoUsuario().equals("campesino")){
                            Intent intent = new Intent(MainActivity.this, VistaCampesino.class);
                            SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
                            singletonUsuario.setUsuario(usuario);
                            startActivity(intent);
                        }
                    }
                });
    }
}
