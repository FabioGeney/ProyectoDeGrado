package com.proyecto.marketdillo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Chat extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MensajesAdapter mensajelAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageButton enviar;
    private EditText texto;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceDestinatario;
    private DatabaseReference databaseReferenceRemitente;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //obtiene datos del intent
        final String nombreDestinatario = getIntent().getStringExtra("nombreDestinatario");
        final String idDestinatario = getIntent().getStringExtra("idDestinatario");

        //cambia titulo al toolbar
        this.setTitle(nombreDestinatario);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //inicializa variables
        enviar = findViewById(R.id.enviar);
        texto = findViewById(R.id.texto);
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        final String useriD = firebaseUser.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceDestinatario = firebaseDatabase.getReference(idDestinatario).child(useriD).child("Mensajes");
        databaseReferenceRemitente = firebaseDatabase.getReference(useriD).child(idDestinatario).child("Mensajes");






        layoutManager = new LinearLayoutManager(this);
        // Instancia del recyclertView.
        mRecyclerView = findViewById(R.id.mesaje_recycler);

        mensajelAdapter = new MensajesAdapter(this, new MensajesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Mensaje mensaje, int posicion) {

            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mensajelAdapter);


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(texto!=null){
                    Calendar calendario = Calendar.getInstance();
                    int hora, minutos, segundos;
                    hora = calendario.get(Calendar.HOUR_OF_DAY);
                    minutos = calendario.get(Calendar.MINUTE);
                    sendInfoChat(idDestinatario, texto.getText().toString(), nombreDestinatario);
                    databaseReferenceDestinatario.push().setValue(new Mensaje(useriD, texto.getText().toString(), hora +":" + minutos));
                    databaseReferenceRemitente.push().setValue(new Mensaje(useriD, texto.getText().toString(), hora +":" + minutos));
                    texto.setText(null);
                }
            }
        });

        //Mueve el scroll de la lista al ultimo elemento
        mensajelAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                mRecyclerView.scrollToPosition(mensajelAdapter.getItemCount()-1);
            }
        });

        //Recibe y agrega mensajes del chat en tiempo real desde la base de datos
        databaseReferenceRemitente.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Mensaje mensaje = dataSnapshot.getValue(Mensaje.class);
                mensajelAdapter.agregarMensaje(mensaje);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private ArrayList<Mensaje> getMensajes(){
        ArrayList<Mensaje> mensajes = new ArrayList<>();

        return mensajes;
    }

    //Este metodo almacena la informacion del usuario en el objeto contacto y la envia a la sala de chat del Destinatario
    private void sendInfoChat(String idDestinatario, String texto, String nombreDestinatario) {
        SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
        Usuario usuario = singletonUsuario.getUsuario();
        HashMap<String, Object> resultDestino = new HashMap<>();
        HashMap<String, Object> resultRemitente = new HashMap<>();
        resultDestino.put("nombre", usuario.getNombre());
        resultDestino.put("id", usuario.getId());
        resultDestino.put("imagen", "1231");
        resultDestino.put("ultimoMensaje", texto);

        resultRemitente.put("nombre", nombreDestinatario);
        resultRemitente.put("id", idDestinatario);
        resultRemitente.put("imagen", "1231");

        resultRemitente.put("ultimoMensaje", texto);


        firebaseDatabase.getReference(idDestinatario).child(usuario.getId()).child("Datos").updateChildren(resultDestino);
        firebaseDatabase.getReference(usuario.getId()).child(idDestinatario).child("Datos").updateChildren(resultRemitente);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
