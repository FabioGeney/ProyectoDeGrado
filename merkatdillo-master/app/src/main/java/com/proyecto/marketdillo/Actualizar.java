package com.proyecto.marketdillo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Actualizar extends AppCompatActivity {

    private EditText edtNombre1;
    private EditText edtDescripcion1;
    private EditText edtCantidad1;
    private EditText precioCantidad1;
    private ImageView imagen1;
    private Button editar;
    private Button cancelar;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "Actualizar";
    private String idCampesino;
    private File f;
    private String eleccionusuario;
    private int REQUEST_CAMERA = 0;
    private int SELECT_FILE = 1;
    private Uri hImagenUri;
    private StorageReference hStorageRef;
    private DatabaseReference hDatabaseRef;
    private String picture;
    private String photoPath;
    private Intent intentL;
    private Producto producto;

    private final int PICTURE_FROM_CAMERA = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtNombre1 = findViewById(R.id.edtnombre1);
        edtDescripcion1 = findViewById(R.id.edtdescripcion1);
        edtCantidad1 = findViewById(R.id.edtcantidad1);
        precioCantidad1 = findViewById(R.id.edtprecio1);
        editar = findViewById(R.id.btneditar);
        cancelar = findViewById(R.id.btncancelar);
        imagen1 = findViewById(R.id.imagen1);

        producto = (Producto) getIntent().getSerializableExtra("produ");
        String titulo = producto.getNombre();
        this.setTitle("Editar: "+ titulo);
        edtNombre1.setText(producto.getNombre());
        edtDescripcion1.setText(producto.getDescripcion());
        edtCantidad1.setText(producto.getCantidad());
        precioCantidad1.setText("" + producto.getPrecioCantidad());
        String img = producto.getImagen();
        Picasso.with(this).load(img).fit().into(imagen1);

        hStorageRef = FirebaseStorage.getInstance().getReference("Subidas");

        initialize();

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        imagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarImagen();
            }
        });

        edtNombre1.addTextChangedListener(loginTextWatcher);
        edtDescripcion1.addTextChangedListener(loginTextWatcher);
        edtCantidad1.addTextChangedListener(loginTextWatcher);
        precioCantidad1.addTextChangedListener(loginTextWatcher);

    }

    private void seleccionarImagen(){

        final CharSequence[] items = {"Tomar foto", "Galería", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Agregar Foto");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Actualizar.this);

                if (items[item].equals("Tomar foto")) {
                    eleccionusuario = "Tomar foto";

                    if(result) {
                        camaraIntent();
                    } else
                        Toast.makeText(Actualizar.this, "Intente de nuevo", Toast.LENGTH_SHORT).show();
                } else if (items[item].equals("Galería")){
                    eleccionusuario = "Galería";

                    if(result) {
                        galeriaIntent();
                    } else
                        Toast.makeText(Actualizar.this, "Intente de nuevo", Toast.LENGTH_SHORT).show();
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
        requestStoragePermission();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                String authorities = getApplicationContext().getPackageName() + ".fileprovider";
                Uri imageUri = FileProvider.getUriForFile(this, authorities, photoFile);
                intent.putExtra("data", imageUri);
                startActivityForResult(intent, REQUEST_CAMERA);
                //hImagenUri = imageUri;
            }
        }
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
    //Permiso Dexter para solicitar un multiple permiso
    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            }else if (requestCode == REQUEST_CAMERA) {
                Bundle extras = data.getExtras();
                Bitmap thumbnail = (Bitmap) extras.get("data");
                imagen1.setImageBitmap(thumbnail);
                hImagenUri = data.getData();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File ima = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        photoPath = ima.getAbsolutePath();
        return ima;
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
        imagen1.setImageBitmap(b);
        hImagenUri = data.getData();
    }

    private String getFileExtension(Uri uri){
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(uri));
    }

    private void uploadFile(){
        if(hImagenUri != null) {

            final StorageReference fileReference = hStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(hImagenUri));

            fileReference.putFile(hImagenUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Actualizar.this, "Imagen cargada", Toast.LENGTH_SHORT).show();

                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            picture = uri.toString();
                            editarProducto();
                        }
                    });
                }
            });
        }
        else {

        }
    }

    private void editarProducto(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DocumentReference editarProducto = db.collection("Mercadillo").document(firebaseUser.getUid()).collection("Productos").document(producto.getIdDocument());

        String nombre = edtNombre1.getText().toString();
        String descripcion = edtDescripcion1.getText().toString();
        String cantidad = edtCantidad1.getText().toString();
        String cantidadP = precioCantidad1.getText().toString();
        final Map<String, Object> upda = new HashMap<>();
        upda.put("nombre", nombre);
        upda.put("descripcion", descripcion);
        upda.put("precioCantidad", Integer.parseInt(cantidadP));
        upda.put("cantidad", cantidad);
        upda.put("imagen", picture);
        editarProducto.update(upda);
        Toast.makeText(Actualizar.this, "Producto Actualizado", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Actualizar.this, VistaCampesino.class);
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
            String nombre = edtNombre1.getText().toString();
            String descripcion = edtDescripcion1.getText().toString();
            String cantidad = edtCantidad1.getText().toString();
            String cantidadP = precioCantidad1.getText().toString();
            editar.setEnabled(!nombre.isEmpty() && !descripcion.isEmpty() && !cantidad.isEmpty() && !cantidadP.isEmpty());
            if(!nombre.isEmpty() && !descripcion.isEmpty() && !cantidad.isEmpty() && !cantidadP.isEmpty()){
                editar.setBackgroundColor(getResources().getColor(R.color.colorPrimary2));
            }else {
                editar.setBackgroundColor(getResources().getColor(R.color.colorDivider));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!editar.isEnabled()){
                editar.setBackgroundColor(getResources().getColor(R.color.colorDivider));
            }else {
                editar.setBackgroundColor(getResources().getColor(R.color.colorPrimary2));
            }
        }
    };
}