package com.proyecto.marketdillo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
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
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    private  Uri hImagenUri;
    private StorageReference hStorageRef;
    private DatabaseReference hDatabaseRef;
    private String picture;
    private String photoPath;
    private Intent intentL;

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
        hDatabaseRef = FirebaseDatabase.getInstance().getReference("Subidas");

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
        builder.setTitle("Agregar Foto");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(CrearProducto.this);

                if (items[item].equals("Tomar foto")) {
                    eleccionusuario = "Tomar foto";

                    if(result) {
                        camaraIntent();
                    } else
                        Toast.makeText(CrearProducto.this, "Intente de nuevo", Toast.LENGTH_SHORT).show();
                } else if (items[item].equals("Galería")){
                    eleccionusuario = "Galería";

                    if(result) {
                        galeriaIntent();
                    } else
                        Toast.makeText(CrearProducto.this, "Intente de nuevo", Toast.LENGTH_SHORT).show();
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
                imagen.setImageBitmap(thumbnail);
                hImagenUri = data.getData();
            }
        }
    }

    private void onCaptureImageResult(Intent data){

        /*try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Error occurred while creating the File
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {

            String authorities = getApplicationContext().getPackageName() + ".fileprovider";
            Uri imageUri = FileProvider.getUriForFile(this, authorities, photoFile);
            intentL.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        }

        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }*/
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

    private void galleryAddPic(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
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
        hImagenUri = data.getData();
    }

    private String getFileExtension(Uri uri){
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(uri));
    }

    private void uploadFile(){
        if(hImagenUri != null){

            StorageReference fileReference = hStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(hImagenUri));

            fileReference.putFile(hImagenUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(CrearProducto.this, "Imagen cargada", Toast.LENGTH_SHORT).show();
                    picture = taskSnapshot.getStorage().getDownloadUrl().toString();
                    /*String uploadId = hDatabaseRef.push().getKey();
                    hDatabaseRef.child(uploadId).setValue(picture);*/
                }
            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CrearProducto.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });

        } else {

        }
    }

    private void guardarProducto(){

        uploadFile();
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
