package com.proyecto.marketdillo;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class ConfiguracionUsuarioFragment extends Fragment {

    private EditText nombre;
    private EditText apellido;
    private TextView correo;
    private TextView celular;
    private EditText documentoidentidad;
    private EditText fechanacimiento;
    private EditText direccion;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private Button guardar;
    private Button cerrar;
    private SessionManager sessionManager;

    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar calendar = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = calendar.get(Calendar.MONTH);
    final int dia = calendar.get(Calendar.DAY_OF_MONTH);
    final int anio = calendar.get(Calendar.YEAR);


    public ConfiguracionUsuarioFragment() {
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
        View root = inflater.inflate(R.layout.fragment_configuracion_usuario, container, false);
        nombre = (EditText) root.findViewById(R.id.nnnnombre);
        apellido = (EditText) root.findViewById(R.id.aaaapellido);
        correo = (TextView) root.findViewById(R.id.correo);
        celular = (TextView) root.findViewById(R.id.celuco);
        documentoidentidad = (EditText) root.findViewById(R.id.identitydocument);
        fechanacimiento = (EditText) root.findViewById(R.id.birth);
        direccion = (EditText) root.findViewById(R.id.address);
        guardar = (Button) root.findViewById(R.id.guardaarr);
        cerrar = (Button) root.findViewById(R.id.cerrar);
        initialize();
        cargar();
        nombre.addTextChangedListener(loginTextWatcher);
        apellido.addTextChangedListener(loginTextWatcher);
        celular.addTextChangedListener(loginTextWatcher);
        documentoidentidad.addTextChangedListener(loginTextWatcher);
        fechanacimiento.addTextChangedListener(loginTextWatcher);
        direccion.addTextChangedListener(loginTextWatcher);

        fechanacimiento.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    obtenerFecha();
                }

                return false;
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarPerfil();
            }
        });

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] item = {"Si", "No"};
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("¿Esta seguro de querer salir?");
                alert.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (item[which].equals("Si")) {
                            FirebaseAuth.getInstance().signOut();
                            sessionManager = new SessionManager(getActivity());
                            sessionManager.logout();
                        } else if (item[which].equals("No")) {
                            dialog.dismiss();
                        }
                    }
                });
                alert.show();
            }
        });

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
        Toast.makeText(getContext(), usuario.getFecha(), Toast.LENGTH_SHORT).show();
    }

    public void editarPerfil(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DocumentReference editarperf = db.collection("Consumidor").document(firebaseUser.getUid());
        //obtiene la informacion actual del usuario
        SingletonUsuario singletonUsuario =SingletonUsuario.getInstance();
        Usuario usuario = singletonUsuario.getUsuario();
        //obtiene datos de los edtitext
        String nombre1 = nombre.getText().toString();
        String apellido1 = apellido.getText().toString();
        String documentoidentidad1 = documentoidentidad.getText().toString();
        String fechanacimiento1 = fechanacimiento.getText().toString();
        String direccion1 = direccion.getText().toString();
        //setea usuario
        usuario.setNombre(nombre1);
        usuario.setApellidos(apellido1);
        usuario.setDoc_identidad(documentoidentidad1);
        usuario.setFecha(fechanacimiento1);
        usuario.setDireccion(direccion1);
        //modifica usuario en sessionManager y singlton
        SessionManager sessionManager = new SessionManager(getContext());
        sessionManager.actualizaUsuario(usuario, usuario.getTipoUsuario());
        singletonUsuario.setUsuario(usuario);
        final Map<String, Object> updat = new HashMap<>();

        updat.put("nombre", nombre1);
        updat.put("apellidos", apellido1);
        updat.put("doc_identidad", documentoidentidad1);
        updat.put("fecha", fechanacimiento1);
        updat.put("direccion", direccion1);

        editarperf.update(updat);

        Toast.makeText(getContext(), "Perfil Actualizado", Toast.LENGTH_SHORT).show();

    }

    private void initialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
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
            String nombre1 = nombre.getText().toString();
            String apellido1 = apellido.getText().toString();
            String celular1 = celular.getText().toString();
            String documentoidentidad1 = documentoidentidad.getText().toString();
            String fechanacimiento1 = fechanacimiento.getText().toString();
            String direccion1 = direccion.getText().toString();
            guardar.setEnabled(!nombre1.isEmpty() && !apellido1.isEmpty() && !celular1.isEmpty() && !documentoidentidad1.isEmpty() && !fechanacimiento1.isEmpty() && !direccion1.isEmpty());
            if (!nombre1.isEmpty() && !apellido1.isEmpty() && !celular1.isEmpty() && !documentoidentidad1.isEmpty() && !fechanacimiento1.isEmpty() && !direccion1.isEmpty()) {
                guardar.setBackgroundDrawable(getResources().getDrawable(R.drawable.save_button2));
            } else {
                guardar.setBackgroundDrawable(getResources().getDrawable(R.drawable.save_button));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!guardar.isEnabled()) {
                guardar.setBackgroundDrawable(getResources().getDrawable(R.drawable.save_button));
            } else {
                guardar.setBackgroundDrawable(getResources().getDrawable(R.drawable.save_button2));
            }
        }
    };
    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                fechanacimiento.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }

}
