package com.proyecto.marketdillo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*clase usada para crear la cuenta de un campesino*/
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
    private EditText edtpassword;
    private EditText edtpassword2;
    private Button btncrearcuenta;
    private EditText edtCostoDomii;
    private EditText edtTipo;

    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar calendar = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = calendar.get(Calendar.MONTH);
    final int dia = calendar.get(Calendar.DAY_OF_MONTH);
    final int anio = calendar.get(Calendar.YEAR);

    private TextInputLayout textInputLayoutNombres;
    private TextInputLayout textInputFecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_campesino);
        Toolbar toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Registro");

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
        textInputFecha=findViewById(R.id.textInputLayoutFecha);
        textInputLayoutNombres = findViewById(R.id.textInputLayoutNombres);
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

        edtfechanacimiento.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    obtenerFecha();
                }

                return false;
            }
        });

        /*boton para crear cuenta, llama al metodo cuentanueva*/
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
    /*inicializa la instancia de autenticacion de firebase*/
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
    /*Se llena el formulario con los campos pedidos, luego se agregan y se llama a un hashmap con un llave valor, que en
     * la base de datos se crea con la informacion ingresada. Al crearse la cuenta, ya esta disponible poder acceder a la app*/
    private void cuentanueva(){
        String nombres = edtnombres.getText().toString();
        String apellidos = edtapellidos.getText().toString();
        String email = edtemail.getText().toString();
        String celular = edtcelular.getText().toString();
        String fecha = edtfechanacimiento.getText().toString();
        final String didentidad = edtdidentidad.getText().toString();
        String direccion = edtdireccion.getText().toString();
        final String mercadillos = edtmercadillo.getText().toString();
        final String tiempoaprox = edttiempoaprox.getText().toString();
        String password = edtpassword.getText().toString();
        String password2 = edtpassword2.getText().toString();
        if(password.equals(password2)){
            final Map<String, Object> user = new HashMap<>();
            final Map<String, Object> mercadillo = new HashMap<>();
            user.put("nombre",nombres);
            user.put("apellidos",apellidos);
            user.put("email",email);
            user.put("celular",celular);
            user.put("fecha",fecha);
            user.put("doc_identidad",didentidad);
            user.put("direccion",direccion);
            user.put("password",password);
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                        user.put("id",firebaseUser.getUid());
                        mercadillo.put("id",firebaseUser.getUid());
                        mercadillo.put("nombre",mercadillos);
                        mercadillo.put("tiempoEntrega", tiempoaprox);
                        mercadillo.put("calificacion", "--");
                        db.collection("Campesino").document(firebaseUser.getUid()).set(user);
                        db.collection("Mercadillo").document(firebaseUser.getUid()).set(mercadillo);
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

    /*Habilita el boton de crear cuenta hasta que todas los campos esten llenos y las contraseñas sean iguales, despues de que se habilite
     * ahi si podra clickear el boton de crear cuenta*/
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
                edtfechanacimiento.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


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

        boolean[] checkedItems = new boolean[]{true, false, true, false, true, false};
        builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                Toast.makeText(CrearCampesinoActivity.this, "Position: " + which + " Value: " + listItems[which] + " State: " + (isChecked ? "checked" : "unchecked"), Toast.LENGTH_LONG).show();
            }
        });

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
