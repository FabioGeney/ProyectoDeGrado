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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoritosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoritosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritosFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mercadilloAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RealmResults<Mercadillo> mercadillos;
    private Realm realm;


    public FavoritosFragment() {
        // Required empty public constructor
    }


    public static FavoritosFragment newInstance(/*parámetros*/) {
        FavoritosFragment fragment = new FavoritosFragment();
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

        View root = inflater.inflate(R.layout.fragment_favoritos, container, false);
        realm = Realm.getDefaultInstance();
        mercadillos = realm.where(Mercadillo.class).findAll();

        layoutManager = new LinearLayoutManager(getContext());
        // Instancia del ListView.
        mRecyclerView = (RecyclerView) root.findViewById(R.id.mercadillo_Recycler);

        mercadilloAdapter = new MercadilloAdapter(mercadillos, R.layout.list_item_mercadillo, new MercadilloAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Mercadillo mercadillo, int posicion) {
                Intent intent = new Intent(getContext(), VistaProductosMercadillo.class);
                //crea el singleotnMercadillo para almacenar en memoria los detos del mercadillo seleccionado por el usuario
                SingletonMercadillo singletonMercadillo = SingletonMercadillo.getInstance();
                //en caso de que ya haya un mercadillo almacenado será reemplazado por otro seleccionado por el usuario
                singletonMercadillo.setMercadillo(mercadillo);

                startActivity(intent);
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mercadilloAdapter);



        return root;
    }


}
