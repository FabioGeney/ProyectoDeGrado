package com.proyecto.marketdillo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CrearProducto extends AppCompatActivity {
    private EditText edtNombre;
    private EditText edtDescripcion;
    private EditText edtCantidad;
    private EditText precioCantidad;
    private ImageView imagen;
    private Button guardar;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "CrearProducto";
    private String idCampesino;
    private File f;
    private String eleccionusuario;
    private int REQUEST_CAMERA = 0;
    private int SELECT_FILE = 1;
    private  Uri mImagenUri;
    private StorageReference hStorageRef;
    private DatabaseReference hDatabaseRef;


    private final int PICTURE_FROM_CAMERA = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("Crear productos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtNombre = findViewById(R.id.edtnombre);
        edtDescripcion = findViewById(R.id.edtdescripcion);
        edtCantidad = findViewById(R.id.edtcantidad);
        precioCantidad = findViewById(R.id.edtprecio);
        guardar = findViewById(R.id.btnguardar);
        imagen = findViewById(R.id.imagen);

        hStorageRef = FirebaseStorage.getInstance().getReference("Subidas");
        hDatabaseRef = FirebaseDatabase.getInstance().getReference();

        idCampesino = getIntent().getExtras().getString("id");

        initialize();
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarProducto();
            }
        });

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarImagen();

                //Intent camara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivity(Intent.createChooser(camara, "Elige la opción"));
                /*
                camara.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(f));
                camara.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);*/

            }
        });

        edtNombre.addTextChangedListener(loginTextWatcher);
        edtDescripcion.addTextChangedListener(loginTextWatcher);
        edtCantidad.addTextChangedListener(loginTextWatcher);
        precioCantidad.addTextChangedListener(loginTextWatcher);

    }

    private void seleccionarImagen(){

        final CharSequence[] items = {"Tomar foto", "Galería", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Agrega Foto");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(CrearProducto.this);

                if (items[item].equals("Tomar foto")) {
                    eleccionusuario = "Tomar foto";

                    if(result) {
                        camaraIntent();
                    } else
                        Toast.makeText(CrearProducto.this, "No se pudo, intente de nuevo", Toast.LENGTH_SHORT).show();
                } else if (items[item].equals("Galería")){
                    eleccionusuario = "Galería";

                    if(result) {
                        galeriaIntent();
                    } else
                        Toast.makeText(CrearProducto.this, "No se pudo, intente de nuevo", Toast.LENGTH_SHORT).show();
                } else if (items[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galeriaIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccione Archivo"), SELECT_FILE);
    }

    private void camaraIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode , @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode){
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(eleccionusuario.equals("Tomar foto"))
                        camaraIntent();
                    else if(eleccionusuario.equals("Galería"))
                        galeriaIntent();
                } else {
                    Toast.makeText(this, "Denegado", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK /*"Mirar esta linea posiblemente ayuda mas" && data != null && data.getData() != null*/){
            if(requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data){
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + "jpg");

        FileOutputStream f;
        try {
            destination.createNewFile();
            f = new FileOutputStream(destination);
            f.write(bytes.toByteArray());
            f.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        imagen.setImageBitmap(thumbnail);
        mImagenUri = data.getData();
    }

    private void onSelectFromGalleryResult(Intent data){
        Bitmap b = null;

        if(data != null){
            try {
                b = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        imagen.setImageBitmap(b);
        mImagenUri = data.getData();
    }
    
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode) {
            case PICTURE_FROM_CAMERA

                if(resultCode == Activity.RESULT_OK){
                    String result = data.toUri(0);
                    Toast.makeText(this, "Result " +result, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "No seleccionó la imagen, intente de nuevo", Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
        }
    }*/



    private void guardarProducto(){
        String nombre = edtNombre.getText().toString();
        String descripcion = edtDescripcion.getText().toString();
        String cantidad = edtCantidad.getText().toString();
        String cantidadP = precioCantidad.getText().toString();
        final Map<String, Object> producto = new HashMap<>();
        producto.put("nombre",nombre);
        producto.put("descripcion",descripcion);
        producto.put("cantidad",cantidad);
        producto.put("precioCantidad",cantidadP);
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        producto.put("id",idCampesino);
        db.collection("Producto").document().set(producto);
        Toast.makeText(CrearProducto.this, "Producto Guardado", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(CrearProducto.this, VistaCampesino.class);
        startActivity(i);


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

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nombre = edtNombre.getText().toString();
            String descripcion = edtDescripcion.getText().toString();
            String cantidad = edtCantidad.getText().toString();
            String cantidadP = precioCantidad.getText().toString();
            guardar.setEnabled(!nombre.isEmpty() && !descripcion.isEmpty() && !cantidad.isEmpty() && !cantidadP.isEmpty());
            if(!nombre.isEmpty() && !descripcion.isEmpty() && !cantidad.isEmpty() && !cantidadP.isEmpty()){
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
    };
}
