package com.proyecto.marketdillo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfiguracionFragment extends Fragment {

    private EditText nombre;
    private EditText apellido;
    private TextView correo;
    private EditText celular;
    private EditText documentoidentidad;
    private EditText fechanacimiento;
    private EditText direccion;
    private EditText nommercadillo;
    private EditText tiempoaprox;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private Button guardar;

    public ConfiguracionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_configuracion, container, false);
        nombre = (EditText) root.findViewById(R.id.nnnnombre);
        apellido = (EditText) root.findViewById(R.id.aaaapellido);
        correo = (TextView) root.findViewById(R.id.ccorreoespacio);
        celular = (EditText) root.findViewById(R.id.celuco);
        documentoidentidad = (EditText) root.findViewById(R.id.identitydocument);
        fechanacimiento = (EditText) root.findViewById(R.id.birth);
        direccion = (EditText) root.findViewById(R.id.address);
        nommercadillo = (EditText) root.findViewById(R.id.nommercadillo);
        tiempoaprox = (EditText) root.findViewById(R.id.tiempoaprox);
        initialize();
        cargar();
        ((VistaCampesino) getActivity()).hideFloatingActionButton();
        /*nombre.addTextChangedListener(loginTextWatcher);
        apellido.addTextChangedListener(loginTextWatcher);
        celular.addTextChangedListener(loginTextWatcher);
        documentoidentidad.addTextChangedListener(loginTextWatcher);
        fechanacimiento.addTextChangedListener(loginTextWatcher);
        direccion.addTextChangedListener(loginTextWatcher);
        nommercadillo.addTextChangedListener(loginTextWatcher);
        tiempoaprox.addTextChangedListener(loginTextWatcher);*/

        /*guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/
        return root;
    }

    public void cargar() {
        SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
        Usuario usuario = singletonUsuario.getUsuario();
        nombre.setText(usuario.getNombre());
        apellido.setText(usuario.getApellidos());
        correo.setText(usuario.getEmail());
        celular.setText(usuario.getCelular());
        documentoidentidad.setText(usuario.getDoc_identidad());
        fechanacimiento.setText(usuario.getFecha());
        direccion.setText(usuario.getDireccion());
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Mercadillo").document(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Mercadillo mercadillo = document.toObject(Mercadillo.class);
                    SingletonMercadillo singletonMercadillo = SingletonMercadillo.getInstance();
                    singletonMercadillo.setMercadillo(mercadillo);
                    nommercadillo.setText(mercadillo.getNombre());
                    tiempoaprox.setText(mercadillo.getTiempoEntrega());
                }
            }
        });

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

    /*private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nombre1 = nombre.getText().toString();
            String apellido1 = apellido.getText().toString();
            String celular1 = celular.getText().toString();
            String documentoidentidad1 = documentoidentidad.getText().toString();
            String fechanacimiento1 = fechanacimiento.getText().toString();
            String direccion1 = direccion.getText().toString();
            String nommercadillo1 = nommercadillo.getText().toString();
            String tiempoaprox1 = tiempoaprox.getText().toString();
            guardar.setEnabled(!nombre1.isEmpty() && !apellido1.isEmpty() && !celular1.isEmpty() && !documentoidentidad1.isEmpty() && !fechanacimiento1.isEmpty() && !direccion1.isEmpty() && !nommercadillo1.isEmpty() && !tiempoaprox1.isEmpty());
            if(!nombre1.isEmpty() && !apellido1.isEmpty() && !celular1.isEmpty() && !documentoidentidad1.isEmpty() && !fechanacimiento1.isEmpty() && !direccion1.isEmpty() && !nommercadillo1.isEmpty() && !tiempoaprox1.isEmpty()){
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
    };*/
}
