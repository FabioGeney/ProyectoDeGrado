package com.proyecto.marketdillo;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MercadilloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MercadilloFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewFav;
    private RecyclerView mRecyclerViewTipo;
    private RecyclerView.Adapter categoriaAdapter;
    private RecyclerView.Adapter favAdapter;
    private List<Categoria> categorias;
    private RecyclerView.LayoutManager layoutManagerTipo;
    private RecyclerView.LayoutManager layoutManagerFav;
    private RecyclerView.Adapter mercadilloAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Mercadillo> mercadillos1;
    private List<Mercadillo> favoritos;
    private ProgressBar progressBar;
    private DividerItemDecoration dividerItemDecoration;
    private Realm realm;
    private CardView cardView;

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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mercadillo, container, false);

        cardView = root.findViewById(R.id.card);

        dividerItemDecoration = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManagerFav = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        //recycler fav
        mRecyclerViewFav = root.findViewById(R.id.recyclerFav);
        //recycler mercadillos
        mRecyclerView = (RecyclerView) root.findViewById(R.id.mercadillo_Recycler);
        //recycler de tipo
        mRecyclerViewTipo = root.findViewById(R.id.recyclerTipo);
        layoutManagerTipo = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        //listCategorias
        categorias = getCategorias();
        //Adapter categorias
        categoriaAdapter = new CategoriaAdapter(categorias, R.layout.list_categoria, new CategoriaAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Categoria categoria, int posicion) {
                Intent intent = new Intent(getContext(), VistaCategoria.class);
                intent.putExtra("categoria", categoria.getNombre());
                startActivity(intent);
            }
        });
        mRecyclerViewTipo.setLayoutManager(layoutManagerTipo);
        mRecyclerViewTipo.setAdapter(categoriaAdapter);

        //List de mercadillos
        mercadillos1 = getMercadillos();

        //list favoritos
        realm = Realm.getDefaultInstance();
        favoritos = realm.where(Mercadillo.class).findAll();

        if(favoritos.size()!=0){
            //adapter Favoritos
            favAdapter = new MercadilloAdapter(favoritos, R.layout.list_item_mercadillo, new MercadilloAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(Mercadillo mercadillo, int posicion) {
                    Intent intent = new Intent(getContext(), VistaProductosMercadillo.class);
                    //crea el singleotnMercadillo para almacenar en memoria los detos del mercadillo seleccionado por el usuario
                    SingletonMercadillo singletonMercadillo = SingletonMercadillo.getInstance();
                    //en caso de que ya haya un mercadillo almacenado será reemplazado por otro seleccionado por el usuario
                    singletonMercadillo.setMercadillo(mercadillo);
                    Toast.makeText(getContext(), mercadillo.getId(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });

            mRecyclerViewFav.setLayoutManager(layoutManagerFav);
            mRecyclerViewFav.setAdapter(favAdapter);
        }else {
            cardView.setVisibility(View.GONE);
        }






        return root;
    }

    private List<Categoria> getCategorias() {
        ArrayList<Categoria> tipos = new ArrayList<>();

        tipos.add(new Categoria("Frutas", R.drawable.frutas));
        tipos.add(new Categoria("Vegetales", R.drawable.vegetales));
        tipos.add(new Categoria("Legumbres", R.drawable.legumbres));
        return tipos;
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
                            mercadilloAdapter = new MercadilloAdapter(mercadillos1, R.layout.list_item_mercadillo, new MercadilloAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(Mercadillo mercadillo, int posicion) {
                                    Intent intent = new Intent(getContext(), VistaProductosMercadillo.class);
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
                            mRecyclerView.addItemDecoration(dividerItemDecoration);


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return mercadillos;
    }

}
