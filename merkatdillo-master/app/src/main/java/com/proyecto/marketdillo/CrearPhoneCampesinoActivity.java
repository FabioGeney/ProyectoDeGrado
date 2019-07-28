package com.proyecto.marketdillo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CrearPhoneCampesinoActivity extends AppCompatActivity {

    private static final String TAG = "CrearPhoneCampesActivty";
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
    private EditText etmercadillo;
    private EditText ettiempoaprox;
    private String etpassword;
    private Button bnsiguiente;
    private Usuario usuario;
    private SessionManager sessionManager;
    private EditText edtCostoDomii;
    private EditText edtTipo;
    private ArrayList<String> tipos = new ArrayList<>();
    private boolean[] checkedItems = new boolean[]{false, false, false, false, false, false};

    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar calendar = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = calendar.get(Calendar.MONTH);
    final int dia = calendar.get(Calendar.DAY_OF_MONTH);
    final int anio = calendar.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_phone_campesino);

        etnombres = findViewById(R.id.etnombres);
        etapellidos = findViewById(R.id.etapellidos);
        etcelular = findViewById(R.id.etcelular);
        etfechanacimiento = findViewById(R.id.etfechanacimiento);
        etdidentidad = findViewById(R.id.etdidentidad);
        etdireccion = findViewById(R.id.etdireccion);
        etmercadillo = findViewById(R.id.etmercadillo);
        ettiempoaprox = findViewById(R.id.ettiempoaprox);
        bnsiguiente = findViewById(R.id.bnsiguiente);
        edtCostoDomii = findViewById(R.id.edtCostoDomii);
        edtTipo = findViewById(R.id.edtTipo);

        initialize();
        edtTipo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_HOVER_EXIT){
                    getAlert();
                }

                return false;
            }
        });

        etfechanacimiento.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    obtenerFecha();
                }

                return false;
            }
        });

        bnsiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llenandodatos();
                busqueda();
            }
        });

        etnombres.addTextChangedListener(loginTextWatcher);
        etapellidos.addTextChangedListener(loginTextWatcher);
        etcelular.addTextChangedListener(loginTextWatcher);
        etfechanacimiento.addTextChangedListener(loginTextWatcher);
        etdidentidad.addTextChangedListener(loginTextWatcher);
        etdireccion.addTextChangedListener(loginTextWatcher);
        etmercadillo.addTextChangedListener(loginTextWatcher);
        ettiempoaprox.addTextChangedListener(loginTextWatcher);
        edtCostoDomii.addTextChangedListener(loginTextWatcher);
        edtTipo.addTextChangedListener(loginTextWatcher);


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

    private void llenandodatos() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        sessionManager = new SessionManager(this);
        String nombres = etnombres.getText().toString();
        String apellidos = etapellidos.getText().toString();
        String email = etemail;
        String celular = etcelular.getText().toString();
        String fecha = etfechanacimiento.getText().toString();
        String didentidad = etdidentidad.getText().toString();
        String direccion = etdireccion.getText().toString();
        String mercadillos = etmercadillo.getText().toString();
        String tiempoaprox = ettiempoaprox.getText().toString();
        String password = etcelular.getText().toString();
        final int costo = Integer.parseInt(edtCostoDomii.getText().toString());
        final Map<String, Object> user = new HashMap<>();
        final Map<String, Object> mercadillo = new HashMap<>();
        user.put("nombre", nombres);
        user.put("apellidos", apellidos);
        user.put("email", email);
        user.put("celular", celular);
        user.put("fecha", fecha);
        user.put("doc_identidad", didentidad);
        user.put("direccion", direccion);
        user.put("password", password);
        user.put("id", firebaseUser.getUid());
        mercadillo.put("id", firebaseUser.getUid());
        mercadillo.put("nombre", mercadillos);
        mercadillo.put("tiempoEntrega", tiempoaprox);
        mercadillo.put("calificacion", "--");
        mercadillo.put("costoEnvio", costo);
        db.collection("Campesino").document(firebaseUser.getUid()).set(user);
        db.collection("Mercadillo").document(firebaseUser.getUid()).set(mercadillo);
        for(String temp : tipos){
            db.collection("Categorias").document("mercadillos").collection(temp).document(firebaseUser.getUid()).set(mercadillo);
        }
        Toast.makeText(CrearPhoneCampesinoActivity.this, "Cuenta Creada", Toast.LENGTH_SHORT).show();
    }

    private void busqueda() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        db.collection("Campesino").document(firebaseUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            usuario = document.toObject(Usuario.class);
                            usuario.setTipoUsuario("campesino");
                        } else {
                            Toast.makeText(CrearPhoneCampesinoActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                        if (usuario != null && usuario.getTipoUsuario().equals("campesino")) {
                            tokenID("Campesino");
                            Intent i = new Intent(CrearPhoneCampesinoActivity.this, VistaCampesino.class);
                            SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
                            sessionManager.createSession(usuario, usuario.getTipoUsuario());
                            singletonUsuario.setUsuario(usuario);
                            startActivity(i);
                        }
                    }
                });
    }

    private void tokenID(final String coleccion) {
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
            String nombresentrada = etnombres.getText().toString().trim();
            String apellidosentrada = etapellidos.getText().toString().trim();
            String celularentrada = etcelular.getText().toString().trim();
            String fechaentrada = etfechanacimiento.getText().toString().trim();
            String didentidadentrada = etdidentidad.getText().toString().trim();
            String direccionentrada = etdireccion.getText().toString().trim();
            String mercadilloentrada = etmercadillo.getText().toString().trim();
            String tiempoaproxentrada = ettiempoaprox.getText().toString().trim();
            String tipoMercadillo = edtTipo.getText().toString().trim();
            String costoDomi = edtCostoDomii.getText().toString().trim();
            bnsiguiente.setEnabled(!nombresentrada.isEmpty() && !apellidosentrada.isEmpty() && !celularentrada.isEmpty() && !fechaentrada.isEmpty() && !didentidadentrada.isEmpty() && !direccionentrada.isEmpty() && !mercadilloentrada.isEmpty() && !tiempoaproxentrada.isEmpty() && !costoDomi.isEmpty() && !tipoMercadillo.isEmpty());
            if (!nombresentrada.isEmpty() && !apellidosentrada.isEmpty() && !celularentrada.isEmpty() && !fechaentrada.isEmpty() && !didentidadentrada.isEmpty() && !direccionentrada.isEmpty() && !mercadilloentrada.isEmpty() && !tiempoaproxentrada.isEmpty() && !costoDomi.isEmpty() && !tipoMercadillo.isEmpty()) {
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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Por favor termina de registrarte, y oprime en siguiente ", Toast.LENGTH_LONG).show();
    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                etfechanacimiento.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }

    private void getAlert(){
        final String[] listItems = {"Frutas","Vegetales","Legumbres","Cereales", "Tubérculos","Otros"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tipos de productos");


        builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                if(isChecked){
                    tipos.add(listItems[which]);
                    checkedItems[which] = true;

                }else{
                    checkedItems[which] = false;
                    tipos.remove(listItems[which]);
                }

            }
        });

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String stringTipos = "";
                for(String temp : tipos){
                    stringTipos =  temp + ", "+ stringTipos;
                }
                edtTipo.setText(stringTipos);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
