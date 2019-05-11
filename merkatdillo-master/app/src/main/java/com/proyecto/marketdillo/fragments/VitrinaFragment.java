package com.proyecto.marketdillo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.proyecto.marketdillo.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class VitrinaFragment extends Fragment {


    public VitrinaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vitrina, container, false);
        RecyclerView imagenesRecycler = (RecyclerView) view.findViewById(R.id.imagenRecycler);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        imagenesRecycler.setItemAnimator(new DefaultItemAnimator());
        imagenesRecycler.setLayoutManager(gridLayoutManager);

        ImagenAdapter imagenAdapter = new ImagenAdapter(getImagenes(), R.layout.cardview_ima, getActivity());

        imagenesRecycler.setAdapter(imagenAdapter);

        return view;
    }

    public ArrayList<ImagenCard> getImagenes(){
        ArrayList<ImagenCard> imagenes = new ArrayList<>();
        imagenes.add(new ImagenCard("https://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Limon", "2000", "Finca Fabio"));
        imagenes.add(new ImagenCard("https://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Sandia", "5000", "Finca Maria"));
        imagenes.add(new ImagenCard("https://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Papaya", "3000", "Finca Pedro"));
        imagenes.add(new ImagenCard("https://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Limon", "2000", "Finca Fabio"));
        imagenes.add(new ImagenCard("https://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Sandia", "5000", "Finca Maria"));
        imagenes.add(new ImagenCard("https://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Papaya", "3000", "Finca Pedro"));
        imagenes.add(new ImagenCard("https://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Limon", "2000", "Finca Fabio"));
        imagenes.add(new ImagenCard("https://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Sandia", "5000", "Finca Maria"));
        imagenes.add(new ImagenCard("https://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Papaya", "3000", "Finca Pedro"));
        imagenes.add(new ImagenCard("https://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Limon", "2000", "Finca Fabio"));
        imagenes.add(new ImagenCard("https://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Sandia", "5000", "Finca Maria"));
        imagenes.add(new ImagenCard("https://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Papaya", "3000", "Finca Pedro"));
        imagenes.add(new ImagenCard("https://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Limon", "2000", "Finca Fabio"));
        imagenes.add(new ImagenCard("https://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Sandia", "5000", "Finca Maria"));
        imagenes.add(new ImagenCard("https://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Papaya", "3000", "Finca Pedro"));
        return imagenes;
    }

}
