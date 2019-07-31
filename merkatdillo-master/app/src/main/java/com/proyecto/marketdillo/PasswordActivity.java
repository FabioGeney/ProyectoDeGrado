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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.support.constraint.Constraints.TAG;

public class PasswordActivity extends AppCompatActivity {

    private EditText edtemail;
    private Button enviarcorreo;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Te ayudamos a recuperar tu contraseña");

        edtemail = findViewById(R.id.edtemail);
        enviarcorreo = findViewById(R.id.enviarcorreo);
        initialize();

        enviarcorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = edtemail.getText().toString().trim();
                if (useremail.isEmpty()) {
                    Toast.makeText(PasswordActivity.this, "Ingresa tu correo", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PasswordActivity.this, "Te hemos enviado un correo", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(PasswordActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(PasswordActivity.this, "Error enviando correo", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }


            }
        });

        edtemail.addTextChangedListener(loginTextWatcher);
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

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = edtemail.getText().toString();

            enviarcorreo.setEnabled(!email.isEmpty());
            if (!email.isEmpty()) {
                enviarcorreo.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_button2));
            } else {
                enviarcorreo.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_button));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!enviarcorreo.isEnabled()) {
                enviarcorreo.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_button));
            } else {
                enviarcorreo.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_button2));
            }

        }
    };

}
