package com.proyecto.marketdillo;

import android.content.Context;
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
import android.widget.ListView;
import android.widget.SimpleCursorTreeAdapter;
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
 * Activities that contain this fragment must implement the
 * {@link PtsCampesinoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PtsCampesinoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PtsCampesinoFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter ptsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Producto> productos;
    private String id;



    public PtsCampesinoFragment() {

        // Required empty public constructor
    }

    public static PtsCampesinoFragment newInstance(/*parámetros*/) {
        PtsCampesinoFragment fragment = new PtsCampesinoFragment();
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

        View root = inflater.inflate(R.layout.fragment_pts_campesino, container, false);
        // toma el objeto Usuario que ha sido mandados por VistaCampesino para consultar los productos de dicho usuario
        Usuario usuario = (Usuario) getArguments().getSerializable("usuario");
        id = usuario.getId();
        productos = getProducto();
        // Instancia del ListView.
        mRecyclerView =  root.findViewById(R.id.list_Recycler);
        layoutManager = new LinearLayoutManager(getContext());

        //Relacionando la lista con el adaptador
        mRecyclerView.setAdapter(ptsAdapter);
        mRecyclerView.setLayoutManager(layoutManager);

        return root;
    }
    public List<Producto> getProducto() {
        final ArrayList<Producto> p = new ArrayList<>();
        p.add(new Producto("Manzana", "la descripcion es muy grande ", "$3000 por lb", R.mipmap.ic_fruit));
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Producto").whereEqualTo("id",id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Producto producto = document.toObject(Producto.class);
                                producto.setImagen(R.mipmap.ic_fruit);
                                p.add(producto);

                            }
                            // Inicializar el adaptador con la fuente de datos.
                            ptsAdapter = new PtsCampesinoAdapter(productos, R.layout.list_item_campesino_prdto, new PtsCampesinoAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(Producto producto, int posicion) {
                                    Toast.makeText(getContext(), producto.getId(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            //Relacionando la lista con el adaptador
                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setAdapter(ptsAdapter);


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return p;
    }

}
