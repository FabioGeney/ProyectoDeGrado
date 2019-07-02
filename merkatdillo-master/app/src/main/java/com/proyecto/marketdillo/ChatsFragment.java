package com.proyecto.marketdillo;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ChatsAdapter chatsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public ChatsFragment() {
        // Required empty public constructor
    }
    public static ChatsFragment newInstance(/*parámetros*/) {
        ChatsFragment fragment = new ChatsFragment();
        // Setup parámetros
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Gets parámetros
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chats, container, false);
        // Inflate the layout for this fragment
        layoutManager = new LinearLayoutManager(getContext());
        // Instancia del ListView.
        mRecyclerView = root.findViewById(R.id.mercadillo_Recycler);

        final SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(singletonUsuario.getUsuario().getId());

        chatsAdapter = new ChatsAdapter(getContext(), new ChatsAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Contacto contacto, int posicion) {
                Intent intent = new Intent(getContext(), Chat.class);
                intent.putExtra("nombreDestinatario", contacto.getNombre());
                intent.putExtra("idDestinatario", contacto.getId());
                startActivity(intent);
            }
        });

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(chatsAdapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               consultaDatos(dataSnapshot.getKey(), singletonUsuario.getUsuario().getId());

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
        return root;
    }

    private void consultaDatos(String key, String id){
        final DatabaseReference databaseReference = firebaseDatabase.getReference(id).child(key).child("Datos");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id = dataSnapshot.child("id").getValue(String.class);
                String imagen = dataSnapshot.child("imagen").getValue(String.class);
                String nombre = dataSnapshot.child("nombre").getValue(String.class);
                String telefono = dataSnapshot.child("telefono").getValue(String.class);
                String ultimoMensaje = dataSnapshot.child("ultimoMensaje").getValue(String.class);
                Contacto contacto = new Contacto(id, imagen, nombre, telefono, ultimoMensaje);
                Toast.makeText(getContext(), nombre, Toast.LENGTH_SHORT).show();

                chatsAdapter.agregarMensaje(contacto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
