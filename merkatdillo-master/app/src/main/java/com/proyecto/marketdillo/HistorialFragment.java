package com.proyecto.marketdillo;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistorialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistorialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistorialFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter historialAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Pedidos> historialList;



    public HistorialFragment() {
        // Required empty public constructor
    }


    public static HistorialFragment newInstance(/*parámetros*/) {
        HistorialFragment fragment = new HistorialFragment();
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

        View root = inflater.inflate(R.layout.fragment_historial, container, false);

        historialList = getHistorial();
        layoutManager = new LinearLayoutManager(getContext());
        // Instancia del ListView.
        mRecyclerView = (RecyclerView) root.findViewById(R.id.historial_recycler);




        return root;

    }
    private ArrayList<Pedidos> getHistorial() {
        final ArrayList<Pedidos> pedidos = new ArrayList<>();
        //llama al singletonUsuario para obtener los datos del usuario
        SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
        //almacena en el objeo usuario los datos del singleton
        Usuario usuario = singletonUsuario.getUsuario();
        //obtiene la id del usuario
        String id = usuario.getId();



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query dbUser = db.collection("Pedidos").whereEqualTo("idConsumidor", id);

        if(usuario.getTipoUsuario().equals("campesino")){
            dbUser = db.collection("Pedidos").whereEqualTo("idCampesino", id);
        }

        dbUser.whereEqualTo("estado", "Finalizado").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Pedidos pedido = document.toObject(Pedidos.class);
                                pedido.setIdDocument(document.getId());
                                pedidos.add(pedido);
                            }
                            // Inicializar el adaptador con la fuente de datos.
                            historialAdapter = new HistorialAdapter(historialList, R.layout.list_item_historial, new HistorialAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(Pedidos pedidos, int posicion) {
                                    ArrayList<Producto> productos = new ArrayList<>(pedidos.getProductos());
                                    SingletonCanasta singletonCanasta = SingletonCanasta.getInstance();
                                    singletonCanasta.setHistorial(pedidos.getProductos());
                                    Intent intent = new Intent(getContext(), VistaDetalles.class);
                                    intent.putExtra("pedido", pedidos);
                                    intent.putExtra("visible", "si");
                                    startActivity(intent);
                                }
                            });

                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setAdapter(historialAdapter);


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return pedidos;
    }


}
