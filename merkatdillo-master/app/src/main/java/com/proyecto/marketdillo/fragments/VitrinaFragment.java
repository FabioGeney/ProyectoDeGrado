package com.proyecto.marketdillo.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import com.proyecto.marketdillo.Producto;
import com.proyecto.marketdillo.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class VitrinaFragment extends Fragment {

    private List<ImagenCard> imagenes1;
    private ImagenAdapter imagenAdapter;
    private RecyclerView imagenesRecycler;
    private GridLayoutManager gridLayoutManager;


    public VitrinaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vitrina, container, false);
        imagenesRecycler = (RecyclerView) view.findViewById(R.id.imagenRecycler);
        imagenes1 = getImagenes();
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        imagenesRecycler.setItemAnimator(new DefaultItemAnimator());



        /*ImagenAdapter imagenAdapter = new ImagenAdapter(getImagenes(), R.layout.cardview_ima, getActivity());

        imagenesRecycler.setAdapter(imagenAdapter);*/

        return view;
    }

    public ArrayList<ImagenCard> getImagenes(){
        final ArrayList<ImagenCard> imagenes = new ArrayList<>();
        //imagenes.add(new ImagenCard(R.drawable.limone, "Limon", "2000", "Finca Fabio"));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Producto").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){

                        ImagenCard imagen = new ImagenCard(document.getData().get("id").toString(), document.getData().get("nombre").toString(),
                                document.getData().get("descripcion").toString(), ""+document.getData().get("precioCantidad"), document.getData().get("imagen").toString());
                        imagenes.add(imagen);
                    }

                    imagenAdapter = new ImagenAdapter(imagenes1, R.layout.cardview_ima, getActivity(), new ImagenAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(ImagenCard imagen, int posicion) {
                            Intent i = new Intent(getContext(), ImagenDetalle.class);
                            i.putExtra("imagen", imagen);
                            startActivity(i);
                        }
                    });
                    imagenesRecycler.setAdapter(imagenAdapter);
                    imagenesRecycler.setLayoutManager(gridLayoutManager);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        return imagenes;
    }

}
