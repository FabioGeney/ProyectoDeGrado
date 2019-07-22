package com.proyecto.marketdillo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.support.constraint.Constraints.TAG;

public class PhoneActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText numerocelular;
    private EditText codigoingresar;
    private Button enviarsms;
    private Button ingresarsms;
    private String mVerificationId;
    private String phoneNumber;
    private Usuario usuario;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Ingresa con tu teléfono");

        numerocelular = findViewById(R.id.numerocelular);
        codigoingresar = findViewById(R.id.codigoingresar);
        enviarsms = findViewById(R.id.enviarsms);
        ingresarsms = findViewById(R.id.ingresarsms);
        initialize();

        enviarsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviar();
            }
        });

        ingresarsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar();
            }
        });

        numerocelular.addTextChangedListener(loginTextWatcher);
        codigoingresar.addTextChangedListener(loginTextWatcher);

    }

    private void initialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Log.w(TAG, "onAuthStateChanged - inició sesión" + firebaseUser.getUid());
                } else {
                    Log.w(TAG, "onAuthStateChanged - cerró sesión");
                }
            }
        };
    }

    private void enviar() {
        phoneNumber = numerocelular.getText().toString();
        if (TextUtils.isEmpty(phoneNumber))
            return;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, 60, TimeUnit.SECONDS, PhoneActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
                        //singInWithCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(PhoneActivity.this, "Error en la verificación " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verificationId, forceResendingToken);
                        mVerificationId = verificationId;
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(String verificationId) {
                        super.onCodeAutoRetrievalTimeOut(verificationId);
                        Toast.makeText(PhoneActivity.this, "Se acabó el tiempo de espera: " + verificationId, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void singInWithCredential(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    sessionManager = new SessionManager(PhoneActivity.this);
                    db.collection("Consumidor").whereEqualTo("celular",phoneNumber)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            usuario = document.toObject(Usuario.class);
                                            usuario.setTipoUsuario("consumidor");
                                        }

                                    } else {
                                        Toast.makeText(PhoneActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                    if (usuario != null && usuario.getTipoUsuario().equals("consumidor")) {
                                        tokenID("Consumidor");
                                        Intent intent = new Intent(PhoneActivity.this, VistaUsuarios.class);
                                        SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
                                        singletonUsuario.setUsuario(usuario);
                                        sessionManager.createSession(usuario, usuario.getTipoUsuario());
                                        startActivity(intent);

                                    }
                                }
                    });

                    db.collection("Campesino").whereEqualTo("celular",phoneNumber)
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
                                        Toast.makeText(PhoneActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                    if(usuario!=null && usuario.getTipoUsuario().equals("campesino")){
                                        tokenID("Campesino");
                                        Intent intent = new Intent(PhoneActivity.this, VistaCampesino.class);
                                        SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
                                        sessionManager.createSession(usuario, usuario.getTipoUsuario());
                                        singletonUsuario.setUsuario(usuario);
                                        startActivity(intent);
                                    }
                                }
                    });
                    if(usuario != null){
                        final CharSequence[] itemss = {"Productor","Comprador"};
                        AlertDialog.Builder alert = new AlertDialog.Builder(PhoneActivity.this);
                        alert.setTitle("Bienvenido, ¿Eres Productor o Comprador?");
                        alert.setItems(itemss, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(itemss[which].equals("Productor")){
                                    Intent iproductor = new Intent(PhoneActivity.this, CrearPhoneCampesinoActivity.class);
                                    startActivity(iproductor);
                                } else if(itemss[which].equals("Comprador")){
                                    Intent icomprador = new Intent(PhoneActivity.this, CrearPhoneActivity.class);
                                    startActivity(icomprador);
                                }else {
                                    dialog.dismiss();
                                }
                            }
                        });
                        alert.show();
                    }

                } else {
                    Toast.makeText(PhoneActivity.this, "Error con la credencial" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ingresar() {
        String code = codigoingresar.getText().toString();
        if(TextUtils.isEmpty(code))
            return;

        singInWithCredential(PhoneAuthProvider.getCredential(mVerificationId, code));

    }

    private void tokenID(final String coleccion){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String token_id = FirebaseInstanceId.getInstance().getToken();
        Map<String, Object> token = new HashMap<>();
        token.put("token_id", token_id);
        db.collection(coleccion).document(firebaseAuth.getUid()).update(token);
        firebaseDatabase.getReference(firebaseAuth.getUid()).child("TokenId").updateChildren(token);

    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String sms = numerocelular.getText().toString();
            String codigo = codigoingresar.getText().toString();
            enviarsms.setEnabled(!sms.isEmpty());
            if(!sms.isEmpty()){
                enviarsms.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_button2));
            }else {
                enviarsms.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_button));
            }

            ingresarsms.setEnabled(!codigo.isEmpty());
            if(!codigo.isEmpty()){
                ingresarsms.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_button2));
            }else {
                ingresarsms.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_button));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!enviarsms.isEnabled()){
                enviarsms.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_button));
            }else {
                enviarsms.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_button2));
            }

            if(!ingresarsms.isEnabled()){
                ingresarsms.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_button));
            }else {
                ingresarsms.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_button2));
            }

        }
    };

}
