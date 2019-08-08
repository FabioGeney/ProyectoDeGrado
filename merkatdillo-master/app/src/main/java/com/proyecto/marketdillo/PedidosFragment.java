package com.proyecto.marketdillo;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PedidosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PedidosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PedidosFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter pedidosAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Pedidos> pedidos;
    private Intent intent;
    private Realm realm = Realm.getDefaultInstance();

    public PedidosFragment() {
        // Required empty public constructor
    }


    public static PedidosFragment newInstance(/*parámetros*/) {
        PedidosFragment fragment = new PedidosFragment();
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

        View root = inflater.inflate(R.layout.fragment_pedidos, container, false);

        pedidos = getPedidos();
        layoutManager = new LinearLayoutManager(getContext());
        // Instancia del RecyclerView.
        mRecyclerView = (RecyclerView) root.findViewById(R.id.pedidos_recycler);

        return root;
    }

    private ArrayList<Pedidos> getPedidos(){
        final ArrayList<Pedidos> pedidosRequest = new ArrayList<>();
        //llama al singletonUsuario para obtener los datos del usuario
        SingletonUsuario singletonUsuario = SingletonUsuario.getInstance();
        //almacena en el objeo usuario los datos del singleton
        Usuario usuario = singletonUsuario.getUsuario();
        //obtiene la id del usuario
        String id = usuario.getId();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query dbUser = db.collection("Consumidor").document(usuario.getId()).collection("Pedidos").orderBy("fecha");
        intent = new Intent(getContext(), EstadoPedido.class);
        if(usuario.getTipoUsuario().equals("campesino")){
            dbUser = db.collection("Campesino").document(usuario.getId()).collection("Pedidos").orderBy("fecha");
            intent = new Intent(getContext(), EstadoPedidoCampesino.class);
        }

        dbUser.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String estado = document.getData().get("estado").toString();

                                if(estado.equals("Finalizado")){

                                }else {
                                    Pedidos pedido = document.toObject(Pedidos.class);
                                    pedido.setIdDocument(document.getId());
                                    pedidosRequest.add(pedido);
                                }

                            }
                            // Inicializar el adaptador con la fuente de datos.
                            pedidosAdapter = new PedidosAdapter(pedidos, R.layout.list_item_pedido, new PedidosAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(Pedidos pedidos, int posicion) {
                                    SingletonPedido singletonPedido = SingletonPedido.getInstance();
                                    singletonPedido.setPedido(pedidos);
                                    startActivity(intent);

                                }
                            });
                            //Relacionando la lista con el adaptador
                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setAdapter(pedidosAdapter);


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return pedidosRequest;
    }



}
