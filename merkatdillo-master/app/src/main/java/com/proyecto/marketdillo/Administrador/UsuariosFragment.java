package com.proyecto.marketdillo.Administrador;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.proyecto.marketdillo.Mercadillo;
import com.proyecto.marketdillo.MercadilloAdapter;
import com.proyecto.marketdillo.R;
import com.proyecto.marketdillo.SingletonMercadillo;
import com.proyecto.marketdillo.Usuario;
import com.proyecto.marketdillo.VistaProductosMercadillo;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UsuariosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UsuariosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsuariosFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter usuariosAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Usuario> usuarios;


    public UsuariosFragment() {
        // Required empty public constructor
    }


    public static UsuariosFragment newInstance(/*parámetros*/) {
        UsuariosFragment fragment = new UsuariosFragment();
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

        View root = inflater.inflate(R.layout.fragment_usuarios, container, false);
        usuarios = getMercadillos();

        layoutManager = new LinearLayoutManager(getContext());
        // Instancia del ListView.
        mRecyclerView = (RecyclerView) root.findViewById(R.id.usuario_Recycler);

        return root;
    }

    public List<Usuario> getMercadillos() {
        final ArrayList<Usuario> usersResult = new ArrayList<>();


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Consumidor")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Usuario usuario = document.toObject(Usuario.class);
                                usuario.setTipoUsuario("Comprador");
                                usersResult.add(usuario);

                            }
                            // Inicializar el adaptador con la fuente de datos.
                            usuariosAdapter = new UsuarioAdapter(usuarios, R.layout.list_user_admin, new UsuarioAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(Usuario usuario, int posicion) {

                                }
                            });
                            //Relacionando la lista con el adaptador
                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setAdapter(usuariosAdapter);


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        db.collection("Campesino")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Usuario usuario = document.toObject(Usuario.class);
                                usuario.setTipoUsuario("Campesino");
                                usersResult.add(usuario);

                            }
                            // Inicializar el adaptador con la fuente de datos.
                            usuariosAdapter = new UsuarioAdapter(usuarios, R.layout.list_user_admin, new UsuarioAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(Usuario usuario, int posicion) {

                                }
                            });
                            //Relacionando la lista con el adaptador
                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setAdapter(usuariosAdapter);


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return usersResult;
    }

}
