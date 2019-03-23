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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MercadilloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MercadilloFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mercadilloAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Mercadillo> mercadillos1;




    public MercadilloFragment() {
        // Required empty public constructor
    }


    public static MercadilloFragment newInstance(/*parámetros*/) {
        MercadilloFragment fragment = new MercadilloFragment();
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
        mercadillos.add(new Mercadillo("Mercadillo de frutas", "40 min", "4.5", "4500", R.mipmap.ic_merca_image));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Mercadillo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Mercadillo mercadillo = document.toObject(Mercadillo.class);

                                mercadillo.setImagen(R.mipmap.ic_merca_image);
                                mercadillos.add(mercadillo);

                            }
                            // Inicializar el adaptador con la fuente de datos.

                            mercadilloAdapter = new MercadilloAdapter(mercadillos1, R.layout.list_item_mercadillo, new MercadilloAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(Mercadillo mercadillo, int posicion) {
                                    Toast.makeText(getContext(), mercadillo.getNombre(), Toast.LENGTH_SHORT).show();
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
