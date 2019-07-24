package com.proyecto.marketdillo.Administrador;

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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.proyecto.marketdillo.Mercadillo;
import com.proyecto.marketdillo.MercadilloAdapter;
import com.proyecto.marketdillo.R;
import com.proyecto.marketdillo.SingletonMercadillo;
import com.proyecto.marketdillo.VistaProductosMercadillo;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.proyecto.marketdillo.MercadilloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MercadillosAdminFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mercadilloAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Mercadillo> mercadillos1;


    public MercadillosAdminFragment() {
        // Required empty public constructor
    }


    public static com.proyecto.marketdillo.MercadilloFragment newInstance(/*parámetros*/) {
        com.proyecto.marketdillo.MercadilloFragment fragment = new com.proyecto.marketdillo.MercadilloFragment();
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

        View root = inflater.inflate(R.layout.fragment_mercadillo, container, false);
        mercadillos1 = getMercadillos();

        layoutManager = new LinearLayoutManager(getContext());
        // Instancia del ListView.
        mRecyclerView = (RecyclerView) root.findViewById(R.id.mercadillo_Recycler);

        return root;
    }

    public List<Mercadillo> getMercadillos() {
        final ArrayList<Mercadillo> mercadillos = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Mercadillo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Mercadillo mercadillo = document.toObject(Mercadillo.class);
                                mercadillo.setId(document.getId());
                                mercadillo.setImagen(R.mipmap.ic_merca_image);
                                mercadillos.add(mercadillo);

                            }
                            // Inicializar el adaptador con la fuente de datos.
                            mercadilloAdapter = new MercadillosAdminAdapter(mercadillos1, R.layout.list_admin_mercados, new MercadillosAdminAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(Mercadillo mercadillo, int posicion) {
                                    Intent intent = new Intent(getContext(), VistaMercadilloAdmin.class);
                                    //crea el singleotnMercadillo para almacenar en memoria los detos del mercadillo seleccionado por el usuario
                                    SingletonMercadillo singletonMercadillo = SingletonMercadillo.getInstance();
                                    //en caso de que ya haya un mercadillo almacenado será reemplazado por otro seleccionado por el usuario
                                    singletonMercadillo.setMercadillo(mercadillo);
                                    Toast.makeText(getContext(), mercadillo.getId(), Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                            });
                            //Relacionando la lista con el adaptador
                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setAdapter(mercadilloAdapter);


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return mercadillos;
    }

}